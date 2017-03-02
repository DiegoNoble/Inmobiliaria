package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.Renderers.MeDateCellRenderer;
import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.beans.CCPropietario;
import com.dnsoft.inmobiliaria.beans.Caja;
import com.dnsoft.inmobiliaria.beans.PagoRecibo;
import com.dnsoft.inmobiliaria.beans.PagoPropietario;
import com.dnsoft.inmobiliaria.beans.Situacion;
import com.dnsoft.inmobiliaria.daos.IRecibosDAO;
import com.dnsoft.inmobiliaria.daos.ICCPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.ICajaDAO;
import com.dnsoft.inmobiliaria.daos.IPagoReciboDAO;
import com.dnsoft.inmobiliaria.daos.IPagoPropietarioDAO;
import com.dnsoft.inmobiliaria.models.PagoAlquilerTableModel;
import com.dnsoft.inmobiliaria.models.PagoPropietarioTableModel;
import com.dnsoft.inmobiliaria.utils.ActualizaSaldos;
import com.dnsoft.inmobiliaria.utils.ButtonColumnBorrar;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Diego Noble
 */
public final class PagosDetalleDlg extends javax.swing.JDialog {

    IPagoReciboDAO pagoAlquilerDAO;
    IPagoPropietarioDAO pagoPropietarioDAO;
    ICCPropietarioDAO cCPropietarioDAO;
    ICajaDAO cajaDAO;
    IRecibosDAO recibosDAO;
    ApplicationContext applicationContext;
    PagoRecibo pagoRecibo;
    List<PagoRecibo> listPagoAlquiler;
    List<PagoPropietario> listPagoPropietarios;
    PagoAlquilerTableModel tableModel;
    PagoPropietarioTableModel tableModelPagoPropietario;
    ListSelectionModel listModel;
    Recibo alquiler;

    public PagosDetalleDlg(java.awt.Frame parent, boolean modal, Recibo alquiler) {
        super(parent, modal);
        initComponents();
        this.alquiler = alquiler;
        applicationContext = applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        pagoAlquilerDAO = applicationContext.getBean(IPagoReciboDAO.class);
        pagoPropietarioDAO = applicationContext.getBean(IPagoPropietarioDAO.class);
        cCPropietarioDAO = applicationContext.getBean(ICCPropietarioDAO.class);
        recibosDAO = applicationContext.getBean(IRecibosDAO.class);
        cajaDAO = applicationContext.getBean(ICajaDAO.class);
        setLocationRelativeTo(null);
        configuraTblPagoAlquiler();
        configuraTblPagoPropietario();
    }

