package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.CCPropietario;
import com.dnsoft.inmobiliaria.beans.Caja;
import com.dnsoft.inmobiliaria.beans.DestinoGasto;
import com.dnsoft.inmobiliaria.beans.GastoInmuebleInmobiliaria;
import com.dnsoft.inmobiliaria.beans.GastoInmuebleInquilino;
import com.dnsoft.inmobiliaria.beans.GastoInmueblePropietario;
import com.dnsoft.inmobiliaria.beans.Inmueble;
import com.dnsoft.inmobiliaria.beans.Inquilino;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.Parametros;
import com.dnsoft.inmobiliaria.beans.Propietario;
import com.dnsoft.inmobiliaria.beans.PropietarioHasInmueble;
import com.dnsoft.inmobiliaria.beans.Proveedor;
import com.dnsoft.inmobiliaria.beans.Situacion;
import com.dnsoft.inmobiliaria.beans.TipoDeCaja;
import com.dnsoft.inmobiliaria.daos.ICCPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.ICajaDAO;
import com.dnsoft.inmobiliaria.daos.IContratoDAO;
import com.dnsoft.inmobiliaria.daos.IGastoInmuebleInmobiliariaDAO;
import com.dnsoft.inmobiliaria.daos.IGastoInmuebleInquilinoDAO;
import com.dnsoft.inmobiliaria.daos.IGastoInmueblePropietarioDAO;
import com.dnsoft.inmobiliaria.daos.IInmuebleDAO;
import com.dnsoft.inmobiliaria.daos.IParametrosDAO;
import com.dnsoft.inmobiliaria.daos.IPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.IPropietarioHasInmuebleDAO;
import com.dnsoft.inmobiliaria.daos.IProveedorDAO;
import com.dnsoft.inmobiliaria.daos.ITipoDeCajaDAO;
import com.dnsoft.inmobiliaria.utils.ActualizaSaldos;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ControlarEntradaTexto;
import com.dnsoft.inmobiliaria.utils.ImprimeRecibo;
import com.dnsoft.inmobiliaria.utils.OptionPaneEstandar;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Diego Noble
 */
public class NuevoGastoInmueble extends javax.swing.JDialog {

    Container container;
    ICajaDAO cajaDAO;
    IPropietarioDAO propietarioDAO;
    IInmuebleDAO inmuebleDAO;
    ICCPropietarioDAO cCPropietarioDAO;
    IContratoDAO contratoDAO;
    IGastoInmueblePropietarioDAO gastoInmueblePropietarioDAO;
    IGastoInmuebleInquilinoDAO gastoInmuebleInquilinoDAO;
    IPropietarioHasInmuebleDAO propietariosHasInmuebleDAO;
    IGastoInmuebleInmobiliariaDAO gastoInmuebleInmobiliariaDAO;
    ITipoDeCajaDAO tipoDeCajaDAO;
    IParametrosDAO parametrosDAO;
    IProveedorDAO proveedorDAO;
    Parametros parametros;
    Inmueble inmuebleSeleccionado;
    Propietario propietarioSeleccionado;
    Inquilino inquilinoSeleccionado;
    Moneda monedaSeleccionada;
    Caja movimientoCaja;
    GastoInmuebleInmobiliaria gastoInmuebleInmobiliaria;
    GastoInmueblePropietario gastoInmueblePropietario;
    GastoInmuebleInquilino gastoInmuebleInquilino;

    public NuevoGastoInmueble(java.awt.Frame parent, boolean modal, Inmueble inmueble, TipoDeCaja tipoDeCajaSeleccionado) {
        super(parent, modal);
        initComponents();
        
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logoTrans.png")));
        //CIERRA JOPTIONPANE CON ESCAPE
        jPanel1.grabFocus();
        jPanel1.addKeyListener(new OptionPaneEstandar(this));
        
        this.container = Container.getInstancia();
        this.inmuebleSeleccionado = inmueble;
        txtInmueble.setText(inmueble.toString());

        inicio();
        if (tipoDeCajaSeleccionado != null) {
            cbTipoDeCaja.setSelectedItem(tipoDeCajaSeleccionado);
        }
    }

