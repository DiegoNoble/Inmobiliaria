package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.Banco;
import com.dnsoft.inmobiliaria.beans.Barrio;
import com.dnsoft.inmobiliaria.beans.Calle;
import com.dnsoft.inmobiliaria.beans.Ciudad;
import com.dnsoft.inmobiliaria.beans.CuentaBancoPropietario;
import com.dnsoft.inmobiliaria.beans.Propietario;
import com.dnsoft.inmobiliaria.beans.TipoDocumento;
import com.dnsoft.inmobiliaria.daos.IBancoDAO;
import com.dnsoft.inmobiliaria.daos.IBarriosDAO;
import com.dnsoft.inmobiliaria.daos.ICallesDAO;
import com.dnsoft.inmobiliaria.daos.ICiudadesDAO;
import com.dnsoft.inmobiliaria.daos.ICuentaBancoPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.IPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.ITipoDocumentoDAO;
import com.dnsoft.inmobiliaria.models.CuentasBancosPropietarioTableModel;
import com.dnsoft.inmobiliaria.utils.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.FetchType;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.hibernate.annotations.Fetch;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;

/**
 *
 * @author Diego Noble
 */
public class PropietariosDetalleDlg extends javax.swing.JDialog {

    Container container;
    IPropietarioDAO propietarioDAO;
    IBancoDAO bancosDAO;
    ICuentaBancoPropietarioDAO cuentaBancoDAO;
    ITipoDocumentoDAO tipoDocumentoDAO;
    ICallesDAO callesDAO;
    IBarriosDAO barriosDAO;
    ICiudadesDAO ciudadesDAO;
    Propietario propietario;
    CuentasBancosPropietarioTableModel tableModel;
    List<CuentaBancoPropietario> listCuentasBanco;
    CuentaBancoPropietario cuentaBanco;
    List<CuentaBancoPropietario> listCuentasToRemove;

    public PropietariosDetalleDlg(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        inicio();
        setLocationRelativeTo(null);
        propietario = new Propietario();

        configuraTbl();
    }

    public PropietariosDetalleDlg(java.awt.Frame parent, boolean modal, Propietario propietarioSeleccionado) {
        super(parent, modal);
        initComponents();
        inicio();

        this.propietario = propietarioDAO.findByPropietario(propietarioSeleccionado.getId());
        setLocationRelativeTo(null);

        detallesInquilino();

    }

    final void inicio() {
        this.container = Container.getInstancia();
        propietarioDAO = container.getBean(IPropietarioDAO.class);
        bancosDAO = container.getBean(IBancoDAO.class);
        cuentaBancoDAO = container.getBean(ICuentaBancoPropietarioDAO.class);
        tipoDocumentoDAO = container.getBean(ITipoDocumentoDAO.class);
        callesDAO = container.getBean(ICallesDAO.class);
        barriosDAO = container.getBean(IBarriosDAO.class);
        ciudadesDAO = container.getBean(ICiudadesDAO.class);
        listCuentasToRemove = new ArrayList<>();
        configuraTbl();
        comboTipoDocumentos();
        comboCalles();
        comboBarrios();
        comboCiudades();
    }

    final void detallesInquilino() {

        txtApellidos.setText(propietario.getApellidos());
        txtCel.setText(propietario.getCel());
        txtNro.setText(propietario.getNro());
        cbCalle.setSelectedItem(propietario.getCalle());
        cbBarrio.setSelectedItem(propietario.getBarrio());
        cbCiudad.setSelectedItem(propietario.getCiudad());
        txtEmail.setText(propietario.getEmail());
        txtNombre.setText(propietario.getNombre());
        txtTel.setText(propietario.getTel());
        cbTIpoDocumento.setSelectedItem(propietario.getTipoDocumento());
        txtDocumento.setText(propietario.getDocumento());
        chbRetenerIRPF.setSelected(propietario.getRetenerIrpf());
        listCuentasBanco.addAll(propietario.getListCuentas());

    }

