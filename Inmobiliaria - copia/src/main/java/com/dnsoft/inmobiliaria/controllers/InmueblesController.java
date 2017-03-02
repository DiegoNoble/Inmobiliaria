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
import com.dnsoft.inmobiliaria.beans.TipoDeCaja;
import com.dnsoft.inmobiliaria.daos.IInmuebleDAO;
import com.dnsoft.inmobiliaria.daos.IPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.IPropietarioHasInmuebleDAO;
import com.dnsoft.inmobiliaria.models.InmuebleTableModel;
import com.dnsoft.inmobiliaria.models.PropietariosHasInmueblesTableModel;
import com.dnsoft.inmobiliaria.utils.ButtonColumnEditar;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.views.InmueblesDialog;
import com.dnsoft.inmobiliaria.views.PropietariosDialog;
import com.dnsoft.inmobiliaria.views.InmuebleDetallesDialog;
import com.dnsoft.inmobiliaria.views.NuevoGastoInmueble;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
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
public class InmueblesController implements ActionListener {

    Container container;
    public InmueblesDialog view;
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
    ContratosController contratosController;
    TipoDeCaja tipoDeCaja;

    public InmueblesController(InmueblesDialog view) {
        this.container = Container.getInstancia();
        propietariosDAO = container.getBean(IPropietarioDAO.class);
        inmuebleDAO = container.getBean(IInmuebleDAO.class);
        propietariosHasInmuebleDAO = container.getBean(IPropietarioHasInmuebleDAO.class);
        this.view = view;
        inicia();
        this.view.btnIncluye.setEnabled(false);
        this.view.btnQuitar.setEnabled(false);
        this.view.btnAsignar.setVisible(false);
        this.view.btnNuevoGasto.setVisible(false);
    }

    public InmueblesController(InmueblesDialog view, ContratosController contratosController) {

        this.container = Container.getInstancia();
        propietariosDAO = container.getBean(IPropietarioDAO.class);
        inmuebleDAO = container.getBean(IInmuebleDAO.class);
        propietariosHasInmuebleDAO = container.getBean(IPropietarioHasInmuebleDAO.class);
        this.contratosController = contratosController;
        this.view = view;

        inicia();

        this.view.btnIncluye.setVisible(false);
        this.view.btnQuitar.setVisible(false);
        this.view.btnAsignar.setVisible(true);
        this.view.btnNuevoGasto.setVisible(false);
        this.view.btnNuevo.setVisible(false);
        this.view.btnGuardar.setVisible(false);

    }

    public InmueblesController(InmueblesDialog view, ControlDeCajaController cajaControllern, TipoDeCaja tipoDeCaja) {
        this.container = Container.getInstancia();
        propietariosDAO = container.getBean(IPropietarioDAO.class);
        inmuebleDAO = container.getBean(IInmuebleDAO.class);
        propietariosHasInmuebleDAO = container.getBean(IPropietarioHasInmuebleDAO.class);
        this.view = view;
        this.tipoDeCaja = tipoDeCaja;
        inicia();
        this.view.btnIncluye.setVisible(false);
        this.view.btnQuitar.setVisible(false);
        this.view.btnAsignar.setVisible(false);
        this.view.btnGuardar.setVisible(false);
        this.view.btnNuevo.setVisible(false);
        this.view.btnIncluye.setVisible(false);
    }

    private void inicia() {
        this.view.txtBuscaPropiedad.setActionCommand("txtBuscar");
        this.view.txtBuscaPropiedad.addActionListener(this);
        PromptSupport.setPrompt("Buscar por nro. padrón, calle, nro puerta, barrio, ciudad o valor referencia", view.txtBuscaPropiedad);
        configTblPropiedades();
        configTblAsignados();
        accionesBotones();
        view.setLocationRelativeTo(null);
    }

    public void go() {
        this.view.setVisible(true);
        this.view.toFront();

    }

