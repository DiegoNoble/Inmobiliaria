/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.beans.Propietario;
import com.dnsoft.inmobiliaria.beans.PropietarioHasInmueble;
import com.dnsoft.inmobiliaria.daos.IPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.IPropietarioHasInmuebleDAO;
import com.dnsoft.inmobiliaria.models.PropietariosHasInmueblesTableModel;
import com.dnsoft.inmobiliaria.models.PropietariosTableModel;
import com.dnsoft.inmobiliaria.utils.ButtonColumnEditar;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.views.PropietariosDialog;
import com.dnsoft.inmobiliaria.views.PropietariosDetalleDlg;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    IPropietarioHasInmuebleDAO propietarioHasInmuebleDAO;
    InmueblesController asociaPropietariosPropiedadesController;
    ListSelectionModel listModel;
    List<PropietarioHasInmueble> propiedades;
    PropietariosHasInmueblesTableModel tableModelPropiedades;
    Propietario propietarioSeleccionado;

    public PropietariosController(PropietariosDialog view) {

        this.view = view;
        inicia();
        this.view.btnSeleccionar.setVisible(false);
    }

    public PropietariosController(PropietariosDialog view, InmueblesController asociaPropietariosPropiedadesController) {

        this.asociaPropietariosPropiedadesController = asociaPropietariosPropiedadesController;
        this.view = view;
        this.view.btnSeleccionar.setVisible(true);
        this.view.btnSeleccionar.setEnabled(false);
        this.view.btnVolver.setVisible(true);
        inicia();
    }

    private void inicia() {

        this.view.txtBusqueda.setActionCommand("txtBusqueda");
        this.view.txtBusqueda.addActionListener(this);
        PromptSupport.setPrompt("Buscar por nombre, apellido o documento", view.txtBusqueda);
        this.container = Container.getInstancia();
        propietariosDAO = container.getBean(IPropietarioDAO.class);
        propietarioHasInmuebleDAO = container.getBean(IPropietarioHasInmuebleDAO.class);
        configTblPropietario();
        configTblPropiedades();
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
        view.btnVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                view.dispose();

            }
        });

        view.btnSeleccionar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                Propietario propietarioSeleccionado = listPropietarios.get(view.tbl.getSelectedRow());
                if (verificaPropietario(propietarioSeleccionado) == true) {
                    JOptionPane.showMessageDialog(null, "Ya se encuentra asignado este propietario", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                    PropietariosController.this.asociaPropietariosPropiedadesController.listPropietariosHasInmueble.add(new PropietarioHasInmueble(null, null, propietarioSeleccionado));
                    int cantidadPropietarios = PropietariosController.this.asociaPropietariosPropiedadesController.listPropietariosHasInmueble.size();
                    for (PropietarioHasInmueble propietarios : PropietariosController.this.asociaPropietariosPropiedadesController.listPropietariosHasInmueble) {
                        Double procentage = 100.00 / cantidadPropietarios;
                        propietarios.setProcentagePropiedad(BigDecimal.valueOf(procentage).setScale(2, RoundingMode.CEILING));
                    }
                    PropietariosController.this.asociaPropietariosPropiedadesController.tableModelAsignados.fireTableDataChanged();
                }
            }
        });

    }

    private void configTblPropietario() {

        ((DefaultTableCellRenderer) view.tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listPropietarios = new ArrayList<>();
        listPropietarios.addAll(propietariosDAO.findAll());

        tableModel = new PropietariosTableModel(listPropietarios);
        view.tbl.setModel(tableModel);

        new ButtonColumnEditar(view.tbl, 3) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                editaSeleccionado();
            }
        };

        listModel = view.tbl.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (view.tbl.getSelectedRow() != -1) {
                    view.btnSeleccionar.setEnabled(true);
                    propietarioSeleccionado = listPropietarios.get(view.tbl.getSelectedRow());
                    propiedades.clear();
                    propiedades.addAll(propietarioHasInmuebleDAO.findByPropietario(propietarioSeleccionado));
                    tableModelPropiedades.fireTableDataChanged();
                } else {
                    view.btnSeleccionar.setEnabled(false);
                }
            }
        });

        view.tbl.setRowHeight(25);

    }

    void configTblPropiedades() {
        ((DefaultTableCellRenderer) view.tblPropiedades.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        propiedades = new ArrayList<>();
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
            PropietariosDetalleDlg editaInquilino = new PropietariosDetalleDlg(null, true, editar);
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