    public NuevoGastoInmueble(java.awt.Frame parent, boolean modal, Inmueble inmueble, TipoDeCaja tipoDeCajaSeleccionado, DestinoGasto destinoGasto) {
        super(parent, modal);
        initComponents();
        this.container = Container.getInstancia();
        this.inmuebleSeleccionado = inmueble;
        txtInmueble.setText(inmueble.toString());

        if (destinoGasto == DestinoGasto.INMOBILIARIA) {
            rbInmobiliaria.setSelected(true);
            rbInquilino.setVisible(false);
            rbPropietario.setVisible(false);
        } else {
            rbInmobiliaria.setVisible(false);
        }
        inicio();
        if (tipoDeCajaSeleccionado != null) {
            cbTipoDeCaja.setSelectedItem(tipoDeCajaSeleccionado);
        }
    }

    final void inicio() {
        propietarioDAO = container.getBean(IPropietarioDAO.class);
        cajaDAO = container.getBean(ICajaDAO.class);
        inmuebleDAO = container.getBean(IInmuebleDAO.class);
        cCPropietarioDAO = container.getBean(ICCPropietarioDAO.class);
        contratoDAO = container.getBean(IContratoDAO.class);
        gastoInmuebleInmobiliariaDAO = container.getBean(IGastoInmuebleInmobiliariaDAO.class);
        gastoInmueblePropietarioDAO = container.getBean(IGastoInmueblePropietarioDAO.class);
        gastoInmuebleInquilinoDAO = container.getBean(IGastoInmuebleInquilinoDAO.class);
        propietariosHasInmuebleDAO = container.getBean(IPropietarioHasInmuebleDAO.class);
        tipoDeCajaDAO = container.getBean(ITipoDeCajaDAO.class);
        parametrosDAO = container.getBean(IParametrosDAO.class);
        proveedorDAO = container.getBean(IProveedorDAO.class);
        parametros = parametrosDAO.findAll().get(0);
        if (contratoDAO.findByInmuebleAndActivo(inmuebleSeleccionado, true) == null) {
            rbInquilino.setVisible(false);
        }
        if (propietariosHasInmuebleDAO.findByInmueble(inmuebleSeleccionado).isEmpty()) {
            rbPropietario.setVisible(false);
        }

        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-'};
        txtValor.setDocument(new ControlarEntradaTexto(10, chs));
        cargaMonedas();
        setLocationRelativeTo(null);
        accionesBotones();
        cargaTiposDeCaja();
        cargaProveedores();
    }