    void accionesBotones() {

        view.btnAsignar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (inmuebleSeleccionado.getStatuspropiedad() != StatusInmueble.DISPONIBLE) {
                    JOptionPane.showMessageDialog(view, "No se puede vincular a un contrato un inmueble con status" + inmuebleSeleccionado.getStatuspropiedad(), "Error", JOptionPane.ERROR_MESSAGE);
                } else if (propietariosHasInmuebleDAO.findByInmuebleId(inmuebleSeleccionado.getId()).isEmpty()) {
                    JOptionPane.showMessageDialog(view, "No se posible vincular un inmueble sin propietarios", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    contratosController.setInmuebleSeleccionado(inmuebleSeleccionado);
                    view.dispose();
                }

            }
        });
        view.btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                try {
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
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al guardar registros! " + e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        view.btnVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                view.dispose();

            }
        });

        view.btnNuevo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                InmuebleDetallesDialog nuevo = new InmuebleDetallesDialog(null, true);
                nuevo.setVisible(true);
                nuevo.toFront();
                actualizaTablas();
            }
        });
        view.btnIncluye.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                PropietariosDialog propietarioView = new PropietariosDialog(null, true);
                PropietariosController controller = new PropietariosController(propietarioView, InmueblesController.this);
                controller.go();

            }
        });
        view.btnQuitar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                propietariosHasInmuebleDAO.deletePropietarioHasInmueble(listPropietariosHasInmueble.get(view.tblAsignados.getSelectedRow()).getId());
                listPropietariosHasInmueble.remove(view.tblAsignados.getSelectedRow());
                tableModelAsignados.fireTableDataChanged();

            }
        });
        view.btnNuevoGasto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                NuevoGastoInmueble nuevoGasto = new NuevoGastoInmueble(null, true, inmuebleSeleccionado, tipoDeCaja);
                nuevoGasto.setVisible(true);
                nuevoGasto.toFront();
            }
        });
    }

    void configTblPropiedades() {
        ((DefaultTableCellRenderer) view.tblInmuebles.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listInmueble = new ArrayList<>();
        listInmueble.addAll(inmuebleDAO.findAll());
        tableModel = new InmuebleTableModel(listInmueble);
        view.tblInmuebles.setModel(tableModel);

        new ButtonColumnEditar(view.tblInmuebles, 4) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                editaSeleccionado();
            }
        };

        view.tblInmuebles.setRowHeight(25);

        listModelInmueble = view.tblInmuebles.getSelectionModel();
        listModelInmueble.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (view.tblInmuebles.getSelectedRow() != -1) {
                    inmuebleSeleccionado = listInmueble.get(view.tblInmuebles.getSelectedRow());
                    view.btnIncluye.setEnabled(true);
                    view.btnAsignar.setEnabled(true);
                    view.btnNuevoGasto.setEnabled(true);
                    buscaPropietariosAsignados();
                } else {
                    view.btnIncluye.setEnabled(false);
                    view.btnAsignar.setEnabled(false);
                    view.btnNuevoGasto.setEnabled(false);
                    buscaPropietariosAsignados();
                }
            }
        });

    }

    void configTblAsignados() {
        ((DefaultTableCellRenderer) view.tblAsignados.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listPropietariosHasInmueble = new ArrayList<>();
        propietariosSeleccionados = new ArrayList<>();
        tableModelAsignados = new PropietariosHasInmueblesTableModel(listPropietariosHasInmueble);
        view.tblAsignados.setModel(tableModelAsignados);

        view.tblAsignados.setRowHeight(25);
        view.tblAsignados.removeColumn(view.tblAsignados.getColumn("Situación"));
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
        Inmueble editar = listInmueble.get(view.tblInmuebles.getSelectedRow());
        InmuebleDetallesDialog editaInquilino = new InmuebleDetallesDialog(null, true, editar);
        editaInquilino.setVisible(true);
        editaInquilino.toFront();
        actualizaTablas();
    }

    boolean VerificaPorcentage() {

        BigDecimal verificaPorcentaje = BigDecimal.ZERO;

        for (PropietarioHasInmueble propietarioHasPropiedad : listPropietariosHasInmueble) {

            if ((propietarioHasPropiedad.getProcentagePropiedad().compareTo(BigDecimal.ZERO) == 0) || (propietarioHasPropiedad.getProcentagePropiedad().compareTo(BigDecimal.ZERO) == 0)) {
                return false;
            } else {
                verificaPorcentaje = verificaPorcentaje.add(propietarioHasPropiedad.getProcentagePropiedad());
            }
        }
        return verificaPorcentaje.compareTo(new BigDecimal(100)) == 0;
    }

    void buscarInmuebles() {
        listInmueble.clear();
        listInmueble.addAll(inmuebleDAO.findInmuebles(view.txtBuscaPropiedad.getText()));
        tableModel.fireTableDataChanged();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {

            case "txtBuscar":

                buscarInmuebles();

                break;

            default:
                throw new AssertionError();
        }
    }

}
