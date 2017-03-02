/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.Renderers.MeDefaultCellRenderer;
import com.dnsoft.inmobiliaria.Renderers.MeTimeCellRenderer;
import com.dnsoft.inmobiliaria.beans.TipoReajusteAlquilerEnum;
import com.dnsoft.inmobiliaria.beans.CotizacionReajustes;
import com.dnsoft.inmobiliaria.beans.Mes;
import com.dnsoft.inmobiliaria.beans.TipoReajuste;
import com.dnsoft.inmobiliaria.daos.ICotizacionReajustesDAO;
import com.dnsoft.inmobiliaria.daos.ITipoReajusteDAO;
import com.dnsoft.inmobiliaria.models.CotizacionIndicesTableModel;
import com.dnsoft.inmobiliaria.utils.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;

/**
 *
 * @author dnoble
 */
public final class CotizacionReajustesVariablesDialog extends javax.swing.JDialog {

    private List<CotizacionReajustes> listCotizacionIndices;
    Container container;
    ICotizacionReajustesDAO cotizacionDAO;
    CotizacionIndicesTableModel tableModel;
    ITipoReajusteDAO tipoReajusteDAO;
    TipoReajuste tipoReajuste;

    public CotizacionReajustesVariablesDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        try {
            initComponents();
            this.container = Container.getInstancia();
            inicio();
            buscarTiposReajusteVariables();
            jXHyperlink1.setURI(new URI("http://ciu.org.uy/indicadores_economicos/"));
        } catch (URISyntaxException ex) {
            Logger.getLogger(CotizacionReajustesVariablesDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public CotizacionReajustesVariablesDialog(java.awt.Frame parent, boolean modal,TipoReajuste tipoReajuste) {
        super(parent, modal);
        try {
            initComponents();
            this.container = Container.getInstancia();
            inicio();
            this.tipoReajuste = tipoReajuste;
            cbTiposReajuste.addItem(tipoReajuste);
            jXHyperlink1.setURI(new URI("http://ciu.org.uy/indicadores_economicos/"));
        } catch (URISyntaxException ex) {
            Logger.getLogger(CotizacionReajustesVariablesDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void inicio() {

        cotizacionDAO = container.getBean(ICotizacionReajustesDAO.class);
        tipoReajusteDAO = container.getBean(ITipoReajusteDAO.class);
        defineModelo();

        buscarCotizacionIndiceses();
        accionesBotones();
        setLocationRelativeTo(null);
    }

    void buscarTiposReajusteVariables() {
        for (TipoReajuste tipoReajuste : tipoReajusteDAO.findByTipoReajusteAlquilerEnum(TipoReajusteAlquilerEnum.COEFICIENTE_VARIABLE)) {
            cbTiposReajuste.addItem(tipoReajuste);
        }
    }

    void accionesBotones() {
        btnNuevaCotizacion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                tableModel.agregar(new CotizacionReajustes(new Date(), BigDecimal.ZERO, (TipoReajuste) cbTiposReajuste.getSelectedItem(), verificaMes()));
                btnNuevaCotizacion.setEnabled(false);
                habilitaCampos();
            }
        });
        btnCancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                buscarCotizacionIndiceses();
                deshabilitaCampos();
            }
        });
        btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                guardar();
                deshabilitaCampos();
            }
        });
    }

    void habilitaCampos() {
        btnNuevaCotizacion.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnGuardar.setEnabled(true);
    }

    void deshabilitaCampos() {
        btnNuevaCotizacion.setEnabled(true);
        btnCancelar.setEnabled(false);
        btnGuardar.setEnabled(false);
    }

    void guardar() {
        try {
            cotizacionDAO.save(listCotizacionIndices);
            JOptionPane.showMessageDialog(this, "Se guardaron los datos correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar datos " + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void defineModelo() {

        ((DefaultTableCellRenderer) tblCotizacion.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listCotizacionIndices = new ArrayList<>();

        tableModel = new CotizacionIndicesTableModel(listCotizacionIndices);
        tblCotizacion.setModel(tableModel);

        tblCotizacion.getColumn("Valor").setCellRenderer(new MeDefaultCellRenderer());
        tblCotizacion.getColumn("Fecha").setCellRenderer(new MeTimeCellRenderer());
        tblCotizacion.getColumn("Mes").setCellEditor(new ComboBoxCellEditor(new JComboBox(Mes.values())));

        tblCotizacion.setRowHeight(25);

    }

    Mes verificaMes() {
        Mes mesToReturn;
        Calendar ahora = Calendar.getInstance();
        int mes = ahora.get(Calendar.MONTH);
        switch (mes) {
            case 0:
                mesToReturn = Mes.ENERO;
                break;
            case 1:
                mesToReturn = Mes.FEBRERO;
                break;
            case 2:
                mesToReturn = Mes.MARZO;
                break;
            case 3:
                mesToReturn = Mes.ABRIL;
                break;
            case 4:
                mesToReturn = Mes.MAYO;
                break;
            case 5:
                mesToReturn = Mes.JUNIO;
                break;
            case 6:
                mesToReturn = Mes.JULIO;
                break;
            case 7:
                mesToReturn = Mes.AGOSTO;
                break;
            case 8:
                mesToReturn = Mes.SETIEMBRE;
                break;
            case 9:
                mesToReturn = Mes.OCTUBRE;
                break;
            case 10:
                mesToReturn = Mes.NOVIEMBRE;
                break;
            case 11:
                mesToReturn = Mes.DICIEMBRE;
                break;
            default:
                throw new AssertionError();
        }

        return mesToReturn;

    }

    void buscarCotizacionIndiceses() {

        listCotizacionIndices.clear();
        listCotizacionIndices.addAll(cotizacionDAO.findByTipoReajuste((TipoReajuste) cbTiposReajuste.getSelectedItem()));

        tableModel.fireTableDataChanged();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCotizacion = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnNuevaCotizacion = new botones.BotonNuevo();
        btnGuardar = new botones.BotonGuardar();
        btnCancelar = new botones.BotonCancelar();
        jXHyperlink1 = new org.jdesktop.swingx.JXHyperlink();
        jLabel1 = new javax.swing.JLabel();
        cbTiposReajuste = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel3.setLayout(new java.awt.GridBagLayout());

        tblCotizacion.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblCotizacion);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Coeficiente de reajuste alquileres");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 1;
        getContentPane().add(jPanel2, gridBagConstraints);

        btnNuevaCotizacion.setText("Nueva cotización");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnNuevaCotizacion, gridBagConstraints);

        btnGuardar.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnGuardar, gridBagConstraints);

        btnCancelar.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(btnCancelar, gridBagConstraints);

        jXHyperlink1.setText("Consultar indices online");
        jXHyperlink1.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jXHyperlink1, gridBagConstraints);

        jLabel1.setText("Tipo reajuste");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        getContentPane().add(jLabel1, gridBagConstraints);

        cbTiposReajuste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTiposReajusteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(cbTiposReajuste, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbTiposReajusteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTiposReajusteActionPerformed

        buscarCotizacionIndiceses();

    }//GEN-LAST:event_cbTiposReajusteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BotonCancelar btnCancelar;
    private botones.BotonGuardar btnGuardar;
    private botones.BotonNuevo btnNuevaCotizacion;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbTiposReajuste;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXHyperlink jXHyperlink1;
    private javax.swing.JTable tblCotizacion;
    // End of variables declaration//GEN-END:variables
}
