package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.Renderers.MeDateCellRenderer;
import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.GastoInmuebleInquilino;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.Inquilino;
import com.dnsoft.inmobiliaria.beans.Rubro;
import com.dnsoft.inmobiliaria.daos.IContratoDAO;
import com.dnsoft.inmobiliaria.daos.IGastoInmuebleInquilinoDAO;
import com.dnsoft.inmobiliaria.daos.IInmuebleDAO;
import com.dnsoft.inmobiliaria.daos.IInquilinoDAO;
import com.dnsoft.inmobiliaria.daos.IRubroDAO;
import com.dnsoft.inmobiliaria.models.GastoInmuebleInquilinoTableModel;
import com.dnsoft.inmobiliaria.models.InquilinosTableModel;
import com.dnsoft.inmobiliaria.utils.AjustaColumnas;
import com.dnsoft.inmobiliaria.utils.ButtonColumnEditar;
import com.dnsoft.inmobiliaria.utils.Container;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;
import org.jdesktop.swingx.prompt.PromptSupport;
import org.jdesktop.swingx.table.DatePickerCellEditor;

/**
 *
 * @author Diego Noble
 */
public class GastosInmuebleInquilino extends javax.swing.JDialog {

    Container container;
    Inquilino inquilinoSeleccionado;
    ComboInmueble cbInmueble;
    GastoInmuebleInquilino gastoInmuebleInquilino;
    List<Inquilino> listInquilino;
    List<Rubro> listRubro;
    IRubroDAO rubroDAO;
    IInquilinoDAO inquilinoDAO;
    IInmuebleDAO inmuebleDAO;
    IContratoDAO contratoDAO;
    IGastoInmuebleInquilinoDAO gastoInmuebleInquilinoDAO;
    List<GastoInmuebleInquilino> listGastoInmuebleInquilino;
    GastoInmuebleInquilinoTableModel tableModelGastos;
    InquilinosTableModel tableModelInquilinos;

    public GastosInmuebleInquilino(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.container = Container.getInstancia();
        setLocationRelativeTo(null);
        inicio();
        configuraTblGastos();
    }

    final void inicio() {

        inquilinoDAO = container.getBean(IInquilinoDAO.class);
        gastoInmuebleInquilinoDAO = container.getBean(IGastoInmuebleInquilinoDAO.class);
        rubroDAO = container.getBean(IRubroDAO.class);
        inmuebleDAO = container.getBean(IInmuebleDAO.class);
        contratoDAO = container.getBean(IContratoDAO.class);
        configTblInquilino();
        configuraTblGastos();
        PromptSupport.setPrompt("Buscar por nombre, apellido o documento", txtBusqueda);
    }

