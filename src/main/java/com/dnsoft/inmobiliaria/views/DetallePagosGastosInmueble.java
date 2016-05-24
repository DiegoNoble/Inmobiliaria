package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.Renderers.MeDateCellRenderer;
import com.dnsoft.inmobiliaria.beans.GastoInmuebleInquilino;
import com.dnsoft.inmobiliaria.beans.GastoInmueblePropietario;
import com.dnsoft.inmobiliaria.beans.PagoGastoInmueble;
import com.dnsoft.inmobiliaria.daos.IPagoGastoInmuebleDAO;
import com.dnsoft.inmobiliaria.models.PagoGastoInmuebleTableModel;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.OptionPaneEstandar;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Diego Noble
 */
public final class DetallePagosGastosInmueble extends javax.swing.JDialog {

    Container container;
    IPagoGastoInmuebleDAO gastoInmuebleDAO;
    List<PagoGastoInmueble> listPagos;
    PagoGastoInmuebleTableModel tableModel;

    public DetallePagosGastosInmueble(java.awt.Frame parent, boolean modal, GastoInmuebleInquilino gastoInmuebleInquilino) {
        super(parent, modal);
        initComponents();
        
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logoTrans.png")));
        //CIERRA JOPTIONPANE CON ESCAPE
        jPanel2.grabFocus();
        jPanel2.addKeyListener(new OptionPaneEstandar(this));
        
        this.container = Container.getInstancia();
        gastoInmuebleDAO = container.getBean(IPagoGastoInmuebleDAO.class);
        setLocationRelativeTo(null);

        listPagos = new ArrayList<>();
        listPagos.addAll(gastoInmuebleDAO.findByGastoInmuebleInquilino(gastoInmuebleInquilino));

        configuraTblPagos();
    }

    public DetallePagosGastosInmueble(java.awt.Frame parent, boolean modal, GastoInmueblePropietario gastoInmueblePropietario) {
        super(parent, modal);
        initComponents();
        this.container = Container.getInstancia();
        gastoInmuebleDAO = container.getBean(IPagoGastoInmuebleDAO.class);
        setLocationRelativeTo(null);

        listPagos = new ArrayList<>();
        listPagos.addAll(gastoInmuebleDAO.findByGastoInmueblePropietario(gastoInmueblePropietario));

        configuraTblPagos();
    }

    void configuraTblPagos() {
        ((DefaultTableCellRenderer) tblPagos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        tableModel = new PagoGastoInmuebleTableModel(listPagos);
        tblPagos.setModel(tableModel);
        tblPagos.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());

        tblPagos.setRowHeight(25);

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
        tblPagos = new javax.swing.JTable();

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Detalles de Pagos");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        tblPagos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblPagos);

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
    private javax.swing.JTable tblPagos;
    // End of variables declaration//GEN-END:variables

}
