package com.dnsoft.inmobiliaria.views.detalles;

import com.dnsoft.inmobiliaria.Renderers.MeTimeCellRenderer;
import com.dnsoft.inmobiliaria.beans.Caja;
import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.Cotizaciones;
import com.dnsoft.inmobiliaria.beans.Monedas;
import com.dnsoft.inmobiliaria.beans.Recibos;
import com.dnsoft.inmobiliaria.beans.Rubro;
import com.dnsoft.inmobiliaria.controllers.ConsultaContratosController;
import com.dnsoft.inmobiliaria.daos.ICajaDAO;
import com.dnsoft.inmobiliaria.daos.ICotizacionDAO;
import com.dnsoft.inmobiliaria.daos.IMonedaDAO;
import com.dnsoft.inmobiliaria.daos.IRubroDAO;
import com.dnsoft.inmobiliaria.models.CajaTableModel;
import com.dnsoft.inmobiliaria.utils.ControlarEntradaTexto;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Diego Noble
 */
public class MovimientodeCajaRecibo extends javax.swing.JDialog {

    IMonedaDAO monedaDAO;
    ICajaDAO cajaDAO;
    IRubroDAO rubroDAO;
    ICotizacionDAO cotizacionDAO;
    List<Caja> listMovimientos;
    Recibos recibo;
    Contrato contratoSeleccionado;
    ConsultaContratosController controller;
    ApplicationContext applicationContext;
    BigDecimal saldo;
    Monedas monedaTotal;
    Monedas otraMoneda;
    Cotizaciones cotizacionOtraMoneda;
    Cotizaciones cotizacionMonedaOrigen;
    CajaTableModel tableModel;

    public MovimientodeCajaRecibo(java.awt.Frame parent, boolean modal, Recibos recibo, ConsultaContratosController controller) {
        super(parent, modal);
        initComponents();
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        monedaDAO = applicationContext.getBean(IMonedaDAO.class);
        rubroDAO = applicationContext.getBean(IRubroDAO.class);
        cajaDAO = applicationContext.getBean(ICajaDAO.class);
        cotizacionDAO = applicationContext.getBean(ICotizacionDAO.class);
        this.controller = controller;
        this.recibo = recibo;
        this.monedaTotal = recibo.getContrato().getMoneda();
        cotizacionMonedaOrigen = (Cotizaciones) cotizacionDAO.findLastCotizacion(monedaTotal);
        setLocationRelativeTo(controller.view);

        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-'};
        txtImporteTotal.setDocument(new ControlarEntradaTexto(10, chs));
        txtSaldo.setDocument(new ControlarEntradaTexto(10, chs));
        txtImporteMedioPago.setDocument(new ControlarEntradaTexto(10, chs));

        cargaMonedas();
        cargaRubros();
        inicio();
        calculaSaldoOtraMoneda();
    }

    void cargaMonedas() {

        List<Monedas> monedas = monedaDAO.findAll();
        for (Monedas moneda : monedas) {

            cbMonedaMedioPago.addItem(moneda);
            cbMonedaImporteTotal.addItem(moneda);
        }

    }

    void cargaRubros() {
        AutoCompleteDecorator.decorate(cbRubro);
        List<Rubro> rubros = rubroDAO.findAll();
        for (Rubro rubro : rubros) {
            cbRubro.addItem(rubro);

        }
    }

    private void TableModel() {

        ((DefaultTableCellRenderer) tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listMovimientos = new ArrayList<Caja>();

        tableModel = new CajaTableModel(listMovimientos);
        tbl.setModel(tableModel);
        tbl.getColumn("Fecha").setCellRenderer(new MeTimeCellRenderer());
        tbl.setRowHeight(25);
        int[] anchos = {50, 100, 20, 20, 20};

        for (int i = 0; i < tbl.getColumnCount(); i++) {

            tbl.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
    }

    void inicio() {
        TableModel();
        txtImporteTotal.setText(recibo.getValor().toString());
        txtSaldo.setText(recibo.getValor().toString());
        saldo = new BigDecimal(txtSaldo.getText());

        cbMonedaImporteTotal.setSelectedItem(recibo.getContrato().getMoneda());

        cbMonedaMedioPago.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent ie) {
                calculaSaldoOtraMoneda();
            }
        });

