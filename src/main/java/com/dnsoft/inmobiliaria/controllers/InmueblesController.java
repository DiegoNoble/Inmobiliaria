/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.Renderers.TableRendererColorActivo;
import com.dnsoft.inmobiliaria.beans.DestinoGasto;
import com.dnsoft.inmobiliaria.beans.Inmueble;
import com.dnsoft.inmobiliaria.beans.StatusInmueble;
import com.dnsoft.inmobiliaria.beans.TipoDeCaja;
import com.dnsoft.inmobiliaria.daos.IInmuebleDAO;
import com.dnsoft.inmobiliaria.daos.IPropietarioHasInmuebleDAO;
import com.dnsoft.inmobiliaria.models.InmuebleTableModel;
import com.dnsoft.inmobiliaria.models.PropietariosHasInmueblesTableModel;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.views.InmueblesDialog;
import com.dnsoft.inmobiliaria.views.InmuebleDetallesDialog_new;
import com.dnsoft.inmobiliaria.views.NuevoGastoInmueble;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import org.jdesktop.swingx.prompt.PromptSupport;

/**
 *
 * @author Diego Noble
 */
public class InmueblesController implements ActionListener {

    Container container;
    public InmueblesDialog view;
    public PropietariosHasInmueblesTableModel tableModelAsignados;
    LinkedList<Inmueble> listInmueble;
    IInmuebleDAO inmuebleDAO;
    IPropietarioHasInmuebleDAO propietariosHasInmuebleDAO;
    InmuebleTableModel tableModel;
    Inmueble inmuebleSeleccionado;
    ListSelectionModel listModelInmueble;
    ContratosController contratosController;
    TipoDeCaja tipoDeCaja;
    DestinoGasto destinoGasto;

    public InmueblesController(InmueblesDialog view) {

        this.view = view;
        inicia();

        this.view.btnAsignar.setVisible(false);
        this.view.btnNuevoGasto.setVisible(false);
    }

    public InmueblesController(InmueblesDialog view, ContratosController contratosController) {

        this.contratosController = contratosController;
        this.view = view;
        inicia();
        this.view.btnAsignar.setVisible(true);
        this.view.btnNuevoGasto.setVisible(false);
        this.view.btnNuevo.setVisible(true);

    }

    public InmueblesController(InmueblesDialog view, ControlDeCajaController cajaControllern, TipoDeCaja tipoDeCaja, DestinoGasto destinoGasto) {

        this.view = view;
        this.tipoDeCaja = tipoDeCaja;
        this.destinoGasto = destinoGasto;
        inicia();
        this.view.btnAsignar.setVisible(false);
        this.view.btnNuevo.setVisible(false);
    }

