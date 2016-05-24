package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.Banco;
import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.CuentaBancoInquilino;
import com.dnsoft.inmobiliaria.beans.Inquilino;
import com.dnsoft.inmobiliaria.beans.TipoDocumento;
import com.dnsoft.inmobiliaria.daos.IBancoDAO;
import com.dnsoft.inmobiliaria.daos.IBarriosDAO;
import com.dnsoft.inmobiliaria.daos.ICallesDAO;
import com.dnsoft.inmobiliaria.daos.ICiudadesDAO;
import com.dnsoft.inmobiliaria.daos.IContratoDAO;
import com.dnsoft.inmobiliaria.daos.ICuentaBancoInquilinoDAO;
import com.dnsoft.inmobiliaria.daos.IInquilinoDAO;
import com.dnsoft.inmobiliaria.daos.ITipoDocumentoDAO;
import com.dnsoft.inmobiliaria.models.CuentasBancosInquilinoTableModel;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.OptionPaneEstandar;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;

/**
 *
 * @author Diego Noble
 */
public class InquilinoDetallesDlg extends javax.swing.JDialog {

    Container container;
    Contrato contratoSeleccionado;
    IInquilinoDAO inquilinosDAO;
    ITipoDocumentoDAO tipoDocumentoDAO;
    ICallesDAO callesDAO;
    IBarriosDAO barriosDAO;
    ICiudadesDAO ciudadesDAO;
    IBancoDAO bancosDAO;
    IContratoDAO iContratoDAO;
    ICuentaBancoInquilinoDAO cuentaBancoDAO;
    Inquilino inquilino;
    CuentasBancosInquilinoTableModel tableModel;
    List<CuentaBancoInquilino> listCuentasBanco;
    CuentaBancoInquilino cuentaBanco;
    List<CuentaBancoInquilino> listCuentasToRemove;

    public InquilinoDetallesDlg(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logoTrans.png")));
        //CIERRA JOPTIONPANE CON ESCAPE
        jPanel1.grabFocus();
        jPanel1.addKeyListener(new OptionPaneEstandar(this));
        
        this.container = Container.getInstancia();
        setLocationRelativeTo(null);
        inquilino = new Inquilino();
        inicio();

    }

    public InquilinoDetallesDlg(java.awt.Frame parent, boolean modal, Inquilino inquilinoSeleccionado) {
        super(parent, modal);
        initComponents();
        this.container = Container.getInstancia();
        this.inquilino = inquilinoSeleccionado;
        setLocationRelativeTo(null);
        inicio();
        detallesInquilino();
    }

    public InquilinoDetallesDlg(java.awt.Frame parent, boolean modal, Inquilino inquilinoSeleccionado, Contrato contratoSeleccionado) {
        super(parent, modal);
        initComponents();
        this.container = Container.getInstancia();
        this.inquilino = inquilinoSeleccionado;
        iContratoDAO = container.getBean(IContratoDAO.class);
        setLocationRelativeTo(null);
        this.contratoSeleccionado = contratoSeleccionado;
        inicio();
        detallesInquilino();
    }

    void inicio() {
        tipoDocumentoDAO = container.getBean(ITipoDocumentoDAO.class);
        inquilinosDAO = container.getBean(IInquilinoDAO.class);
        callesDAO = container.getBean(ICallesDAO.class);
        barriosDAO = container.getBean(IBarriosDAO.class);
        ciudadesDAO = container.getBean(ICiudadesDAO.class);
        bancosDAO = container.getBean(IBancoDAO.class);
        cuentaBancoDAO = container.getBean(ICuentaBancoInquilinoDAO.class);
        listCuentasToRemove = new ArrayList<>();
        comboTipoDocumentos();
        configuraTbl();
        accionesBotones();
    }

