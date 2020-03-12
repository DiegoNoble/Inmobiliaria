package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.Rubro;
import com.dnsoft.inmobiliaria.daos.IRubroDAO;
import com.dnsoft.inmobiliaria.models.RubroTableModel;
import com.dnsoft.inmobiliaria.utils.AjustaColumnas;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.OptionPaneEstandar;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Diego Noble
 */
public final class RubroDlg extends javax.swing.JDialog {

    IRubroDAO rubrosDAO;
    Container container;
    Rubro rubro;
    List<Rubro> listRubros;
    RubroTableModel tableModel;
    ListSelectionModel listModel;

    public RubroDlg(java.awt.Frame parent, boolean modal, Component frame) {
        super(parent, modal);
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logoTrans.png")));
        //CIERRA JOPTIONPANE CON ESCAPE
        jPanel2.grabFocus();
        jPanel2.addKeyListener(new OptionPaneEstandar(this));
        
        this.container = Container.getInstancia();
        rubrosDAO = container.getBean(IRubroDAO.class);
        setLocationRelativeTo(frame);
        creaRubros();
        configuraTbl();
        btnEliminar.setEnabled(false);

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
    }

    void configuraTbl() {
        ((DefaultTableCellRenderer) tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listRubros = new ArrayList<>();
        listRubros.addAll(rubrosDAO.findAll());

        tableModel = new RubroTableModel(listRubros);
        tbl.setModel(tableModel);
        createKeybindings(tbl);

        tbl.setRowHeight(25);
        int[] anchos = {150, 300};
        new AjustaColumnas().ajustar(tbl, anchos);

        ListSelectionModel selectionModel = tbl.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {

                if (tbl.getSelectedRow() != -1) {
                    btnEliminar.setEnabled(true);
                } else {
                    btnEliminar.setEnabled(false);
                }
            }
        });
        tableModel.addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent tme) {
                rubrosDAO.saveAll(listRubros);
            }
        });

    }

    private void createKeybindings(JTable table) {
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "Tab");
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "Delete");
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Escape");
        table.getActionMap().put("Tab", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                agregarNuevo();
            }
        });
        table.getActionMap().put("Delete", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                elminarSeleccionado();
            }
        });
        table.getActionMap().put("Escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
    }

    void agregarNuevo() {
        rubro = new Rubro();
        tableModel.agregar(rubro);
    }

    void elminarSeleccionado() {
        try {
            Rubro toDelete = tableModel.getCliente(tbl.getSelectedRow());
            tableModel.eliminar(tbl.getSelectedRow());
            rubrosDAO.delete(toDelete);
            btnEliminar.setEnabled(false);

        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            JOptionPane.showMessageDialog(this, "No se puede elinar el registro pues esta asignado a contratos", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        txtBuscaPropiedad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        btnNuevo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNuevo.setText("Nuevo \"Tab\"");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        btnNuevo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnNuevoKeyPressed(evt);
            }
        });
        jPanel3.add(btnNuevo);

        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminar.setText("Eliminar \"Del\"");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        btnEliminar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEliminarKeyPressed(evt);
            }
        });
        jPanel3.add(btnEliminar);

        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancelar.setText("Volver \"Esc\"");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        btnCancelar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCancelarKeyPressed(evt);
            }
        });
        jPanel3.add(btnCancelar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Registro de Rubros");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
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
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jScrollPane1, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        txtBuscaPropiedad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscaPropiedadActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel5.add(txtBuscaPropiedad, gridBagConstraints);

        jLabel5.setText("Buscar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel5.add(jLabel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jPanel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        elminarSeleccionado();

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        this.dispose();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEliminarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminarKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            elminarSeleccionado();
        }

    }//GEN-LAST:event_btnEliminarKeyPressed

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
        this.dispose();

    }//GEN-LAST:event_btnCancelarKeyPressed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        rubro = new Rubro();
        tableModel.agregar(rubro);

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnNuevoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNuevoKeyPressed
        rubro = new Rubro();
        tableModel.agregar(rubro);

    }//GEN-LAST:event_btnNuevoKeyPressed

    private void txtBuscaPropiedadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscaPropiedadActionPerformed

        listRubros.clear();
        listRubros.addAll(rubrosDAO.findRubro(txtBuscaPropiedad.getText()));
        tableModel.fireTableDataChanged();
    }//GEN-LAST:event_txtBuscaPropiedadActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl;
    public javax.swing.JTextField txtBuscaPropiedad;
    // End of variables declaration//GEN-END:variables

}
