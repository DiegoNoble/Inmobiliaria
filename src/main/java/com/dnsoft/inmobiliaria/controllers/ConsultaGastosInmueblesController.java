/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.Renderers.MeDateCellRenderer;
import com.dnsoft.inmobiliaria.Renderers.TableRendererColorSituacion;
import com.dnsoft.inmobiliaria.beans.GastoInmuebleInmobiliaria;
import com.dnsoft.inmobiliaria.beans.GastoInmuebleInquilino;
import com.dnsoft.inmobiliaria.beans.GastoInmueblePropietario;
import com.dnsoft.inmobiliaria.beans.Inmueble;
import com.dnsoft.inmobiliaria.daos.IGastoInmuebleInmobiliariaDAO;
import com.dnsoft.inmobiliaria.daos.IGastoInmuebleInquilinoDAO;
import com.dnsoft.inmobiliaria.daos.IGastoInmueblePropietarioDAO;
import com.dnsoft.inmobiliaria.daos.IInmuebleDAO;
import com.dnsoft.inmobiliaria.daos.IInquilinoDAO;
import com.dnsoft.inmobiliaria.daos.IRubroDAO;
import com.dnsoft.inmobiliaria.models.GastoInmuebleInmobiliariaTableModel;
import com.dnsoft.inmobiliaria.models.GastoInmuebleInquilinoTableModel;
import com.dnsoft.inmobiliaria.models.GastoInmueblePropietarioTableModel;
import com.dnsoft.inmobiliaria.models.InmuebleTableModel;
import com.dnsoft.inmobiliaria.utils.AjustaColumnas;
import com.dnsoft.inmobiliaria.utils.ButtonColumnEditar;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.views.ConsultaGastosInmueblesDialog;
import com.dnsoft.inmobiliaria.views.DetalleGasto;
import com.dnsoft.inmobiliaria.views.NuevoGastoInmueble;
import com.dnsoft.inmobiliaria.views.DetallePagosGastosInmueble;
import com.dnsoft.inmobiliaria.views.InmuebleDetallesDialog_new;
import com.dnsoft.inmobiliaria.views.PagoGastoInmuebleDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
public class ConsultaGastosInmueblesController implements ActionListener {

    Container container;
    ConsultaGastosInmueblesDialog view;
    List<Inmueble> listInmueble;
    Inmueble inmuebleSeleccionado;
    GastoInmueblePropietario gastoInmueblePropietarioSeleccionado;
    GastoInmuebleInquilino gastoInmuebleInquilinoSeleccionado;

    IInmuebleDAO inmuebleDAO;
    InmuebleTableModel tableModelInmuebles;

    IRubroDAO tipoGastoDAO;
    IInquilinoDAO inquilinoDAO;
    IGastoInmuebleInquilinoDAO gastoInmuebleInquilinoDAO;
    IGastoInmueblePropietarioDAO gastoInmueblePropietarioDAO;
    IGastoInmuebleInmobiliariaDAO gastoInmuebleInmobiliariaDAO;
    List<GastoInmuebleInquilino> listGastoInmuebleInquilino;
    GastoInmuebleInquilinoTableModel tableModelInquilino;
    List<GastoInmueblePropietario> listGastoInmueblePropietario;
    List<GastoInmuebleInmobiliaria> listGastoInmuebleInmobiliaria;
    GastoInmueblePropietarioTableModel tableModelPropietarios;
    GastoInmuebleInmobiliariaTableModel tableModelInmobiliaria;

    public ConsultaGastosInmueblesController(ConsultaGastosInmueblesDialog view) {

        this.container = Container.getInstancia();
        this.view = view;
        inicia();
    }
  