    void configuraTblPagoAlquiler() {
        ((DefaultTableCellRenderer) tblPagoAlquiler.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listPagoAlquiler = new ArrayList<>();
        listPagoAlquiler.addAll(pagoAlquilerDAO.findByRecibo(alquiler));

        tableModel = new PagoAlquilerTableModel(listPagoAlquiler);
        tblPagoAlquiler.setModel(tableModel);
        tblPagoAlquiler.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());

        tblPagoAlquiler.setRowHeight(25);
        new ButtonColumnBorrar(tblPagoAlquiler, 5) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                eliminarSeleccionado();
            }
        };

        listModel = tblPagoAlquiler.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tblPagoAlquiler.getSelectedRow() != -1) {
                    pagoRecibo = listPagoAlquiler.get(tblPagoAlquiler.getSelectedRow());
                    listPagoPropietarios.clear();
                    listPagoPropietarios.addAll(pagoPropietarioDAO.findByPagoAlquiler(pagoRecibo));
                    tableModelPagoPropietario.fireTableDataChanged();

                } else {
                    listPagoPropietarios.clear();

                    tableModelPagoPropietario.fireTableDataChanged();
                }
            }
        });

    }

    void configuraTblPagoPropietario() {
        ((DefaultTableCellRenderer) tblPagoPropietario.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listPagoPropietarios = new ArrayList<>();

        tableModelPagoPropietario = new PagoPropietarioTableModel(listPagoPropietarios);
        tblPagoPropietario.setModel(tableModelPagoPropietario);

        tblPagoPropietario.setRowHeight(25);

    }

    void eliminarSeleccionado() {
        try {

            PagoRecibo toDelete = tableModel.getCliente(tblPagoAlquiler.getSelectedRow());
            tableModel.eliminar(tblPagoAlquiler.getSelectedRow());
            
            Caja movimientoToRemove = cajaDAO.findByPagoRecibo(pagoRecibo);
            cajaDAO.delete(movimientoToRemove);
            ActualizaSaldos actualizaSaldos = new ActualizaSaldos();
            cajaDAO.save(actualizaSaldos.ActualizaSaldosCaja(cajaDAO.findByFechaAfterOrFechaEqualAndMonedaAndTipoDeCajaOrderByFechaDesc(pagoRecibo.getFecha(), pagoRecibo.getMoneda(),movimientoToRemove.getTipoDeCaja())));
            
            List<PagoPropietario> listPagoPropietarioToDelete = pagoPropietarioDAO.findByPagoAlquiler(toDelete);
            pagoPropietarioDAO.delete(listPagoPropietarioToDelete);
            pagoAlquilerDAO.delete(toDelete);

            for (PagoPropietario pagos : listPagoPropietarioToDelete) {

                List<CCPropietario> listCC = cCPropietarioDAO.findByPropietarioAndMonedaOrderByIdAsc(pagos.getPropietario(), pagos.getMoneda());

                ActualizaSaldos acSaldo = new ActualizaSaldos();
                cCPropietarioDAO.save(acSaldo.ActualizaSaldosPropietarios(listCC));
            }

            BigDecimal valorPago = toDelete.getValor();
            BigDecimal nuevoSaldo = alquiler.getSaldo().add(valorPago);

            if (nuevoSaldo.compareTo(alquiler.getValor()) != 0) {
                alquiler.setStatusAlquiler(Situacion.CON_SALDO);
                alquiler.setSaldo(nuevoSaldo);
            } else {
                alquiler.setStatusAlquiler(Situacion.PENDIENTE);
                alquiler.setSaldo(nuevoSaldo);
            }
            recibosDAO.save(alquiler);
            JOptionPane.showMessageDialog(this, "Pago elminado correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            JOptionPane.showMessageDialog(this, "No se puede elinar el registro pues esta asignado a contratos", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    void imprimieRecibo() {
        try {

            InputStream resource = getClass().getClassLoader().getResourceAsStream("reportes/Recibo1.jasper");

            BufferedImage logo = ImageIO.read(getClass().getResource("/imagenes/logo.png"));

            HashMap parametros = new HashMap<>();

            parametros.put("inquilino", pagoRecibo.getRecibo().getContrato().getInquilino().toString());
            parametros.put("direccion", pagoRecibo.getRecibo().getContrato().getInmueble().toString());
            parametros.put("fecha", pagoRecibo.getFecha());
            parametros.put("saldo", pagoRecibo.getRecibo().getSaldo().toString());
            parametros.put("valor", pagoRecibo.getValor().toString());
            parametros.put("idPago", pagoRecibo.getId().toString());
            parametros.put("logo", logo);
            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, new JREmptyDataSource());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPagoAlquiler = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPagoPropietario = new javax.swing.JTable();
        btnImprimeRecibo = new javax.swing.JButton();

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 400));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Detalles de Pagos");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        tblPagoAlquiler.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblPagoAlquiler);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jScrollPane1, gridBagConstraints);

        tblPagoPropietario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblPagoPropietario);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jScrollPane2, gridBagConstraints);

        btnImprimeRecibo.setText("Reimprimir recibo");
        btnImprimeRecibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimeReciboActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel4.add(btnImprimeRecibo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnImprimeReciboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimeReciboActionPerformed

        imprimieRecibo();

    }//GEN-LAST:event_btnImprimeReciboActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnImprimeRecibo;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblPagoAlquiler;
    private javax.swing.JTable tblPagoPropietario;
    // End of variables declaration//GEN-END:variables

}
