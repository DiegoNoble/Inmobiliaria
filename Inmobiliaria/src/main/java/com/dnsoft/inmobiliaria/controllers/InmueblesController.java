/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.beans.Inmueble;
import com.dnsoft.inmobiliaria.beans.Propietario;
import com.dnsoft.inmobiliaria.beans.PropietarioHasInmueble;
import com.dnsoft.inmobiliaria.beans.StatusInmueble;
import com.dnsoft.inmobiliaria.daos.IInmuebleDAO;
import com.dnsoft.inmobiliaria.daos.IPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.IPropietarioHasInmuebleDAO;
import com.dnsoft.inmobiliaria.models.InmuebleTableModel;
import com.dnsoft.inmobiliaria.models.PropietariosHasInmueblesTableModel;
import com.dnsoft.inmobiliaria.views.InmueblesView;
import com.dnsoft.inmobiliaria.views.PropietarioView;
import com.dnsoft.inmobiliaria.views.detalles.DetalleInmueble;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
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
public class InmueblesController implements ActionListener {

    ApplicationContext applicationContext;
    public InmueblesView view;
    public PropietariosHasInmueblesTableModel tableModelAsignados;
    List<Inmueble> listInmueble;
    IInmuebleDAO inmuebleDAO;
    InmuebleTableModel tableModel;
    List<PropietarioHasInmueble> listPropietariosHasInmueble;
    IPropietarioDAO propietariosDAO;
    IPropietarioHasInmuebleDAO propietariosHasInmuebleDAO;
    Inmueble inmuebleSeleccionado;
    ListSelectionModel listModelInmueble;
    ListSelectionModel listModelPropietarios;
    public List<Propietario> propietariosSeleccionados;
    JDesktopPane desktopPane;
    ContratosController contratosController;

    public InmueblesController(InmueblesView view, JDesktopPane desktopPane) {

        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        propietariosDAO = applicationContext.getBean(IPropietarioDAO.class);
        inmuebleDAO = applicationContext.getBean(IInmuebleDAO.class);
        propietariosHasInmuebleDAO = applicationContext.getBean(IPropietarioHasInmuebleDAO.class);
        this.view = view;
        this.desktopPane = desktopPane;
        this.view.setSize(desktopPane.getSize());
        inicia();
        view.btnIncluye.setEnabled(false);
        view.btnQuitar.setEnabled(false);
        view.btnEditar.setEnabled(false);
        this.view.btnAsignar.setVisible(false);
    }

    public InmueblesController(InmueblesView view, JDesktopPane desktopPane, ContratosController contratosController) {

        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        propietariosDAO = applicationContext.getBean(IPropietarioDAO.class);
        inmuebleDAO = applicationContext.getBean(IInmuebleDAO.class);
        propietariosHasInmuebleDAO = applicationContext.getBean(IPropietarioHasInmuebleDAO.class);
        this.view = view;
        this.desktopPane = desktopPane;
        this.contratosController = contratosController;
        this.view.setSize(desktopPane.getWidth() - 200, desktopPane.getHeight());

        inicia();
        view.btnIncluye.setEnabled(false);
        view.btnQuitar.setEnabled(false);
        view.btnEditar.setEnabled(false);
        this.view.btnAsignar.setVisible(true);
        view.btnAsignar.setEnabled(false);
    }

    private void inicia() {

        this.view.btnVolver.setActionCommand("btnVolver");
        this.view.btnAsignar.setActionCommand("btnAsignar");
        this.view.txtBuscaPropiedad.setActionCommand("txtBuscaPropiedad");
        this.view.btnIncluye.setActionCommand("btnIncluye");
        this.view.btnIncluye.setActionCommand("btnIncluye");
        this.view.btnIncluye.setActionCommand("btnIncluye");
        this.view.btnQuitar.setActionCommand("btnQuitar");
        this.view.btnGuardar.setActionCommand("btnGuardar");
        this.view.btnNuevo.setActionCommand("btnNuevo");
        this.view.btnEditar.setActionCommand("btnEditar");

        this.view.btnVolver.addActionListener(this);
        this.view.btnAsignar.addActionListener(this);
        this.view.btnNuevo.addActionListener(this);
        this.view.btnEditar.addActionListener(this);
        this.view.btnIncluye.addActionListener(this);
        this.view.btnQuitar.addActionListener(this);
        this.view.btnGuardar.addActionListener(this);

        configTblPropiedades();
        configTblAsignados();

    }

    public void go() {
        this.view.setVisible(true);
        this.view.toFront();

    }