    final void accionesBotones() {

        btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                guardar();
            }
        });
        btnVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                dispose();

            }
        });

    }

    void guardar() {
        if (!txtDetalle.getText().isEmpty()) {
            monedaSeleccionada = (Moneda) cbMoneda.getSelectedItem();

            if (rbInmobiliaria.isSelected()) {
                try {
                    movimientoCaja();
                    gastoInmuebleInmobiliaria();
                    JOptionPane.showMessageDialog(this, "Movimiento registrado correctamente!", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                    new ImprimeRecibo(movimientoCaja, "Inmobiliaria", gastoInmuebleInmobiliaria.toString(), gastoInmuebleInmobiliaria.getProveedor().toString(), inmuebleSeleccionado.toString()).imprimieReciboSalida();
                    this.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error " + e, "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }

            } else if (rbPropietario.isSelected()) {
                try {
                    movimientoCaja();
                    gastoInmueblePropietarios();
                    JOptionPane.showMessageDialog(this, "Movimiento registrado correctamente!", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                    new ImprimeRecibo(movimientoCaja, gastoInmueblePropietario.getPropietario().toString(), gastoInmueblePropietario.toString(), gastoInmueblePropietario.getProveedor().toString(), inmuebleSeleccionado.toString()).imprimieReciboSalida();
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
                    new ImprimeRecibo(movimientoCaja, gastoInmuebleInquilino.getInquilino().toString(), gastoInmuebleInquilino.toString(), gastoInmuebleInquilino.getProveedor().toString(), inmuebleSeleccionado.toString()).imprimieReciboSalida();
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
        movimientoCaja.setDescripcion("Inmueble :" + inmuebleSeleccionado.toString() + " Detalle: " + txtDetalle.getText());
        movimientoCaja.setEntrada(BigDecimal.ZERO);
        movimientoCaja.setFecha(new Date());
        movimientoCaja.setMoneda(monedaSeleccionada);
        movimientoCaja.setRubro(parametros.getRubroObrasMantenimiento());
        movimientoCaja.setSaldo(calculaSaldo().subtract(valor));
        movimientoCaja.setSalida(valor);
        movimientoCaja.setTipoDeCaja((TipoDeCaja) cbTipoDeCaja.getSelectedItem());
        cajaDAO.save(movimientoCaja);
    }

    void gastoInmueblePropietarios() {

        BigDecimal valor = BigDecimal.valueOf(Double.parseDouble(txtValor.getText()));
        List<PropietarioHasInmueble> propietarioHasPropiedad = propietariosHasInmuebleDAO.findByInmuebleId(inmuebleSeleccionado.getId());
        for (PropietarioHasInmueble propietarioHasInmueble : propietarioHasPropiedad) {

            propietarioSeleccionado = propietarioHasInmueble.getPropietario();
            BigDecimal porcentagePropiedad = propietarioHasInmueble.getProcentagePropiedad();
            BigDecimal valorPropietarioSeleccionado = valor.multiply(porcentagePropiedad).divide(BigDecimal.valueOf(100.00));
            gastoInmueblePropietario = new GastoInmueblePropietario();
            gastoInmueblePropietario.setDescripcion(txtDetalle.getText());
            gastoInmueblePropietario.setFecha(new Date());
            gastoInmueblePropietario.setInmueble(inmuebleSeleccionado);
            gastoInmueblePropietario.setMoneda(monedaSeleccionada);
            gastoInmueblePropietario.setPropietario(propietarioSeleccionado);
            gastoInmueblePropietario.setRubro(parametros.getRubroObrasMantenimiento());
            gastoInmueblePropietario.setValor(valorPropietarioSeleccionado);
            gastoInmueblePropietario.setSaldo(valorPropietarioSeleccionado);
            gastoInmueblePropietario.setSituacion(Situacion.PENDIENTE);
            gastoInmueblePropietario.setProveedor((Proveedor) cbProveedor.getSelectedItem());
            gastoInmueblePropietarioDAO.save(gastoInmueblePropietario);

            CCPropietario movimientoCCPropietario = new CCPropietario();
            movimientoCCPropietario.setPropietario(propietarioSeleccionado);
            movimientoCCPropietario.setDebito(valorPropietarioSeleccionado);
            movimientoCCPropietario.setCredito(BigDecimal.ZERO);
            movimientoCCPropietario.setDescipcion(txtDetalle.getText());
            movimientoCCPropietario.setFecha(new Date());
            movimientoCCPropietario.setMoneda(monedaSeleccionada);
            movimientoCCPropietario.setGastoInmueblePropietario(gastoInmueblePropietario);

            cCPropietarioDAO.save(movimientoCCPropietario);

            ActualizaSaldos acSaldo = new ActualizaSaldos();
            List<CCPropietario> ccPropietario = cCPropietarioDAO.findByPropietarioAndMonedaOrderByIdAsc(propietarioSeleccionado, monedaSeleccionada);
            cCPropietarioDAO.save(acSaldo.ActualizaSaldosPropietarios(ccPropietario));

        }

    }

    void gastoInmuebleInquilino() {

        BigDecimal valor = BigDecimal.valueOf(Double.parseDouble(txtValor.getText()));

        inquilinoSeleccionado = contratoDAO.findByInmuebleAndActivo(inmuebleSeleccionado, true).getInquilino();
        gastoInmuebleInquilino = new GastoInmuebleInquilino();
        gastoInmuebleInquilino.setDescripcion(txtDetalle.getText());
        gastoInmuebleInquilino.setFecha(new Date());
        gastoInmuebleInquilino.setInmueble(inmuebleSeleccionado);
        gastoInmuebleInquilino.setMoneda(monedaSeleccionada);
        gastoInmuebleInquilino.setInquilino(inquilinoSeleccionado);
        gastoInmuebleInquilino.setRubro(parametros.getRubroObrasMantenimiento());
        gastoInmuebleInquilino.setValor(valor);
        gastoInmuebleInquilino.setSaldo(valor);
        gastoInmuebleInquilino.setSituacion(Situacion.PENDIENTE);
        gastoInmuebleInquilino.setProveedor((Proveedor) cbProveedor.getSelectedItem());
        gastoInmuebleInquilinoDAO.save(gastoInmuebleInquilino);

    }

    void gastoInmuebleInmobiliaria() {

        BigDecimal valor = BigDecimal.valueOf(Double.parseDouble(txtValor.getText()));

        gastoInmuebleInmobiliaria = new GastoInmuebleInmobiliaria();
        gastoInmuebleInmobiliaria.setDescripcion(txtDetalle.getText());
        gastoInmuebleInmobiliaria.setFecha(new Date());
        gastoInmuebleInmobiliaria.setInmueble(inmuebleSeleccionado);
        gastoInmuebleInmobiliaria.setMoneda(monedaSeleccionada);
        gastoInmuebleInmobiliaria.setRubro(parametros.getRubroObrasMantenimiento());
        gastoInmuebleInmobiliaria.setValor(valor);
        gastoInmuebleInmobiliaria.setProveedor((Proveedor) cbProveedor.getSelectedItem());
        gastoInmuebleInmobiliariaDAO.save(gastoInmuebleInmobiliaria);

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
        jPanel7 = new javax.swing.JPanel();
        rbPropietario = new javax.swing.JRadioButton();
        rbInquilino = new javax.swing.JRadioButton();
        rbInmobiliaria = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        txtInmueble = new javax.swing.JTextField();
        cbTipoDeCaja = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbProveedor = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Nuevo gasto de inmueble");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel3.add(btnGuardar);
        jPanel3.add(btnVolver);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 7;
        jPanel1.add(jPanel3, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Descripción");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanel1.add(jLabel9, gridBagConstraints);

        txtDetalle.setColumns(20);
        txtDetalle.setRows(5);
        jScrollPane1.setViewportView(txtDetalle);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbMoneda, gridBagConstraints);

        txtValor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtValor, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Moneda");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Inmueble seleccionado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel1.add(jLabel5, gridBagConstraints);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Destino del Gasto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel7.setLayout(new java.awt.GridBagLayout());

        buttonGroup1.add(rbPropietario);
        rbPropietario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbPropietario.setSelected(true);
        rbPropietario.setText("Propietario");
        jPanel7.add(rbPropietario, new java.awt.GridBagConstraints());

        buttonGroup1.add(rbInquilino);
        rbInquilino.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbInquilino.setText("Inquilino");
        jPanel7.add(rbInquilino, new java.awt.GridBagConstraints());

        buttonGroup1.add(rbInmobiliaria);
        rbInmobiliaria.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbInmobiliaria.setText("Inmobiliaria");
        jPanel7.add(rbInmobiliaria, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel1.add(jPanel7, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Valor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel6, gridBagConstraints);

        txtInmueble.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        txtInmueble.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtInmueble, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbTipoDeCaja, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Proveedor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel10, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Tipo de caja");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel11, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbProveedor, gridBagConstraints);

        jButton1.setText("Nuevo proveedor");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jButton1, gridBagConstraints);

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ProveedorDetallesDlg nuevoProveedor = new ProveedorDetallesDlg(null, true);
        nuevoProveedor.setVisible(true);
        nuevoProveedor.toFront();
        cargaProveedores();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BotonGuardar btnGuardar;
    private botones.BotonVolver btnVolver;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbMoneda;
    public javax.swing.JComboBox cbProveedor;
    public javax.swing.JComboBox cbTipoDeCaja;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbInmobiliaria;
    private javax.swing.JRadioButton rbInquilino;
    private javax.swing.JRadioButton rbPropietario;
    private javax.swing.JTextArea txtDetalle;
    private javax.swing.JTextField txtInmueble;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables

    private void cargaProveedores() {

        cbProveedor.removeAllItems();
        AutoCompleteDecorator.decorate(cbProveedor);
        for (Proveedor proveedor : proveedorDAO.findAll()) {
            cbProveedor.addItem(proveedor);
        }
    }
}
