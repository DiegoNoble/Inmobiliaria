package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.Banco;
import com.dnsoft.inmobiliaria.beans.Comodidad;
import com.dnsoft.inmobiliaria.beans.Iva;
import com.dnsoft.inmobiliaria.beans.Parametros;
import com.dnsoft.inmobiliaria.beans.Rubro;
import com.dnsoft.inmobiliaria.beans.TipoDeCaja;
import com.dnsoft.inmobiliaria.beans.TipoDocumento;
import com.dnsoft.inmobiliaria.beans.TipoInmueble;
import com.dnsoft.inmobiliaria.daos.IBancoDAO;
import com.dnsoft.inmobiliaria.daos.IComodidadDAO;
import com.dnsoft.inmobiliaria.daos.IIvaDAO;
import com.dnsoft.inmobiliaria.daos.IParametrosDAO;
import com.dnsoft.inmobiliaria.daos.IRubroDAO;
import com.dnsoft.inmobiliaria.daos.ITipoDeCajaDAO;
import com.dnsoft.inmobiliaria.daos.ITipoDocumentoDAO;
import com.dnsoft.inmobiliaria.daos.ITipoInmuebleDAO;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ControlarEntradaTexto;
import com.dnsoft.inmobiliaria.utils.OptionPaneEstandar;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Diego Noble
 */
public class ParametrosView extends javax.swing.JDialog {

    Container container;

    List<Rubro> listRubro;
    IRubroDAO rubrosDAO;
    IIvaDAO iivadao;
    IParametrosDAO parametrosSeleccionadosDAO;
    ITipoInmuebleDAO tipoInmuebleDAO;
    IBancoDAO bancoDAO;
    IComodidadDAO comodidadDAO;
    ITipoDocumentoDAO tipoDocumentoDAO;
    ITipoDeCajaDAO tipoDeCajaDAO;
    Parametros parametrosSeleccionados;

    public ParametrosView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.container = Container.getInstancia();
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logoTrans.png")));
        //CIERRA JOPTIONPANE CON ESCAPE
        jPanel1.grabFocus();
        jPanel1.addKeyListener(new OptionPaneEstandar(this));
        
