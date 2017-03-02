package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.Banco;
import com.dnsoft.inmobiliaria.beans.Barrio;
import com.dnsoft.inmobiliaria.beans.Calle;
import com.dnsoft.inmobiliaria.beans.Ciudad;
import com.dnsoft.inmobiliaria.beans.CuentaBancoProveedor;
import com.dnsoft.inmobiliaria.beans.Proveedor;
import com.dnsoft.inmobiliaria.beans.TipoDocumento;
import com.dnsoft.inmobiliaria.daos.IBancoDAO;
import com.dnsoft.inmobiliaria.daos.IBarriosDAO;
import com.dnsoft.inmobiliaria.daos.ICallesDAO;
import com.dnsoft.inmobiliaria.daos.ICiudadesDAO;
import com.dnsoft.inmobiliaria.daos.ICuentaBancoProveedorDAO;
import com.dnsoft.inmobiliaria.daos.IProveedorDAO;
import com.dnsoft.inmobiliaria.daos.ITipoDocumentoDAO;
import com.dnsoft.inmobiliaria.models.CuentaBancoProveedorTableModel;
import com.dnsoft.inmobiliaria.utils.Container;
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
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Diego Noble
 */
public class ProveedorDetallesDlg extends javax.swing.JDialog {

    Container container;
    IProveedorDAO proveedorsDAO;
    ITipoDocumentoDAO tipoDocumentoDAO;
    ICallesDAO callesDAO;
    IBarriosDAO barriosDAO;
    ICiudadesDAO ciudadesDAO;
    IBancoDAO bancosDAO;
    ICuentaBancoProveedorDAO cuentaBancoDAO;
    Proveedor proveedor;
    CuentaBancoProveedorTableModel tableModel;
    List<CuentaBancoProveedor> listCuentasBanco;
    CuentaBancoProveedor cuentaBanco;
    List<CuentaBancoProveedor> listCuentasToRemove;

    public ProveedorDetallesDlg(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.container = Container.getInstancia();
        setLocationRelativeTo(null);
        proveedor = new Proveedor();
        inicio();

    }

    public ProveedorDetallesDlg(java.awt.Frame parent, boolean modal, Proveedor proveedorSeleccionado) {
        super(parent, modal);
        initComponents();
        this.container = Container.getInstancia();
        this.proveedor = proveedorSeleccionado;
        setLocationRelativeTo(null);
        inicio();
        detallesProveedor();
    }

    final void inicio() {
        tipoDocumentoDAO = container.getBean(ITipoDocumentoDAO.class);
        proveedorsDAO = container.getBean(IProveedorDAO.class);
        callesDAO = container.getBean(ICallesDAO.class);
        barriosDAO = container.getBean(IBarriosDAO.class);
        ciudadesDAO = container.getBean(ICiudadesDAO.class);
        bancosDAO = container.getBean(IBancoDAO.class);
        cuentaBancoDAO = container.getBean(ICuentaBancoProveedorDAO.class);
        listCuentasToRemove = new ArrayList<>();
        comboTipoDocumentos();
        configuraTbl();
        comboCalles();
        comboBarrios();
        comboCiudades();
        accionesBotones();
    }

    final void detallesProveedor() {

        txtApellidos.setText(proveedor.getApellidos());
        txtCel.setText(proveedor.getCel());
        txtNro.setText(proveedor.getNro());
        cbCalle.setSelectedItem(proveedor.getCalle());
        cbBarrio.setSelectedItem(proveedor.getBarrio());
        cbCiudad.setSelectedItem(proveedor.getCiudad());
        txtEmail.setText(proveedor.getEmail());
        txtNombre.setText(proveedor.getNombre());
        txtTel.setText(proveedor.getTel());
        txtDocumento.setText(proveedor.getDocumento());
        cbTIpoDocumento.setSelectedItem(proveedor.getTipoDocumento());
        listCuentasBanco.addAll(proveedor.getListCuentas());

    }