    void guardarCambios() {

        try {
            propietario.setApellidos(txtApellidos.getText());
            propietario.setCel(txtCel.getText());
            propietario.setNro(txtNro.getText());
            propietario.setCalle((Calle) cbCalle.getSelectedItem());
            propietario.setBarrio((Barrio) cbBarrio.getSelectedItem());
            propietario.setCiudad((Ciudad) cbCiudad.getSelectedItem());
            propietario.setEmail(txtEmail.getText());
            propietario.setNombre(txtNombre.getText());
            propietario.setTel(txtTel.getText());
            propietario.setDocumento(txtDocumento.getText());
            propietario.setTipoDocumento((TipoDocumento) cbTIpoDocumento.getSelectedItem());
            propietario.setRetenerIrpf(chbRetenerIRPF.isSelected());
            if (!listCuentasToRemove.isEmpty()) {
                for (CuentaBancoPropietario toRemove : listCuentasToRemove) {
                    cuentaBancoDAO.deleteCuentaBanco(toRemove.getId());
                }
            }
            propietario.setListCuentas(listCuentasBanco);
            propietarioDAO.save(propietario);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar registros! " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
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

    final void configuraTbl() {
        ((DefaultTableCellRenderer) tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listCuentasBanco = new ArrayList<CuentaBancoPropietario>();
        tableModel = new CuentasBancosPropietarioTableModel(listCuentasBanco);
        tbl.setModel(tableModel);
        tbl.getColumn("Banco").setCellEditor(new ComboBoxCellEditor(new comboBancos()));

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
                            tableModel.agregar(new CuentaBancoPropietario("000000", new Banco(), propietario));
                        }
                    }
                }
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
        btnRegistraTipoDocumento = new javax.swing.JButton();
        txtApellidos = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCel = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        btnQuitar = new javax.swing.JButton();
        btnIncluye = new javax.swing.JButton();
        btnRegistraBanco = new javax.swing.JButton();
        cbTIpoDocumento = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cbCalle = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        cbCiudad = new javax.swing.JComboBox();
        cbBarrio = new javax.swing.JComboBox();
        txtNro = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnRegistraCalle = new javax.swing.JButton();
        btnRegistraBarrio = new javax.swing.JButton();
        btnRegistraCiudad = new javax.swing.JButton();
        chbRetenerIRPF = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Detalles propietario");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Nombres / Nombre Fantasía");
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

        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        btnGuardar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnGuardarKeyPressed(evt);
            }
        });
        jPanel3.add(btnGuardar);

        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
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
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 5;
        jPanel1.add(jPanel3, gridBagConstraints);

        jLabel9.setText("C. Identidad / RUT");
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

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Cuentas bancarias"));
        jPanel5.setLayout(new java.awt.GridBagLayout());

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
        jPanel5.add(jScrollPane3, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

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
        jPanel6.add(btnQuitar, gridBagConstraints);

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
        jPanel6.add(btnIncluye, gridBagConstraints);

        btnRegistraBanco.setText("Registra Banco");
        btnRegistraBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraBancoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel6.add(btnRegistraBanco, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel5.add(jPanel6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jPanel5, gridBagConstraints);

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

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Dirección"));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        jLabel10.setText("Barrio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel7.add(jLabel10, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbCalle, gridBagConstraints);

        jLabel11.setText("Ciudad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jPanel7.add(jLabel11, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbCiudad, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbBarrio, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtNro, gridBagConstraints);

        jLabel12.setText("Número");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel7.add(jLabel12, gridBagConstraints);

        jLabel13.setText("Calle");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel7.add(jLabel13, gridBagConstraints);

        btnRegistraCalle.setText("Registra Calle");
        btnRegistraCalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraCalleActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel7.add(btnRegistraCalle, gridBagConstraints);

        btnRegistraBarrio.setText("Registra Barrio");
        btnRegistraBarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraBarrioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel7.add(btnRegistraBarrio, gridBagConstraints);

        btnRegistraCiudad.setText("Registra Ciudad");
        btnRegistraCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraCiudadActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        jPanel7.add(btnRegistraCiudad, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel1.add(jPanel7, gridBagConstraints);

        chbRetenerIRPF.setText("Reneter IRPF?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(chbRetenerIRPF, gridBagConstraints);

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

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        guardarCambios();

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        this.dispose();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGuardarKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            guardarCambios();
        }

    }//GEN-LAST:event_btnGuardarKeyPressed

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
        this.dispose();

    }//GEN-LAST:event_btnCancelarKeyPressed

    private void btnIncluyeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluyeActionPerformed

        tableModel.agregar(new CuentaBancoPropietario("000000", new Banco(), propietario));
    }//GEN-LAST:event_btnIncluyeActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed

        CuentaBancoPropietario cuentaToRemove = listCuentasBanco.get(tbl.getSelectedRow());
        listCuentasBanco.remove(cuentaToRemove);

        listCuentasToRemove.add(cuentaToRemove);

        tableModel.fireTableDataChanged();
        btnQuitar.setEnabled(false);
    }//GEN-LAST:event_btnQuitarActionPerformed

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
        listCuentasBanco.addAll(propietario.getListCuentas());
        tableModel.fireTableDataChanged();

    }//GEN-LAST:event_btnRegistraBancoActionPerformed

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
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
    private javax.swing.JCheckBox chbRetenerIRPF;
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
