package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.CCPropietario;
import com.dnsoft.inmobiliaria.beans.Caja;
import com.dnsoft.inmobiliaria.beans.GastoInmuebleInquilino;
import com.dnsoft.inmobiliaria.beans.GastoInmueblePropietario;
import com.dnsoft.inmobiliaria.beans.Inquilino;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.Propietario;
import com.dnsoft.inmobiliaria.beans.PropietarioHasInmueble;
import com.dnsoft.inmobiliaria.beans.Proveedor;
import com.dnsoft.inmobiliaria.beans.Rubro;
import com.dnsoft.inmobiliaria.beans.Situacion;
import com.dnsoft.inmobiliaria.beans.TipoDeCaja;
import com.dnsoft.inmobiliaria.beans.TipoMovimiento;
import com.dnsoft.inmobiliaria.controllers.ControlDeCajaController;
import com.dnsoft.inmobiliaria.daos.ICCPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.ICajaDAO;
import com.dnsoft.inmobiliaria.daos.IGastoInmuebleInquilinoDAO;
import com.dnsoft.inmobiliaria.daos.IInquilinoDAO;
import com.dnsoft.inmobiliaria.daos.IPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.IRubroDAO;
import com.dnsoft.inmobiliaria.daos.ITipoDeCajaDAO;
import com.dnsoft.inmobiliaria.models.InquilinosTableModel;
import com.dnsoft.inmobiliaria.models.PropietariosTableModel;
import com.dnsoft.inmobiliaria.utils.ActualizaSaldos;
import com.dnsoft.inmobiliaria.utils.ButtonColumnEditar;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ControlarEntradaTexto;
import com.dnsoft.inmobiliaria.utils.ImprimeRecibo;
import com.dnsoft.inmobiliaria.utils.OptionPaneEstandar;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Diego Noble
 */
public class OtrosGastosTerceros extends javax.swing.JDialog {

    Container container;
    IRubroDAO rubroDAO;
    ICajaDAO cajaDAO;
    ITipoDeCajaDAO tipoDeCajaDAO;
    Moneda monedaSeleccionada;
    Rubro rubroSeleccionado;
    ControlDeCajaController cajaController;
    Caja movimientoCaja;
    IInquilinoDAO inquilinosDAO;
    ICCPropietarioDAO cCPropietarioDAO;

    Inquilino inquilinoSeleccionado;
    ListSelectionModel selectionModel;
    InquilinosTableModel tableModelInquilinos;
    List<Inquilino> listInquilinos;
    IGastoInmuebleInquilinoDAO gastoInmuebleInquilinoDAO;

    PropietariosTableModel tableModelPropietarios;
    List<Propietario> listPropietarios;
    IPropietarioDAO propietariosDAO;
    Propietario propietarioSeleccionado;

    public OtrosGastosTerceros(java.awt.Frame parent, boolean modal, Rubro rubroSeleccionado, ControlDeCajaController cajaController, TipoDeCaja tipoDeCaja) {
        super(parent, modal);
        initComponents();

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logoTrans.png")));
        //CIERRA JOPTIONPANE CON ESCAPE
        jPanel1.grabFocus();
        jPanel1.addKeyListener(new OptionPaneEstandar(this));
        
        inicio();
        if (tipoDeCaja != null) {
            cbTipoDeCaja.setSelectedItem(tipoDeCaja);
        }
        this.cajaController = cajaController;
        this.rubroSeleccionado = rubroSeleccionado;
        cbRubro.setSelectedItem(this.rubroSeleccionado);
        cbRubro.setEnabled(false);

    }

    final void inicio() {
        this.container = Container.getInstancia();
        rubroDAO = container.getBean(IRubroDAO.class);
        cajaDAO = container.getBean(ICajaDAO.class);
        tipoDeCajaDAO = container.getBean(ITipoDeCajaDAO.class);
        inquilinosDAO = container.getBean(IInquilinoDAO.class);
        propietariosDAO = container.getBean(IPropietarioDAO.class);
        cCPropietarioDAO = container.getBean(ICCPropietarioDAO.class);
        gastoInmuebleInquilinoDAO = container.getBean(IGastoInmuebleInquilinoDAO.class);
        accionesBotones();
        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-'};
        txtValor.setDocument(new ControlarEntradaTexto(10, chs));
        cargaMonedas();
        cargaRubros();
        cargaTiposDeCaja();
        setLocationRelativeTo(null);
    }

    private void TableModelInquilinos() {

        ((DefaultTableCellRenderer) tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listInquilinos = new ArrayList<>();
        listInquilinos.addAll(inquilinosDAO.findAll());

        tableModelInquilinos = new InquilinosTableModel(listInquilinos);
        tbl.setModel(tableModelInquilinos);

        new ButtonColumnEditar(tbl, 3) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                editaInquilinoSeleccionado();
            }
        };
        tbl.setRowHeight(25);

