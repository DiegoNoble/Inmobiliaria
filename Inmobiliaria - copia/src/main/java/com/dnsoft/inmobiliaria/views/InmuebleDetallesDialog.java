package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.Barrio;
import com.dnsoft.inmobiliaria.beans.Calle;
import com.dnsoft.inmobiliaria.beans.Ciudad;
import com.dnsoft.inmobiliaria.beans.Comodidad;
import com.dnsoft.inmobiliaria.beans.Inmueble;
import com.dnsoft.inmobiliaria.beans.InmuebleHasComodidades;
import com.dnsoft.inmobiliaria.beans.StatusInmueble;
import com.dnsoft.inmobiliaria.beans.TipoContrato;
import com.dnsoft.inmobiliaria.beans.TipoInmueble;
import com.dnsoft.inmobiliaria.daos.IBarriosDAO;
import com.dnsoft.inmobiliaria.daos.ICallesDAO;
import com.dnsoft.inmobiliaria.daos.ICiudadesDAO;
import com.dnsoft.inmobiliaria.daos.IComodidadDAO;
import com.dnsoft.inmobiliaria.daos.IInmuebleDAO;
import com.dnsoft.inmobiliaria.daos.IInmuebleHasComodidadesDAO;
import com.dnsoft.inmobiliaria.daos.ITipoInmuebleDAO;
import com.dnsoft.inmobiliaria.models.InmueblesHasComodidadesTableModel;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ControlarEntradaTexto;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.KeyStroke;
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
public final class InmuebleDetallesDialog extends javax.swing.JDialog {

    IInmuebleDAO inmuebleDAO;
    ICallesDAO callesDAO;
    IBarriosDAO barriosDAO;
    ICiudadesDAO ciudadesDAO;
    ITipoInmuebleDAO tipoInmuebleDAO;
    IComodidadDAO comodidadesDAO;
    IInmuebleHasComodidadesDAO inmuebleHasComodidadesDAO;
    Container container;
    Inmueble inmueble;
    List<InmuebleHasComodidades> listComodidades;
    List<InmuebleHasComodidades> listComodidadesToRemove;
    InmueblesHasComodidadesTableModel tableModel;