    void detallesInquilino() {

        txtDireccion.setText(inquilino.getDireccion());
        chActivo.setSelected(inquilino.getActivo());
        txtCel.setText(inquilino.getCel());
        txtEmail.setText(inquilino.getEmail());
        txtNombre.setText(inquilino.getNombre());
        txtTel.setText(inquilino.getTel());
        txtDocumento.setText(inquilino.getDocumento());
        cbTIpoDocumento.setSelectedItem(inquilino.getTipoDocumento());
        chbAgenteRetensorIRPF.setSelected(inquilino.getAgenteRetensorIRPF());
        listCuentasBanco.addAll(inquilino.getListCuentas());
        txtObservaciones.setText(inquilino.getObservaciones());

    }

    void guardarCambios() {

        inquilino.setDireccion(txtDireccion.getText());
        inquilino.setCel(txtCel.getText());
        inquilino.setActivo(chActivo.isSelected());
        inquilino.setEmail(txtEmail.getText());
        inquilino.setNombre(txtNombre.getText());
        inquilino.setTel(txtTel.getText());
        inquilino.setDocumento(txtDocumento.getText());
        inquilino.setTipoDocumento((TipoDocumento) cbTIpoDocumento.getSelectedItem());
        inquilino.setAgenteRetensorIRPF(chbAgenteRetensorIRPF.isSelected());
        if (!listCuentasToRemove.isEmpty()) {
            for (CuentaBancoInquilino toRemove : listCuentasToRemove) {
                cuentaBancoDAO.deleteCuentaBanco(toRemove.getId());
            }
        }
        inquilino.setListCuentas(listCuentasBanco);
        inquilino.setObservaciones(txtObservaciones.getText());
        inquilinosDAO.save(inquilino);

        if (contratoSeleccionado != null) {
            contratoSeleccionado = iContratoDAO.findByContrato(contratoSeleccionado.getId());
            contratoSeleccionado.setDescripcionInquilino(inquilino.toString());
            iContratoDAO.save(contratoSeleccionado);
        }
        this.dispose();
    }

    void comboTipoDocumentos() {
        AutoCompleteDecorator.decorate(cbTIpoDocumento);
        cbTIpoDocumento.removeAllItems();
        for (TipoDocumento tipo : tipoDocumentoDAO.findAll()) {
            cbTIpoDocumento.addItem(tipo);
        }
    }

    void configuraTbl() {
        ((DefaultTableCellRenderer) tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listCuentasBanco = new ArrayList<CuentaBancoInquilino>();
        tableModel = new CuentasBancosInquilinoTableModel(listCuentasBanco);
        tbl.setModel(tableModel);
        tbl.getColumn("Banco").setCellEditor(new ComboBoxCellEditor(new InquilinoDetallesDlg.comboBancos()));

        tbl.setRowHeight(25);
        

        ListSelectionModel selectionModel = tbl.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {

                if (tbl.getSelectedRow() != -1) {

                    btnQuitar.setEnabled(true);
                } else {
                    btnQuitar.setEnabled(false);
                }
            }
        });

