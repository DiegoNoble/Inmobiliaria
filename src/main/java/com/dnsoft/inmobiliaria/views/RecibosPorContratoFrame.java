/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.utils.FrameEstandar;

/**
 *
 * @author Diego Noble
 */
public class RecibosPorContratoFrame extends FrameEstandar {

    //Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logoTrans.png"));
    public RecibosPorContratoFrame() {
        initComponents();
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRecibos = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        rbPendientes = new javax.swing.JRadioButton();
        rbPagos = new javax.swing.JRadioButton();
        rbTodo = new javax.swing.JRadioButton();
        btnVolver = new botones.BotonVolver();
        btnPagar = new botones.BotonPagar();
        btnRegistarComoPago = new botones.BotonPagar();
        btnNuevoRecibo = new botones.BotonNuevo();
        btnEditar = new botones.BotonEdicion();
        btnAnularPago = new botones.BotonCancelar();
        btnImprimeRecibo = new botones.BotonPDF();
        btnEliminar = new botones.BotonEliminar();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblInquilinos = new javax.swing.JTable();
        btnDetallePagosInquilino = new botones.BotonBuscar();
        btnPagarGastoInquilino = new botones.BotonPagar();
        lblContrato = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Recibos por Contrato");
        setPreferredSize(new java.awt.Dimension(1024, 750));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Recibos");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel8.setLayout(new java.awt.GridBagLayout());

        tblRecibos.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tblRecibos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblRecibos.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane3.setViewportView(tblRecibos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jScrollPane3, gridBagConstraints);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ver:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel1.setLayout(new java.awt.GridBagLayout());

        buttonGroup1.add(rbPendientes);
        rbPendientes.setSelected(true);
        rbPendientes.setText("Pendientes");
        jPanel1.add(rbPendientes, new java.awt.GridBagConstraints());

        buttonGroup1.add(rbPagos);
        rbPagos.setText("Pagos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel1.add(rbPagos, gridBagConstraints);

        buttonGroup1.add(rbTodo);
        rbTodo.setText("Todos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel1.add(rbTodo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 8;
        jPanel8.add(jPanel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel8.add(btnVolver, gridBagConstraints);

        btnPagar.setEnabled(false);
        btnPagar.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        btnPagar.setPreferredSize(new java.awt.Dimension(80, 100));
        btnPagar.setText("Pagar cuota/alquiler");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(btnPagar, gridBagConstraints);

        btnRegistarComoPago.setEnabled(false);
        btnRegistarComoPago.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        btnRegistarComoPago.setPreferredSize(new java.awt.Dimension(80, 100));
        btnRegistarComoPago.setText("Registrar cuota como paga");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(btnRegistarComoPago, gridBagConstraints);

        btnNuevoRecibo.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        btnNuevoRecibo.setPreferredSize(new java.awt.Dimension(80, 60));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(btnNuevoRecibo, gridBagConstraints);

        btnEditar.setEnabled(false);
        btnEditar.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        btnEditar.setText("Modificar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(btnEditar, gridBagConstraints);

        btnAnularPago.setEnabled(false);
        btnAnularPago.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnAnularPago.setText("Anular pago");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(btnAnularPago, gridBagConstraints);

        btnImprimeRecibo.setEnabled(false);
        btnImprimeRecibo.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnImprimeRecibo.setPreferredSize(new java.awt.Dimension(80, 100));
        btnImprimeRecibo.setText("Reimprimir recibo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(btnImprimeRecibo, gridBagConstraints);

        btnEliminar.setEnabled(false);
        btnEliminar.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(btnEliminar, gridBagConstraints);

        jTabbedPane1.addTab("Alquileres/Cuotas", jPanel8);

        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel7.setLayout(new java.awt.GridBagLayout());

        tblInquilinos.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tblInquilinos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblInquilinos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(tblInquilinos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(jScrollPane4, gridBagConstraints);

        btnDetallePagosInquilino.setEnabled(false);
        btnDetallePagosInquilino.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        btnDetallePagosInquilino.setText("Detalles pago");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel7.add(btnDetallePagosInquilino, gridBagConstraints);

        btnPagarGastoInquilino.setEnabled(false);
        btnPagarGastoInquilino.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        btnPagarGastoInquilino.setText("Pagar gasto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel7.add(btnPagarGastoInquilino, gridBagConstraints);

        jTabbedPane1.addTab("Gastos", jPanel7);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jTabbedPane1, gridBagConstraints);

        lblContrato.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        lblContrato.setForeground(new java.awt.Color(0, 0, 255));
        lblContrato.setText("jLabel1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(lblContrato, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public botones.BotonCancelar btnAnularPago;
    public botones.BotonBuscar btnDetallePagosInquilino;
    public botones.BotonEdicion btnEditar;
    public botones.BotonEliminar btnEliminar;
    public botones.BotonPDF btnImprimeRecibo;
    public botones.BotonNuevo btnNuevoRecibo;
    public botones.BotonPagar btnPagar;
    public botones.BotonPagar btnPagarGastoInquilino;
    public botones.BotonPagar btnRegistarComoPago;
    public botones.BotonVolver btnVolver;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JLabel lblContrato;
    public javax.swing.JRadioButton rbPagos;
    public javax.swing.JRadioButton rbPendientes;
    public javax.swing.JRadioButton rbTodo;
    public javax.swing.JTable tblInquilinos;
    public javax.swing.JTable tblRecibos;
    // End of variables declaration//GEN-END:variables
}