        setLocationRelativeTo(null);
        inicio();
    }

    final void inicio() {

        parametrosSeleccionadosDAO = container.getBean(IParametrosDAO.class);
        rubrosDAO = container.getBean(IRubroDAO.class);
        tipoInmuebleDAO = container.getBean(ITipoInmuebleDAO.class);
        comodidadDAO = container.getBean(IComodidadDAO.class);
        bancoDAO = container.getBean(IBancoDAO.class);
        tipoDocumentoDAO = container.getBean(ITipoDocumentoDAO.class);
        tipoDeCajaDAO = container.getBean(ITipoDeCajaDAO.class);

        iivadao = container.getBean(IIvaDAO.class);
        listRubro = rubrosDAO.findAll();
        accionesBotones();
        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
        txtPorcentageRetencionIRPF.setDocument(new ControlarEntradaTexto(10, chs));
        txtMínimoComisionPesos.setDocument(new ControlarEntradaTexto(10, chs));
        creaRubros();
        comboRubro(cbAportes);
        comboRubro(CbInsumosCocinaLimpieza);
        comboRubro(cbAlquileres);
        comboRubro(cbImpuestos);
        comboRubro(cbInsumosOficina);
        comboRubro(cbObrasMantenimiento);
        comboRubro(cbPagoFacturas);
        comboRubro(cbRetirosPatronales);
        comboRubro(cbRetirosPropietarios);
        comboRubro(cbSueldos);
        comboRubro(cbTransferencias);
        comboRubro(cbVales);
        comboRubro(cbCobroDeudaPropietarios);
        comboRubro(cbOtrosGastosTerceros);

        buscaParametros();
        buscaIVAS();
        cargaDatosIniciales();

    }

    void accionesBotones() {

        btnGuardar1.addMouseListener(new MouseAdapter() {
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

    void guardarCambios() {
        try {
            parametrosSeleccionados.setMinimoComisionPesos(BigDecimal.valueOf(Double.parseDouble(txtMínimoComisionPesos.getText())));
            parametrosSeleccionados.setPorcentageRetencionIRPF(BigDecimal.valueOf(Double.parseDouble(txtPorcentageRetencionIRPF.getText())));
            parametrosSeleccionados.setRubroAlquileres((Rubro) cbAlquileres.getSelectedItem());
            parametrosSeleccionados.setRubroAportes((Rubro) cbAportes.getSelectedItem());
            parametrosSeleccionados.setRubroImpuestos((Rubro) cbImpuestos.getSelectedItem());
            parametrosSeleccionados.setRubroInsumosCocinaLimpieza((Rubro) CbInsumosCocinaLimpieza.getSelectedItem());
            parametrosSeleccionados.setRubroInsumosOficina((Rubro) cbInsumosOficina.getSelectedItem());
            parametrosSeleccionados.setRubroObrasMantenimiento((Rubro) cbObrasMantenimiento.getSelectedItem());
            parametrosSeleccionados.setRubroPagoFacturas((Rubro) cbPagoFacturas.getSelectedItem());
            parametrosSeleccionados.setRubroRetirosPatronales((Rubro) cbRetirosPatronales.getSelectedItem());
            parametrosSeleccionados.setRubroRetirosPropietarios((Rubro) cbRetirosPropietarios.getSelectedItem());
            parametrosSeleccionados.setRubroSueldos((Rubro) cbSueldos.getSelectedItem());
            parametrosSeleccionados.setRubroTransferencias((Rubro) cbTransferencias.getSelectedItem());
            parametrosSeleccionados.setRubroVales((Rubro) cbVales.getSelectedItem());
            parametrosSeleccionados.setOtrosGastosTerceros((Rubro) cbOtrosGastosTerceros.getSelectedItem());
            parametrosSeleccionados.setCobroDeudaPopietario((Rubro) cbCobroDeudaPropietarios.getSelectedItem());
            parametrosSeleccionados.setMinimoComisionDolares(BigDecimal.valueOf(Double.parseDouble(txtMínimoComisionDolares.getText())));
            parametrosSeleccionados.setMinimoComisionUI(BigDecimal.valueOf(Double.parseDouble(txtMínimoComisionUI.getText())));
            parametrosSeleccionados.setMinimoComisionUR(BigDecimal.valueOf(Double.parseDouble(txtMínimoComisionUR.getText())));

            parametrosSeleccionadosDAO.save(parametrosSeleccionados);
            JOptionPane.showMessageDialog(null, "Se registro correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar registos " + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }

    void buscaParametros() {
        List<Parametros> parametros = parametrosSeleccionadosDAO.findAll();
        if (!parametros.isEmpty()) {
            parametrosSeleccionados = parametros.get(0);

            txtMínimoComisionPesos.setText(parametrosSeleccionados.getMinimoComisionPesos().toString());
            txtMínimoComisionDolares.setText(parametrosSeleccionados.getMinimoComisionDolares().toString());
            txtMínimoComisionUI.setText(parametrosSeleccionados.getMinimoComisionUI().toString());
            txtMínimoComisionUR.setText(parametrosSeleccionados.getMinimoComisionUR().toString());
            txtPorcentageRetencionIRPF.setText(parametrosSeleccionados.getPorcentageRetencionIRPF().toString());
            cbAlquileres.setSelectedItem(parametrosSeleccionados.getRubroAlquileres());
            cbAportes.setSelectedItem(parametrosSeleccionados.getRubroAportes());
            cbImpuestos.setSelectedItem(parametrosSeleccionados.getRubroImpuestos());
            CbInsumosCocinaLimpieza.setSelectedItem(parametrosSeleccionados.getRubroInsumosCocinaLimpieza());
            cbInsumosOficina.setSelectedItem(parametrosSeleccionados.getRubroInsumosOficina());
            cbObrasMantenimiento.setSelectedItem(parametrosSeleccionados.getRubroObrasMantenimiento());
            cbPagoFacturas.setSelectedItem(parametrosSeleccionados.getRubroPagoFacturas());
            cbCobroDeudaPropietarios.setSelectedItem(parametrosSeleccionados.getCobroDeudaPopietario());
            cbRetirosPatronales.setSelectedItem(parametrosSeleccionados.getRubroRetirosPatronales());
            cbRetirosPropietarios.setSelectedItem(parametrosSeleccionados.getRubroRetirosPropietarios());
            cbSueldos.setSelectedItem(parametrosSeleccionados.getRubroSueldos());
            cbOtrosGastosTerceros.setSelectedItem(parametrosSeleccionados.getOtrosGastosTerceros());
            cbTransferencias.setSelectedItem(parametrosSeleccionados.getRubroTransferencias());
            cbVales.setSelectedItem(parametrosSeleccionados.getRubroVales());
        } else {
            parametrosSeleccionados = new Parametros();
        }
    }

    private void buscaIVAS() {
        if (iivadao.findAll().isEmpty()) {
            iivadao.save(new Iva("Tasa básica 22%", BigDecimal.valueOf(1.22)));
            iivadao.save(new Iva("Tasa mínima 10%", BigDecimal.valueOf(1.10)));
            iivadao.save(new Iva("Exento", BigDecimal.valueOf(1.00)));
        }
    }

    void creaRubros() {
        Rubro rubro;
        rubro = (Rubro) rubrosDAO.findByNombre("Alquileres");
        if (rubro == null) {
            rubrosDAO.save(new Rubro("Alquileres"));
        }
        rubro = (Rubro) rubrosDAO.findByNombre("Aportes");
        if (rubro == null) {
            rubrosDAO.save(new Rubro("Aportes"));
        }
        rubro = (Rubro) rubrosDAO.findByNombre("Retiros propietarios");
        if (rubro == null) {
            rubrosDAO.save(new Rubro("Retiros propietarios"));
        }
        rubro = (Rubro) rubrosDAO.findByNombre("Retiros patronales");
        if (rubro == null) {
            rubrosDAO.save(new Rubro("Retiros patronales"));
        }
        rubro = (Rubro) rubrosDAO.findByNombre("Vales");
        if (rubro == null) {
            rubrosDAO.save(new Rubro("Vales"));
        }
        rubro = (Rubro) rubrosDAO.findByNombre("Transferencias");
        if (rubro == null) {
            rubrosDAO.save(new Rubro("Transferencias"));
        }
        rubro = (Rubro) rubrosDAO.findByNombre("Insumos Oficina");
        if (rubro == null) {
            rubrosDAO.save(new Rubro("Insumos Oficina"));
        }
        rubro = (Rubro) rubrosDAO.findByNombre("Insumos Cocina y Limpieza");
        if (rubro == null) {
            rubrosDAO.save(new Rubro("Insumos Cocina y Limpieza"));
        }
        rubro = (Rubro) rubrosDAO.findByNombre("Sueldos");
        if (rubro == null) {
            rubrosDAO.save(new Rubro("Sueldos"));
        }
        rubro = (Rubro) rubrosDAO.findByNombre("Impuestos");
        if (rubro == null) {
            rubrosDAO.save(new Rubro("Impuestos"));
        }
        rubro = (Rubro) rubrosDAO.findByNombre("Obras y mantenimiento");
        if (rubro == null) {
            rubrosDAO.save(new Rubro("Obras y mantenimiento"));
        }
        rubro = (Rubro) rubrosDAO.findByNombre("Pago facturas");
        if (rubro == null) {
            rubrosDAO.save(new Rubro("Pago facturas"));
        }
        rubro = (Rubro) rubrosDAO.findByNombre("Cobro deuda propietario");
        if (rubro == null) {
            rubrosDAO.save(new Rubro("Cobro deuda propietario"));
        }
         rubro = (Rubro) rubrosDAO.findByNombre("Otros gastos de terceros");
        if (rubro == null) {
            rubrosDAO.save(new Rubro("Otros gastos de terceros"));
        }
        listRubro.clear();
        listRubro = rubrosDAO.findAll();
    }

    void comboRubro(JComboBox comboRubro) {
        AutoCompleteDecorator.decorate(comboRubro);
        for (Rubro tipos : listRubro) {
            comboRubro.addItem(tipos);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar1 = new botones.BotonGuardar();
        btnVolver = new botones.BotonVolver();
        jPanel7 = new javax.swing.JPanel();
        cbVales = new javax.swing.JComboBox();
        cbAlquileres = new javax.swing.JComboBox();
        cbInsumosOficina = new javax.swing.JComboBox();
        CbInsumosCocinaLimpieza = new javax.swing.JComboBox();
        cbSueldos = new javax.swing.JComboBox();
        cbTransferencias = new javax.swing.JComboBox();
        cbRetirosPatronales = new javax.swing.JComboBox();
        cbObrasMantenimiento = new javax.swing.JComboBox();
        cbImpuestos = new javax.swing.JComboBox();
        cbPagoFacturas = new javax.swing.JComboBox();
        cbAportes = new javax.swing.JComboBox();
        cbRetirosPropietarios = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtPorcentageRetencionIRPF = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtMínimoComisionPesos = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtMínimoComisionUI = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtMínimoComisionUR = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtMínimoComisionDolares = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        cbCobroDeudaPropietarios = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        cbOtrosGastosTerceros = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Parametros generales");
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
        jPanel3.add(btnGuardar1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnVolver, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        jPanel1.add(jPanel3, gridBagConstraints);

        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbVales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbAlquileres, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbInsumosOficina, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(CbInsumosCocinaLimpieza, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbSueldos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbTransferencias, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbRetirosPatronales, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbObrasMantenimiento, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbImpuestos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbPagoFacturas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbAportes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbRetirosPropietarios, gridBagConstraints);

        jLabel1.setText("Vales");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel7.add(jLabel1, gridBagConstraints);

        jLabel2.setText("Aportes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel7.add(jLabel2, gridBagConstraints);

        jLabel4.setText("Transferencias");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanel7.add(jLabel4, gridBagConstraints);

        jLabel5.setText("Retiros patronales");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel7.add(jLabel5, gridBagConstraints);

        jLabel6.setText("Insumos cocina y limpieza");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        jPanel7.add(jLabel6, gridBagConstraints);

        jLabel7.setText("Obras y mantenimiento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        jPanel7.add(jLabel7, gridBagConstraints);

        jLabel8.setText("Retiros propietarios");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel7.add(jLabel8, gridBagConstraints);

        jLabel9.setText("Insumos oficina");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel7.add(jLabel9, gridBagConstraints);

        jLabel10.setText("Sueldos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanel7.add(jLabel10, gridBagConstraints);

        jLabel11.setText("Impuestos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        jPanel7.add(jLabel11, gridBagConstraints);

        jLabel12.setText("Otros Gastos de terceros");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        jPanel7.add(jLabel12, gridBagConstraints);

        jLabel13.setText("Alquileres");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel7.add(jLabel13, gridBagConstraints);

        jLabel14.setText("Porcentage Retencion IRPF");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        jPanel7.add(jLabel14, gridBagConstraints);

        txtPorcentageRetencionIRPF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtPorcentageRetencionIRPF, gridBagConstraints);

        jLabel15.setText("Mínimo comisión Dólares");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        jPanel7.add(jLabel15, gridBagConstraints);

        txtMínimoComisionPesos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtMínimoComisionPesos, gridBagConstraints);

        jLabel16.setText("Mínimo comisión UI");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        jPanel7.add(jLabel16, gridBagConstraints);

        txtMínimoComisionUI.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtMínimoComisionUI, gridBagConstraints);

        jLabel17.setText("Mínimo comisión UR");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        jPanel7.add(jLabel17, gridBagConstraints);

        txtMínimoComisionUR.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtMínimoComisionUR, gridBagConstraints);

        jLabel18.setText("Mínimo comisión Pesos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        jPanel7.add(jLabel18, gridBagConstraints);

        txtMínimoComisionDolares.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtMínimoComisionDolares, gridBagConstraints);

        jLabel19.setText("Cobro deuda propietarios");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        jPanel7.add(jLabel19, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbCobroDeudaPropietarios, gridBagConstraints);

        jLabel20.setText("Pagos facturas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        jPanel7.add(jLabel20, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbOtrosGastosTerceros, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel7, gridBagConstraints);

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox CbInsumosCocinaLimpieza;
    public botones.BotonGuardar btnGuardar1;
    public botones.BotonVolver btnVolver;
    private javax.swing.JComboBox cbAlquileres;
    private javax.swing.JComboBox cbAportes;
    private javax.swing.JComboBox cbCobroDeudaPropietarios;
    private javax.swing.JComboBox cbImpuestos;
    private javax.swing.JComboBox cbInsumosOficina;
    private javax.swing.JComboBox cbObrasMantenimiento;
    private javax.swing.JComboBox cbOtrosGastosTerceros;
    private javax.swing.JComboBox cbPagoFacturas;
    private javax.swing.JComboBox cbRetirosPatronales;
    private javax.swing.JComboBox cbRetirosPropietarios;
    private javax.swing.JComboBox cbSueldos;
    private javax.swing.JComboBox cbTransferencias;
    private javax.swing.JComboBox cbVales;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTextField txtMínimoComisionDolares;
    private javax.swing.JTextField txtMínimoComisionPesos;
    private javax.swing.JTextField txtMínimoComisionUI;
    private javax.swing.JTextField txtMínimoComisionUR;
    private javax.swing.JTextField txtPorcentageRetencionIRPF;
    // End of variables declaration//GEN-END:variables

    private void cargaDatosIniciales() {
        if (tipoInmuebleDAO.findAll().isEmpty()) {
            tipoInmuebleDAO.save(new TipoInmueble("Apartamento"));
            tipoInmuebleDAO.save(new TipoInmueble("Casa"));
            tipoInmuebleDAO.save(new TipoInmueble("Local comercial"));
            tipoInmuebleDAO.save(new TipoInmueble("Terreno"));
            tipoInmuebleDAO.save(new TipoInmueble("Chacra"));
        }
        if (comodidadDAO.findAll().isEmpty()) {
            comodidadDAO.save(new Comodidad("Cuarto"));
            comodidadDAO.save(new Comodidad("Baño"));
            comodidadDAO.save(new Comodidad("Garage"));
            comodidadDAO.save(new Comodidad("Patio"));
            comodidadDAO.save(new Comodidad("Estufa"));
        }
        if (bancoDAO.findAll().isEmpty()) {
            bancoDAO.save(new Banco("BROU"));
            bancoDAO.save(new Banco("BBVA"));
            bancoDAO.save(new Banco("SCOTIABANK"));
            bancoDAO.save(new Banco("BANDES"));
            bancoDAO.save(new Banco("ITAU"));

        }
        if (tipoDocumentoDAO.findAll().isEmpty()) {
            tipoDocumentoDAO.save(new TipoDocumento("C.I."));
            tipoDocumentoDAO.save(new TipoDocumento("R.U.T."));
            tipoDocumentoDAO.save(new TipoDocumento("PASAPORTE"));
        }
        if (tipoDeCajaDAO.findAll().isEmpty()) {
            tipoDeCajaDAO.save(new TipoDeCaja("Mostrador"));
        }
    }
}