    void guardarCambios() {

        proveedor.setApellidos(txtApellidos.getText());
        proveedor.setCel(txtCel.getText());
        proveedor.setNro(txtNro.getText());
        proveedor.setCalle((Calle) cbCalle.getSelectedItem());
        proveedor.setBarrio((Barrio) cbBarrio.getSelectedItem());
        proveedor.setCiudad((Ciudad) cbCiudad.getSelectedItem());
        proveedor.setEmail(txtEmail.getText());
        proveedor.setNombre(txtNombre.getText());
        proveedor.setTel(txtTel.getText());
        proveedor.setDocumento(txtDocumento.getText());
        proveedor.setTipoDocumento((TipoDocumento) cbTIpoDocumento.getSelectedItem());
        if (!listCuentasToRemove.isEmpty()) {
            for (CuentaBancoProveedor toRemove : listCuentasToRemove) {
                cuentaBancoDAO.deleteCuentaBanco(toRemove.getId());
            }
        }
        proveedor.setListCuentas(listCuentasBanco);
        proveedorsDAO.save(proveedor);
        this.dispose();

    }

     void comboTipoDocumentos() {
        AutoCompleteDecorator.decorate(cbTIpoDocumento);
        cbTIpoDocumento.removeAllItems();
        for (TipoDocumento tipo : tipoDocumentoDAO.findAll()) {
            cbTIpoDocumento.addItem(tipo);
        }
    }
    void comboCalles() {
        AutoCompleteDecorator.decorate(cbCalle);
        cbCalle.removeAllItems();
        for (Calle calle : callesDAO.findAll()) {
            cbCalle.addItem(calle);
        }
    }

    void comboBarrios() {
        AutoCompleteDecorator.decorate(cbBarrio);
        cbBarrio.removeAllItems();
        for (Barrio barrio : barriosDAO.findAll()) {
            cbBarrio.addItem(barrio);
        }
    }

    void comboCiudades() {
        AutoCompleteDecorator.decorate(cbCiudad);
        cbCiudad.removeAllItems();
        for (Ciudad ciudad : ciudadesDAO.findAll()) {
            cbCiudad.addItem(ciudad);
        }
    }

