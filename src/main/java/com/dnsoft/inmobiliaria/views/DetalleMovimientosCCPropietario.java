/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.utils.OptionPaneEstandar;
import java.awt.Toolkit;

/**
 *
 * @author Diego Noble
 */
public class DetalleMovimientosCCPropietario extends javax.swing.JDialog {

    /**
     * Creates new form DetalleMovimientosCCPropietario
     */
    public DetalleMovimientosCCPropietario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logoTrans.png")));
        //CIERRA JOPTIONPANE CON ESCAPE
        jPanel2.grabFocus();
        jPanel2.addKeyListener(new OptionPaneEstandar(this));

        btnActualizaSaldoD.setVisible(false);
        btnActualizaSaldoP.setVisible(false);

       /* this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("Tecla apretada");
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("entro");
                if (e.getKeyCode() == 27) {//27 es el código de la tecla esc
                    System.out.println("Adios!");
                    dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("Después de soltar la tecla!");
            }
        });*/

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        btnActualizaSaldoP = new javax.swing.JButton();
        botonVolver1 = new botones.BotonVolver();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblCCPesos = new javax.swing.JTable();
        txtSaldoPesos = new javax.swing.JTextField();
        btnRetiroPesos = new botones.BotonPagar();
        jLabel6 = new javax.swing.JLabel();
        btnEntregaPesos = new botones.BotonPagar();
        btnPDFPesos = new botones.BotonPDF();
        btnExcelPesos = new botones.BotonExcel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblCCDolares = new javax.swing.JTable();
        txtSaldoDolares = new javax.swing.JTextField();
        btnRetiroDolares = new botones.BotonPagar();
        jLabel7 = new javax.swing.JLabel();
        btnEntregaDolares = new botones.BotonPagar();
        btnPDFDolares = new botones.BotonPDF();
        btnExcelDolares = new botones.BotonExcel();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        dpInicio = new org.jdesktop.swingx.JXDatePicker();
        jLabel4 = new javax.swing.JLabel();
        dpFin = new org.jdesktop.swingx.JXDatePicker();
        lblPropietario = new javax.swing.JLabel();
        btnActualizaSaldoD = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1024, 750));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        btnActualizaSaldoP.setText("Actualiza Saldos Pesos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        getContentPane().add(btnActualizaSaldoP, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(botonVolver1, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Cuenta Corriente Propietario");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jPanel2, gridBagConstraints);

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jPanel8.setLayout(new java.awt.GridBagLayout());

        tblCCPesos.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tblCCPesos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblCCPesos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jScrollPane3, gridBagConstraints);

        txtSaldoPesos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSaldoPesos.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(txtSaldoPesos, gridBagConstraints);

        btnRetiroPesos.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnRetiroPesos.setText("Registrar retiro en PESOS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(btnRetiroPesos, gridBagConstraints);

        jLabel6.setText("Saldo disponible en PESOS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel8.add(jLabel6, gridBagConstraints);

        btnEntregaPesos.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnEntregaPesos.setPreferredSize(new java.awt.Dimension(20, 60));
        btnEntregaPesos.setText("Aporte a cuenta PESOS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(btnEntregaPesos, gridBagConstraints);

        btnPDFPesos.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnPDFPesos.setPreferredSize(new java.awt.Dimension(80, 100));
        btnPDFPesos.setText("Exportar a PDF CC PESOS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(btnPDFPesos, gridBagConstraints);

        btnExcelPesos.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnExcelPesos.setPreferredSize(new java.awt.Dimension(80, 100));
        btnExcelPesos.setText("Exportar a Excel CC Pesos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(btnExcelPesos, gridBagConstraints);

        jTabbedPane1.addTab("Cuenta Corriente PESOS", jPanel8);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        tblCCDolares.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tblCCDolares.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tblCCDolares);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(jScrollPane5, gridBagConstraints);

        txtSaldoDolares.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSaldoDolares.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(txtSaldoDolares, gridBagConstraints);

        btnRetiroDolares.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnRetiroDolares.setText("Registrar retiro en DOLARES");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(btnRetiroDolares, gridBagConstraints);

        jLabel7.setText("Saldo disponible en DOLARES");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel6.add(jLabel7, gridBagConstraints);

        btnEntregaDolares.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnEntregaDolares.setPreferredSize(new java.awt.Dimension(20, 60));
        btnEntregaDolares.setText("Aporte a cuenta DOLARES");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(btnEntregaDolares, gridBagConstraints);

        btnPDFDolares.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnPDFDolares.setPreferredSize(new java.awt.Dimension(80, 100));
        btnPDFDolares.setText("Exportar a PDF CC DOLARES");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(btnPDFDolares, gridBagConstraints);

        btnExcelDolares.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnExcelDolares.setPreferredSize(new java.awt.Dimension(80, 100));
        btnExcelDolares.setText("Exportar a Excel CC DOLARES");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(btnExcelDolares, gridBagConstraints);

        jTabbedPane1.addTab("Cuenta Corriente DOLARES", jPanel6);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jTabbedPane1, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle Cuenta Corriente"));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel5.setText("Período:");
        jPanel4.add(jLabel5, new java.awt.GridBagConstraints());
        jPanel4.add(dpInicio, new java.awt.GridBagConstraints());

        jLabel4.setText("al");
        jPanel4.add(jLabel4, new java.awt.GridBagConstraints());
        jPanel4.add(dpFin, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        jPanel5.add(jPanel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel5, gridBagConstraints);

        lblPropietario.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        lblPropietario.setText("jLabel1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(lblPropietario, gridBagConstraints);

        btnActualizaSaldoD.setText("Actualiza Saldos Dolares");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        getContentPane().add(btnActualizaSaldoD, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed


    }//GEN-LAST:event_formKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public botones.BotonVolver botonVolver1;
    public javax.swing.JButton btnActualizaSaldoD;
    public javax.swing.JButton btnActualizaSaldoP;
    public botones.BotonPagar btnEntregaDolares;
    public botones.BotonPagar btnEntregaPesos;
    public botones.BotonExcel btnExcelDolares;
    public botones.BotonExcel btnExcelPesos;
    public botones.BotonPDF btnPDFDolares;
    public botones.BotonPDF btnPDFPesos;
    public botones.BotonPagar btnRetiroDolares;
    public botones.BotonPagar btnRetiroPesos;
    public org.jdesktop.swingx.JXDatePicker dpFin;
    public org.jdesktop.swingx.JXDatePicker dpInicio;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JLabel lblPropietario;
    public javax.swing.JTable tblCCDolares;
    public javax.swing.JTable tblCCPesos;
    public javax.swing.JTextField txtSaldoDolares;
    public javax.swing.JTextField txtSaldoPesos;
    // End of variables declaration//GEN-END:variables
}