    private void inicia() {
        this.container = Container.getInstancia();
        this.view.txtBuscaPropiedad.setActionCommand("txtBuscar");
        this.view.txtBuscaPropiedad.addActionListener(this);

        PromptSupport.setPrompt("Buscar por nro. padrÃ³n, calle, nro puerta, barrio, ciudad o valor referencia", view.txtBuscaPropiedad);
        listInmueble = new LinkedList<>();
        inmuebleDAO = container.getBean(IInmuebleDAO.class);
        propietariosHasInmuebleDAO = container.getBean(IPropietarioHasInmuebleDAO.class);
        configTblInmuebles();
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
                if (inmuebleSeleccionado.getStatusInmueble() != StatusInmueble.DISPONIBLE) {
                    JOptionPane.showMessageDialog(view, "No se puede vincular a un contrato un inmueble con status" + inmuebleSeleccionado.getStatusInmueble(), "Error", JOptionPane.ERROR_MESSAGE);
                } else if (propietariosHasInmuebleDAO.findByInmuebleId(inmuebleSeleccionado.getId()).isEmpty()) {
                    JOptionPane.showMessageDialog(view, "No se posible vincular un inmueble sin propietarios", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    contratosController.setInmuebleSeleccionado(inmuebleSeleccionado);
                    view.dispose();
                }

            }
        });
        view.btnVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                view.dispose();

            }
        });

        view.btnModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                editaSeleccionado();

            }
        });

        view.btnNuevo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                InmuebleDetallesDialog_new nuevo = new InmuebleDetallesDialog_new(null, true);
                nuevo.setVisible(true);
                nuevo.toFront();
                buscarInmuebles();
            }
        });

        view.btnNuevoGasto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                NuevoGastoInmueble nuevoGasto = new NuevoGastoInmueble(null, true, inmuebleSeleccionado, tipoDeCaja, destinoGasto);
                nuevoGasto.setVisible(true);
                nuevoGasto.toFront();
                view.dispose();
            }
        });

        view.btnEliminarInmueble.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int c = JOptionPane.showConfirmDialog(view, "Confirma eliminaciÃ³n del inmueble: " + inmuebleSeleccionado.toString(), "ConfirmaciÃ³n", JOptionPane.YES_NO_OPTION);
                if (c == 0) {
                    try {
                        inmuebleDAO.delete(inmuebleSeleccionado);
                        buscarInmuebles();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "No es posible eliminar el inmueble", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });
        view.btnActivarInactivar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int c = JOptionPane.showConfirmDialog(view, "Confirma inactivacion del cliente: " + inmuebleSeleccionado.toString(), "ConfirmaciÃ³n", JOptionPane.YES_NO_OPTION);
                if (c == 0) {
                    if (inmuebleSeleccionado.getActivo() == Boolean.FALSE) {
                        inmuebleSeleccionado.setActivo(Boolean.TRUE);
                    } else {
                        inmuebleSeleccionado.setActivo(Boolean.FALSE);
                    }
                    inmuebleDAO.save(inmuebleSeleccionado);
                    buscarInmuebles();

                }
                buscarInmuebles();
            }
        });
    }

    void configTblInmuebles() {
        try {
            ((DefaultTableCellRenderer) view.tblInmuebles.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

            tableModel = new InmuebleTableModel(listInmueble);

            view.tblInmuebles.setDefaultRenderer(Object.class, new TableRendererColorActivo(3));
            RowSorter ordenador = new TableRowSorter(tableModel);

            view.tblInmuebles.setRowSorter(ordenador);

            view.tblInmuebles.setModel(tableModel);
//view.tblInmuebles.setAutoCreateRowSorter(true);
            int[] anchos = {1, 30, 500, 10, 40};

            for (int i = 0; i < view.tblInmuebles.getColumnCount(); i++) {

                view.tblInmuebles.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

            }

            view.tblInmuebles.setRowHeight(25);
            view.tblInmuebles.getColumn("Activo").setMaxWidth(0);
            view.tblInmuebles.getColumn("Activo").setMinWidth(0);
            view.tblInmuebles.getColumn("Activo").setPreferredWidth(0);
            view.tblInmuebles.getColumn("Activo").setWidth(0);

            listModelInmueble = view.tblInmuebles.getSelectionModel();
            listModelInmueble.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {
                    if (view.tblInmuebles.getSelectedRow() != -1) {
                        int viewRow = view.tblInmuebles.getSelectedRow();
                        int modelRow = view.tblInmuebles.convertRowIndexToModel(viewRow);
                        inmuebleSeleccionado = listInmueble.get(modelRow);
                        view.btnAsignar.setEnabled(true);
                        view.btnNuevoGasto.setEnabled(true);
                        view.btnModificar.setEnabled(true);
                        view.btnEliminarInmueble.setEnabled(true);
                        view.btnActivarInactivar.setEnabled(true);
                    } else {
                        view.btnAsignar.setEnabled(false);
                        view.btnNuevoGasto.setEnabled(false);
                        view.btnModificar.setEnabled(false);
                        view.btnEliminarInmueble.setEnabled(false);
                        view.btnActivarInactivar.setEnabled(false);
                    }
                }
            });

            view.tblInmuebles.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent me) {

                    if (me.getClickCount() == 2) {
                        editaSeleccionado();
                    }
                }
            });
            buscarInmuebles();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*public void actualizaTablas() {
     listInmueble.clear();
     listInmueble.addAll(inmuebleDAO.findAll());
     tableModel.fireTableDataChanged();

     }*/
    void editaSeleccionado() {

        int viewRow = view.tblInmuebles.getSelectedRow();
        int modelRow = view.tblInmuebles.convertRowIndexToModel(viewRow);
        inmuebleSeleccionado = listInmueble.get(modelRow);
        Inmueble editar = inmuebleSeleccionado;

        InmuebleDetallesDialog_new editaInmueble = new InmuebleDetallesDialog_new(null, true, inmuebleDAO.findByInmuebleEager(editar.getId()));
        editaInmueble.setVisible(true);
        editaInmueble.toFront();
        buscarInmuebles();
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
