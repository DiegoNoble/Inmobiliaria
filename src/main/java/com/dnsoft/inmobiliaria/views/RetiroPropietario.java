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
public final class RetiroPropietario extends javax.swing.JDialog {

    BigDecimal importeRetiro;
    Rubro rubro;
    Propietario propietario;
    BigDecimal saldo;
    Moneda moneda;
    ICCPropietarioDAO cCPropietarioDAO;
    ITipoDeCajaDAO tipoDeCajaDAO;
    ICajaDAO cajaDAO;
    Container container;
    Caja movimientoCaja;

    public RetiroPropietario(java.awt.Frame parent, boolean modal, Rubro rubro, Propietario propietario, Moneda moneda, BigDecimal saldo) {
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
        this.saldo = saldo;

        inicio();
    }

    void inicio() {
        setLocationRelativeTo(null);
        cCPropietarioDAO = container.getBean(ICCPropietarioDAO.class);
        cajaDAO = container.getBean(ICajaDAO.class);
        tipoDeCajaDAO = container.getBean(ITipoDeCajaDAO.class);
        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-'};
        txtImporte.setDocument(new ControlarEntradaTexto(10, chs));
        txtImporte.setText(saldo.toString());
        txtMoneda.setText(moneda.toString());
        accionesBotones();
        cargaTiposDeCaja();

    }

    void accionesBotones() {
        btnPagar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                VerificaImporte();

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
            importeRetiro = new BigDecimal(txtImporte.getText()).setScale(2, RoundingMode.CEILING);
            if (importeRetiro.doubleValue() > saldo.doubleValue()) {
                //Si el retiro es mayor al saldo, solicita confirmacion
                int resp = JOptionPane.showConfirmDialog(this, "Es importe de retiro supera el saldo disponible, es correcto?", "Atención!", JOptionPane.YES_NO_OPTION);
                if (resp != 1) {
                    registraRetiro();
                    //new ImprimeRecibo().imprimieReciboRetiroPropietario(null);
                }
            } else {
                int respu = JOptionPane.showConfirmDialog(this, "Confirma el importe del retiro?", "Atención!", JOptionPane.YES_NO_OPTION);
                if (respu != 1) {
                    registraRetiro();
                    //new ImprimeRecibo(movimientoCaja, propietario.toString(), movimientoCaja.getDescripcion(), "", "").imprimieReciboSalida();
                }
            }

            /*DetalleMovimientoDeCaja movimientodeCajaRecibo = new DetalleMovimientoDeCaja(null, true, reciboSeleccionado, importeRetiro, pagar, container);
             movimientodeCajaRecibo.setVisible(true);
             movimientodeCajaRecibo.toFront();*/
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar registros: " + e);

        }
    }

    private void movimientoCaja() {
        movimientoCaja = new Caja();
        movimientoCaja.setDescripcion("Retiro de propietario " + propietario);
        movimientoCaja.setEntrada(BigDecimal.ZERO);
        movimientoCaja.setFecha(new Date());
        movimientoCaja.setMoneda(moneda);
        movimientoCaja.setRubro(rubro);
        movimientoCaja.setSaldo(calculaSaldo().subtract(importeRetiro));
        movimientoCaja.setSalida(importeRetiro);
        movimientoCaja.setTipoDeCaja((TipoDeCaja) cbTipoDeCaja.getSelectedItem());
        cajaDAO.save(movimientoCaja);
        this.dispose();
    }

    private void registraRetiro() {

        BigDecimal saldoAnterior = BigDecimal.ZERO;
        CCPropietario findUltimoMovimiento;
        findUltimoMovimiento = cCPropietarioDAO.findUltimoMovimiento(moneda, propietario);
        if (findUltimoMovimiento != null) {
            saldoAnterior = findUltimoMovimiento.getSaldo();
        } else {
            saldoAnterior = BigDecimal.ZERO;
        }

        CCPropietario retiro = new CCPropietario();
        retiro.setPropietario(propietario);
        retiro.setDebito(importeRetiro);
        retiro.setCredito(BigDecimal.ZERO);
        retiro.setSaldo(saldoAnterior.subtract(importeRetiro));
        retiro.setDescipcion("RETIRO ");
        retiro.setFecha(new Date());
        retiro.setMoneda(moneda);
        cCPropietarioDAO.save(retiro);

        //Ajusta saldo cc propietario
       /* ActualizaSaldos acSaldo = new ActualizaSaldos();
         List<CCPropietario> ccPropietario = cCPropietarioDAO.findByPropietarioAndMonedaOrderByIdAsc(propietario, moneda);
         cCPropietarioDAO.save(acSaldo.ActualizaSaldosPropietarios(ccPropietario));*/
        movimientoCaja();
        JOptionPane.showMessageDialog(null, "Se registro el retiro correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
        new ImprimeRecibo().imprimieReciboRetiroPropietario(retiro);
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Detalle retiro propietario");
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
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtImporte, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("Moneda");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel11, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(btnPagar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
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
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtMoneda, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Tipo de caja");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel10, gridBagConstraints);

        cbTipoDeCaja.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbTipoDeCaja, gridBagConstraints);

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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtImporte;
    private javax.swing.JTextField txtMoneda;
    // End of variables declaration//GEN-END:variables

}
