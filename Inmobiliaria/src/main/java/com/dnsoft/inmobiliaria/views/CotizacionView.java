package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.views.detalles.DetalleMoneda;
import com.dnsoft.inmobiliaria.Renderers.MeDateCellRenderer;
import com.dnsoft.inmobiliaria.Renderers.MeDefaultCellRenderer;
import com.dnsoft.inmobiliaria.beans.Cotizaciones;
import com.dnsoft.inmobiliaria.beans.Monedas;
import com.dnsoft.inmobiliaria.daos.ICotizacionDAO;
import com.dnsoft.inmobiliaria.daos.IMonedaDAO;
import com.dnsoft.inmobiliaria.models.CotizacionTableModel;
import com.dnsoft.inmobiliaria.utils.ButtonColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import org.jdesktop.swingx.table.DatePickerCellEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class CotizacionView extends javax.swing.JInternalFrame {

    Cotizaciones cotizacion;
    Monedas monedaSeleccionada;
    private List<Cotizaciones> listCotizacion;
    ApplicationContext applicationContext;
    ICotizacionDAO cotizacionDAO;
    IMonedaDAO monedaDAO;
    CotizacionTableModel tableModel;
    ButtonColumn btnEditar;

    public CotizacionView() {

        initComponents();

        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        cotizacionDAO = applicationContext.getBean(ICotizacionDAO.class);
        monedaDAO = applicationContext.getBean(IMonedaDAO.class);

        cargaMonedas();
        defineModelo();
        buscaCotizacion();
    }

    public void cargaMonedas() {
        //AutoCompleteDecorator.decorate(cbMonedas);
        cbMonedas.removeAllItems();
        cbMonedas.setEditable(true);
        List<Monedas> listMonedas = monedaDAO.findAll();
        for (Monedas m : listMonedas) {
            cbMonedas.addItem(m);
        }

    }

    private void defineModelo() {

        ((DefaultTableCellRenderer) tblCotizacion.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listCotizacion = new ArrayList<Cotizaciones>();

        tableModel = new CotizacionTableModel(listCotizacion);
        tblCotizacion.setModel(tableModel);

        tblCotizacion.getColumn("Valor").setCellRenderer(new MeDefaultCellRenderer());
        tblCotizacion.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());
        tblCotizacion.getColumn("Fecha").setCellEditor(new DatePickerCellEditor(new SimpleDateFormat("dd/MM/yyyy")));

        btnEditar = new ButtonColumn(tblCotizacion, 2, "Guardar", "Correcto") {

            @Override
            public void actionPerformed(ActionEvent ae) {

                cotizacionDAO.save(listCotizacion.get(tblCotizacion.getSelectedRow()));
                tableModel.fireTableDataChanged();
                btnNuevoCotizacion.setEnabled(true);
            }
        };

        cbMonedas.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                buscaCotizacion();
            }
        }
        );
    }

    void buscaCotizacion() {
        listCotizacion.clear();
        monedaSeleccionada = (Monedas) cbMonedas.getSelectedItem();
        listCotizacion.addAll(cotizacionDAO.findByMonedas(monedaSeleccionada));

        tableModel.fireTableDataChanged();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCotizacion = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnNuevoCotizacion = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cbMonedas = new javax.swing.JComboBox();
        btnNuevaMoneda = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setClosable(true);
        setPreferredSize(new java.awt.Dimension(500, 600));
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
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        btnNuevoCotizacion.setText("Nueva Cotización");
        btnNuevoCotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCotizacionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(btnNuevoCotizacion, gridBagConstraints);

        jLabel1.setText("Monedas");
        jPanel1.add(jLabel1, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbMonedas, gridBagConstraints);

        btnNuevaMoneda.setText("Nueva Moneda");
        btnNuevaMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaMonedaActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevaMoneda, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Cotizacion");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 1;
        getContentPane().add(jPanel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoCotizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCotizacionActionPerformed

        cotizacion = new Cotizaciones();
        cotizacion.setFecha(new Date());
        monedaSeleccionada = (Monedas) cbMonedas.getSelectedItem();
        cotizacion.setMonedaId(monedaSeleccionada);

        listCotizacion.add(cotizacion);
        tableModel.fireTableDataChanged();
        btnNuevoCotizacion.setEnabled(false);

    }//GEN-LAST:event_btnNuevoCotizacionActionPerformed

    private void btnNuevaMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaMonedaActionPerformed

        DetalleMoneda nm = new DetalleMoneda(null, closable, monedaDAO, this);
        nm.setVisible(true);
        nm.toFront();

    }//GEN-LAST:event_btnNuevaMonedaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevaMoneda;
    private javax.swing.JButton btnNuevoCotizacion;
    private javax.swing.JComboBox cbMonedas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCotizacion;
    // End of variables declaration//GEN-END:variables

}
