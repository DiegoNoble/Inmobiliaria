package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.Renderers.MeTimeCellRenderer;
import com.dnsoft.inmobiliaria.beans.Caja;
import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.Cotizacion;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.PagoRecibo;
import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.beans.Parametros;
import com.dnsoft.inmobiliaria.beans.TipoContrato;
import com.dnsoft.inmobiliaria.beans.TipoCotizacionContrato;
import com.dnsoft.inmobiliaria.beans.TipoDeCaja;
import com.dnsoft.inmobiliaria.daos.ICajaDAO;
import com.dnsoft.inmobiliaria.daos.ICotizacionDAO;
import com.dnsoft.inmobiliaria.daos.IParametrosDAO;
import com.dnsoft.inmobiliaria.daos.ITipoDeCajaDAO;
import com.dnsoft.inmobiliaria.models.CajaTableModel;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ControlarEntradaTexto;
import com.dnsoft.inmobiliaria.utils.OptionPaneEstandar;
import com.dnsoft.inmobiliaria.utils.PagarRecibo;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Diego Noble
 */
public final class DetalleMovimientoDeCaja extends javax.swing.JDialog {

    ICajaDAO cajaDAO;
    IParametrosDAO parametrosDAO;
    ICotizacionDAO cotizacionDAO;
    ITipoDeCajaDAO tipoDeCajaDAO;
    List<Caja> listMovimientos;
    Recibo recibo;
    Contrato contratoSeleccionado;
    Container container;
    BigDecimal saldo;
    BigDecimal entrega;
    Moneda monedaTotal;
    Moneda otraMoneda;
    Cotizacion cotizacionOtraMoneda;
    Cotizacion cotizacionMonedaOrigen;
    TipoCotizacionContrato tipoCotizacionContrato;
    CajaTableModel tableModel;
    Parametros parametros;
    PagarRecibo pagarRecibo;

