/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.Renderers.TableRendererColorActivo;
import com.dnsoft.inmobiliaria.beans.Propietario;
import com.dnsoft.inmobiliaria.daos.IPropietarioDAO;
import com.dnsoft.inmobiliaria.models.PropietariosTableModel;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.OptionPaneEstandar;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author Diego Noble
 */
public class InformeComisiones extends javax.swing.JDialog {

    Container container;
    PropietariosTableModel tableModel;
    List<Propietario> listPropietarios;
    IPropietarioDAO propietariosDAO;
    ListSelectionModel listModel;
    Propietario propietarioSeleccionado;

    public InformeComisiones(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        inicia();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logoTrans.png")));
        //CIERRA JOPTIONPANE CON ESCAPE
        jPanel1.grabFocus();
        jPanel1.addKeyListener(new OptionPaneEstandar(this));
    }

    private void inicia() {

        this.container = Container.getInstancia();
        propietariosDAO = container.getBean(IPropietarioDAO.class);
        configTblPropietario();
    }

    public void go() {
        this.setVisible(true);
        this.toFront();

    }

    private void configTblPropietario() {

        ((DefaultTableCellRenderer) tblPropietarios.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listPropietarios = new ArrayList<>();
        listPropietarios.addAll(propietariosDAO.findAllNombreDocumento());

        tableModel = new PropietariosTableModel(listPropietarios);
        tblPropietarios.setModel(tableModel);
        tblPropietarios.setDefaultRenderer(Object.class, new TableRendererColorActivo(2));
        tblPropietarios.setRowHeight(25);
        int[] anchos = {25, 150, 50, 100, 150};

        for (int i = 0; i < tblPropietarios.getColumnCount(); i++) {

            tblPropietarios.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }

        tblPropietarios.getColumn("Activo").setWidth(0);
        tblPropietarios.getColumn("Activo").setMaxWidth(0);
        tblPropietarios.getColumn("Activo").setMinWidth(0);
        tblPropietarios.getColumn("Activo").setPreferredWidth(0);

        listModel = tblPropietarios.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tblPropietarios.getSelectedRow() != -1) {
                    propietarioSeleccionado = propietariosDAO.findByPropietarioEager(listPropietarios.get(tblPropietarios.getSelectedRow()).getId());
                } else {
                }
            }
        });

        tblPropietarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    editaSeleccionado();
                }
            }
        });

    }

    public void actualizaTbl() {
        listPropietarios.clear();
        listPropietarios.addAll(propietariosDAO.findAllNombreDocumento());
        tableModel.fireTableDataChanged();
    }

    void editaSeleccionado() {
        try {

            PropietariosDetalleDlg editaInquilino = new PropietariosDetalleDlg(null, true, propietarioSeleccionado);
            editaInquilino.setVisible(true);
            editaInquilino.toFront();
            actualizaTbl();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void reporte() {
        try {
            HashMap parametros = new HashMap();
            parametros.clear();
            parametros.put("desde", dpDesde.getDate());
            parametros.put("hasta", dpHasta.getDate());
            List propietarios = new ArrayList();
            int[] selectedRows = tblPropietarios.getSelectedRows();
            for (int selectedRow : selectedRows) {
                propietarios.add(listPropietarios.get(selectedRow).getId());
            }
            parametros.put("propietarios", propietarios);
            parametros.put("tipoContrato", "VENTA");
            btnInformeIngresos2.setUseJPA(Boolean.TRUE);
            btnInformeIngresos2.setReportParameters(parametros);

            btnInformeIngresos2.setReportURL("/ReportesJasperServer/informeComisiones.jasper");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPropietarios = new javax.swing.JTable();
        btnVolver = new botones.BotonVolver();
        btnInforme = new botones.BotonPDF();
        btnInformeIngresos2 = new org.jasper.viewer.components.JasperRunnerButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dpDesde = new org.jdesktop.swingx.JXDatePicker();
        dpHasta = new org.jdesktop.swingx.JXDatePicker();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

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
        tblPropietarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblPropietarios);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jScrollPane1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jPanel3.add(btnVolver, gridBagConstraints);

        btnInforme.setText("Informe");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel3.add(btnInforme, gridBagConstraints);

        btnInformeIngresos2.setText("Balance por Sectores");
        btnInformeIngresos2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformeIngresos2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel3.add(btnInformeIngresos2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Propietarios");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Hasta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel5.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel5.add(dpDesde, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel5.add(dpHasta, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Desde");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel5.add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 451;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(jPanel5, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInformeIngresos2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformeIngresos2ActionPerformed
        reporte();
    }//GEN-LAST:event_btnInformeIngresos2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public botones.BotonPDF btnInforme;
    public org.jasper.viewer.components.JasperRunnerButton btnInformeIngresos2;
    public botones.BotonVolver btnVolver;
    public org.jdesktop.swingx.JXDatePicker dpDesde;
    public org.jdesktop.swingx.JXDatePicker dpHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tblPropietarios;
    // End of variables declaration//GEN-END:variables
}
