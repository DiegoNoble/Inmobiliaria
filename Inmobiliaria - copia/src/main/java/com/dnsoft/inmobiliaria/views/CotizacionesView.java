package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.Cotizacion;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.daos.ICotizacionDAO;
import com.dnsoft.inmobiliaria.models.CotizacionTableModel;
import com.dnsoft.inmobiliaria.utils.ButtonColumnDetalles;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ControlarEntradaTexto;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public final class CotizacionesView extends javax.swing.JInternalFrame {

    private List<Cotizacion> listCotizacion;
    //ApplicationContext applicationContext;
    ICotizacionDAO cotizacionDAO;
    CotizacionTableModel tableModel;
    ButtonColumnDetalles btnEditar;
    Container container;

    public CotizacionesView() {

        initComponents();
        //this.applicationContext = applicationContext;
        container = Container.getInstancia();
        cotizacionDAO = container.getBean(ICotizacionDAO.class);
        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-'};
        txtDolares.setDocument(new ControlarEntradaTexto(10, chs));
        txtPesos.setDocument(new ControlarEntradaTexto(10, chs));
        txtUI.setDocument(new ControlarEntradaTexto(10, chs));
        txtUR.setDocument(new ControlarEntradaTexto(10, chs));
        buscaUltimasCotizaciones();
        accionesBotones();
    }

    void desahbilitaCampos() {
        buscaUltimasCotizaciones();
        txtPesos.setText("1.0");
        txtDolares.setEnabled(false);
        txtUI.setEnabled(false);
        txtUR.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnNuevaCotizacion.setEnabled(true);
    }

    void habilitaCampos() {
        //txtPesos.setEnabled(true);
        txtPesos.setText("1.0");
        txtDolares.setEnabled(true);
        txtUI.setEnabled(true);
        txtUR.setEnabled(true);
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
    }

    void accionesBotones() {
        btnNuevaCotizacion.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                habilitaCampos();
            }
        });
        btnCancelar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                desahbilitaCampos();

            }
        });
        btnGuardar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {

                guardarCotizaciones();
            }

        });

    }

    void buscaUltimasCotizaciones() {

        List<Cotizacion> cotizaciones = new ArrayList<>();

        for (Moneda moneda : Moneda.values()) {
            Cotizacion ultimaCotizacion = cotizacionDAO.findLastCotizacion(moneda);
            if (ultimaCotizacion != null) {
                cotizaciones.add(ultimaCotizacion);
            }
        }
        if (!cotizaciones.isEmpty()) {

            txtPesos.setText(cotizaciones.get(0).getValor().toString());
            txtDolares.setText(cotizaciones.get(1).getValor().toString());
            txtUI.setText(cotizaciones.get(2).getValor().toString());
            txtUR.setText(cotizaciones.get(3).getValor().toString());
        }
    }

    private void guardarCotizaciones() {
        listCotizacion = new ArrayList<>();
        Cotizacion pesos = new Cotizacion();
        pesos.setFecha(new Date());
        pesos.setMoneda(Moneda.PESOS);
        pesos.setValor(BigDecimal.valueOf(Double.parseDouble(txtPesos.getText())));
        listCotizacion.add(pesos);

        Cotizacion dolares = new Cotizacion();
        dolares.setFecha(new Date());
        dolares.setMoneda(Moneda.DOLARES);
        dolares.setValor(BigDecimal.valueOf(Double.parseDouble(txtDolares.getText())));
        listCotizacion.add(dolares);

        Cotizacion ui = new Cotizacion();
        ui.setFecha(new Date());
        ui.setMoneda(Moneda.UNIDADES_INDEXADAS);
        ui.setValor(BigDecimal.valueOf(Double.parseDouble(txtUI.getText())));
        listCotizacion.add(ui);

        Cotizacion ur = new Cotizacion();
        ur.setFecha(new Date());
        ur.setMoneda(Moneda.UNIDADES_REAJUSTABLES);
        ur.setValor(BigDecimal.valueOf(Double.parseDouble(txtUR.getText())));
        listCotizacion.add(ur);

        cotizacionDAO.save(listCotizacion);
        desahbilitaCampos();
        JOptionPane.showMessageDialog(this, "Se registraron los datos correctamente!", "Correcto!", JOptionPane.INFORMATION_MESSAGE);

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel3 = new javax.swing.JPanel();
        btnNuevaCotizacion = new botones.BotonNuevo();
        btnGuardar = new botones.BotonGuardar();
        btnCancelar = new botones.BotonCancelar();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtPesos = new javax.swing.JTextField();
        txtDolares = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtUI = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtUR = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnDetallePesos = new javax.swing.JButton();
        btnDetalleDolares = new javax.swing.JButton();
        btnDetalleUI = new javax.swing.JButton();
        btnDetalleUR = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setClosable(true);
        setPreferredSize(new java.awt.Dimension(400, 350));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel3.setLayout(new java.awt.GridBagLayout());

        btnNuevaCotizacion.setPreferredSize(new java.awt.Dimension(105, 60));
        btnNuevaCotizacion.setText("Nueva cotización");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnNuevaCotizacion, gridBagConstraints);

        btnGuardar.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnGuardar, gridBagConstraints);
        jPanel3.add(btnCancelar, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Pesos");
        jPanel4.add(jLabel2, new java.awt.GridBagConstraints());

        txtPesos.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtPesos, gridBagConstraints);

        txtDolares.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtDolares, gridBagConstraints);

        jLabel4.setText("Dólares");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel4.add(jLabel4, gridBagConstraints);

        txtUI.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtUI, gridBagConstraints);

        jLabel5.setText("UI");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel4.add(jLabel5, gridBagConstraints);

        txtUR.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtUR, gridBagConstraints);

        jLabel6.setText("UR");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel4.add(jLabel6, gridBagConstraints);

        btnDetallePesos.setText("Detalles");
        btnDetallePesos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetallePesosActionPerformed(evt);
            }
        });
        jPanel4.add(btnDetallePesos, new java.awt.GridBagConstraints());

        btnDetalleDolares.setText("Detalles");
        btnDetalleDolares.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetalleDolaresActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel4.add(btnDetalleDolares, gridBagConstraints);

        btnDetalleUI.setText("Detalles");
        btnDetalleUI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetalleUIActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanel4.add(btnDetalleUI, gridBagConstraints);

        btnDetalleUR.setText("Detalles");
        btnDetalleUR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetalleURActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        jPanel4.add(btnDetalleUR, gridBagConstraints);

        jPanel1.add(jPanel4, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Cotizaciones");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 1;
        getContentPane().add(jPanel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDetallePesosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetallePesosActionPerformed
        /*ConsultaCotizacionDialog detalles = new ConsultaCotizacionDialog(null, true, applicationContext, Moneda.PESOS);
        detalles.setVisible(true);
        detalles.toFront();*/
    }//GEN-LAST:event_btnDetallePesosActionPerformed

    private void btnDetalleDolaresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetalleDolaresActionPerformed
        /*ConsultaCotizacionDialog detalles = new ConsultaCotizacionDialog(null, true, applicationContext, Moneda.DOLARES);
        detalles.setVisible(true);
        detalles.toFront();*/
    }//GEN-LAST:event_btnDetalleDolaresActionPerformed

    private void btnDetalleUIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetalleUIActionPerformed
        /*ConsultaCotizacionDialog detalles = new ConsultaCotizacionDialog(null, true, applicationContext, Moneda.UNIDADES_INDEXADAS);
        detalles.setVisible(true);
        detalles.toFront();*/

    }//GEN-LAST:event_btnDetalleUIActionPerformed

    private void btnDetalleURActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetalleURActionPerformed
        /*ConsultaCotizacionDialog detalles = new ConsultaCotizacionDialog(null, true, applicationContext, Moneda.UNIDADES_REAJUSTABLES);
        detalles.setVisible(true);
        detalles.toFront();*/
    }//GEN-LAST:event_btnDetalleURActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BotonCancelar btnCancelar;
    private javax.swing.JButton btnDetalleDolares;
    private javax.swing.JButton btnDetallePesos;
    private javax.swing.JButton btnDetalleUI;
    private javax.swing.JButton btnDetalleUR;
    private botones.BotonGuardar btnGuardar;
    private botones.BotonNuevo btnNuevaCotizacion;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField txtDolares;
    private javax.swing.JTextField txtPesos;
    private javax.swing.JTextField txtUI;
    private javax.swing.JTextField txtUR;
    // End of variables declaration//GEN-END:variables

}
