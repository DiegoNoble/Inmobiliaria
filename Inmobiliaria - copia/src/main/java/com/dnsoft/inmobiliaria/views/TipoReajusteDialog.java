package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.TipoReajusteAlquilerEnum;
import com.dnsoft.inmobiliaria.beans.TipoReajuste;
import com.dnsoft.inmobiliaria.daos.ITipoReajusteDAO;
import com.dnsoft.inmobiliaria.models.TipoReajusteTableModel;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ControlarEntradaTexto;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Diego Noble
 */
public final class TipoReajusteDialog extends javax.swing.JDialog {

    ITipoReajusteDAO tiporeajusteDAO;
    Container container;
    TipoReajuste tiporeajusteSeleccionado;
    List<TipoReajuste> listTiporeajuste;
    TipoReajusteTableModel tableModel;
    ListSelectionModel listModel;
    int nuevo = 0;

    public TipoReajusteDialog(java.awt.Frame parent, boolean modal, Component frame) {
        super(parent, modal);
        initComponents();
        this.container = Container.getInstancia();
        setLocationRelativeTo(frame);
        inicio();

    }

    void inicio() {
        tiporeajusteDAO = container.getBean(ITipoReajusteDAO.class);
        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
        txtValor.setDocument(new ControlarEntradaTexto(10, chs));
        txtPeriodicidad.setDocument(new ControlarEntradaTexto(2, chs));
        txtPeriodo.setDocument(new ControlarEntradaTexto(2, chs));
        cbTipoReajuste.setModel(new DefaultComboBoxModel(TipoReajusteAlquilerEnum.values()));
        configuraTbl();
        buscaDatos();
        accionesBotones();

    }

