package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.Caja;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.Rubro;
import com.dnsoft.inmobiliaria.beans.TipoDeCaja;
import com.dnsoft.inmobiliaria.beans.TipoMovimiento;
import com.dnsoft.inmobiliaria.controllers.ControlDeCajaController;
import com.dnsoft.inmobiliaria.daos.ICajaDAO;
import com.dnsoft.inmobiliaria.daos.IRubroDAO;
import com.dnsoft.inmobiliaria.daos.ITipoDeCajaDAO;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ControlarEntradaTexto;
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
public class DetalleMovimientoCajaInmobiliaria extends javax.swing.JDialog {

    Container container;
    IRubroDAO rubroDAO;
    ICajaDAO cajaDAO;
    ITipoDeCajaDAO tipoDeCajaDAO;
    Moneda monedaSeleccionada;
    Rubro rubroSeleccionado;
    ControlDeCajaController cajaController;

    public DetalleMovimientoCajaInmobiliaria(java.awt.Frame parent, boolean modal ) {
        super(parent, modal);
        initComponents();
        this.container = Container.getInstancia();
        inicio();

    }

    public DetalleMovimientoCajaInmobiliaria(java.awt.Frame parent, boolean modal, Rubro rubroSeleccionado, TipoMovimiento tipoMovimiento, ControlDeCajaController cajaController, TipoDeCaja tipoDeCaja) {
        super(parent, modal);
        initComponents();

        this.container = Container.getInstancia();
        inicio();
        if (tipoDeCaja != null) {
            cbTipoDeCaja.setSelectedItem(tipoDeCaja);
        }
        this.cajaController = cajaController;
        this.rubroSeleccionado = rubroSeleccionado;
        cbRubro.setSelectedItem(this.rubroSeleccionado);
        cbRubro.setEnabled(false);
        if (tipoMovimiento == TipoMovimiento.ENTRADA) {
            rbEntrada.setSelected(true);
            rbEntrada.setEnabled(false);
            rbSalida.setEnabled(false);
        } else if (tipoMovimiento == TipoMovimiento.SALIDA) {
            rbSalida.setSelected(true);
            rbEntrada.setEnabled(false);
            rbSalida.setEnabled(false);
        }

    }

    final void inicio() {
        rubroDAO = container.getBean(IRubroDAO.class);
        cajaDAO = container.getBean(ICajaDAO.class);
        tipoDeCajaDAO = container.getBean(ITipoDeCajaDAO.class);
        accionesBotones();
        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-'};
        txtValor.setDocument(new ControlarEntradaTexto(10, chs));
        cargaMonedas();
        cargaRubros();
        cargaTiposDeCaja();
        setLocationRelativeTo(null);
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

    void guardar() {
        monedaSeleccionada = (Moneda) cbMoneda.getSelectedItem();
        rubroSeleccionado = (Rubro) cbRubro.getSelectedItem();

        movimientoCaja();

    }

    void movimientoCaja() {

        try {
            BigDecimal valor = BigDecimal.valueOf(Double.parseDouble(txtValor.getText()));

            if (rbEntrada.isSelected()) {

                Caja movimientoCaja = new Caja();
                movimientoCaja.setDescripcion(txtDetalle.getText());
                movimientoCaja.setEntrada(valor);
                movimientoCaja.setFecha(new Date());
                movimientoCaja.setMoneda(monedaSeleccionada);
                movimientoCaja.setRubro(rubroSeleccionado);
                movimientoCaja.setSaldo(calculaSaldo().add(valor));
                movimientoCaja.setSalida(BigDecimal.ZERO);
                movimientoCaja.setTipoDeCaja((TipoDeCaja) cbTipoDeCaja.getSelectedItem());
                cajaDAO.save(movimientoCaja);

            } else if (rbSalida.isSelected()) {

                Caja movimientoCaja = new Caja();
                movimientoCaja.setDescripcion(txtDetalle.getText());
                movimientoCaja.setEntrada(BigDecimal.ZERO);
                movimientoCaja.setFecha(new Date());
                movimientoCaja.setMoneda(monedaSeleccionada);
                movimientoCaja.setRubro(rubroSeleccionado);
                movimientoCaja.setSaldo(calculaSaldo().subtract(valor));
                movimientoCaja.setSalida(valor);
                movimientoCaja.setTipoDeCaja((TipoDeCaja) cbTipoDeCaja.getSelectedItem());
                cajaDAO.save(movimientoCaja);
            }
            JOptionPane.showMessageDialog(this, "Movimiento registrado correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erroral guardar registros", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

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
        jPanel7 = new javax.swing.JPanel();
        rbEntrada = new javax.swing.JRadioButton();
        rbSalida = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        cbTipoDeCaja = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 400));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Movimiento de caja");
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
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 7;
        jPanel1.add(jPanel3, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Descripción");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jLabel9, gridBagConstraints);

        txtDetalle.setColumns(20);
        txtDetalle.setRows(5);
        jScrollPane1.setViewportView(txtDetalle);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbMoneda, gridBagConstraints);

        txtValor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtValor, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Moneda");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Valor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel5, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Rubro");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel7, gridBagConstraints);

        cbRubro.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbRubro, gridBagConstraints);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tipo movimiento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 14))); // NOI18N
        jPanel7.setLayout(new java.awt.GridBagLayout());

        buttonGroup1.add(rbEntrada);
        rbEntrada.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbEntrada.setSelected(true);
        rbEntrada.setText("ENTRADA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel7.add(rbEntrada, gridBagConstraints);

        buttonGroup1.add(rbSalida);
        rbSalida.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rbSalida.setText("SALIDA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel7.add(rbSalida, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel1.add(jPanel7, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Caja");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel10, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbTipoDeCaja, gridBagConstraints);

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
    private botones.BotonGuardar btnGuardar;
    private botones.BotonVolver btnVolver;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbMoneda;
    private javax.swing.JComboBox cbRubro;
    public javax.swing.JComboBox cbTipoDeCaja;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbEntrada;
    private javax.swing.JRadioButton rbSalida;
    private javax.swing.JTextArea txtDetalle;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