    public DetalleMovimientoDeCaja(java.awt.Frame parent, boolean modal, Recibo recibo, BigDecimal entrega, PagarRecibo pagarAlquiler, TipoDeCaja tipoDeCaja) {
        super(parent, modal);
        initComponents();

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logoTrans.png")));
        //CIERRA JOPTIONPANE CON ESCAPE
        jPanel1.grabFocus();
        jPanel1.addKeyListener(new OptionPaneEstandar(this));
        
        this.container = Container.getInstancia();
        parametrosDAO = container.getBean(IParametrosDAO.class);
        cajaDAO = container.getBean(ICajaDAO.class);
        cotizacionDAO = container.getBean(ICotizacionDAO.class);
        tipoDeCajaDAO = container.getBean(ITipoDeCajaDAO.class);
        this.recibo = recibo;
        this.entrega = entrega;
        this.monedaTotal = recibo.getContrato().getMoneda();
        this.parametros = parametrosDAO.findOne(Long.valueOf(1));
        this.pagarRecibo = pagarAlquiler;

        tipoCotizacionContrato = recibo.getContrato().getTipoCotizacionContrato();
        if (tipoCotizacionContrato == TipoCotizacionContrato.FIJA) {
            cotizacionMonedaOrigen = new Cotizacion(recibo.getContrato().getCotizacion());
        } else {
            cotizacionMonedaOrigen = (Cotizacion) cotizacionDAO.findLastCotizacion(monedaTotal);
        }
        setLocationRelativeTo(null);

        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-'};
        txtImporteAPagar.setDocument(new ControlarEntradaTexto(10, chs));
        txtSaldo.setDocument(new ControlarEntradaTexto(10, chs));
        txtImporteMedioPago.setDocument(new ControlarEntradaTexto(10, chs));

        cargaMonedas();
        inicio();
        accionesBotones();
        calculaSaldoOtraMoneda();
        cargaTiposDeCaja();
        txtImporteAPagar.setVisible(false);
        jLabel1.setVisible(false);
        cbMonedaImporteTotal.setVisible(false);

        if (recibo.getContrato().getTipoContrato() == TipoContrato.ALQUILER) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            txtDetalle.setText("Pago recibo " + recibo.getContrato().getTipoContrato() + " " + recibo.getContrato().getInquilino() + " período "
                    + formato.format(recibo.getPeriodo_desde()) + " al " + formato.format(recibo.getPeriodo_hasta()));
        } else {
            txtDetalle.setText("Pago recibo " + recibo.getContrato().getTipoContrato() + " " + recibo.getContrato().getInquilino() + " cuota nro. " + recibo.getNroRecibo());
        }
        if (tipoDeCaja != null) {
            cbTipoDeCaja.setSelectedItem(tipoDeCaja);
        }
    }

    void cargaMonedas() {

        cbMonedaMedioPago.addItem(Moneda.PESOS);
        cbMonedaMedioPago.addItem(Moneda.DOLARES);

        cbMonedaImporteTotal.addItem(Moneda.PESOS);
        cbMonedaImporteTotal.addItem(Moneda.DOLARES);

    }

    private void TableModel() {

        ((DefaultTableCellRenderer) tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listMovimientos = new ArrayList<>();

        tableModel = new CajaTableModel(listMovimientos);
        tbl.setModel(tableModel);
        tbl.getColumn("Fecha").setCellRenderer(new MeTimeCellRenderer());
        tbl.setRowHeight(25);
        tbl.removeColumn(tbl.getColumn("Salida"));
        tbl.removeColumn(tbl.getColumn("Saldo"));
    }

    void inicio() {
        TableModel();
        txtImporteAPagar.setText(entrega.toString());
        txtSaldo.setText(entrega.toString());
        saldo = new BigDecimal(txtSaldo.getText());

        cbMonedaImporteTotal.setSelectedItem(recibo.getContrato().getMoneda());

        cbMonedaMedioPago.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent ie) {
                calculaSaldoOtraMoneda();
            }
        });

        txtSaldo.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                verificaSaldo();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                verificaSaldo();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                verificaSaldo();
            }
        });

    }

    void accionesBotones() {
        btnCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                inicio();

            }
        });
        btnCargar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (verificaCargaMonedaRepetida() == false) {

                    Caja movimiento = new Caja();
                    BigDecimal importe = new BigDecimal(txtImporteMedioPago.getText());
                    movimiento.setDescripcion("Pago alquiler " + recibo.getContrato().getInquilino() + ", " + recibo.getContrato().getInquilino().getNombre());
                    movimiento.setEntrada(importe);
                    movimiento.setFecha(new Date());
                    movimiento.setMoneda(otraMoneda);
                    movimiento.setRubro(parametros.getRubroAlquileres());
                    movimiento.setSaldo(calculaSaldo().add(importe));
                    movimiento.setSalida(BigDecimal.ZERO);
                    movimiento.setTipoDeCaja((TipoDeCaja) cbTipoDeCaja.getSelectedItem());
                    listMovimientos.add(movimiento);

                    tableModel.fireTableDataChanged();

                    calculaEntregaOtraMoneda();
                }
            }
        });
        btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                PagoRecibo pagoRecibo = pagarRecibo.pagarRecibo();
                for (Caja movimiento : listMovimientos) {
                    movimiento.setPagoRecibo(pagoRecibo);
                }
                cajaDAO.save(listMovimientos);
                DetalleMovimientoDeCaja.this.dispose();
            }
        });

    }

    void verificaSaldo() {
        String verificaSaldo = txtSaldo.getText();
        if (verificaSaldo.equals("0.00")) {

            btnGuardar.setEnabled(true);
            btnCargar.setEnabled(false);
            txtImporteMedioPago.setEditable(false);
            txtImporteMedioPago.setText("0.00");

        } else {
            btnGuardar.setEnabled(false);
            txtImporteMedioPago.setEditable(true);
            btnCargar.setEnabled(true);
        }
    }

    void calculaSaldoOtraMoneda() {

        otraMoneda = (Moneda) cbMonedaMedioPago.getSelectedItem();

        if (otraMoneda == Moneda.PESOS) {
            cotizacionOtraMoneda = new Cotizacion(new BigDecimal(1));
        } else {
            if (tipoCotizacionContrato == TipoCotizacionContrato.FIJA) {
                cotizacionOtraMoneda = new Cotizacion(recibo.getContrato().getCotizacion());
            } else {
                cotizacionOtraMoneda = (Cotizacion) cotizacionDAO.findLastCotizacion(otraMoneda);
            }
        }
        saldo = new BigDecimal(txtSaldo.getText());
        txtImporteMedioPago.setText(saldo.multiply(cotizacionMonedaOrigen.getValor().divide(
                cotizacionOtraMoneda.getValor(), 10, RoundingMode.CEILING)).setScale(2, RoundingMode.CEILING).toString());
        //Importe *(Cotizacion Moneda Origen/Cotizacion Moneda Destino)
    }

    void calculaEntregaOtraMoneda() {

        otraMoneda = (Moneda) cbMonedaMedioPago.getSelectedItem();

        if (otraMoneda == Moneda.PESOS) {
            cotizacionOtraMoneda = new Cotizacion(new BigDecimal(1));
        } else {
            if (tipoCotizacionContrato == TipoCotizacionContrato.FIJA) {
                cotizacionOtraMoneda = new Cotizacion(recibo.getContrato().getCotizacion());
            } else {
                cotizacionOtraMoneda = (Cotizacion) cotizacionDAO.findLastCotizacion(otraMoneda);
            }
        }
        BigDecimal entregaOtraMoneda = new BigDecimal(txtImporteMedioPago.getText());
        saldo = new BigDecimal(txtSaldo.getText());
        txtSaldo.setText(saldo.subtract(entregaOtraMoneda.multiply(cotizacionOtraMoneda.getValor().divide(
                cotizacionMonedaOrigen.getValor(), 10, RoundingMode.CEILING))).setScale(2, RoundingMode.CEILING).toString());
        //Saldo - (Importe otra moneda *(Cotizacion Moneda Origen/Cotizacion Moneda Saldo))
    }

    BigDecimal calculaSaldo() {
        BigDecimal toReturn;
        Caja ultimoMovimiento = cajaDAO.findUltimoMovimiento((Moneda) cbMonedaMedioPago.getSelectedItem(), (TipoDeCaja) cbTipoDeCaja.getSelectedItem());
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
                movimientosCargados.setTipoDeCaja((TipoDeCaja) cbTipoDeCaja.getSelectedItem());
                movimientosCargados.setRubro(parametros.getRubroAlquileres());
                movimientosCargados.setSaldo(calculaSaldo().add(movimientosCargados.getEntrada()));
                movimientosCargados.setSalida(BigDecimal.ZERO);
                tableModel.fireTableDataChanged();

                calculaEntregaOtraMoneda();
                return true;
            }
        }
        return false;
    }

    private void cargaTiposDeCaja() {
        cbTipoDeCaja.removeAllItems();
        for (TipoDeCaja tipoDeCaja : tipoDeCajaDAO.findAll()) {
            cbTipoDeCaja.addItem(tipoDeCaja);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnCargar = new botones.BotonNuevo();
        btnGuardar = new botones.BotonGuardar();
        btnCancel = new botones.BotonCancelar();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtImporteAPagar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtImporteMedioPago = new javax.swing.JTextField();
        cbMonedaMedioPago = new javax.swing.JComboBox();
        cbMonedaImporteTotal = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        txtSaldo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbTipoDeCaja = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        txtDetalle = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 450));
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

        jPanel3.setLayout(new java.awt.GridBagLayout());

        btnCargar.setPreferredSize(new java.awt.Dimension(100, 60));
        btnCargar.setText("Cargar medio de Pago");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnCargar, gridBagConstraints);

        btnGuardar.setEnabled(false);
        btnGuardar.setPreferredSize(new java.awt.Dimension(120, 60));
        btnGuardar.setText("CONFIRMAR");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnGuardar, gridBagConstraints);

        btnCancel.setPreferredSize(new java.awt.Dimension(120, 60));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnCancel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("A Pagar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel1, gridBagConstraints);

        txtImporteAPagar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtImporteAPagar, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Valor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jLabel2, gridBagConstraints);

        txtImporteMedioPago.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtImporteMedioPago, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
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
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        txtSaldo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtSaldo.setEnabled(false);
        txtSaldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSaldoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtSaldo, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Saldo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jLabel4, gridBagConstraints);

        cbTipoDeCaja.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbTipoDeCaja, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Tipo de caja");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel10, gridBagConstraints);

        txtDetalle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDetalle.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtDetalle, gridBagConstraints);

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

    private void txtSaldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSaldoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSaldoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BotonCancelar btnCancel;
    private botones.BotonNuevo btnCargar;
    private botones.BotonGuardar btnGuardar;
    private javax.swing.JComboBox cbMonedaImporteTotal;
    private javax.swing.JComboBox cbMonedaMedioPago;
    public javax.swing.JComboBox cbTipoDeCaja;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl;
    private javax.swing.JTextField txtDetalle;
    private javax.swing.JTextField txtImporteAPagar;
    private javax.swing.JTextField txtImporteMedioPago;
    private javax.swing.JTextField txtSaldo;
    // End of variables declaration//GEN-END:variables
}
