package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.Barrio;
import com.dnsoft.inmobiliaria.beans.Calle;
import com.dnsoft.inmobiliaria.beans.Ciudad;
import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.Inmueble;
import com.dnsoft.inmobiliaria.beans.Propietario;
import com.dnsoft.inmobiliaria.beans.PropietarioHasInmueble;
import com.dnsoft.inmobiliaria.beans.StatusInmueble;
import com.dnsoft.inmobiliaria.beans.TipoInmueble;
import com.dnsoft.inmobiliaria.daos.IBarriosDAO;
import com.dnsoft.inmobiliaria.daos.ICallesDAO;
import com.dnsoft.inmobiliaria.daos.ICiudadesDAO;
import com.dnsoft.inmobiliaria.daos.IContratoDAO;
import com.dnsoft.inmobiliaria.daos.IInmuebleDAO;
import com.dnsoft.inmobiliaria.daos.IPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.IPropietarioHasInmuebleDAO;
import com.dnsoft.inmobiliaria.daos.ITipoInmuebleDAO;
import com.dnsoft.inmobiliaria.models.InmueblesHasComodidadesTableModel;
import com.dnsoft.inmobiliaria.models.PropietariosHasInmueblesTableModel;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.OptionPaneEstandar;
import com.dnsoft.inmobiliaria.utils.RollOverButtonsChicos;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
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
public final class InmuebleDetallesDialog_new extends javax.swing.JDialog {

    Contrato contratoSeleccionado;
    IContratoDAO iContratoDAO;
    IInmuebleDAO inmuebleDAO;
    ICallesDAO callesDAO;
    IBarriosDAO barriosDAO;
    ICiudadesDAO ciudadesDAO;
    ITipoInmuebleDAO tipoInmuebleDAO;
    IPropietarioDAO propietarioDAO;
    IPropietarioHasInmuebleDAO propietariosHasInmuebleDAO;
    Container container;
    Inmueble inmueble;
    ListSelectionModel listModelPropietarios;
    public List<PropietarioHasInmueble> listPropietariosHasInmueble;
    public List<PropietarioHasInmueble> listPropietariosHasInmuebleToRemove;
    public PropietariosHasInmueblesTableModel tableModelPropietarios;
    public List<Propietario> propietariosSeleccionados;

    public InmuebleDetallesDialog_new(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.container = Container.getInstancia();
        inmueble = new Inmueble();
        setLocationRelativeTo(null);
        inicio();
    }

    public InmuebleDetallesDialog_new(java.awt.Frame parent, boolean modal, Inmueble propiedadSeleccionado) {
        super(parent, modal);
        initComponents();
        this.container = Container.getInstancia();;
        this.inmueble = propiedadSeleccionado;

        setLocationRelativeTo(null);
        inicio();
        detallesInmueble();

    }

    public InmuebleDetallesDialog_new(java.awt.Frame parent, boolean modal, Inmueble propiedadSeleccionado, Contrato contratoSeleccionado) {
        super(parent, modal);
        initComponents();
        this.container = Container.getInstancia();;
        this.inmueble = propiedadSeleccionado;
        this.contratoSeleccionado = contratoSeleccionado;
        setLocationRelativeTo(null);
        inicio();
        detallesInmueble();

    }

