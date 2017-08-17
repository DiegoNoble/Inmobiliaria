package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.Renderers.MeDateCellRenderer;
import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.beans.CCPropietario;
import com.dnsoft.inmobiliaria.beans.Caja;
import com.dnsoft.inmobiliaria.beans.PagoRecibo;
import com.dnsoft.inmobiliaria.beans.PagoPropietario;
import com.dnsoft.inmobiliaria.beans.Propietario;
import com.dnsoft.inmobiliaria.beans.Situacion;
import com.dnsoft.inmobiliaria.daos.IRecibosDAO;
import com.dnsoft.inmobiliaria.daos.ICCPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.ICajaDAO;
import com.dnsoft.inmobiliaria.daos.IContratoDAO;
import com.dnsoft.inmobiliaria.daos.IPagoReciboDAO;
import com.dnsoft.inmobiliaria.daos.IPagoPropietarioDAO;
import com.dnsoft.inmobiliaria.models.PagoAlquilerTableModel;
import com.dnsoft.inmobiliaria.models.PagoPropietarioTableModel;
import com.dnsoft.inmobiliaria.utils.ActualizaSaldos;
import com.dnsoft.inmobiliaria.utils.ButtonColumnBorrar;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ImprimeRecibo;
import com.dnsoft.inmobiliaria.utils.OptionPaneEstandar;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

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
    IContratoDAO contratoDAO;
    Container container;
    PagoRecibo pagoRecibo;
    List<PagoRecibo> listPagoAlquiler;
    PagoAlquilerTableModel tableModel;
    ListSelectionModel listModel;
    Recibo alquiler;

    public PagosDetalleDlg(java.awt.Frame parent, boolean modal, Recibo alquiler) {
        super(parent, modal);

        initComponents();
        this.alquiler = alquiler;
        inicia();
        accionesBotones();
    }

    public PagosDetalleDlg(java.awt.Frame parent, boolean modal, Recibo alquiler, Boolean reimprimir) {
        super(parent, modal);
        initComponents();
        this.alquiler = alquiler;
        inicia();
        tblPagoAlquiler.removeColumn(tblPagoAlquiler.getColumn("Anular Pago"));
    }

    void inicia() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logoTrans.png")));
        //CIERRA JOPTIONPANE CON ESCAPE
        jPanel2.grabFocus();
        jPanel2.addKeyListener(new OptionPaneEstandar(this));

        this.container = Container.getInstancia();
        pagoAlquilerDAO = container.getBean(IPagoReciboDAO.class);
        pagoPropietarioDAO = container.getBean(IPagoPropietarioDAO.class);
        cCPropietarioDAO = container.getBean(ICCPropietarioDAO.class);
        contratoDAO = container.getBean(IContratoDAO.class);
        recibosDAO = container.getBean(IRecibosDAO.class);
        cajaDAO = container.getBean(ICajaDAO.class);
        setLocationRelativeTo(null);
        configuraTblPagoAlquiler();
        accionesBotones();

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
                    btnImprimeRecibo.setEnabled(true);

                } else {
                    btnImprimeRecibo.setEnabled(false);
                }
            }
        });

    }

    void eliminarSeleccionado() {
        try {
            PagoRecibo toDelete = tableModel.getCliente(tblPagoAlquiler.getSelectedRow());
            tableModel.eliminar(tblPagoAlquiler.getSelectedRow());

            Caja movimientoToRemove = cajaDAO.findByPagoRecibo(pagoRecibo);
            if (movimientoToRemove != null) {
                cajaDAO.delete(movimientoToRemove);

                ActualizaSaldos actualizaSaldos = new ActualizaSaldos();
                cajaDAO.save(actualizaSaldos.ActualizaSaldosCaja(cajaDAO.findByFechaAfterOrFechaEqualAndMonedaAndTipoDeCajaOrderByFechaDesc(pagoRecibo.getFecha(), pagoRecibo.getMoneda(), movimientoToRemove.getTipoDeCaja())));
            }
            List<PagoPropietario> listPagoPropietarioToDelete = pagoPropietarioDAO.findByPagoAlquiler(toDelete);
            pagoPropietarioDAO.delete(listPagoPropietarioToDelete);
            pagoAlquilerDAO.delete(toDelete);

            List<Propietario> propietarios = new ArrayList<>();
            for (PagoPropietario pagoS : listPagoPropietarioToDelete) {
                propietarios.add(pagoS.getPropietario());
            }

            for (Propietario propietario : propietarios) {

                List<CCPropietario> listCC = cCPropietarioDAO.findByPropietarioAndMonedaOrderByIdAsc(propietario, pagoRecibo.getMoneda());

                ActualizaSaldos acSaldo = new ActualizaSaldos();
                cCPropietarioDAO.save(acSaldo.ActualizaSaldosPropietarios(listCC));
            }

            BigDecimal valorPago = toDelete.getValor();
            BigDecimal nuevoSaldo = alquiler.getSaldo().add(valorPago);

            if (nuevoSaldo.compareTo(alquiler.getValor()) != 0) {
                alquiler.setSituacion(Situacion.CON_SALDO);
                alquiler.setSaldo(nuevoSaldo);
            } else {
                alquiler.setSituacion(Situacion.PENDIENTE);
                alquiler.setFechaPago(null);
                alquiler.setSaldo(nuevoSaldo);
            }
            recibosDAO.save(alquiler);
            JOptionPane.showMessageDialog(this, "Pago elminado correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al anular pago: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    void accionesBotones() {
        botonVolver1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                dispose();
            }
        });
        btnImprimeRecibo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                imprimieRecibo();
            }
        });

    }

    void imprimieRecibo() {

        try {
//            contratoDAO.findByContrato(pagoRecibo.get)
            /* if (pagoRecibo.getRecibo().getContrato().getTipoContrato() == TipoContrato.ALQUILER) {

             new ImprimeRecibo().imprimieReciboAlquiler(pagoRecibo, pagoRecibo.getCotizacion());

             } else {*/

            new ImprimeRecibo().imprimieReciboPagoCuota(pagoRecibo, pagoRecibo.getCotizacion());
            //  }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al imprimir recibo " + ex, "Error", JOptionPane.ERROR_MESSAGE);

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
        btnImprimeRecibo = new botones.BotonPDF();
        botonVolver1 = new botones.BotonVolver();

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
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jScrollPane1, gridBagConstraints);

        btnImprimeRecibo.setEnabled(false);
        btnImprimeRecibo.setText("Imprimir recibo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(btnImprimeRecibo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(botonVolver1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BotonVolver botonVolver1;
    private botones.BotonPDF btnImprimeRecibo;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPagoAlquiler;
    // End of variables declaration//GEN-END:variables

}
