/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.beans.Propietario;
import com.dnsoft.inmobiliaria.beans.PropietarioHasInmueble;
import com.dnsoft.inmobiliaria.daos.IPropietarioDAO;
import com.dnsoft.inmobiliaria.models.PropietariosHasInmueblesTableModel;
import com.dnsoft.inmobiliaria.models.PropietariosTableModel;
import com.dnsoft.inmobiliaria.views.PropietarioView;
import com.dnsoft.inmobiliaria.views.detalles.DetallePropietario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Diego Noble
 */
public class PropietariosController implements ActionListener {

    ApplicationContext applicationContext;
    public PropietarioView view;
    PropietariosTableModel tableModel;
    List<Propietario> listPropietarios;
    IPropietarioDAO propietariosDAO;
    InmueblesController asociaPropietariosPropiedadesController;
    ListSelectionModel listModel;
    List<PropietarioHasInmueble> propiedades;
    PropietariosHasInmueblesTableModel tableModelPropiedades;

    public PropietariosController(PropietarioView view) {

        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        propietariosDAO = applicationContext.getBean(IPropietarioDAO.class);

        this.view = view;
        this.view.btnAsignar.setVisible(false);
        inicia();
    }

    public PropietariosController(PropietarioView view, InmueblesController asociaPropietariosPropiedadesController) {

        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        propietariosDAO = applicationContext.getBean(IPropietarioDAO.class);
        this.asociaPropietariosPropiedadesController = asociaPropietariosPropiedadesController;
        this.view = view;
        this.view.btnAsignar.setVisible(true);
        this.view.btnVolver.setVisible(true);
        view.btnAsignar.setVisible(false);
        inicia();
    }

    private void inicia() {

        this.view.btnNuevo.setActionCommand("btnNuevo");
        this.view.btnEditar.setActionCommand("btnEditar");
        this.view.txtBusqueda.setActionCommand("txtBusqueda");
        this.view.btnAsignar.setActionCommand("txtAsociaPropietario");
        this.view.btnVolver.setActionCommand("btnVolver");

        this.view.btnNuevo.addActionListener(this);
        this.view.btnEditar.addActionListener(this);
        this.view.txtBusqueda.addActionListener(this);
        this.view.btnAsignar.addActionListener(this);
        this.view.btnVolver.addActionListener(this);

        configTblPropietario();
        configTblPropiedades();
    }

    public void go() {
        this.view.setVisible(true);
        this.view.toFront();

    }

    private void configTblPropietario() {

        ((DefaultTableCellRenderer) view.tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listPropietarios = new ArrayList<Propietario>();
        listPropietarios.addAll(propietariosDAO.findAll());

        tableModel = new PropietariosTableModel(listPropietarios);
        view.tbl.setModel(tableModel);

        listModel = view.tbl.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (view.tbl.getSelectedRow() != -1) {
                    view.btnAsignar.setVisible(true);
                    propiedades.clear();
                    propiedades.addAll(listPropietarios.get(view.tbl.getSelectedRow()).getPropietarioHasInmueble());
                    tableModelPropiedades.fireTableDataChanged();
                }else{
                    view.btnAsignar.setVisible(false);
                }
            }
        });

        view.tbl.setRowHeight(25);

        view.tbl.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    editaSeleccionado();
                }
            }
        });

    }

    void configTblPropiedades() {
        ((DefaultTableCellRenderer) view.tblPropiedades.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        propiedades = new ArrayList<PropietarioHasInmueble>();
        tableModelPropiedades = new PropietariosHasInmueblesTableModel(propiedades);

        view.tblPropiedades.setModel(tableModelPropiedades);
        view.tblPropiedades.setAutoCreateRowSorter(true);
        view.tblPropiedades.setRowHeight(25);
        view.tblPropiedades.removeColumn(view.tblPropiedades.getColumn("Nombre"));
        view.tblPropiedades.removeColumn(view.tblPropiedades.getColumn("Apellido"));

    }

    public void actualizaTbl() {
        listPropietarios.clear();
        listPropietarios.addAll(propietariosDAO.findAll());
        tableModel.fireTableDataChanged();
    }

    void editaSeleccionado() {
        try {
            Propietario editar = listPropietarios.get(view.tbl.getSelectedRow());
            DetallePropietario editaInquilino = new DetallePropietario(null, true, this, editar);
            this.view.setSelected(false);
            editaInquilino.setVisible(true);
            editaInquilino.toFront();
        } catch (PropertyVetoException ex) {
            Logger.getLogger(PropietariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {

            case "btnNuevo":

                DetallePropietario nuevo = new DetallePropietario(null, true, this);
                nuevo.setVisible(true);
                nuevo.toFront();

                break;

            case "btnEditar":

                editaSeleccionado();

                break;
            case "txtBusqueda":

                listPropietarios.clear();
                listPropietarios.addAll(propietariosDAO.findPropietarios(view.txtBusqueda.getText()));
                tableModel.fireTableDataChanged();
                break;
            case "txtAsociaPropietario":

                Propietario propietarioSeleccionado = listPropietarios.get(view.tbl.getSelectedRow());
                if (verificaPropietario(propietarioSeleccionado) == true) {
                    JOptionPane.showMessageDialog(this.view, "Ya se encuentra asignado este propietario", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    this.asociaPropietariosPropiedadesController.listPropietariosHasInmueble.add(new PropietarioHasInmueble(null, null, propietarioSeleccionado));
                    this.asociaPropietariosPropiedadesController.tableModelAsignados.fireTableDataChanged();
                }
                break;

            case "btnVolver":

                this.view.dispose();

                break;
            default:
                throw new AssertionError();
        }
    }

    boolean verificaPropietario(Propietario propietario) {

        boolean verifica = false;
        for (PropietarioHasInmueble hasPropiedad : this.asociaPropietariosPropiedadesController.listPropietariosHasInmueble) {
            verifica = hasPropiedad.getPropietario().equals(propietario);
            if (verifica == true) {
                return verifica;
            }
        }
        return verifica;

    }
}