    void accionesBotones() {
        btnNuevo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                habilitaCampos();
                nuevo = 1;
            }
        });
        btnCancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                deshabilitaCampos();
                nuevo = 0;
            }
        });
        btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                guardar();
                deshabilitaCampos();
                limpiaCampos();
                buscaDatos();
                nuevo = 0;

            }

        });
        btnValores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                CotizacionReajustesVariablesDialog cotizacionIndicesDialog = new CotizacionReajustesVariablesDialog(null, true, tiporeajusteSeleccionado);
                cotizacionIndicesDialog.setVisible(true);
                cotizacionIndicesDialog.toFront();
            }
        });

    }

    final void configuraTbl() {
        ((DefaultTableCellRenderer) tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listTiporeajuste = new ArrayList<>();

        tableModel = new TipoReajusteTableModel(listTiporeajuste);
        tbl.setModel(tableModel);

        tbl.setRowHeight(25);

        ListSelectionModel selectionModel = tbl.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {

                if (tbl.getSelectedRow() != -1) {
                    tiporeajusteSeleccionado = listTiporeajuste.get(tbl.getSelectedRow());
                    muestraDetalles();

                } else {
                    limpiaCampos();
                }
            }
        });

    }

    void buscaDatos() {
        listTiporeajuste.clear();
        listTiporeajuste.addAll(tiporeajusteDAO.findAll());
        tableModel.fireTableDataChanged();
    }

    private void guardar() {
        try {
            TipoReajuste nuevoTipoReajuste = new TipoReajuste();
            nuevoTipoReajuste.setDescripcion(txtDescripcion.getText());
            nuevoTipoReajuste.setNombre(txtNombre.getText());
            nuevoTipoReajuste.setPeriodicidad(Integer.parseInt(txtPeriodicidad.getText()));
            nuevoTipoReajuste.setPeriodogeneracion(Integer.parseInt(txtPeriodo.getText()));
            if (cbTipoReajuste.getSelectedItem() == TipoReajusteAlquilerEnum.FIJO) {
                nuevoTipoReajuste.setValor(BigDecimal.valueOf(Double.parseDouble(txtValor.getText())));
            }
            nuevoTipoReajuste.setTipoReajusteAlquilerEnum((TipoReajusteAlquilerEnum) cbTipoReajuste.getSelectedItem());

            tiporeajusteDAO.save(nuevoTipoReajuste);
            JOptionPane.showMessageDialog(this, "Se guardaron los datos correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar datos " + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void habilitaCampos() {
        txtDescripcion.setEnabled(true);
        txtNombre.setEnabled(true);
        txtPeriodicidad.setEnabled(true);
        txtPeriodo.setEnabled(true);
        if (cbTipoReajuste.getSelectedItem() == TipoReajusteAlquilerEnum.FIJO) {
            txtValor.setEnabled(true);
        } else {
            txtValor.setEnabled(false);
            txtValor.setText("");

        }
        cbTipoReajuste.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnGuardar.setEnabled(true);
        btnNuevo.setEnabled(false);
    }

    void deshabilitaCampos() {
        txtDescripcion.setEnabled(false);
        txtNombre.setEnabled(false);
        txtPeriodicidad.setEnabled(false);
        txtPeriodo.setEnabled(false);
        txtValor.setEnabled(false);
        cbTipoReajuste.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnNuevo.setEnabled(true);
    }

    private void limpiaCampos() {
        txtDescripcion.setText("");
        txtNombre.setText("");
        txtPeriodicidad.setText("");
        txtPeriodo.setText("");
        txtValor.setText("");
    }

    void muestraDetalles() {
        txtDescripcion.setText(tiporeajusteSeleccionado.getDescripcion());
        txtNombre.setText(tiporeajusteSeleccionado.getNombre());
        txtPeriodicidad.setText(tiporeajusteSeleccionado.getPeriodicidad().toString());
        txtPeriodo.setText(tiporeajusteSeleccionado.getPeriodogeneracion().toString());
        cbTipoReajuste.setSelectedItem(tiporeajusteSeleccionado.getTipoReajusteAlquilerEnum());

        if (tiporeajusteSeleccionado.getTipoReajusteAlquilerEnum() == TipoReajusteAlquilerEnum.FIJO) {
            txtValor.setText(tiporeajusteSeleccionado.getValor().toString());
            btnValores.setEnabled(false);
        } else if (tiporeajusteSeleccionado.getTipoReajusteAlquilerEnum() == TipoReajusteAlquilerEnum.COEFICIENTE_VARIABLE) {
            txtValor.setText("");
            btnValores.setEnabled(true);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnValores = new botones.BotonPagar();
        btnGuardar = new botones.BotonGuardar();
        btnNuevo = new botones.BotonNuevo();
        btnCancelar = new botones.BotonCancelar();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtPeriodicidad = new javax.swing.JTextField();
        txtPeriodo = new javax.swing.JTextField();
        txtValor = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        cbTipoReajuste = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 450));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel3.setLayout(new java.awt.GridBagLayout());

        btnValores.setEnabled(false);
        btnValores.setText("Valores");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnValores, gridBagConstraints);

        btnGuardar.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnGuardar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnNuevo, gridBagConstraints);

        btnCancelar.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(btnCancelar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Registro de tipos de reajuste");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

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
        jScrollPane1.setViewportView(tbl);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel4, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        txtPeriodicidad.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtPeriodicidad, gridBagConstraints);

        txtPeriodo.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtPeriodo, gridBagConstraints);

        txtValor.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtValor, gridBagConstraints);

        txtNombre.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtNombre, gridBagConstraints);

        cbTipoReajuste.setEnabled(false);
        cbTipoReajuste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipoReajusteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbTipoReajuste, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Periodo de generación");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Valor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Periodicidad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel5, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Tipo de reajuste");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel1.add(jLabel7, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Descripción");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel6, gridBagConstraints);

        txtDescripcion.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtDescripcion, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbTipoReajusteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoReajusteActionPerformed

        if (nuevo == 1) {

            if (cbTipoReajuste.getSelectedItem() == TipoReajusteAlquilerEnum.FIJO) {
                txtValor.setEnabled(true);
            } else if (cbTipoReajuste.getSelectedItem() == TipoReajusteAlquilerEnum.COEFICIENTE_VARIABLE) {
                txtValor.setEnabled(false);
            }
        }
    }//GEN-LAST:event_cbTipoReajusteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BotonCancelar btnCancelar;
    private botones.BotonGuardar btnGuardar;
    private botones.BotonNuevo btnNuevo;
    private botones.BotonPagar btnValores;
    private javax.swing.JComboBox cbTipoReajuste;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPeriodicidad;
    private javax.swing.JTextField txtPeriodo;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables

}
