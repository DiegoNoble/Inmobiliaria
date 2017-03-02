package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.Inmueble;
import com.dnsoft.inmobiliaria.beans.PropietarioHasInmueble;
import com.dnsoft.inmobiliaria.controllers.InmueblesController;
import com.dnsoft.inmobiliaria.daos.IPropietarioHasInmuebleDAO;
import com.dnsoft.inmobiliaria.models.PropietariosHasInmueblesTableModel;
import com.dnsoft.inmobiliaria.utils.Container;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Diego Noble
 */
public final class DetallePropietariosInmueble extends javax.swing.JDialog {

    List<PropietarioHasInmueble> listPropietariosHasInmueble;
    PropietariosHasInmueblesTableModel tableModelAsignados;
    IPropietarioHasInmuebleDAO propietarioHasInmuebleDAO;
    Container container;
    Inmueble inmuebleSeleccionado;

    public DetallePropietariosInmueble(java.awt.Frame parent, boolean modal, Inmueble inmuebleSeleccionado) {
        super(parent, modal);
        initComponents();
        this.container = Container.getInstancia();
        propietarioHasInmuebleDAO = container.getBean(IPropietarioHasInmuebleDAO.class);
        this.inmuebleSeleccionado = inmuebleSeleccionado;
        setLocationRelativeTo(null);
        configTblPropietarios();

        listPropietariosHasInmueble.addAll(propietarioHasInmuebleDAO.findByInmueble(inmuebleSeleccionado));
        tableModelAsignados.fireTableDataChanged();
    }
    
    public DetallePropietariosInmueble(java.awt.Frame parent, boolean modal, Inmueble inmuebleSeleccionado, InmueblesController controller) {
        super(parent, modal);
        initComponents();
        this.container = Container.getInstancia();
        propietarioHasInmuebleDAO = container.getBean(IPropietarioHasInmuebleDAO.class);
        this.inmuebleSeleccionado = inmuebleSeleccionado;
        setLocationRelativeTo(null);
        configTblPropietarios();

        listPropietariosHasInmueble.addAll(propietarioHasInmuebleDAO.findByInmueble(inmuebleSeleccionado));
        tableModelAsignados.fireTableDataChanged();
    }
    

    void configTblPropietarios() {
        ((DefaultTableCellRenderer) tblPropietarios.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listPropietariosHasInmueble = new ArrayList<>();
        tableModelAsignados = new PropietariosHasInmueblesTableModel(listPropietariosHasInmueble);
        tblPropietarios.setModel(tableModelAsignados);
        tblPropietarios.removeColumn(tblPropietarios.getColumn("Dirección"));
        tblPropietarios.removeColumn(tblPropietarios.getColumn("Padrón"));
        tblPropietarios.removeColumn(tblPropietarios.getColumn("Situación"));

        tblPropietarios.setRowHeight(25);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPropietarios = new javax.swing.JTable();

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Propietarios del inmueble");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

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
        jScrollPane1.setViewportView(tblPropietarios);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPropietarios;
    // End of variables declaration//GEN-END:variables

}