    public InmuebleDetallesDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.container = Container.getInstancia();
        inmueble = new Inmueble();
        setLocationRelativeTo(null);
        inicio();
    }

    public InmuebleDetallesDialog(java.awt.Frame parent, boolean modal,Inmueble propiedadSeleccionado) {
        super(parent, modal);
        initComponents();
        this.container = Container.getInstancia();;
        this.inmueble = propiedadSeleccionado;
        setLocationRelativeTo(null);
        inicio();
        detallesInquilino();

    }

    void inicio() {
        
        callesDAO = container.getBean(ICallesDAO.class);
        barriosDAO = container.getBean(IBarriosDAO.class);
        ciudadesDAO = container.getBean(ICiudadesDAO.class);
        inmuebleHasComodidadesDAO = container.getBean(IInmuebleHasComodidadesDAO.class);
        comodidadesDAO = container.getBean(IComodidadDAO.class);
        tipoInmuebleDAO = container.getBean(ITipoInmuebleDAO.class);
        inmuebleDAO = container.getBean(IInmuebleDAO.class);
        cbTipoContrato.setModel(new DefaultComboBoxModel(TipoContrato.values()));
        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
        txtValor.setDocument(new ControlarEntradaTexto(10, chs));
        listComodidadesToRemove = new ArrayList<>();
        cargaCbStatus();
        comboCalles();
        comboBarrios();
        comboCiudades();
        comboTipoInmueble();
        configTblComodidades();
        accionesBotones();
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
    void cargaCbStatus() {
        for (StatusInmueble s : StatusInmueble.values()) {
            cbstatus.addItem(s);
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

    void comboTipoInmueble() {
        AutoCompleteDecorator.decorate(cbTipoInmueble);
        cbTipoInmueble.removeAllItems();
        for (TipoInmueble tipoInmueble : tipoInmuebleDAO.findAll()) {
            cbTipoInmueble.addItem(tipoInmueble);
        }
    }

    private class ComboTiposComodidad extends JComboBox<Object> {

        public ComboTiposComodidad() {
            AutoCompleteDecorator.decorate(this);
            for (Comodidad comodidades : comodidadesDAO.findAll()) {
                this.addItem(comodidades);
            }
        }
    }

    void configTblComodidades() {
        ((DefaultTableCellRenderer) tblAsignados.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listComodidades = new ArrayList<InmuebleHasComodidades>();
        tableModel = new InmueblesHasComodidadesTableModel(listComodidades);
        tblAsignados.setModel(tableModel);
        tblAsignados.setRowHeight(25);
        createKeybindings(tblAsignados);
        tblAsignados.getColumn("Comodidad").setCellEditor(new ComboBoxCellEditor(new ComboTiposComodidad()));
        ListSelectionModel selectionModel = tblAsignados.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {

                if (tblAsignados.getSelectedRow() != -1) {

                    btnQuitar.setEnabled(true);
                } else {
                    btnQuitar.setEnabled(false);
                }
            }
        });

    }

    void detallesInquilino() {

        txtValor.setText(inmueble.getValorReferencia().toString());
        txtPadron.setText(inmueble.getPadron());
        cbstatus.setSelectedItem(inmueble.getStatuspropiedad());
        txtNro.setText(inmueble.getNro());
        cbCalle.setSelectedItem(inmueble.getCalle());
        cbBarrio.setSelectedItem(inmueble.getBarrio());
        cbCiudad.setSelectedItem(inmueble.getCiudad());
        txtAreaEdificada.setText(inmueble.getAreaedificada());
        txtAreaPredio.setText(inmueble.getAreapredio());
        txtDocumentacion.setText(inmueble.getDocumentacion());
        dpFechaConstruccion.setDate(inmueble.getFechaconstruccion());
        tableModel.agregarLista(inmuebleHasComodidadesDAO.findByInmuebleId(inmueble.getId()));
        txtLlaves.setText(inmueble.getLlaves());
        txtManzana.setText(inmueble.getManzana());
        txtObservaciones.setText(inmueble.getObservaciones());
        txtSolar.setText(inmueble.getSolar());
        txtFraccionamiento.setText(inmueble.getFraccionamiento());
        cbTipoInmueble.setSelectedItem(inmueble.getTipoinmueble());
        cbTipoContrato.setSelectedItem(inmueble.getTipoContrato());

    }

    void guardarCambios() {

        inmueble.setPadron(txtPadron.getText());
        inmueble.setValorReferencia(BigDecimal.valueOf(Double.parseDouble(txtValor.getText())));
        inmueble.setStatuspropiedad((StatusInmueble) cbstatus.getSelectedItem());
        inmueble.setNro(txtNro.getText());
        inmueble.setCalle((Calle) cbCalle.getSelectedItem());
        inmueble.setBarrio((Barrio) cbBarrio.getSelectedItem());
        inmueble.setCiudad((Ciudad) cbCiudad.getSelectedItem());
        inmueble.setAreaedificada(txtAreaEdificada.getText());
        inmueble.setAreapredio(txtAreaPredio.getText());
        inmueble.setDocumentacion(txtDocumentacion.getText());
        inmueble.setFechaconstruccion(dpFechaConstruccion.getDate());
        inmueble.setInmuebleHasComodidades(listComodidades);
        inmueble.setLlaves(txtLlaves.getText());
        inmueble.setManzana(txtManzana.getText());
        inmueble.setObservaciones(txtObservaciones.getText());
        inmueble.setSolar(txtSolar.getText());
        inmueble.setFraccionamiento(txtFraccionamiento.getText());
        inmueble.setTipoinmueble((TipoInmueble) cbTipoInmueble.getSelectedItem());
        inmueble.setTipoContrato((TipoContrato) cbTipoContrato.getSelectedItem());

        if (!listComodidadesToRemove.isEmpty()) {
            for (InmuebleHasComodidades toRemove : listComodidadesToRemove) {
                inmuebleHasComodidadesDAO.deleteComodidad(toRemove.getId());
            }
        }
        inmuebleDAO.save(inmueble);
        this.dispose();

    }

    private void createKeybindings(JTable table) {
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "Tab");
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "Delete");
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Escape");
        table.getActionMap().put("Tab", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                agregarNuevo();
            }
        });
        table.getActionMap().put("Delete", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                elminarSeleccionado();
            }

        });
       
    }

    void agregarNuevo() {
        tableModel.agregar(new InmuebleHasComodidades(inmueble));
    }

    void elminarSeleccionado() {
        InmuebleHasComodidades comodidadToRemove = listComodidades.get(tblAsignados.getSelectedRow());
        listComodidades.remove(comodidadToRemove);
        listComodidadesToRemove.add(comodidadToRemove);
        tableModel.fireTableDataChanged();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new botones.BotonGuardar();
        btnCancelar = new botones.BotonCancelar();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbCalle = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        cbCiudad = new javax.swing.JComboBox();
        cbBarrio = new javax.swing.JComboBox();
        txtNro = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtPadron = new javax.swing.JTextField();
        btnRegistraBarrio = new javax.swing.JButton();
        btnRegistraCiudad = new javax.swing.JButton();
        btnRegistraCalle = new javax.swing.JButton();
        cbstatus = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservaciones = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDocumentacion = new javax.swing.JTextArea();
        txtAreaPredio = new javax.swing.JTextField();
        txtAreaEdificada = new javax.swing.JTextField();
        txtSolar = new javax.swing.JTextField();
        txtLlaves = new javax.swing.JTextField();
        txtFraccionamiento = new javax.swing.JTextField();
        txtManzana = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        dpFechaConstruccion = new org.jdesktop.swingx.JXDatePicker();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblAsignados = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        btnQuitar = new javax.swing.JButton();
        btnIncluye = new javax.swing.JButton();
        btnRegistraComodidades = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        btnRegistraTipoDocumento = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        cbTipoInmueble = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        cbTipoContrato = new javax.swing.JComboBox();

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1024, 700));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Detalles Inmueble");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel7.setText("Valor Referencia");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel7, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtValor, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnGuardar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnCancelar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 9;
        jPanel1.add(jPanel3, gridBagConstraints);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Dirección"));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Barrio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel4.add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cbCalle, gridBagConstraints);

        jLabel4.setText("Ciudad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jPanel4.add(jLabel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cbCiudad, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cbBarrio, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtNro, gridBagConstraints);

        jLabel8.setText("Número");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel4.add(jLabel8, gridBagConstraints);

        jLabel1.setText("Calle");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel4.add(jLabel1, gridBagConstraints);

        jLabel6.setText("Nro. Padrón");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel4.add(jLabel6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtPadron, gridBagConstraints);

        btnRegistraBarrio.setText("Registra Barrio");
        btnRegistraBarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraBarrioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel4.add(btnRegistraBarrio, gridBagConstraints);

        btnRegistraCiudad.setText("Registra Ciudad");
        btnRegistraCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraCiudadActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        jPanel4.add(btnRegistraCiudad, gridBagConstraints);

        btnRegistraCalle.setText("Registra Calle");
        btnRegistraCalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraCalleActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel4.add(btnRegistraCalle, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel1.add(jPanel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbstatus, gridBagConstraints);

        jLabel5.setText("Situación");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel5, gridBagConstraints);

        txtObservaciones.setColumns(20);
        txtObservaciones.setRows(5);
        jScrollPane1.setViewportView(txtObservaciones);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        txtDocumentacion.setColumns(20);
        txtDocumentacion.setRows(5);
        jScrollPane2.setViewportView(txtDocumentacion);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtAreaPredio, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtAreaEdificada, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtSolar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtLlaves, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtFraccionamiento, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtManzana, gridBagConstraints);

        jLabel9.setText("Área predio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel9, gridBagConstraints);

        jLabel10.setText("Área edificada");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel10, gridBagConstraints);

        jLabel11.setText("Manzana");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jLabel11, gridBagConstraints);

        jLabel12.setText("Solar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jLabel12, gridBagConstraints);

        jLabel13.setText("Fecha construcción");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel13, gridBagConstraints);

        jLabel14.setText("Llaves");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jLabel14, gridBagConstraints);

        jLabel15.setText("Fraccionamiento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel15, gridBagConstraints);

        jLabel17.setText("Observaciones");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        jPanel1.add(jLabel17, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(dpFechaConstruccion, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Comodidades"));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        tblAsignados.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblAsignados);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel5.add(jScrollPane3, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        btnQuitar.setText("Quitar comodidades \"Del\"");
        btnQuitar.setEnabled(false);
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

        btnIncluye.setText("Asignar comodidades \"Tab\"");
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

        btnRegistraComodidades.setText("Registra Comodidades");
        btnRegistraComodidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraComodidadesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel6.add(btnRegistraComodidades, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel5.add(jPanel6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jPanel5, gridBagConstraints);

        jLabel19.setText("Documentación");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        jPanel1.add(jLabel19, gridBagConstraints);

        btnRegistraTipoDocumento.setText("Registra Tipo");
        btnRegistraTipoDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraTipoDocumentoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(btnRegistraTipoDocumento, gridBagConstraints);

        jLabel18.setText("Tipo inmueble");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel18, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbTipoInmueble, gridBagConstraints);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Tipo de contrato");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel20, gridBagConstraints);

        cbTipoContrato.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbTipoContrato, gridBagConstraints);

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

    private void btnIncluyeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluyeActionPerformed

        agregarNuevo();

    }//GEN-LAST:event_btnIncluyeActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        elminarSeleccionado();
    }//GEN-LAST:event_btnQuitarActionPerformed

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

    private void btnRegistraCalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraCalleActionPerformed
        DetalleCalles calles = new DetalleCalles(null, true, this);
        calles.setVisible(true);
        calles.toFront();
        comboCalles();

    }//GEN-LAST:event_btnRegistraCalleActionPerformed

    private void btnRegistraTipoDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraTipoDocumentoActionPerformed
        DetalleTipoInmueble detalleDetalleTipoInmueble = new DetalleTipoInmueble(null, true, this);
        detalleDetalleTipoInmueble.setVisible(true);
        detalleDetalleTipoInmueble.toFront();
        comboTipoInmueble();
    }//GEN-LAST:event_btnRegistraTipoDocumentoActionPerformed

    private void btnRegistraComodidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraComodidadesActionPerformed
        DetalleComodidades comodidades = new DetalleComodidades(null, true, this);
        comodidades.setVisible(true);
        comodidades.toFront();
        configTblComodidades();
        listComodidades.addAll(inmueble.getInmuebleHasComodidades());
        tableModel.fireTableDataChanged();
    }//GEN-LAST:event_btnRegistraComodidadesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BotonCancelar btnCancelar;
    private botones.BotonGuardar btnGuardar;
    public javax.swing.JButton btnIncluye;
    public javax.swing.JButton btnQuitar;
    private javax.swing.JButton btnRegistraBarrio;
    private javax.swing.JButton btnRegistraCalle;
    private javax.swing.JButton btnRegistraCiudad;
    private javax.swing.JButton btnRegistraComodidades;
    private javax.swing.JButton btnRegistraTipoDocumento;
    private javax.swing.JComboBox cbBarrio;
    private javax.swing.JComboBox cbCalle;
    private javax.swing.JComboBox cbCiudad;
    public javax.swing.JComboBox cbTipoContrato;
    private javax.swing.JComboBox cbTipoInmueble;
    private javax.swing.JComboBox cbstatus;
    private org.jdesktop.swingx.JXDatePicker dpFechaConstruccion;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTable tblAsignados;
    private javax.swing.JTextField txtAreaEdificada;
    private javax.swing.JTextField txtAreaPredio;
    private javax.swing.JTextArea txtDocumentacion;
    private javax.swing.JTextField txtFraccionamiento;
    private javax.swing.JTextField txtLlaves;
    private javax.swing.JTextField txtManzana;
    private javax.swing.JTextField txtNro;
    private javax.swing.JTextArea txtObservaciones;
    private javax.swing.JTextField txtPadron;
    private javax.swing.JTextField txtSolar;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