        txtSaldo.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                verificaSaldo();

            }

            public void removeUpdate(DocumentEvent e) {
                verificaSaldo();
            }

            public void insertUpdate(DocumentEvent e) {
                verificaSaldo();
            }
        });

    }

    void verificaSaldo() {
        String saldo = txtSaldo.getText();
        if (saldo.equals("0.00")) {

            btnGuardarMovimiento.setEnabled(true);

        } else {
            btnGuardarMovimiento.setEnabled(false);
        }
    }

    void calculaSaldoOtraMoneda() {

        otraMoneda = (Monedas) cbMonedaMedioPago.getSelectedItem();
        cotizacionOtraMoneda = (Cotizaciones) cotizacionDAO.findLastCotizacion(otraMoneda);
        saldo = new BigDecimal(txtSaldo.getText());
        txtImporteMedioPago.setText(saldo.multiply(cotizacionMonedaOrigen.getValor().divide(cotizacionOtraMoneda.getValor(), 10, RoundingMode.CEILING)).setScale(2, RoundingMode.CEILING).toString());
        //Importe *(Cotizacion Moneda Origen/Cotizacion Moneda Destino)
    }

    void calculaEntregaOtraMoneda() {

        otraMoneda = (Monedas) cbMonedaMedioPago.getSelectedItem();
        cotizacionOtraMoneda = (Cotizaciones) cotizacionDAO.findLastCotizacion(otraMoneda);
        BigDecimal entregaOtraMoneda = new BigDecimal(txtImporteMedioPago.getText());
        saldo = new BigDecimal(txtSaldo.getText());
        txtSaldo.setText(saldo.subtract(entregaOtraMoneda.multiply(cotizacionOtraMoneda.getValor().divide(cotizacionMonedaOrigen.getValor(), 10, RoundingMode.CEILING))).setScale(2, RoundingMode.CEILING).toString());
        //Saldo - (Importe otra moneda *(Cotizacion Moneda Origen/Cotizacion Moneda Saldo))
    }

    BigDecimal calculaSaldo() {
        BigDecimal toReturn;
        Caja ultimoMovimiento = new Caja();
        ultimoMovimiento = cajaDAO.findUltimoMovimiento(((Monedas) cbMonedaMedioPago.getSelectedItem()).getId());
        if (ultimoMovimiento == null) {
            toReturn = BigDecimal.ZERO;
        } else {
            toReturn = ultimoMovimiento.getSaldo();
        }
        return toReturn;

    }

    boolean verificaCargaMonedaRepetida() {
        for (Caja movimientosCargados : listMovimientos) {
            if (movimientosCargados.getMoneda() == otraMoneda) {
                BigDecimal importe = new BigDecimal(txtImporteMedioPago.getText());
                movimientosCargados.setDescripcion("Pago alquiler " + recibo.getContrato().getInquilino());
                movimientosCargados.setEntrada(movimientosCargados.getEntrada().add(importe));
                movimientosCargados.setFecha(new Date());
                movimientosCargados.setMoneda(otraMoneda);
                movimientosCargados.setRubro((Rubro) cbRubro.getSelectedItem());
                movimientosCargados.setSaldo(calculaSaldo().add(movimientosCargados.getEntrada()));
                movimientosCargados.setSalida(BigDecimal.ZERO);
                tableModel.fireTableDataChanged();

                calculaEntregaOtraMoneda();
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnCargarPago = new javax.swing.JButton();
        btnGuardarMovimiento = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtImporteTotal = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtImporteMedioPago = new javax.swing.JTextField();
        cbMonedaMedioPago = new javax.swing.JComboBox();
        cbMonedaImporteTotal = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        txtSaldo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbRubro = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(600, 400));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Movimiento de caja");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        btnCargarPago.setText("Cargar medio de pago");
        btnCargarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarPagoActionPerformed(evt);
            }
        });
        btnCargarPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCargarPagoKeyPressed(evt);
            }
        });
        jPanel3.add(btnCargarPago);

        btnGuardarMovimiento.setText("Guardar movimiento");
        btnGuardarMovimiento.setEnabled(false);
        btnGuardarMovimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarMovimientoActionPerformed(evt);
            }
        });
        btnGuardarMovimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnGuardarMovimientoKeyPressed(evt);
            }
        });
        jPanel3.add(btnGuardarMovimiento);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        btnCancelar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCancelarKeyPressed(evt);
            }
        });
        jPanel3.add(btnCancelar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Importe Total en:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel1, gridBagConstraints);

        txtImporteTotal.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtImporteTotal, gridBagConstraints);

        jLabel2.setText("Valor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtImporteMedioPago, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanel1.add(cbMonedaMedioPago, gridBagConstraints);

        cbMonedaImporteTotal.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel1.add(cbMonedaImporteTotal, gridBagConstraints);

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Moneda", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbl);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        txtSaldo.setEnabled(false);
        txtSaldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSaldoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtSaldo, gridBagConstraints);

        jLabel4.setText("Saldo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel1.add(cbRubro, gridBagConstraints);

        jLabel6.setText("Rubro");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel1.add(jLabel6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCargarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarPagoActionPerformed

        if (verificaCargaMonedaRepetida() == false) {

            Caja movimiento = new Caja();
            BigDecimal importe = new BigDecimal(txtImporteMedioPago.getText());
            movimiento.setDescripcion("Pago alquiler " + recibo.getContrato().getInquilino().getApellidos() + ", " + recibo.getContrato().getInquilino().getNombre());
            movimiento.setEntrada(importe);
            movimiento.setFecha(new Date());
            movimiento.setMoneda(otraMoneda);
            movimiento.setRubro((Rubro) cbRubro.getSelectedItem());
            movimiento.setSaldo(calculaSaldo().add(importe));
            movimiento.setSalida(BigDecimal.ZERO);
            listMovimientos.add(movimiento);

            tableModel.fireTableDataChanged();

            calculaEntregaOtraMoneda();
        }

    }//GEN-LAST:event_btnCargarPagoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        inicio();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCargarPagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCargarPagoKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

        }

    }//GEN-LAST:event_btnCargarPagoKeyPressed

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
        this.dispose();

    }//GEN-LAST:event_btnCancelarKeyPressed

    private void btnGuardarMovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarMovimientoActionPerformed

        cajaDAO.save(listMovimientos);
        this.dispose();
    }//GEN-LAST:event_btnGuardarMovimientoActionPerformed

    private void btnGuardarMovimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGuardarMovimientoKeyPressed
        cajaDAO.save(listMovimientos);
        this.dispose();

    }//GEN-LAST:event_btnGuardarMovimientoKeyPressed

    private void txtSaldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSaldoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSaldoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCargarPago;
    private javax.swing.JButton btnGuardarMovimiento;
    private javax.swing.JComboBox cbMonedaImporteTotal;
    private javax.swing.JComboBox cbMonedaMedioPago;
    private javax.swing.JComboBox cbRubro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl;
    private javax.swing.JTextField txtImporteMedioPago;
    private javax.swing.JTextField txtImporteTotal;
    private javax.swing.JTextField txtSaldo;
    // End of variables declaration//GEN-END:variables
}