    final void configuraTblGastos() {
        ((DefaultTableCellRenderer) tblGastos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listGastoInmuebleInquilino = new ArrayList<>();
        tableModelGastos = new GastoInmuebleInquilinoTableModel(listGastoInmuebleInquilino);
        tblGastos.setModel(tableModelGastos);
        tblGastos.getColumn("Rubro").setCellEditor(new ComboBoxCellEditor(new comboRubro()));
        tblGastos.getColumn("Moneda").setCellEditor(new ComboBoxCellEditor(new comboMoneda()));
        tblGastos.getColumn("Fecha").setCellEditor(new DatePickerCellEditor());
        tblGastos.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());
        cbInmueble = new ComboInmueble();
        tblGastos.getColumn("Inmueble").setCellEditor(new ComboBoxCellEditor(cbInmueble));
        tblGastos.removeColumn(tblGastos.getColumn("Inquilino"));

        tblGastos.setRowHeight(25);
        int[] anchos = {10, 10, 150, 10, 10, 10, 10};
        new AjustaColumnas().ajustar(tblGastos, anchos);
        tblGastos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    detalle();

                }
            }
        });

    }

    private void configTblInquilino() {

        ((DefaultTableCellRenderer) tblInquilino.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listInquilino = new ArrayList<Inquilino>();
        listInquilino.addAll(inquilinoDAO.findAll());

        tableModelInquilinos = new InquilinosTableModel(listInquilino);
        tblInquilino.setModel(tableModelInquilinos);
        new ButtonColumnEditar(tblInquilino, 4) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                editaSeleccionado();
            }
        };
        tblInquilino.setRowHeight(25);
        ListSelectionModel selectionModelInquilinos = tblInquilino.getSelectionModel();
        selectionModelInquilinos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {

                if (tblInquilino.getSelectedRow() != -1) {

                    inquilinoSeleccionado = listInquilino.get(tblInquilino.getSelectedRow());
                    listGastoInmuebleInquilino.clear();
                    listGastoInmuebleInquilino.addAll(inquilinoSeleccionado.getListGastos());
                    tableModelGastos.fireTableDataChanged();

                } else {
                    listGastoInmuebleInquilino.clear();
                    tableModelGastos.fireTableDataChanged();
                }
            }
        });

    }

    void editaSeleccionado() {
        inquilinoSeleccionado = listInquilino.get(tblInquilino.getSelectedRow());
        InquilinoDetallesDlg editaInquilino = new InquilinoDetallesDlg(null, true, inquilinoSeleccionado);
        editaInquilino.setVisible(true);
        editaInquilino.toFront();
        listInquilino.clear();
        listInquilino.addAll(inquilinoDAO.findAll());
        tableModelInquilinos.fireTableDataChanged();
    }

    void detalle() {

        GastoInmuebleInquilino seleccionado = listGastoInmuebleInquilino.get(tblGastos.getSelectedRow());

        DetalleGasto detalle = new DetalleGasto(null, true, seleccionado.getDescripcion(), 1);
        detalle.setVisible(true);
        detalle.toFront();

    }

    void agregarNuevo() {
        cbInmueble.actualiza(inquilinoSeleccionado);
        gastoInmuebleInquilino = new GastoInmuebleInquilino(new Date(), inquilinoSeleccionado);
        tableModelGastos.agregar(gastoInmuebleInquilino);

    }

    void elminarSeleccionado() {
        try {
            GastoInmuebleInquilino toDelete = tableModelGastos.getCliente(tblGastos.getSelectedRow());
            gastoInmuebleInquilinoDAO.delete(toDelete);
            tableModelGastos.eliminar(tblGastos.getSelectedRow());
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            JOptionPane.showMessageDialog(this, "No se puede elinar el registro pues esta asignado a contratos", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    void buscarInquilino() {
        listInquilino.clear();
        listInquilino.addAll(inquilinoDAO.findInquilino(txtBusqueda.getText()));
        tableModelInquilinos.fireTableDataChanged();
    }

    private class comboRubro extends JComboBox<Object> {

        public comboRubro() {
            AutoCompleteDecorator.decorate(this);
            for (Rubro tipos : rubroDAO.findAll()) {
                this.addItem(tipos);
            }
        }
    }

    private class comboMoneda extends JComboBox<Object> {

        public comboMoneda() {
            this.setModel(new DefaultComboBoxModel<>(Moneda.values()));
        }
    }

    private class ComboInmueble extends JComboBox<Object> {

        public ComboInmueble() {
            AutoCompleteDecorator.decorate(this);

        }

        public void actualiza(Inquilino inquilinoSeleccionado) {
            for (Contrato contrato : contratoDAO.findByInquilino(inquilinoSeleccionado)) {
                this.removeAllItems();
                this.addItem(contrato.getInmueble());
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtBusqueda = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblGastos = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblInquilino = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(900, 700));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Asocia gastos de inmuebles  a inquilino");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Buscar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel1.add(jLabel2, gridBagConstraints);

        txtBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBusquedaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 350;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtBusqueda, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Gastos asociados a inquilino"));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        tblGastos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblGastos.setToolTipText("Doble click para añadir el detalle del gasto");
        jScrollPane3.setViewportView(tblGastos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jScrollPane3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jPanel5, gridBagConstraints);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Dirección"));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        tblInquilino.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblInquilino);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(jScrollPane4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
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

    private void txtBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBusquedaActionPerformed

        buscarInquilino();

    }//GEN-LAST:event_txtBusquedaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JTable tblGastos;
    public javax.swing.JTable tblInquilino;
    private javax.swing.JTextField txtBusqueda;
    // End of variables declaration//GEN-END:variables
}
