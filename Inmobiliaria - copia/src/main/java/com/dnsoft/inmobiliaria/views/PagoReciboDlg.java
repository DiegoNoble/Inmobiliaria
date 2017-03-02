package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ControlarEntradaTexto;
import com.dnsoft.inmobiliaria.utils.PagarRecibo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego Noble
 */
public class PagoReciboDlg extends javax.swing.JDialog {

    Recibo reciboSeleccionado;
    BigDecimal mora;
    BigDecimal valorEntrega;
    Container container;
    BigDecimal importe;

    public PagoReciboDlg(java.awt.Frame parent, boolean modal, Recibo reciboSeleccionado, BigDecimal mora) {
        super(parent, modal);
        initComponents();
        this.container = Container.getInstancia();
        setLocationRelativeTo(null);
        this.reciboSeleccionado = reciboSeleccionado;
        this.mora = mora;

        inicio();
    }

    void inicio() {

        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-'};

        /*if (mora.compareTo(BigDecimal.ZERO) != 0) {
         DetallePagoMora moraaPagar = new DetallePagoMora(null, true, mora);
         moraaPagar.setVisible(true);
         moraaPagar.toFront();
         mora = moraaPagar.mora();
         }*/
        importe = reciboSeleccionado.getSaldo().add(mora);
        txtImporte.setDocument(new ControlarEntradaTexto(10, chs));
        txtImporte.setText(importe.toString());

        accionesBotones();

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
        PagarRecibo pagar;
        try {
            valorEntrega = new BigDecimal(txtImporte.getText()).setScale(2, RoundingMode.CEILING);
            if (valorEntrega.doubleValue() <= reciboSeleccionado.getSaldo().add(mora).doubleValue()) {

                BigDecimal variable = valorEntrega.subtract(reciboSeleccionado.getValor().add(mora));

                if (variable.compareTo(BigDecimal.ZERO) == 0) {//Valor de entrega = a Saldo+Mora, En este caso pago totales, pago todo recibo y toda la mora
                    pagar = new PagarRecibo(reciboSeleccionado, reciboSeleccionado.getValor(), mora);
                    this.dispose();
                
                } else if (valorEntrega.doubleValue() == reciboSeleccionado.getSaldo().doubleValue()) {//Entrega = Saldo, Exento de la mora, paga total recibo
                    pagar = new PagarRecibo(reciboSeleccionado, valorEntrega, BigDecimal.ZERO);
                    this.dispose();

                } else if (valorEntrega.doubleValue() > reciboSeleccionado.getSaldo().doubleValue()) {//Valor entrga es mayor que saldo recibo, la diferencia es mora, pago todo recibo
                    
                    mora = valorEntrega.subtract(reciboSeleccionado.getSaldo());
                    pagar = new PagarRecibo(reciboSeleccionado, valorEntrega.subtract(mora), mora);
                    this.dispose();

                } else {//Pagos parciales

                    BigDecimal morita = valorEntrega.multiply(mora).divide(reciboSeleccionado.getSaldo().add(mora), 2, RoundingMode.CEILING);
                    pagar = new PagarRecibo(reciboSeleccionado, valorEntrega.subtract(morita), morita);
                    this.dispose();
                }
                DetalleMovimientoDeCaja movimientodeCajaRecibo = new DetalleMovimientoDeCaja(null, true, reciboSeleccionado, valorEntrega, pagar, null);
                movimientodeCajaRecibo.setVisible(true);
                movimientodeCajaRecibo.toFront();
            } else {
                JOptionPane.showMessageDialog(null, "El valor ingresado no es correcto, pues supera el saldo pendiente del recibo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar registros: " + e);

        }
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnPagar = new botones.BotonPagar();
        btnVolver = new botones.BotonVolver();
        jLabel12 = new javax.swing.JLabel();
        txtImporte = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Detalles Pago");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());
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
        jLabel12.setText("Total a pagar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel12, gridBagConstraints);

        txtImporte.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtImporte, gridBagConstraints);

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
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtImporte;
    // End of variables declaration//GEN-END:variables
}
