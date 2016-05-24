/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.Renderers.MeDefaultCellRenderer;
import com.dnsoft.inmobiliaria.Renderers.MeTimeCellRenderer;
import com.dnsoft.inmobiliaria.beans.Cotizacion;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.daos.ICotizacionDAO;
import com.dnsoft.inmobiliaria.models.CotizacionTableModel;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.OptionPaneEstandar;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author dnoble
 */
public final class ConsultaCotizacionDialog extends javax.swing.JDialog {

    private List<Cotizacion> listCotizacion;
    Container container;
    ICotizacionDAO cotizacionDAO;
    CotizacionTableModel tableModel;

    public ConsultaCotizacionDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logoTrans.png")));
        //CIERRA JOPTIONPANE CON ESCAPE
        jPanel1.grabFocus();
        jPanel1.addKeyListener(new OptionPaneEstandar(this));
        
        this.container = Container.getInstancia();
        inicio();
    }

    public ConsultaCotizacionDialog(java.awt.Frame parent, boolean modal, Moneda moneda) {
        super(parent, modal);
        initComponents();
        this.container = Container.getInstancia();
        inicio();
        switch (moneda) {
            case PESOS:
                rbPesos.setSelected(true);
                break;
            case DOLARES:
                rbDolares.setSelected(true);
                break;
            case UNIDADES_INDEXADAS:
                rbUI.setSelected(true);
                break;
            case UNIDADES_REAJUSTABLES:
                rbUR.setSelected(true);
                break;
            default:
                throw new AssertionError();
        }

    }

    void inicio() {

        rbPesos.setVisible(false);
        cotizacionDAO = container.getBean(ICotizacionDAO.class);
        defineModelo();
        buscarCotizaciones();
        accionesBotones();
        setLocationRelativeTo(null);
    }

    void accionesBotones() {
        botonVolver1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                ConsultaCotizacionDialog.this.dispose();
            }
        });
    }

    private void defineModelo() {

        ((DefaultTableCellRenderer) tblCotizacion.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listCotizacion = new ArrayList<>();

        tableModel = new CotizacionTableModel(listCotizacion);
        tblCotizacion.setModel(tableModel);

        tblCotizacion.getColumn("Valor").setCellRenderer(new MeDefaultCellRenderer());
        tblCotizacion.getColumn("Fecha").setCellRenderer(new MeTimeCellRenderer());

        tblCotizacion.setRowHeight(25);

    }

    void buscarCotizaciones() {

        listCotizacion.clear();
        if (rbDolares.isSelected()) {
            listCotizacion.addAll(cotizacionDAO.findByMonedaOrderByFechaDesc(Moneda.DOLARES));
        }
        if (rbPesos.isSelected()) {
            listCotizacion.addAll(cotizacionDAO.findByMonedaOrderByFechaDesc(Moneda.PESOS));
        }
        if (rbUI.isSelected()) {
            listCotizacion.addAll(cotizacionDAO.findByMonedaOrderByFechaDesc(Moneda.UNIDADES_INDEXADAS));
        }
        if (rbUR.isSelected()) {
            listCotizacion.addAll(cotizacionDAO.findByMonedaOrderByFechaDesc(Moneda.UNIDADES_REAJUSTABLES));
        }

        tableModel.fireTableDataChanged();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCotizacion = new javax.swing.JTable();
        botonVolver1 = new botones.BotonVolver();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        rbPesos = new javax.swing.JRadioButton();
        rbDolares = new javax.swing.JRadioButton();
        rbUI = new javax.swing.JRadioButton();
        rbUR = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel3.setLayout(new java.awt.GridBagLayout());

        tblCotizacion.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblCotizacion);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jScrollPane1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel3.add(botonVolver1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Monedas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        buttonGroup1.add(rbPesos);
        rbPesos.setText("Pesos");
        rbPesos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbPesosActionPerformed(evt);
            }
        });
        jPanel4.add(rbPesos);

        buttonGroup1.add(rbDolares);
        rbDolares.setSelected(true);
        rbDolares.setText("Dólares");
        rbDolares.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbDolaresActionPerformed(evt);
            }
        });
        jPanel4.add(rbDolares);

        buttonGroup1.add(rbUI);
        rbUI.setText("U.I.");
        rbUI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbUIActionPerformed(evt);
            }
        });
        jPanel4.add(rbUI);

        buttonGroup1.add(rbUR);
        rbUR.setText("U.R.");
        rbUR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbURActionPerformed(evt);
            }
        });
        jPanel4.add(rbUR);

        jPanel1.add(jPanel4, new java.awt.GridBagConstraints());

        jButton1.setText("Cotizaciones reajustes");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Cotizaciones");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 1;
        getContentPane().add(jPanel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbPesosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbPesosActionPerformed
        buscarCotizaciones();
    }//GEN-LAST:event_rbPesosActionPerformed

    private void rbDolaresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbDolaresActionPerformed
        buscarCotizaciones();
    }//GEN-LAST:event_rbDolaresActionPerformed

    private void rbUIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbUIActionPerformed
        buscarCotizaciones();
    }//GEN-LAST:event_rbUIActionPerformed

    private void rbURActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbURActionPerformed
        buscarCotizaciones();
    }//GEN-LAST:event_rbURActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CotizacionReajustesVariablesDialog cotizacionIndicesDialog = new CotizacionReajustesVariablesDialog(null, true);
        cotizacionIndicesDialog.setVisible(true);
        cotizacionIndicesDialog.toFront();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BotonVolver botonVolver1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbDolares;
    private javax.swing.JRadioButton rbPesos;
    private javax.swing.JRadioButton rbUI;
    private javax.swing.JRadioButton rbUR;
    private javax.swing.JTable tblCotizacion;
    // End of variables declaration//GEN-END:variables
}
