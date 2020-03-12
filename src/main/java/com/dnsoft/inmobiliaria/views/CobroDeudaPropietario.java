package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.CCPropietario;
import com.dnsoft.inmobiliaria.beans.Caja;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.Propietario;
import com.dnsoft.inmobiliaria.beans.Rubro;
import com.dnsoft.inmobiliaria.beans.TipoDeCaja;
import com.dnsoft.inmobiliaria.daos.ICCPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.ICajaDAO;
import com.dnsoft.inmobiliaria.daos.ITipoDeCajaDAO;
import com.dnsoft.inmobiliaria.utils.ActualizaSaldos;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ControlarEntradaTexto;
import com.dnsoft.inmobiliaria.utils.ImprimeRecibo;
import com.dnsoft.inmobiliaria.utils.OptionPaneEstandar;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego Noble
 */
public final class CobroDeudaPropietario extends javax.swing.JDialog {

    BigDecimal importePago;
    Rubro rubro;
    Propietario propietario;
    Moneda moneda;
    ICCPropietarioDAO cCPropietarioDAO;
    ITipoDeCajaDAO tipoDeCajaDAO;
    ICajaDAO cajaDAO;
    Container container;
    Caja movimientoCaja;

    public CobroDeudaPropietario(java.awt.Frame parent, boolean modal, Rubro rubro, Propietario propietario, Moneda moneda) {
        super(parent, modal);
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logoTrans.png")));
        //CIERRA JOPTIONPANE CON ESCAPE
        jPanel1.grabFocus();
        jPanel1.addKeyListener(new OptionPaneEstandar(this));
        
        this.container = Container.getInstancia();
        this.propietario = propietario;
        this.moneda = moneda;
        this.rubro = rubro;

        inicio();
    }

    void inicio() {
        setLocationRelativeTo(null);
        cCPropietarioDAO = container.getBean(ICCPropietarioDAO.class);
        cajaDAO = container.getBean(ICajaDAO.class);
        tipoDeCajaDAO = container.getBean(ITipoDeCajaDAO.class);
        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-'};
        txtImporte.setDocument(new ControlarEntradaTexto(10, chs));
        txtMoneda.setText(moneda.toString());
        accionesBotones();
        cargaTiposDeCaja();

    }

    void accionesBotones() {
        btnPagar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (!txtDescripcion.getText().equals("")) {
                    VerificaImporte();
                } else {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un detalle del pago", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                dispose();
            }
        });

    }

    void VerificaImporte() {
        try {
            importePago = new BigDecimal(txtImporte.getText()).setScale(2, RoundingMode.CEILING);
            if (importePago.doubleValue() > 0.00) {
                //Si el retiro es mayor al saldo, solicita confirmacion
                int resp = JOptionPane.showConfirmDialog(this, "Confirma el importe ingresado ? " + moneda + " " + importePago, "Atención!", JOptionPane.YES_NO_OPTION);
                if (resp != -1) {
                    registraPago();
                    JOptionPane.showMessageDialog(null, "Se registro el pago correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                    new ImprimeRecibo(movimientoCaja, propietario.toString(), txtDescripcion.getText(), "", "").imprimieReciboEntrada();
                }
            } else {
                JOptionPane.showConfirmDialog(this, "El importe debe ser mayor que 0", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar registros: " + e);

        }
    }

    private void movimientoCaja() {
        movimientoCaja = new Caja();
        movimientoCaja.setDescripcion("Cobro deuda propietario " + propietario + ", Descripción: " + txtDescripcion.getText());
        movimientoCaja.setEntrada(importePago);
        movimientoCaja.setFecha(new Date());
        movimientoCaja.setMoneda(moneda);
        movimientoCaja.setRubro(rubro);
        movimientoCaja.setSaldo(calculaSaldo().add(importePago));
        movimientoCaja.setSalida(BigDecimal.ZERO);
        movimientoCaja.setTipoDeCaja((TipoDeCaja) cbTipoDeCaja.getSelectedItem());
        cajaDAO.save(movimientoCaja);
        this.dispose();
    }

    private void registraPago() {

        CCPropietario pago = new CCPropietario();
        pago.setPropietario(propietario);
        pago.setDebito(BigDecimal.ZERO);
        pago.setCredito(importePago);
        pago.setDescipcion(txtDescripcion.getText());
        pago.setFecha(new Date());
        pago.setMoneda(moneda);
        cCPropietarioDAO.save(pago);

        //Ajusta saldo cc propietario
        ActualizaSaldos acSaldo = new ActualizaSaldos();
        List<CCPropietario> ccPropietario = cCPropietarioDAO.findByPropietarioAndMonedaOrderByIdAsc(propietario, moneda);
        cCPropietarioDAO.saveAll(acSaldo.ActualizaSaldosPropietarios(ccPropietario));
        movimientoCaja();
    }

    BigDecimal calculaSaldo() {
        BigDecimal toReturn;

        Caja ultimoMovimiento = cajaDAO.findUltimoMovimiento((Moneda) moneda, (TipoDeCaja) cbTipoDeCaja.getSelectedItem());
        if (ultimoMovimiento == null) {
            toReturn = BigDecimal.ZERO;
        } else {
            toReturn = ultimoMovimiento.getSaldo();
        }
        return toReturn;

    }

    private void cargaTiposDeCaja() {
        cbTipoDeCaja.removeAllItems();
        for (TipoDeCaja tipoDeCaja : tipoDeCajaDAO.findAll()) {
            cbTipoDeCaja.addItem(tipoDeCaja);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtImporte = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnPagar = new botones.BotonPagar();
        btnVolver = new botones.BotonVolver();
        jLabel12 = new javax.swing.JLabel();
        txtMoneda = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cbTipoDeCaja = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Detalle aporte propietario");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        txtImporte.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtImporte, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("Moneda");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel11, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(btnPagar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(btnVolver, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Importe");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel12, gridBagConstraints);

        txtMoneda.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtMoneda.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtMoneda, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Concepto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel10, gridBagConstraints);

        cbTipoDeCaja.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbTipoDeCaja, gridBagConstraints);

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane1.setViewportView(txtDescripcion);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("Tipo de caja");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel13, gridBagConstraints);

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BotonPagar btnPagar;
    private botones.BotonVolver btnVolver;
    public javax.swing.JComboBox cbTipoDeCaja;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtImporte;
    private javax.swing.JTextField txtMoneda;
    // End of variables declaration//GEN-END:variables

}
