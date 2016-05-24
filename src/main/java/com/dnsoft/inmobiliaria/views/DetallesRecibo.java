package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.beans.Situacion;
import com.dnsoft.inmobiliaria.beans.TipoContrato;
import com.dnsoft.inmobiliaria.daos.IRecibosDAO;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ControlarEntradaTexto;
import com.dnsoft.inmobiliaria.utils.OptionPaneEstandar;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.Date;
import javax.swing.JOptionPane;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Diego Noble
 */
public class DetallesRecibo extends javax.swing.JDialog {

    IRecibosDAO recibosDAO;
    Container container;
    Recibo recibo;
    Contrato contratoSeleccionado;
    ApplicationContext applicationContext;

    public DetallesRecibo(java.awt.Frame parent, boolean modal, Recibo recibo) {
        super(parent, modal);
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logoTrans.png")));
        //CIERRA JOPTIONPANE CON ESCAPE
        jPanel1.grabFocus();
        jPanel1.addKeyListener(new OptionPaneEstandar(this));
        
        this.container = Container.getInstancia();
        recibosDAO = container.getBean(IRecibosDAO.class);
        this.recibo = recibo;
        this.contratoSeleccionado = recibo.getContrato();
        setLocationRelativeTo(null);
        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
        txtValor.setDocument(new ControlarEntradaTexto(10, chs));
        txtNroRecibo.setDocument(new ControlarEntradaTexto(10, chs));
        detallesRecibo();
        inicio();
        btnGuardarNuevo.setVisible(false);
        contratoSeleccionado = recibo.getContrato();

    }

    public DetallesRecibo(java.awt.Frame parent, boolean modal, Contrato contratoSeleccionado) {
        super(parent, modal);
        initComponents();

        this.container = Container.getInstancia();
        recibosDAO = container.getBean(IRecibosDAO.class);
        this.contratoSeleccionado = contratoSeleccionado;
        inicio();
        setLocationRelativeTo(null);
        btnGuardarModificacion.setVisible(false);
        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
        txtValor.setDocument(new ControlarEntradaTexto(10, chs));
        txtNroRecibo.setDocument(new ControlarEntradaTexto(10, chs));
    }

    void detallesRecibo() {

        txtNroRecibo.setText(recibo.getNroRecibo().toString());
        txtValor.setText(recibo.getSaldo().toString());
        dpVencimiento.setDate(recibo.getFechaVencimiento());

    }

    void inicio() {
        if (contratoSeleccionado.getTipoContrato() == TipoContrato.VENTA) {
            jLabel5.setVisible(false);
            jLabel6.setVisible(false);
            dpPeriodoFIn.setVisible(false);
            dpPeriodoInicio.setVisible(false);
        } else {
            jLabel5.setVisible(true);
            jLabel6.setVisible(true);
            dpPeriodoFIn.setVisible(true);
            dpPeriodoInicio.setVisible(true);
        }
    }

    void guardarCambios() {

        recibo.setFechaVencimiento(dpVencimiento.getDate());
        recibo.setSaldo(new BigDecimal(txtValor.getText()));
        recibo.setValor(new BigDecimal(txtValor.getText()));
        recibo.setNroRecibo(Integer.valueOf(txtNroRecibo.getText()));

        if (contratoSeleccionado.getTipoContrato() == TipoContrato.ALQUILER) {

            recibo.setPeriodo_desde(dpPeriodoInicio.getDate());
            recibo.setPeriodo_hasta(dpPeriodoFIn.getDate());
        }

        recibosDAO.save(recibo);
        this.dispose();
    }

    void nuevoRecibo() {

        recibo = new Recibo();
        recibo.setNroRecibo(Integer.valueOf(txtNroRecibo.getText()));
        recibo.setFechaEmision(new Date());
        recibo.setSituacion(Situacion.PENDIENTE);
        recibo.setContrato(contratoSeleccionado);
        recibo.setFechaVencimiento(dpVencimiento.getDate());
        recibo.setValor(new BigDecimal(txtValor.getText()));
        recibo.setSaldo(new BigDecimal(txtValor.getText()));
        if (contratoSeleccionado.getTipoContrato() == TipoContrato.ALQUILER) {

            recibo.setPeriodo_desde(dpPeriodoInicio.getDate());
            recibo.setPeriodo_hasta(dpPeriodoFIn.getDate());
        }
        recibo.setMoneda(contratoSeleccionado.getMoneda());

        recibo.setCantidadRecibos(1);
        recibosDAO.save(recibo);
        this.dispose();

    }

    void guardaNuevo() {

        if (recibosDAO.findByContratoAndNroRecibo(contratoSeleccionado, Integer.valueOf(txtNroRecibo.getText())) != null) {
            int resp = JOptionPane.showConfirmDialog(null, "Existe un recibo con el mismo nro, confirma la generacion del recibo?", "Atención!", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                nuevoRecibo();

            }
        } else {
            nuevoRecibo();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnGuardarModificacion = new javax.swing.JButton();
        btnGuardarNuevo = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        dpVencimiento = new org.jdesktop.swingx.JXDatePicker();
        jLabel1 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        dpPeriodoInicio = new org.jdesktop.swingx.JXDatePicker();
        jLabel6 = new javax.swing.JLabel();
        dpPeriodoFIn = new org.jdesktop.swingx.JXDatePicker();
        jLabel2 = new javax.swing.JLabel();
        txtNroRecibo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Detalles recibo");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        btnGuardarModificacion.setText("Guardar modificaciones");
        btnGuardarModificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarModificacionActionPerformed(evt);
            }
        });
        btnGuardarModificacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnGuardarModificacionKeyPressed(evt);
            }
        });
        jPanel3.add(btnGuardarModificacion);

        btnGuardarNuevo.setText("Guardar nuevo");
        btnGuardarNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarNuevoActionPerformed(evt);
            }
        });
        btnGuardarNuevo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnGuardarNuevoKeyPressed(evt);
            }
        });
        jPanel3.add(btnGuardarNuevo);

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

        jLabel4.setText("Vencimiento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel1.add(jLabel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(dpVencimiento, gridBagConstraints);

        jLabel1.setText("Nro Recibo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel1.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtValor, gridBagConstraints);

        jLabel5.setText("Período");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(dpPeriodoInicio, gridBagConstraints);

        jLabel6.setText("al ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(dpPeriodoFIn, gridBagConstraints);

        jLabel2.setText("Saldo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        jPanel1.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtNroRecibo, gridBagConstraints);

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

    private void btnGuardarModificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarModificacionActionPerformed

        guardarCambios();

    }//GEN-LAST:event_btnGuardarModificacionActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        this.dispose();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarModificacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGuardarModificacionKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            guardarCambios();
        }

    }//GEN-LAST:event_btnGuardarModificacionKeyPressed

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
        this.dispose();

    }//GEN-LAST:event_btnCancelarKeyPressed

    private void btnGuardarNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarNuevoActionPerformed
        guardaNuevo();
    }//GEN-LAST:event_btnGuardarNuevoActionPerformed

    private void btnGuardarNuevoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGuardarNuevoKeyPressed
        guardaNuevo();
    }//GEN-LAST:event_btnGuardarNuevoKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardarModificacion;
    private javax.swing.JButton btnGuardarNuevo;
    public org.jdesktop.swingx.JXDatePicker dpPeriodoFIn;
    public org.jdesktop.swingx.JXDatePicker dpPeriodoInicio;
    public org.jdesktop.swingx.JXDatePicker dpVencimiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtNroRecibo;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
