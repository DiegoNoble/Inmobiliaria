package com.dnsoft.inmobiliaria.views;

public class ControlDeCajaView extends javax.swing.JInternalFrame {

    public ControlDeCajaView() {
        initComponents();

       
}

@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        menuEntradas = new javax.swing.JPopupMenu();
        btnAlquileresGastos = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        btnAportes = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        btnGastosPropietarios = new javax.swing.JMenuItem();
        menuSalidas = new javax.swing.JPopupMenu();
        btnRetirosPropietarios = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenu1 = new javax.swing.JMenu();
        btnRetirosPatronales = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        btnVales = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        btnTransferencias = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        jMenu3 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        btnInsumosOficina = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        btnInsumosCocinaLimpieza = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        btnSueldos = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        btnImpuestos = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        btnObrasMantenimiento = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        btnPagoFacturas = new javax.swing.JMenuItem();
        jSeparator17 = new javax.swing.JPopupMenu.Separator();
        jMenu4 = new javax.swing.JMenu();
        btnObrasMantenimiento1 = new javax.swing.JMenuItem();
        jSeparator18 = new javax.swing.JPopupMenu.Separator();
        btnOtrosGastos = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        btnIRPF_IVA = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dpFecha = new org.jdesktop.swingx.JXDatePicker();
        jLabel9 = new javax.swing.JLabel();
        cbMoneda = new javax.swing.JComboBox();
        txtSaldoAnterior = new javax.swing.JTextField();
        btnExcel = new botones.BotonExcel();
        jLabel6 = new javax.swing.JLabel();
        txtSaldo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cbTipoDeCaja = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnEntradas = new botones.BotonPagar();
        btnSalidas = new botones.BotonPagar();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMovimientos = new javax.swing.JTable();

        btnAlquileresGastos.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnAlquileresGastos.setText("Alquileres / Cuotas / Gastos inquilinos");
        menuEntradas.add(btnAlquileresGastos);
        menuEntradas.add(jSeparator1);

        btnAportes.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnAportes.setText("Aportes");
        menuEntradas.add(btnAportes);
        menuEntradas.add(jSeparator12);

        btnGastosPropietarios.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnGastosPropietarios.setText("Pago deudas propietarios");
        menuEntradas.add(btnGastosPropietarios);

        btnRetirosPropietarios.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnRetirosPropietarios.setText("Retiros propietarios");
        menuSalidas.add(btnRetirosPropietarios);
        menuSalidas.add(jSeparator2);

        jMenu1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jMenu1.setText("Retiros");
        jMenu1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N

        btnRetirosPatronales.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnRetirosPatronales.setText("Retiros patronales");
        jMenu1.add(btnRetirosPatronales);
        jMenu1.add(jSeparator3);

        btnVales.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnVales.setText("Vales");
        jMenu1.add(btnVales);
        jMenu1.add(jSeparator4);

        btnTransferencias.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnTransferencias.setText("Transferencias");
        jMenu1.add(btnTransferencias);

        menuSalidas.add(jMenu1);
        menuSalidas.add(jSeparator10);

        jMenu3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jMenu3.setText("Gastos");
        jMenu3.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N

        jMenu2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jMenu2.setText("Gastos Inmobiliaria");
        jMenu2.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N

        btnInsumosOficina.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnInsumosOficina.setText("Insumos Oficina");
        jMenu2.add(btnInsumosOficina);
        jMenu2.add(jSeparator5);

        btnInsumosCocinaLimpieza.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnInsumosCocinaLimpieza.setText("Insumos cocina y limpieza");
        jMenu2.add(btnInsumosCocinaLimpieza);
        jMenu2.add(jSeparator6);

        btnSueldos.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnSueldos.setText("Sueldos");
        jMenu2.add(btnSueldos);
        jMenu2.add(jSeparator7);

        btnImpuestos.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnImpuestos.setText("Impuestos");
        jMenu2.add(btnImpuestos);
        jMenu2.add(jSeparator8);