        tbl.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_DOWN) {

                    if (tbl.getSelectedRow() == listCuentasBanco.size() - 1) {
                        if (!listCuentasBanco.get(tbl.getSelectedRow()).isNew()) {
                            tableModel.agregar(new CuentaBancoInquilino("000000", new Banco(), inquilino));
                        }
                    }
                }
            }
        });

    }

    void accionesBotones() {
        btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                guardarCambios();
            }
        });
        btnVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                dispose();
            }
        });
    }

    private class comboBancos extends JComboBox<Object> {

        public comboBancos() {
            AutoCompleteDecorator.decorate(this);
            for (Banco banco : bancosDAO.findAll()) {
                this.addItem(banco);

            }

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTel = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCel = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new botones.BotonGuardar();
        btnVolver = new botones.BotonVolver();
        jLabel9 = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JTextField();
        cbTIpoDocumento = new javax.swing.JComboBox();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        btnQuitar = new javax.swing.JButton();
        btnIncluye = new javax.swing.JButton();
        btnRegistraBanco = new javax.swing.JButton();
        btnRegistraTipoDocumento = new javax.swing.JButton();
        chbAgenteRetensorIRPF = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservaciones = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        chActivo = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1024, 700));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Detalles Inquilino / Comprador");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel1.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtEmail, gridBagConstraints);

        jLabel4.setText("Dirección");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel1.add(jLabel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtNombre, gridBagConstraints);

        jLabel5.setText("Tel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtTel, gridBagConstraints);

        jLabel6.setText("Email");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jLabel6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtDireccion, gridBagConstraints);

        jLabel7.setText("Cel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel7, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtCel, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnGuardar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnVolver, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 5;
        jPanel1.add(jPanel3, gridBagConstraints);

        jLabel9.setText("Documento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel9, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtDocumento, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbTIpoDocumento, gridBagConstraints);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Cuentas bancarias"));
        jPanel6.setLayout(new java.awt.GridBagLayout());

        tbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tbl);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(jScrollPane3, gridBagConstraints);

        jPanel7.setLayout(new java.awt.GridBagLayout());

        btnQuitar.setText("Quitar cuenta");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel7.add(btnQuitar, gridBagConstraints);

        btnIncluye.setText("Asignar cuenta");
        btnIncluye.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluyeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel7.add(btnIncluye, gridBagConstraints);

        btnRegistraBanco.setText("Registra Banco");
        btnRegistraBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraBancoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel7.add(btnRegistraBanco, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel6.add(jPanel7, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jPanel6, gridBagConstraints);

        btnRegistraTipoDocumento.setText("Registra Tipo");
        btnRegistraTipoDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraTipoDocumentoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel1.add(btnRegistraTipoDocumento, gridBagConstraints);

        chbAgenteRetensorIRPF.setText("Es agente retensor?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jPanel1.add(chbAgenteRetensorIRPF, gridBagConstraints);

        jLabel8.setText("Observaciones");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanel1.add(jLabel8, gridBagConstraints);

        txtObservaciones.setColumns(20);
        txtObservaciones.setRows(5);
        jScrollPane1.setViewportView(txtObservaciones);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jLabel14.setText("Tipo Documento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel14, gridBagConstraints);

        chActivo.setText("Activo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel1.add(chActivo, gridBagConstraints);

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

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed

        CuentaBancoInquilino cuentaToRemove = listCuentasBanco.get(tbl.getSelectedRow());
        listCuentasBanco.remove(cuentaToRemove);

        listCuentasToRemove.add(cuentaToRemove);

        tableModel.fireTableDataChanged();
        btnQuitar.setEnabled(false);
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void btnIncluyeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluyeActionPerformed

        tableModel.agregar(new CuentaBancoInquilino("000000", new Banco(), inquilino));


    }//GEN-LAST:event_btnIncluyeActionPerformed

    private void btnRegistraTipoDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraTipoDocumentoActionPerformed
        DetalleTipoDocumento detalleDetalleTipoDocumento = new DetalleTipoDocumento(null, true, this);
        detalleDetalleTipoDocumento.setVisible(true);
        detalleDetalleTipoDocumento.toFront();
        comboTipoDocumentos();
    }//GEN-LAST:event_btnRegistraTipoDocumentoActionPerformed

    private void btnRegistraBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraBancoActionPerformed
        DetalleBancos detalleBancos = new DetalleBancos(null, true, this);
        detalleBancos.setVisible(true);
        detalleBancos.toFront();
        configuraTbl();
        listCuentasBanco.addAll(inquilino.getListCuentas());
        tableModel.fireTableDataChanged();


    }//GEN-LAST:event_btnRegistraBancoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BotonGuardar btnGuardar;
    public javax.swing.JButton btnIncluye;
    public javax.swing.JButton btnQuitar;
    private javax.swing.JButton btnRegistraBanco;
    private javax.swing.JButton btnRegistraTipoDocumento;
    public botones.BotonVolver btnVolver;
    private javax.swing.JComboBox cbTIpoDocumento;
    public javax.swing.JCheckBox chActivo;
    private javax.swing.JCheckBox chbAgenteRetensorIRPF;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTable tbl;
    private javax.swing.JTextField txtCel;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextArea txtObservaciones;
    private javax.swing.JTextField txtTel;
    // End of variables declaration//GEN-END:variables
}