    void configuraTbl() {
        ((DefaultTableCellRenderer) tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listCuentasBanco = new ArrayList<CuentaBancoProveedor>();
        tableModel = new CuentaBancoProveedorTableModel(listCuentasBanco);
        tbl.setModel(tableModel);
        tbl.getColumn("Banco").setCellEditor(new ComboBoxCellEditor(new ProveedorDetallesDlg.comboBancos()));

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
                            tableModel.agregar(new CuentaBancoProveedor("000000", new Banco(), proveedor));
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
        btnCancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                
                dispose();
            }
        });}

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
        txtApellidos = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCel = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new botones.BotonGuardar();
        btnCancelar = new botones.BotonCancelar();
        jLabel9 = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JTextField();
        cbTIpoDocumento = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cbCalle = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        cbCiudad = new javax.swing.JComboBox();
        cbBarrio = new javax.swing.JComboBox();
        txtNro = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnRegistraCiudad = new javax.swing.JButton();
        btnRegistraCalle = new javax.swing.JButton();
        btnRegistraBarrio = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        btnQuitar = new javax.swing.JButton();
        btnIncluye = new javax.swing.JButton();
        btnRegistraBanco = new javax.swing.JButton();
        btnRegistraTipoDocumento = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(680, 450));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Detalles proveedor");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Nombres / Nombre fantasía");
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

        jLabel4.setText("Apellidos / Razón Social");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel1.add(jLabel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
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
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtApellidos, gridBagConstraints);

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
        jPanel3.add(btnCancelar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
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

        jLabel1.setText("Tipo Documento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel1, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Dirección"));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel10.setText("Barrio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel5.add(jLabel10, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(cbCalle, gridBagConstraints);

        jLabel11.setText("Ciudad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jPanel5.add(jLabel11, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(cbCiudad, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(cbBarrio, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtNro, gridBagConstraints);

        jLabel12.setText("Número");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel5.add(jLabel12, gridBagConstraints);

        jLabel13.setText("Calle");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel5.add(jLabel13, gridBagConstraints);

        btnRegistraCiudad.setText("Registra Ciudad");
        btnRegistraCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraCiudadActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        jPanel5.add(btnRegistraCiudad, gridBagConstraints);

        btnRegistraCalle.setText("Registra Calle");
        btnRegistraCalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraCalleActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel5.add(btnRegistraCalle, gridBagConstraints);

        btnRegistraBarrio.setText("Registra Barrio");
        btnRegistraBarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraBarrioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel5.add(btnRegistraBarrio, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel1.add(jPanel5, gridBagConstraints);

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
        gridBagConstraints.gridy = 6;
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

        CuentaBancoProveedor cuentaToRemove = listCuentasBanco.get(tbl.getSelectedRow());
        listCuentasBanco.remove(cuentaToRemove);

        listCuentasToRemove.add(cuentaToRemove);

        tableModel.fireTableDataChanged();
        btnQuitar.setEnabled(false);
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void btnIncluyeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluyeActionPerformed

        tableModel.agregar(new CuentaBancoProveedor("000000", new Banco(), proveedor));


    }//GEN-LAST:event_btnIncluyeActionPerformed

    private void btnRegistraTipoDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraTipoDocumentoActionPerformed
        DetalleTipoDocumento detalleDetalleTipoDocumento = new DetalleTipoDocumento(null, true, this);
        detalleDetalleTipoDocumento.setVisible(true);
        detalleDetalleTipoDocumento.toFront();
        comboTipoDocumentos();
    }//GEN-LAST:event_btnRegistraTipoDocumentoActionPerformed

    private void btnRegistraCalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraCalleActionPerformed
        DetalleCalles calles = new DetalleCalles(null, true, this);
        calles.setVisible(true);
        calles.toFront();
        comboCalles();
        
    }//GEN-LAST:event_btnRegistraCalleActionPerformed

    private void btnRegistraBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraBarrioActionPerformed
        DetalleBarrios barrios = new DetalleBarrios(null, true, this);
        barrios.setVisible(true);
        barrios.toFront();
        comboBarrios();
    }//GEN-LAST:event_btnRegistraBarrioActionPerformed

    private void btnRegistraCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraCiudadActionPerformed
        DetalleCiudades ciudades = new DetalleCiudades(null, true, this);
        ciudades.setVisible(true);
        ciudades.toFront();
        comboCiudades();
    }//GEN-LAST:event_btnRegistraCiudadActionPerformed

    private void btnRegistraBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraBancoActionPerformed
        DetalleBancos detalleBancos = new DetalleBancos(null, true, this);
        detalleBancos.setVisible(true);
        detalleBancos.toFront();
        configuraTbl();
        listCuentasBanco.addAll(proveedor.getListCuentas());
        tableModel.fireTableDataChanged();
        
        
    }//GEN-LAST:event_btnRegistraBancoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BotonCancelar btnCancelar;
    private botones.BotonGuardar btnGuardar;
    public javax.swing.JButton btnIncluye;
    public javax.swing.JButton btnQuitar;
    private javax.swing.JButton btnRegistraBanco;
    private javax.swing.JButton btnRegistraBarrio;
    private javax.swing.JButton btnRegistraCalle;
    private javax.swing.JButton btnRegistraCiudad;
    private javax.swing.JButton btnRegistraTipoDocumento;
    private javax.swing.JComboBox cbBarrio;
    private javax.swing.JComboBox cbCalle;
    private javax.swing.JComboBox cbCiudad;
    private javax.swing.JComboBox cbTIpoDocumento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTable tbl;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtCel;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNro;
    private javax.swing.JTextField txtTel;
    // End of variables declaration//GEN-END:variables
}