        btnObrasMantenimiento.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnObrasMantenimiento.setText("Obras y Mantenimiento");
        jMenu2.add(btnObrasMantenimiento);
        jMenu2.add(jSeparator9);

        btnPagoFacturas.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnPagoFacturas.setText("Pago facturas");
        jMenu2.add(btnPagoFacturas);

        jMenu3.add(jMenu2);
        jMenu3.add(jSeparator17);

        jMenu4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jMenu4.setText("De 3eros");
        jMenu4.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N

        btnObrasMantenimiento1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnObrasMantenimiento1.setText("Obras y Mantenimiento");
        jMenu4.add(btnObrasMantenimiento1);
        jMenu4.add(jSeparator18);

        btnOtrosGastos.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnOtrosGastos.setText("Otros gastos");
        jMenu4.add(btnOtrosGastos);

        jMenu3.add(jMenu4);

        menuSalidas.add(jMenu3);
        menuSalidas.add(jSeparator11);

        btnIRPF_IVA.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnIRPF_IVA.setText("IRPF / IVA");
        menuSalidas.add(btnIRPF_IVA);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(700, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Fecha Producción");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Saldo actual");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(dpFecha, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Moneda");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel9, gridBagConstraints);

        cbMoneda.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbMoneda, gridBagConstraints);

        txtSaldoAnterior.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtSaldoAnterior.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtSaldoAnterior, gridBagConstraints);

        btnExcel.setPreferredSize(new java.awt.Dimension(80, 110));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(btnExcel, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Saldo Anterior");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel6, gridBagConstraints);

        txtSaldo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtSaldo.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtSaldo, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Tipo de caja");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel10, gridBagConstraints);

        cbTipoDeCaja.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbTipoDeCaja, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 400));

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel4.setText("Movimientos de Caja"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel4)
                .addContainerGap(134, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel4))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        btnEntradas.setText("ENTRADAS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        jPanel3.add(btnEntradas, gridBagConstraints);

        btnSalidas.setText("SALIDAS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        jPanel3.add(btnSalidas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        tblMovimientos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblMovimientos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JMenuItem btnAlquileresGastos;
    public javax.swing.JMenuItem btnAportes;
    public botones.BotonPagar btnEntradas;
    public botones.BotonExcel btnExcel;
    public javax.swing.JMenuItem btnGastosPropietarios;
    public javax.swing.JMenuItem btnIRPF_IVA;
    public javax.swing.JMenuItem btnImpuestos;
    public javax.swing.JMenuItem btnInsumosCocinaLimpieza;
    public javax.swing.JMenuItem btnInsumosOficina;
    public javax.swing.JMenuItem btnObrasMantenimiento;
    public javax.swing.JMenuItem btnObrasMantenimiento1;
    public javax.swing.JMenuItem btnOtrosGastos;
    public javax.swing.JMenuItem btnPagoFacturas;
    public javax.swing.JMenuItem btnRetirosPatronales;
    public javax.swing.JMenuItem btnRetirosPropietarios;
    public botones.BotonPagar btnSalidas;
    public javax.swing.JMenuItem btnSueldos;
    public javax.swing.JMenuItem btnTransferencias;
    public javax.swing.JMenuItem btnVales;
    private javax.swing.ButtonGroup buttonGroup1;
    public javax.swing.JComboBox cbMoneda;
    public javax.swing.JComboBox cbTipoDeCaja;
    public org.jdesktop.swingx.JXDatePicker dpFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JMenu jMenu1;
    public javax.swing.JMenu jMenu2;
    public javax.swing.JMenu jMenu3;
    public javax.swing.JMenu jMenu4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator17;
    private javax.swing.JPopupMenu.Separator jSeparator18;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    public javax.swing.JPopupMenu menuEntradas;
    public javax.swing.JPopupMenu menuSalidas;
    public javax.swing.JTable tblMovimientos;
    public javax.swing.JTextField txtSaldo;
    public javax.swing.JTextField txtSaldoAnterior;
    // End of variables declaration//GEN-END:variables
}