    private void inicia() {
        accionesBotones();
        gastoInmueblePropietarioDAO = container.getBean(IGastoInmueblePropietarioDAO.class);
        inmuebleDAO = container.getBean(IInmuebleDAO.class);
        inquilinoDAO = container.getBean(IInquilinoDAO.class);
        gastoInmuebleInquilinoDAO = container.getBean(IGastoInmuebleInquilinoDAO.class);
        gastoInmuebleInmobiliariaDAO = container.getBean(IGastoInmuebleInmobiliariaDAO.class);;
        tipoGastoDAO = container.getBean(IRubroDAO.class);
        Calendar fecha = Calendar.getInstance();
        fecha.add(Calendar.MONTH, -1);
        view.dpInicio.setDate(fecha.getTime());
        view.dpInicio.setFormats("dd/MM/yyyy");
        view.dpFin.setFormats("dd/MM/yyyy");
        //view.dpInicio.setDate(new Date());
        view.dpFin.setDate(new Date());
        this.view.dpInicio.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                buscarGastosPropietarios();
                buscarGastosInquilinoss();
                buscarGastosInmobiliaria();
            }
        });

        this.view.dpFin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                buscarGastosPropietarios();
                buscarGastosInquilinoss();
                buscarGastosInmobiliaria();
            }
        });

        this.view.txtBuscaPropiedad.setActionCommand("txtBuscaPropiedad");
        this.view.txtBuscaPropiedad.addActionListener(this);
        PromptSupport.setPrompt("Buscar por valor referencia, nro. padrón, calle, nro puerta, barrio o ciudad", view.txtBuscaPropiedad);

        configTblInmuebles();
        configuraTblGastosPropietarios();
        configuraTblGastosInquilino();
        configuraTblGastosInmobiliaria();

    }

    public void go() {
        this.view.setVisible(true);
        this.view.toFront();

    }

    void configTblInmuebles() {
        ((DefaultTableCellRenderer) view.tbInmuebles.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listInmueble = new ArrayList<>();
        listInmueble.addAll(inmuebleDAO.findAll());
        tableModelInmuebles = new InmuebleTableModel(listInmueble);
        view.tbInmuebles.setModel(tableModelInmuebles);
        new ButtonColumnEditar(view.tbInmuebles, 4) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                editaSeleccionado();
            }
        };

        view.tbInmuebles.setRowHeight(25);

        ListSelectionModel listModelInmueble = view.tbInmuebles.getSelectionModel();
        listModelInmueble.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (view.tbInmuebles.getSelectedRow() != -1) {
                    inmuebleSeleccionado = listInmueble.get(view.tbInmuebles.getSelectedRow());
                    view.btnNuevoGasto.setEnabled(true);
                    view.dpInicio.setEditable(true);
                    view.dpFin.setEditable(true);
                    buscarGastosPropietarios();
                    buscarGastosInquilinoss();
                    buscarGastosInmobiliaria();

                } else {
                    view.btnNuevoGasto.setEnabled(false);
                    view.dpInicio.setEditable(false);
                    view.dpFin.setEditable(false);
                    inmuebleSeleccionado = null;
                    buscarGastosPropietarios();
                    buscarGastosInquilinoss();
                    buscarGastosInmobiliaria();

                }
            }

        });

    }

    void configuraTblGastosInquilino() {
        ((DefaultTableCellRenderer) view.tblInquilinos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listGastoInmuebleInquilino = new ArrayList<>();
        tableModelInquilino = new GastoInmuebleInquilinoTableModel(listGastoInmuebleInquilino);
        view.tblInquilinos.setModel(tableModelInquilino);
        view.tblInquilinos.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());
        view.tblInquilinos.removeColumn(view.tblInquilinos.getColumn("Inmueble"));
        view.tblInquilinos.setToolTipText("Doble click para ver la descripcón del gasto");
        view.tblInquilinos.getColumn("Situación").setCellRenderer(new TableRendererColorSituacion(6));

        view.tblInquilinos.setRowHeight(25);

        int[] anchos = {10, 10, 150, 10, 10, 10, 10};
        new AjustaColumnas().ajustar(view.tblInquilinos, anchos);

        view.tblInquilinos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    detalleGastoInquilino();

                }
            }
        });

        view.tblInquilinos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (view.tblInquilinos.getSelectedRow() != -1) {
                    gastoInmuebleInquilinoSeleccionado = listGastoInmuebleInquilino.get(view.tblInquilinos.getSelectedRow());
                    switch (gastoInmuebleInquilinoSeleccionado.getSituacion()) {
                        case CON_SALDO:
                            view.btnDetallePagosInquilino.setEnabled(true);
                            //view.btnEditar.setEnabled(true);
                            //view.btnEliminar.setEnabled(true);
                            view.btnPagarGastoInquilino.setEnabled(true);
                            break;
                        case PAGO:
                            view.btnDetallePagosInquilino.setEnabled(true);
                            //view.btnEditar.setEnabled(true);
                            //view.btnEliminar.setEnabled(true);
                            view.btnPagarGastoInquilino.setEnabled(false);
                            break;
                        case PENDIENTE:
                            view.btnDetallePagosInquilino.setEnabled(false);
                            //view.btnEditar.setEnabled(true);
                            //view.btnEliminar.setEnabled(true);
                            view.btnPagarGastoInquilino.setEnabled(true);
                            break;
                        default:
                            throw new AssertionError();
                    }

                }
            }
        });

    }

    void configuraTblGastosPropietarios() {
        ((DefaultTableCellRenderer) view.tblPropietarios.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listGastoInmueblePropietario = new ArrayList<>();
        tableModelPropietarios = new GastoInmueblePropietarioTableModel(listGastoInmueblePropietario);

        view.tblPropietarios.setModel(tableModelPropietarios);
        view.tblPropietarios.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());
        view.tblPropietarios.removeColumn(view.tblPropietarios.getColumn("Inmueble"));
        view.tblPropietarios.setToolTipText("Doble click para ver la descripcón del gasto");
        view.tblPropietarios.getColumn("Situación").setCellRenderer(new TableRendererColorSituacion(6));
        view.tblPropietarios.setRowHeight(25);
        int[] anchos = {10, 10, 150, 10, 10, 10, 10};
        new AjustaColumnas().ajustar(view.tblPropietarios, anchos);
        view.tblPropietarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    detalleGastoPropietario();

                }
            }
        });

        view.tblPropietarios.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (view.tblPropietarios.getSelectedRow() != -1) {
                    gastoInmueblePropietarioSeleccionado = listGastoInmueblePropietario.get(view.tblPropietarios.getSelectedRow());
                    switch (gastoInmueblePropietarioSeleccionado.getSituacion()) {
                        case CON_SALDO:
                            view.btnDetallePagosPropietarios.setEnabled(true);
                            //view.btnEditar.setEnabled(true);
                            //view.btnEliminar.setEnabled(true);
                            view.btnPagarGastoPropietario.setEnabled(true);
                            break;
                        case PAGO:
                            view.btnDetallePagosPropietarios.setEnabled(true);
                            //view.btnEditar.setEnabled(true);
                            //view.btnEliminar.setEnabled(true);
                            view.btnPagarGastoPropietario.setEnabled(false);
                            break;
                        case PENDIENTE:
                            view.btnDetallePagosPropietarios.setEnabled(false);
                            //view.btnEditar.setEnabled(true);
                            //view.btnEliminar.setEnabled(true);
                            view.btnPagarGastoPropietario.setEnabled(true);
                            break;
                        default:
                            throw new AssertionError();
                    }

                }
            }
        });

    }

    void configuraTblGastosInmobiliaria() {
        ((DefaultTableCellRenderer) view.tblInmobiliaria.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listGastoInmuebleInmobiliaria = new ArrayList<>();
        tableModelInmobiliaria = new GastoInmuebleInmobiliariaTableModel(listGastoInmuebleInmobiliaria);
        view.tblInmobiliaria.setModel(tableModelInmobiliaria);
        view.tblInmobiliaria.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());
        view.tblInmobiliaria.setToolTipText("Doble click para ver la descripcón del gasto");

        view.tblInmobiliaria.setRowHeight(25);

        view.tblInmobiliaria.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    detalleGastoInmobiliaria();

                }
            }
        });

    }

    void editaSeleccionado() {
        Inmueble editar = listInmueble.get(view.tbInmuebles.getSelectedRow());
        InmuebleDetallesDialog_new editaInquilino = new InmuebleDetallesDialog_new(null, true, editar);
        editaInquilino.setVisible(true);
        editaInquilino.toFront();
        listInmueble.clear();
        listInmueble.addAll(inmuebleDAO.findAll());
        tableModelInmuebles.fireTableDataChanged();
    }

    void detalleGastoInquilino() {
        GastoInmuebleInquilino seleccionado = listGastoInmuebleInquilino.get(view.tblInquilinos.getSelectedRow());

        DetalleGasto detalle = new DetalleGasto(null, true, seleccionado.getDescripcion(), 1);
        detalle.setVisible(true);
        detalle.toFront();

    }

    void detalleGastoPropietario() {
        GastoInmueblePropietario seleccionado = listGastoInmueblePropietario.get(view.tblPropietarios.getSelectedRow());

        DetalleGasto detalle = new DetalleGasto(null, true, seleccionado.getDescripcion(), 1);
        detalle.setVisible(true);
        detalle.toFront();

    }

    void detalleGastoInmobiliaria() {
        GastoInmuebleInmobiliaria seleccionado = listGastoInmuebleInmobiliaria.get(view.tblInmobiliaria.getSelectedRow());

        DetalleGasto detalle = new DetalleGasto(null, true, seleccionado.getDescripcion(), 1);
        detalle.setVisible(true);
        detalle.toFront();

    }

    private void buscarGastosInquilinoss() {
        listGastoInmuebleInquilino.clear();
        listGastoInmuebleInquilino.addAll(gastoInmuebleInquilinoDAO.findByInmuebleAndFechaBetween(inmuebleSeleccionado, view.dpInicio.getDate(), view.dpFin.getDate()));
        tableModelInquilino.fireTableDataChanged();
    }

    private void buscarGastosPropietarios() {
        listGastoInmueblePropietario.clear();
        listGastoInmueblePropietario.addAll(gastoInmueblePropietarioDAO.findByInmuebleAndFechaBetween(inmuebleSeleccionado, view.dpInicio.getDate(), view.dpFin.getDate()));
        tableModelPropietarios.fireTableDataChanged();
    }

    private void buscarGastosInmobiliaria() {
        listGastoInmuebleInmobiliaria.clear();
        listGastoInmuebleInmobiliaria.addAll(gastoInmuebleInmobiliariaDAO.findByInmuebleAndFechaBetween(inmuebleSeleccionado, view.dpInicio.getDate(), view.dpFin.getDate()));
        tableModelInmobiliaria.fireTableDataChanged();
    }

    void accionesBotones() {

        view.btnNuevoGasto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                NuevoGastoInmueble nuevoGasto = new NuevoGastoInmueble(null, true, inmuebleSeleccionado,null);
                nuevoGasto.setVisible(true);
                nuevoGasto.toFront();
                buscarGastosPropietarios();
                buscarGastosInquilinoss();

            }
        });
        view.btnDetallePagosInquilino.addMouseListener(new MouseAdapter() {
            @Override

            public void mouseClicked(MouseEvent evt) {
                DetallePagosGastosInmueble detallePagosGastosInmueble = new DetallePagosGastosInmueble(null, true, gastoInmuebleInquilinoSeleccionado);
                detallePagosGastosInmueble.setVisible(true);
                detallePagosGastosInmueble.toFront();
            }
        });
        view.btnDetallePagosPropietarios.addMouseListener(new MouseAdapter() {
            @Override

            public void mouseClicked(MouseEvent evt) {
                DetallePagosGastosInmueble detallePagosGastosInmueble = new DetallePagosGastosInmueble(null, true, gastoInmueblePropietarioSeleccionado);
                detallePagosGastosInmueble.setVisible(true);
                detallePagosGastosInmueble.toFront();
            }
        });
        view.btnPagarGastoPropietario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                PagoGastoInmuebleDialog pagoGastoInmueble = new PagoGastoInmuebleDialog(null, true, gastoInmueblePropietarioSeleccionado);
                pagoGastoInmueble.setVisible(true);
                pagoGastoInmueble.toFront();
            }
        });
        view.btnPagarGastoInquilino.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                PagoGastoInmuebleDialog pagoGastoInmueble = new PagoGastoInmuebleDialog(null, true, gastoInmuebleInquilinoSeleccionado);
                pagoGastoInmueble.setVisible(true);
                pagoGastoInmueble.toFront();
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {

            case "txtBuscaPropiedad":

                listInmueble.clear();
                listInmueble.addAll(inmuebleDAO.findInmuebles(view.txtBuscaPropiedad.getText()));
                tableModelInmuebles.fireTableDataChanged();

                break;

            default:
                throw new AssertionError();
        }
    }

}
