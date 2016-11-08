/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.Renderers.TableRendererColorActivo;
import com.dnsoft.inmobiliaria.beans.Propietario;
import com.dnsoft.inmobiliaria.daos.IPropietarioDAO;
import com.dnsoft.inmobiliaria.models.PropietariosTableModel;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.views.InmuebleDetallesDialog_new;
import com.dnsoft.inmobiliaria.views.PropietariosDialog;
import com.dnsoft.inmobiliaria.views.PropietariosDetalleDlg;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.jdesktop.swingx.prompt.PromptSupport;

/**
 *
 * @author Diego Noble
 */
public class PropietariosController implements ActionListener {

    Container container;
    public PropietariosDialog view;
    PropietariosTableModel tableModel;
    List<Propietario> listPropietarios;
    IPropietarioDAO propietariosDAO;
    ListSelectionModel listModel;
    Propietario propietarioSeleccionado;

    public PropietariosController(PropietariosDialog view) {

        this.view = view;
        view.setLocationRelativeTo(null);
        inicia();

    }

    public PropietariosController(PropietariosDialog view, InmuebleDetallesDialog_new inmuebleDetallesDialog) {

        this.view = view;
        view.setLocationRelativeTo(null);
        this.view.btnVolver.setVisible(true);
        inicia();
    }

    private void inicia() {

        this.view.txtBusqueda.setActionCommand("txtBusqueda");
        this.view.txtBusqueda.addActionListener(this);
        PromptSupport.setPrompt("Buscar por nombre, apellido o documento", view.txtBusqueda);
        this.container = Container.getInstancia();
        propietariosDAO = container.getBean(IPropietarioDAO.class);
        view.btnActivarInactivar.setEnabled(false);
        view.btnEliminar.setEnabled(false);
        configTblPropietario();
        accionesBotones();
    }

    public void go() {
        this.view.setVisible(true);
        this.view.toFront();

    }

    void accionesBotones() {

        view.btnNuevo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                PropietariosDetalleDlg nuevo = new PropietariosDetalleDlg(null, true);
                nuevo.setVisible(true);
                nuevo.toFront();
                actualizaTbl();
            }
        });
        
        view.btnEditar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                editaSeleccionado();
            }
        });
        
        view.btnVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                view.dispose();

            }
        });
        view.btnEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                try {
                    int c = JOptionPane.showConfirmDialog(view, "Confirma eliminación del cliente: " + propietarioSeleccionado.toString(), "Confirmación", JOptionPane.YES_NO_OPTION);
                    if (c == 0) {
                        try {
                            propietariosDAO.delete(propietarioSeleccionado);
                            actualizaTbl();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "No es posible eliminar el cliente", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar propietario, posee historico de transacciones", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        view.btnActivarInactivar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int c = JOptionPane.showConfirmDialog(view, "Confirma inactivacion del cliente: " + propietarioSeleccionado.toString(), "Confirmación", JOptionPane.YES_NO_OPTION);
                if (c == 0) {
                    if (propietarioSeleccionado.getActivo() == Boolean.FALSE) {
                        propietarioSeleccionado.setActivo(Boolean.TRUE);
                    } else {
                        propietarioSeleccionado.setActivo(Boolean.FALSE);
                    }
                    propietariosDAO.save(propietarioSeleccionado);
                    actualizaTbl();

                }
                actualizaTbl();
            }
        });

    }

    private void configTblPropietario() {

        ((DefaultTableCellRenderer) view.tblPropietarios.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listPropietarios = new ArrayList<>();
        listPropietarios.addAll(propietariosDAO.findAllNombreDocumento());

        tableModel = new PropietariosTableModel(listPropietarios);
        view.tblPropietarios.setModel(tableModel);
        view.tblPropietarios.setDefaultRenderer(Object.class, new TableRendererColorActivo(2));
        view.tblPropietarios.setRowHeight(25);
        int[] anchos = {25,150,50,100,150};

            for (int i = 0; i < view.tblPropietarios.getColumnCount(); i++) {

                view.tblPropietarios.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

            }

        view.tblPropietarios.getColumn("Activo").setWidth(0);
        view.tblPropietarios.getColumn("Activo").setMaxWidth(0);
        view.tblPropietarios.getColumn("Activo").setMinWidth(0);
        view.tblPropietarios.getColumn("Activo").setPreferredWidth(0);

        listModel = view.tblPropietarios.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (view.tblPropietarios.getSelectedRow() != -1) {
                    propietarioSeleccionado = propietariosDAO.findByPropietarioEager(listPropietarios.get(view.tblPropietarios.getSelectedRow()).getId());
                    view.btnActivarInactivar.setEnabled(true);
                    view.btnEliminar.setEnabled(true);
                    view.btnEditar.setEnabled(true);
                } else {
                    view.btnActivarInactivar.setEnabled(false);
                    view.btnEliminar.setEnabled(false);
                    view.btnEditar.setEnabled(false);
                }
            }
        });

        view.tblPropietarios.addMouseListener(new MouseAdapter() {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {

            case "txtBusqueda":

                listPropietarios.clear();
                listPropietarios.addAll(propietariosDAO.findPropietario(view.txtBusqueda.getText()));
                tableModel.fireTableDataChanged();
                break;

            default:
                throw new AssertionError();
        }
    }

}