        selectionModel = tbl.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tbl.getSelectedRow() != -1) {
                    inquilinoSeleccionado = inquilinosDAO.findOne(listInquilinos.get(tbl.getSelectedRow()).getId());
                } else {
                }
            }
        });

    }

    final void accionesBotones() {

        btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                guardar();
                if (cajaController != null) {
                    cajaController.actualizaTbl();
                }
            }
        });
        btnVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                dispose();

            }
        });

    }

    void editaInquilinoSeleccionado() {
        //inquilinoSeleccionado = listInquilinos.get(view.tbl.getSelectedRow());
        InquilinoDetallesDlg editaInquilino = new InquilinoDetallesDlg(null, true, inquilinoSeleccionado);
        editaInquilino.setVisible(true);
        editaInquilino.toFront();
        actualizaTblInquilinos();
    }

    public void actualizaTblInquilinos() {
        listInquilinos.clear();
        listInquilinos.addAll(inquilinosDAO.findAll());
        tableModelInquilinos.fireTableDataChanged();
    }

    private void tableModelPropietario() {

        ((DefaultTableCellRenderer) tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listPropietarios = new ArrayList<>();
        listPropietarios.addAll(propietariosDAO.findAll());

        tableModelPropietarios = new PropietariosTableModel(listPropietarios);
        tbl.setModel(tableModelPropietarios);

        new ButtonColumnEditar(tbl, 3) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                editaPropietarioSeleccionado();
            }
        };

        selectionModel = tbl.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tbl.getSelectedRow() != -1) {
                    propietarioSeleccionado = listPropietarios.get(tbl.getSelectedRow());
                } else {

                }
            }
        });

        tbl.setRowHeight(25);

    }

    void editaPropietarioSeleccionado() {
        try {
            Propietario editar = listPropietarios.get(tbl.getSelectedRow());
            PropietariosDetalleDlg editaPropietario = new PropietariosDetalleDlg(null, true, editar);
            editaPropietario.setVisible(true);
            editaPropietario.toFront();
            actualizaTblPropietario();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void actualizaTblPropietario() {
        listPropietarios.clear();
        listPropietarios.addAll(propietariosDAO.findAll());
        tableModelPropietarios.fireTableDataChanged();
    }

    void guardar() {
        if (!txtDetalle.getText().isEmpty()) {
            monedaSeleccionada = (Moneda) cbMoneda.getSelectedItem();

            if (rbPropietario.isSelected()) {
                try {
                    movimientoCaja();
                    gastoPropietarios();
                    JOptionPane.showMessageDialog(this, "Movimiento registrado correctamente!", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                    new ImprimeRecibo(movimientoCaja, propietarioSeleccionado.toString(), movimientoCaja.getDescripcion(),"","").imprimieReciboSalida();
                    this.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error " + e, "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }

            } else if (rbInquilino.isSelected()) {

                try {
                    movimientoCaja();
                    gastoInmuebleInquilino();
                    JOptionPane.showMessageDialog(this, "Movimiento registrado correctamente!", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                    new ImprimeRecibo(movimientoCaja, inquilinoSeleccionado.toString(), movimientoCaja.getDescripcion(),"","").imprimieReciboSalida();
                    this.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error " + e, "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe ingresar un detalle del gasto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void movimientoCaja() {
        BigDecimal valor = BigDecimal.valueOf(Double.parseDouble(txtValor.getText()));
        movimientoCaja = new Caja();
        movimientoCaja.setDescripcion("Detalle: " + txtDetalle.getText());
        movimientoCaja.setEntrada(BigDecimal.ZERO);
        movimientoCaja.setFecha(new Date());
        movimientoCaja.setMoneda(monedaSeleccionada);
        movimientoCaja.setRubro(rubroSeleccionado);
        movimientoCaja.setSaldo(calculaSaldo().subtract(valor));
        movimientoCaja.setSalida(valor);
        movimientoCaja.setTipoDeCaja((TipoDeCaja) cbTipoDeCaja.getSelectedItem());
        cajaDAO.save(movimientoCaja);
    }

    void gastoPropietarios() {

        BigDecimal valor = BigDecimal.valueOf(Double.parseDouble(txtValor.getText()));

        CCPropietario movimientoCCPropietario = new CCPropietario();
        movimientoCCPropietario.setPropietario(propietarioSeleccionado);
        movimientoCCPropietario.setDebito(valor);
        movimientoCCPropietario.setCredito(BigDecimal.ZERO);
        movimientoCCPropietario.setDescipcion("Detalle: " + txtDetalle.getText());
        movimientoCCPropietario.setFecha(new Date());
        movimientoCCPropietario.setMoneda(monedaSeleccionada);

        cCPropietarioDAO.save(movimientoCCPropietario);

        ActualizaSaldos acSaldo = new ActualizaSaldos();
        List<CCPropietario> ccPropietario = cCPropietarioDAO.findByPropietarioAndMonedaOrderByIdAsc(propietarioSeleccionado, monedaSeleccionada);
        cCPropietarioDAO.save(acSaldo.ActualizaSaldosPropietarios(ccPropietario));

    }

    void gastoInmuebleInquilino() {

        BigDecimal valor = BigDecimal.valueOf(Double.parseDouble(txtValor.getText()));

        GastoInmuebleInquilino gastoInmuebleInquilino = new GastoInmuebleInquilino();
        gastoInmuebleInquilino.setDescripcion("Detalle: " + txtDetalle.getText());
        gastoInmuebleInquilino.setFecha(new Date());
        //gastoInmuebleInquilino.setInmueble(inmuebleSeleccionado);
        gastoInmuebleInquilino.setMoneda(monedaSeleccionada);
        gastoInmuebleInquilino.setInquilino(inquilinoSeleccionado);
        gastoInmuebleInquilino.setRubro(rubroSeleccionado);
        gastoInmuebleInquilino.setValor(valor);
        gastoInmuebleInquilino.setSaldo(valor);
        gastoInmuebleInquilino.setSituacion(Situacion.PENDIENTE);
        //gastoInmuebleInquilino.setProveedor((Proveedor) cbProveedor.getSelectedItem());
        gastoInmuebleInquilinoDAO.save(gastoInmuebleInquilino);

    }

    BigDecimal calculaSaldo() {
        BigDecimal toReturn;

        Caja ultimoMovimiento = cajaDAO.findUltimoMovimiento((Moneda) cbMoneda.getSelectedItem(), (TipoDeCaja) cbTipoDeCaja.getSelectedItem());
        if (ultimoMovimiento == null) {
            toReturn = BigDecimal.ZERO;
        } else {
            toReturn = ultimoMovimiento.getSaldo();
        }
        return toReturn;

    }

    final void cargaMonedas() {

        cbMoneda.addItem(Moneda.PESOS);
        cbMoneda.addItem(Moneda.DOLARES);
    }

    final void cargaRubros() {
        AutoCompleteDecorator.decorate(cbRubro);
        List<Rubro> rubros = rubroDAO.findAll();
        cbRubro.removeAllItems();
        for (Rubro rubro : rubros) {
            cbRubro.addItem(rubro);
        }

    }

    private void cargaTiposDeCaja() {
        cbTipoDeCaja.removeAllItems();
        for (TipoDeCaja tipoDeCaja : tipoDeCajaDAO.findAll()) {
            cbTipoDeCaja.addItem(tipoDeCaja);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new botones.BotonGuardar();
        btnVolver = new botones.BotonVolver();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDetalle = new javax.swing.JTextArea();
        cbMoneda = new javax.swing.JComboBox();
        txtValor = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbRubro = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        cbTipoDeCaja = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        txtBusqueda = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        rbPropietario = new javax.swing.JRadioButton();
        rbInquilino = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 700));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Otros gastos terceros");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel3.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnGuardar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnVolver, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 7;
        jPanel1.add(jPanel3, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Descripción");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        jPanel1.add(jLabel9, gridBagConstraints);

        txtDetalle.setColumns(20);
        txtDetalle.setRows(5);
        jScrollPane1.setViewportView(txtDetalle);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbMoneda, gridBagConstraints);

        txtValor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtValor, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Moneda");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Valor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        jPanel1.add(jLabel5, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Rubro");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanel1.add(jLabel7, gridBagConstraints);

        cbRubro.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbRubro, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Caja");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel10, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbTipoDeCaja, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        txtBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBusquedaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel5.add(txtBusqueda, gridBagConstraints);

        jLabel2.setText("Buscar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel5.add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jPanel5, gridBagConstraints);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Destino del Gasto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel8.setLayout(new java.awt.GridBagLayout());

        buttonGroup1.add(rbPropietario);
        rbPropietario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbPropietario.setText("Propietario");
        rbPropietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbPropietarioActionPerformed(evt);
            }
        });
        jPanel8.add(rbPropietario, new java.awt.GridBagConstraints());

        buttonGroup1.add(rbInquilino);
        rbInquilino.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbInquilino.setText("Inquilino");
        rbInquilino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbInquilinoActionPerformed(evt);
            }
        });
        jPanel8.add(rbInquilino, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel1.add(jPanel8, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

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
        tbl.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tbl);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jScrollPane2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel4, gridBagConstraints);

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

    private void rbInquilinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbInquilinoActionPerformed

        TableModelInquilinos();
    }//GEN-LAST:event_rbInquilinoActionPerformed

    private void rbPropietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbPropietarioActionPerformed
        tableModelPropietario();
    }//GEN-LAST:event_rbPropietarioActionPerformed

    private void txtBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBusquedaActionPerformed

        if (rbPropietario.isSelected()) {

            listPropietarios.clear();
            listPropietarios.addAll(propietariosDAO.findPropietario(txtBusqueda.getText()));
            tableModelPropietarios.fireTableDataChanged();
        } else if (rbInquilino.isSelected()) {

            listInquilinos.clear();
            listInquilinos.addAll(inquilinosDAO.findInquilino(txtBusqueda.getText()));
            tableModelInquilinos.fireTableDataChanged();

        }
    }//GEN-LAST:event_txtBusquedaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BotonGuardar btnGuardar;
    private botones.BotonVolver btnVolver;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbMoneda;
    private javax.swing.JComboBox cbRubro;
    public javax.swing.JComboBox cbTipoDeCaja;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rbInquilino;
    private javax.swing.JRadioButton rbPropietario;
    public javax.swing.JTable tbl;
    public javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextArea txtDetalle;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