    void inicio() {
        new RollOverButtonsChicos(btnRegistraBarrio).RollOverButtonBuscar();
        new RollOverButtonsChicos(btnRegistraCalle).RollOverButtonBuscar();
        new RollOverButtonsChicos(btnRegistraCiudad).RollOverButtonBuscar();
        new RollOverButtonsChicos(btnRegistraTipoDocumento).RollOverButtonBuscar();

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logoTrans.png")));
        //CIERRA JOPTIONPANE CON ESCAPE
        jPanel1.grabFocus();
        jPanel1.addKeyListener(new OptionPaneEstandar(this));
        propietariosHasInmuebleDAO = container.getBean(IPropietarioHasInmuebleDAO.class);

        callesDAO = container.getBean(ICallesDAO.class);
        barriosDAO = container.getBean(IBarriosDAO.class);
        ciudadesDAO = container.getBean(ICiudadesDAO.class);
        tipoInmuebleDAO = container.getBean(ITipoInmuebleDAO.class);
        inmuebleDAO = container.getBean(IInmuebleDAO.class);
        iContratoDAO = container.getBean(IContratoDAO.class);
        propietarioDAO = container.getBean(IPropietarioDAO.class);

//        listComodidades = inmuebleHasComodidadesDAO.findByInmuebleId(inmueble.getId());
        //      listPropietariosHasInmueble = propietariosHasInmuebleDAO.findByInmueble(inmueble);
        listPropietariosHasInmuebleToRemove = new ArrayList<>();
        comboCalles();
        comboBarrios();
        comboCiudades();
        comboTipoInmueble();
        configTblPropietarios();
        buscaPropietariosAsignados();
        accionesBotones();
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
        btnAsociaPropietario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                agregarNuevoPropietario();

            }
        });
        btnQuitarPropietario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                elminarPropietarioSeleccionado();

            }
        });
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

    void comboTipoInmueble() {
        AutoCompleteDecorator.decorate(cbTipoInmueble);
        cbTipoInmueble.removeAllItems();
        for (TipoInmueble tipoInmueble : tipoInmuebleDAO.findAll()) {
            cbTipoInmueble.addItem(tipoInmueble);
        }
    }

    private class ComboPropietarios extends JComboBox<Object> {

        public ComboPropietarios() {
            AutoCompleteDecorator.decorate(this);
            for (Propietario propietario : propietarioDAO.findAll()) {
                this.addItem(propietario);
            }
        }
    }

    void configTblPropietarios() {
        ((DefaultTableCellRenderer) tblPropietarios.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listPropietariosHasInmueble = new ArrayList<>();
        propietariosSeleccionados = new ArrayList<>();
        tableModelPropietarios = new PropietariosHasInmueblesTableModel(listPropietariosHasInmueble);
        tblPropietarios.setModel(tableModelPropietarios);

        tblPropietarios.setRowHeight(25);
        //tblPropietarios.removeColumn(tblPropietarios.getColumn("Inmueble"));
        tblPropietarios.getColumn("Propietario").setCellEditor(new ComboBoxCellEditor(new ComboPropietarios()));

        listModelPropietarios = tblPropietarios.getSelectionModel();
        listModelPropietarios.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tblPropietarios.getSelectedRow() != -1) {

                    btnQuitarPropietario.setEnabled(true);
                } else {
                    btnQuitarPropietario.setEnabled(false);
                }
            }
        });

    }

    void detallesInmueble() {

        txtCodReferencia.setText(inmueble.getCodReferencia());
        txtNro.setText(inmueble.getNro());
        cbCalle.setSelectedItem(inmueble.getCalle());
        cbBarrio.setSelectedItem(inmueble.getBarrio());
        cbCiudad.setSelectedItem(inmueble.getCiudad());
        txtAreaEdificada.setText(inmueble.getAreaedificada());
        txtAreaPredio.setText(inmueble.getAreapredio());
        txtDescripcion.setText(inmueble.getDocumentacion());
        dpFechaConstruccion.setDate(inmueble.getFechaconstruccion());

        txtManzana.setText(inmueble.getManzana());
        txtObservaciones.setText(inmueble.getObservaciones());
        txtSolar.setText(inmueble.getSolar());
        txtFraccionamiento.setText(inmueble.getFraccionamiento());
        cbTipoInmueble.setSelectedItem(inmueble.getTipoinmueble());

    }

    void guardarCambios() {
        try {
            inmueble.setCodReferencia(txtCodReferencia.getText());
            inmueble.setNro(txtNro.getText());
            inmueble.setCalle((Calle) cbCalle.getSelectedItem());
            inmueble.setBarrio((Barrio) cbBarrio.getSelectedItem());
            inmueble.setCiudad((Ciudad) cbCiudad.getSelectedItem());
            inmueble.setAreaedificada(txtAreaEdificada.getText());
            inmueble.setAreapredio(txtAreaPredio.getText());
            inmueble.setDocumentacion(txtDescripcion.getText());
            inmueble.setFechaconstruccion(dpFechaConstruccion.getDate());
            if (listPropietariosHasInmueble.isEmpty()) {
                throw new Exception("Debe asociar Propietarios al inmueble");
            } else {
                inmueble.setPropietarioHasPropiedad(listPropietariosHasInmueble);
            }
            inmueble.setManzana(txtManzana.getText());
            inmueble.setObservaciones(txtObservaciones.getText());
            inmueble.setSolar(txtSolar.getText());
            inmueble.setFraccionamiento(txtFraccionamiento.getText());
            inmueble.setTipoinmueble((TipoInmueble) cbTipoInmueble.getSelectedItem());
            if (inmueble.isNew()) {
                inmueble.setActivo(true);
                inmueble.setStatusInmueble(StatusInmueble.DISPONIBLE);
            }

            if (!listPropietariosHasInmuebleToRemove.isEmpty()) {
                for (PropietarioHasInmueble toRemove : listPropietariosHasInmuebleToRemove) {
                    propietariosHasInmuebleDAO.deletePropietarioHasInmueble(toRemove.getId());
                }
            }
            inmuebleDAO.save(inmueble);
            if (contratoSeleccionado != null) {
                contratoSeleccionado = iContratoDAO.findByContrato(contratoSeleccionado.getId());
                contratoSeleccionado.setDescripcionInmueble(inmueble.toString());
                iContratoDAO.save(contratoSeleccionado);
            }

            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear guardar cambios: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void agregarNuevoPropietario() {

        tableModelPropietarios.agregar(new PropietarioHasInmueble(BigDecimal.ZERO, inmueble, new Propietario()));

        for (PropietarioHasInmueble propietarios : listPropietariosHasInmueble) {
            Double procentage = 100.00 / listPropietariosHasInmueble.size();
            propietarios.setProcentagePropiedad(BigDecimal.valueOf(procentage).setScale(2, RoundingMode.CEILING));
        }
        tableModelPropietarios.fireTableDataChanged();
    }

    void elminarPropietarioSeleccionado() {
        PropietarioHasInmueble propietarioToRemove = listPropietariosHasInmueble.get(tblPropietarios.getSelectedRow());
        listPropietariosHasInmueble.remove(propietarioToRemove);
        listPropietariosHasInmuebleToRemove.add(propietarioToRemove);
        tableModelPropietarios.fireTableDataChanged();
        for (PropietarioHasInmueble propietarios : listPropietariosHasInmueble) {
            Double procentage = 100.00 / listPropietariosHasInmueble.size();
            propietarios.setProcentagePropiedad(BigDecimal.valueOf(procentage).setScale(2, RoundingMode.CEILING));
        }
        tableModelPropietarios.fireTableStructureChanged();
    }

    void buscaPropietariosAsignados() {
        for (PropietarioHasInmueble propietarios : propietariosHasInmuebleDAO.findByInmuebleId(inmueble.getId())) {
            tableModelPropietarios.agregar(propietarios);
        }

    }

    boolean VerificaPorcentage() {

        BigDecimal verificaPorcentaje = BigDecimal.ZERO;

        for (PropietarioHasInmueble propietarioHasPropiedad : listPropietariosHasInmueble) {

            if ((propietarioHasPropiedad.getProcentagePropiedad().compareTo(BigDecimal.ZERO) == 0) || (propietarioHasPropiedad.getProcentagePropiedad().compareTo(BigDecimal.ZERO) == 0)) {
                return false;
            } else {
                verificaPorcentaje = verificaPorcentaje.add(propietarioHasPropiedad.getProcentagePropiedad());
            }
        }
        return verificaPorcentaje.compareTo(new BigDecimal(100)) == 0;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new botones.BotonGuardar();
        btnVolver = new botones.BotonVolver();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtAreaPredio = new javax.swing.JTextField();
        txtAreaEdificada = new javax.swing.JTextField();
        txtSolar = new javax.swing.JTextField();
        txtFraccionamiento = new javax.swing.JTextField();
        txtManzana = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        dpFechaConstruccion = new org.jdesktop.swingx.JXDatePicker();
        btnRegistraTipoDocumento = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        cbTipoInmueble = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        cbCalle = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        cbCiudad = new javax.swing.JComboBox();
        cbBarrio = new javax.swing.JComboBox();
        txtNro = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtPadron1 = new javax.swing.JTextField();
        btnRegistraBarrio = new javax.swing.JButton();
        btnRegistraCiudad = new javax.swing.JButton();
        btnRegistraCalle = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservaciones = new javax.swing.JTextArea();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel25 = new javax.swing.JLabel();
        txtCodReferencia = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblPropietarios = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        btnAsociaPropietario = new botones.BotonBuscar();
        btnQuitarPropietario = new botones.BotonEliminar();

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1124, 700));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel3.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnGuardar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnVolver, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Detalles Inmueble");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtAreaPredio, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtAreaEdificada, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtSolar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtFraccionamiento, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtManzana, gridBagConstraints);

        jLabel9.setText("Área predio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel9, gridBagConstraints);

        jLabel10.setText("Área edificada");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel10, gridBagConstraints);

        jLabel11.setText("Manzana");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jLabel11, gridBagConstraints);

        jLabel12.setText("Solar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jLabel12, gridBagConstraints);

        jLabel13.setText("Fecha construcción");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel13, gridBagConstraints);

        jLabel15.setText("Fraccionamiento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jLabel15, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(dpFechaConstruccion, gridBagConstraints);

        btnRegistraTipoDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraTipoDocumentoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(btnRegistraTipoDocumento, gridBagConstraints);

        jLabel18.setText("Tipo inmueble");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel1.add(jLabel18, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbTipoInmueble, gridBagConstraints);

        jLabel16.setText("Barrio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel16, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbCalle, gridBagConstraints);

        jLabel20.setText("Ciudad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel20, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbCiudad, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbBarrio, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtNro, gridBagConstraints);

        jLabel22.setText("Código Referencia");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel1.add(jLabel22, gridBagConstraints);

        jLabel23.setText("Calle");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel23, gridBagConstraints);

        jLabel24.setText("Nro. Padrón");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jLabel24, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtPadron1, gridBagConstraints);

        btnRegistraBarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraBarrioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(btnRegistraBarrio, gridBagConstraints);

        btnRegistraCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraCiudadActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(btnRegistraCiudad, gridBagConstraints);

        btnRegistraCalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraCalleActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(btnRegistraCalle, gridBagConstraints);

        jLabel17.setText("Observaciones");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanel1.add(jLabel17, gridBagConstraints);

        txtObservaciones.setColumns(20);
        txtObservaciones.setRows(5);
        jScrollPane1.setViewportView(txtObservaciones);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jLabel19.setText("Descripción");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        jPanel1.add(jLabel19, gridBagConstraints);

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane2.setViewportView(txtDescripcion);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane2, gridBagConstraints);

        jLabel25.setText("Número");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel25, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtCodReferencia, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Propietarios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel7.setLayout(new java.awt.GridBagLayout());

        tblPropietarios.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblPropietarios);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel7.add(jScrollPane4, gridBagConstraints);

        jPanel8.setLayout(new java.awt.GridBagLayout());

        btnAsociaPropietario.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnAsociaPropietario.setPreferredSize(new java.awt.Dimension(120, 60));
        btnAsociaPropietario.setText("Asociar propietarios");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel8.add(btnAsociaPropietario, gridBagConstraints);

        btnQuitarPropietario.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnQuitarPropietario.setText("Eliminar propietario");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel8.add(btnQuitarPropietario, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel7.add(jPanel8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel7, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistraCalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraCalleActionPerformed
        DetalleCalles detalleCalle = new DetalleCalles(null, true, this);
        detalleCalle.setVisible(true);
        detalleCalle.toFront();
        comboCalles();
    }//GEN-LAST:event_btnRegistraCalleActionPerformed

    private void btnRegistraCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraCiudadActionPerformed
        DetalleCiudades detalleCiudades = new DetalleCiudades(null, true, this);
        detalleCiudades.setVisible(true);
        detalleCiudades.toFront();
        comboCiudades();
    }//GEN-LAST:event_btnRegistraCiudadActionPerformed

    private void btnRegistraBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraBarrioActionPerformed
        DetalleBarrios detalleBarrios = new DetalleBarrios(null, true, this);
        detalleBarrios.setVisible(true);
        detalleBarrios.toFront();
        comboBarrios();
    }//GEN-LAST:event_btnRegistraBarrioActionPerformed

    private void btnRegistraTipoDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraTipoDocumentoActionPerformed
        DetalleTipoInmueble detalleDetalleTipoInmueble = new DetalleTipoInmueble(null, true, this);
        detalleDetalleTipoInmueble.setVisible(true);
        detalleDetalleTipoInmueble.toFront();
        comboTipoInmueble();
    }//GEN-LAST:event_btnRegistraTipoDocumentoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public botones.BotonBuscar btnAsociaPropietario;
    private botones.BotonGuardar btnGuardar;
    public botones.BotonEliminar btnQuitarPropietario;
    private javax.swing.JButton btnRegistraBarrio;
    private javax.swing.JButton btnRegistraCalle;
    private javax.swing.JButton btnRegistraCiudad;
    private javax.swing.JButton btnRegistraTipoDocumento;
    public botones.BotonVolver btnVolver;
    private javax.swing.JComboBox cbBarrio;
    private javax.swing.JComboBox cbCalle;
    private javax.swing.JComboBox cbCiudad;
    private javax.swing.JComboBox cbTipoInmueble;
    private org.jdesktop.swingx.JXDatePicker dpFechaConstruccion;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JTable tblPropietarios;
    private javax.swing.JTextField txtAreaEdificada;
    private javax.swing.JTextField txtAreaPredio;
    private javax.swing.JTextField txtCodReferencia;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtFraccionamiento;
    private javax.swing.JTextField txtManzana;
    private javax.swing.JTextField txtNro;
    private javax.swing.JTextArea txtObservaciones;
    private javax.swing.JTextField txtPadron1;
    private javax.swing.JTextField txtSolar;
    // End of variables declaration//GEN-END:variables
}
