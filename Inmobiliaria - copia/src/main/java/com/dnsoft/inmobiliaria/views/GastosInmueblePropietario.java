package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.Renderers.MeDateCellRenderer;
import com.dnsoft.inmobiliaria.beans.GastoInmueblePropietario;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.Propietario;
import com.dnsoft.inmobiliaria.beans.PropietarioHasInmueble;
import com.dnsoft.inmobiliaria.beans.Rubro;
import com.dnsoft.inmobiliaria.controllers.PropietariosController;
import com.dnsoft.inmobiliaria.daos.ICajaDAO;
import com.dnsoft.inmobiliaria.daos.IGastoInmueblePropietarioDAO;
import com.dnsoft.inmobiliaria.daos.IInmuebleDAO;
import com.dnsoft.inmobiliaria.daos.IPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.IPropietarioHasInmuebleDAO;
import com.dnsoft.inmobiliaria.daos.IRubroDAO;
import com.dnsoft.inmobiliaria.models.GastoInmueblePropietarioTableModel;
import com.dnsoft.inmobiliaria.models.PropietariosTableModel;
import com.dnsoft.inmobiliaria.utils.AjustaColumnas;
import com.dnsoft.inmobiliaria.utils.ButtonColumnEditar;
import com.dnsoft.inmobiliaria.utils.Container;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class GastosInmueblePropietario extends javax.swing.JDialog {

    Container container;

    Propietario propietarioSeleccionado;
    ComboInmueble cbInmueble;
    GastoInmueblePropietario gastoInmueblePropietario;
    List<Propietario> listPropietario;
    List<Rubro> listRubro;
    IRubroDAO rubroDAO;
    ICajaDAO cajaDAO;
    IPropietarioDAO propietarioDAO;
    IInmuebleDAO inmuebleDAO;
    IPropietarioHasInmuebleDAO propietarioHasInmuebleDAO;
    IGastoInmueblePropietarioDAO gastoInmueblePropietarioDAO;
    List<GastoInmueblePropietario> listGastoInmueblePropietario;
    GastoInmueblePropietarioTableModel tableModelGastos;
    PropietariosTableModel tableModelPropietarios;

    public GastosInmueblePropietario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.container = Container.getInstancia();
        initComponents();
        setLocationRelativeTo(null);
        inicio();
        configuraTblGastos();
    }

    final void inicio() {

        propietarioDAO = container.getBean(IPropietarioDAO.class);
        cajaDAO = container.getBean(ICajaDAO.class);
        gastoInmueblePropietarioDAO = container.getBean(IGastoInmueblePropietarioDAO.class);
        rubroDAO = container.getBean(IRubroDAO.class);
        inmuebleDAO = container.getBean(IInmuebleDAO.class);
        propietarioHasInmuebleDAO = container.getBean(IPropietarioHasInmuebleDAO.class);
        configTblPropietario();
        configuraTblGastos();
        PromptSupport.setPrompt("Buscar por nombre, apellido o documento", txtBusqueda);

    }

    /* void guardarCambios() {
     try {

     gastoInmueblePropietarioDAO.save(listGastoInmueblePropietario);
     for (GastoInmueblePropietario gastos : listGastoInmueblePropietario) {
     Caja movimiento = new Caja();
     movimiento.setDescripcion("Gasto inmueble Propietario: " + gastos.getPropietario() + ", Inmueble " + gastos.getInmueble());
     movimiento.setEntrada(BigDecimal.ZERO);
     movimiento.setFecha(new Date());
     movimiento.setMoneda(gastos.getMoneda());
     movimiento.setRubro(gastos.getRubro());
     movimiento.setSaldo(calculaSaldo(gastos.getMoneda()).add(gastos.getValor()));
     movimiento.setSalida(BigDecimal.ZERO);
     cajaDAO.save(movimiento);
     }
     buscarPropietario();
     JOptionPane.showMessageDialog(this, "Se registro correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
     } catch (Exception e) {
     JOptionPane.showMessageDialog(this, "Error al guardar registos " + e, "Error", JOptionPane.ERROR);
     }
     }

     BigDecimal calculaSaldo(Moneda moneda) {
     BigDecimal toReturn;
     Caja ultimoMovimiento = cajaDAO.findUltimoMovimiento(moneda);
     if (ultimoMovimiento == null) {
     toReturn = BigDecimal.ZERO;
     } else {
     toReturn = ultimoMovimiento.getSaldo();
     }
     return toReturn;

     }*/
    final void configuraTblGastos() {
        ((DefaultTableCellRenderer) tblGastos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listGastoInmueblePropietario = new ArrayList<>();
        tableModelGastos = new GastoInmueblePropietarioTableModel(listGastoInmueblePropietario);
        tblGastos.setModel(tableModelGastos);

        tblGastos.getColumn("Rubro").setCellEditor(new ComboBoxCellEditor(new comboRubro()));
        tblGastos.getColumn("Moneda").setCellEditor(new ComboBoxCellEditor(new comboMoneda()));
        tblGastos.getColumn("Fecha").setCellEditor(new DatePickerCellEditor());
        tblGastos.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());
        cbInmueble = new ComboInmueble();
        tblGastos.getColumn("Inmueble").setCellEditor(new ComboBoxCellEditor(cbInmueble));
        tblGastos.removeColumn(tblGastos.getColumn("Propietario"));

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

    private void configTblPropietario() {

        ((DefaultTableCellRenderer) tblPropietario.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listPropietario = new ArrayList<>();
        listPropietario.addAll(propietarioDAO.findAll());

        tableModelPropietarios = new PropietariosTableModel(listPropietario);
        tblPropietario.setModel(tableModelPropietarios);
        new ButtonColumnEditar(tblPropietario, 4) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                editaSeleccionado();
            }
        };
        tblPropietario.setRowHeight(25);
        ListSelectionModel selectionModelPropietarios = tblPropietario.getSelectionModel();
        selectionModelPropietarios.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {

                if (tblPropietario.getSelectedRow() != -1) {

                    propietarioSeleccionado = listPropietario.get(tblPropietario.getSelectedRow());
                    listGastoInmueblePropietario.clear();
                    listGastoInmueblePropietario.addAll(propietarioSeleccionado.getListGastos());
                    tableModelGastos.fireTableDataChanged();

                } else {
                    listGastoInmueblePropietario.clear();
                    tableModelGastos.fireTableDataChanged();
                }
            }
        });

    }

    void editaSeleccionado() {
        try {
            Propietario editar = listPropietario.get(tblPropietario.getSelectedRow());
            PropietariosDetalleDlg editaInquilino = new PropietariosDetalleDlg(null, true, editar);
            editaInquilino.setVisible(true);
            editaInquilino.toFront();
        } catch (Exception ex) {
            Logger.getLogger(PropietariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void detalle() {
        GastoInmueblePropietario seleccionado = listGastoInmueblePropietario.get(tblGastos.getSelectedRow());

        DetalleGasto detalle = new DetalleGasto(null, true, seleccionado.getDescripcion(), 1);
        detalle.setVisible(true);
        detalle.toFront();

    }

    void agregarNuevo() {
        cbInmueble.actualiza(propietarioSeleccionado);
        gastoInmueblePropietario = new GastoInmueblePropietario(new Date(), propietarioSeleccionado);
        tableModelGastos.agregar(gastoInmueblePropietario);

    }

    void elminarSeleccionado() {
        try {
            GastoInmueblePropietario toDelete = tableModelGastos.getCliente(tblGastos.getSelectedRow());
            gastoInmueblePropietarioDAO.delete(toDelete);
            tableModelGastos.eliminar(tblGastos.getSelectedRow());
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            JOptionPane.showMessageDialog(this, "No se puede elinar el registro pues esta asignado a contratos", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    void buscarPropietario() {
        listPropietario.clear();
        listPropietario.addAll(propietarioDAO.findPropietario(txtBusqueda.getText()));
        tableModelPropietarios.fireTableDataChanged();
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

        public void actualiza(Propietario propietarioSeleccionado) {
            for (PropietarioHasInmueble propietarioHasInmueble : propietarioHasInmuebleDAO.findByPropietario(propietarioSeleccionado)) {
                this.removeAllItems();
                this.addItem(propietarioHasInmueble.getInmueble());
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
        tblPropietario = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(980, 700));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Asocia gastos de inmuebles  a propietario");
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

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Gastos asociados a propietario"));
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

        tblPropietario.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblPropietario);

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

        buscarPropietario();

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
    public javax.swing.JTable tblPropietario;
    private javax.swing.JTextField txtBusqueda;
    // End of variables declaration//GEN-END:variables
}