    void configTblPropiedades() {
        ((DefaultTableCellRenderer) view.tblPropiedades.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listInmueble = new ArrayList<Inmueble>();
        listInmueble.addAll(inmuebleDAO.findAll());
        tableModel = new InmuebleTableModel(listInmueble);
        view.tblPropiedades.setModel(tableModel);
        view.tblPropiedades.setAutoCreateRowSorter(true);
        view.tblPropiedades.setRowHeight(25);

        listModelInmueble = view.tblPropiedades.getSelectionModel();
        listModelInmueble.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (view.tblPropiedades.getSelectedRow() != -1) {
                    inmuebleSeleccionado = listInmueble.get(view.tblPropiedades.getSelectedRow());
                    view.btnIncluye.setEnabled(true);
                    view.btnEditar.setEnabled(true);
                    view.btnAsignar.setEnabled(true);
                    buscaPropietariosAsignados();
                } else {
                    view.btnIncluye.setEnabled(false);
                    view.btnEditar.setEnabled(false);
                    view.btnAsignar.setEnabled(false);
                    buscaPropietariosAsignados();
                }
            }
        });

        view.tblPropiedades.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    editaSeleccionado();
                }
            }
        });

    }

    void configTblAsignados() {
        ((DefaultTableCellRenderer) view.tblAsignados.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listPropietariosHasInmueble = new ArrayList<PropietarioHasInmueble>();
        propietariosSeleccionados = new ArrayList<>();
        tableModelAsignados = new PropietariosHasInmueblesTableModel(listPropietariosHasInmueble);
        view.tblAsignados.setModel(tableModelAsignados);

        view.tblAsignados.setRowHeight(25);
        view.tblAsignados.removeColumn(view.tblAsignados.getColumn("Status"));
        view.tblAsignados.removeColumn(view.tblAsignados.getColumn("Padrón"));
        view.tblAsignados.removeColumn(view.tblAsignados.getColumn("Dirección"));

        listModelPropietarios = view.tblAsignados.getSelectionModel();
        listModelPropietarios.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (view.tblAsignados.getSelectedRow() != -1) {

                    view.btnQuitar.setEnabled(true);
                } else {
                    view.btnQuitar.setEnabled(false);
                }
            }
        });

    }

    public void actualizaTablas() {
        listInmueble.clear();
        listInmueble.addAll(inmuebleDAO.findAll());
        tableModel.fireTableDataChanged();

        listPropietariosHasInmueble.clear();
        tableModelAsignados.fireTableDataChanged();
    }

    void buscaPropietariosAsignados() {
        listPropietariosHasInmueble.clear();
        listPropietariosHasInmueble.addAll(propietariosHasInmuebleDAO.findByInmuebleId(inmuebleSeleccionado.getId()));
        tableModelAsignados.fireTableDataChanged();
    }

    void editaSeleccionado() {
        Inmueble editar = listInmueble.get(view.tblPropiedades.getSelectedRow());
        DetalleInmueble editaInquilino = new DetalleInmueble(null, true, inmuebleDAO, this, editar);
        editaInquilino.setVisible(true);
        editaInquilino.toFront();
    }

    boolean VerificaPorcentage() {

        int verificaPorcentaje = 0;

        for (PropietarioHasInmueble propietarioHasPropiedad : listPropietariosHasInmueble) {

            if (propietarioHasPropiedad.getProcentagePropiedad() == null || propietarioHasPropiedad.getProcentagePropiedad() == 0) {
                return false;
            } else {
                verificaPorcentaje = verificaPorcentaje + propietarioHasPropiedad.getProcentagePropiedad();
            }
        }
        return verificaPorcentaje == 100;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {

            case "btnIncluye":

                PropietarioView propietarioView = new PropietarioView();
                PropietariosController controller = new PropietariosController(propietarioView, this);

                this.desktopPane.add(propietarioView);
                controller.go();

                break;

            case "btnQuitar":

                listPropietariosHasInmueble.remove(view.tblAsignados.getSelectedRow());
                tableModelAsignados.fireTableDataChanged();

                break;

            case "btnGuardar":
                if (inmuebleSeleccionado != null) {
                    if (VerificaPorcentage() == false) {
                        JOptionPane.showMessageDialog(view, "Los % asociados no son correctos", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        //JOptionPane.showMessageDialog(view, "OK!");
                        for (PropietarioHasInmueble toDB : listPropietariosHasInmueble) {
                            toDB.setInmueble(inmuebleSeleccionado);
                            propietariosHasInmuebleDAO.save(toDB);
                        }
                        JOptionPane.showMessageDialog(view, "Se guardaron correctamente los datos", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Debe seleccionar una propiedad para asignar cambios", "Error", JOptionPane.ERROR_MESSAGE);
                }

                break;

            case "txtBuscaPropiedad":

                listInmueble.clear();
                listInmueble.addAll(inmuebleDAO.findPropietarios(view.txtBuscaPropiedad.getText()));
                tableModel.fireTableDataChanged();

                break;
            case "btnNuevo":

                DetalleInmueble nuevo = new DetalleInmueble(null, true, inmuebleDAO, this);
                nuevo.setVisible(true);
                nuevo.toFront();

                break;
            case "btnEditar":

                editaSeleccionado();

                break;
            case "btnAsignar":
                if (inmuebleSeleccionado.getStatuspropiedad() != StatusInmueble.DISPONIBLE) {
                    JOptionPane.showMessageDialog(view, "No se puede vincular a un contrato un inmueble con status" + inmuebleSeleccionado.getStatuspropiedad(), "Error", JOptionPane.ERROR_MESSAGE);
                } else if (propietariosHasInmuebleDAO.findByInmuebleId(inmuebleSeleccionado.getId()).isEmpty()) {
                    JOptionPane.showMessageDialog(view, "No se posible vincular un inmueble sin propietarios", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    contratosController.setInmuebleSeleccionado(inmuebleSeleccionado);
                }

                break;
            case "btnVolver":
                view.dispose();

                break;
            default:
                throw new AssertionError();
        }
    }

}
