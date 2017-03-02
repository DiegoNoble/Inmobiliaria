/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.views.ContratosDialog;
import com.dnsoft.inmobiliaria.Renderers.MeAlquilerColorDateRenderer;
import com.dnsoft.inmobiliaria.Renderers.MeDateCellRenderer;
import com.dnsoft.inmobiliaria.Renderers.TableRendererColorMora;
import com.dnsoft.inmobiliaria.Renderers.TableRendererColorSituacion;
import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.beans.GastoInmuebleInquilino;
import com.dnsoft.inmobiliaria.beans.Situacion;
import com.dnsoft.inmobiliaria.daos.IContratoDAO;
import com.dnsoft.inmobiliaria.daos.IRecibosDAO;
import com.dnsoft.inmobiliaria.daos.IGastoInmuebleInquilinoDAO;
import com.dnsoft.inmobiliaria.models.ContratosTableModel;
import com.dnsoft.inmobiliaria.models.AlquileresTableModel;
import com.dnsoft.inmobiliaria.models.GastoInmuebleInquilinoTableModel;
import com.dnsoft.inmobiliaria.utils.AjustaColumnas;
import com.dnsoft.inmobiliaria.utils.ButtonColumnDetalles;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.views.ConsultaContratosDialog;
import com.dnsoft.inmobiliaria.views.DetalleGasto;
import com.dnsoft.inmobiliaria.views.PagoReciboDlg;
import com.dnsoft.inmobiliaria.views.PagosDetalleDlg;
import com.dnsoft.inmobiliaria.views.DetallePagosGastosInmueble;
import com.dnsoft.inmobiliaria.views.DetallePropietariosInmueble;
import com.dnsoft.inmobiliaria.views.DetallesRecibo;
import com.dnsoft.inmobiliaria.views.PagoGastoInmuebleDialog;
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
public class ConsultaContratosController implements ActionListener {

    Container container;
    ConsultaContratosDialog view;
    ContratosTableModel tableModelContratos;
    List<Contrato> listContratos;
    Contrato contratoSeleccionado;
    IContratoDAO contratosDAO;
    IRecibosDAO recibosDAO;
    IGastoInmuebleInquilinoDAO gastoInmuebleInquilinoDAO;
    ListSelectionModel listModel;
    List<Recibo> listRecibos;
    Recibo reciboSeleccionado;
    AlquileresTableModel tableModeRecibos;
    List<GastoInmuebleInquilino> listGastoInmuebleInquilino;
    GastoInmuebleInquilinoTableModel tableModelInquilino;
    GastoInmuebleInquilino gastoInmuebleInquilinoSeleccionado;
    ControlDeCajaController cajaController;

    public ConsultaContratosController(ConsultaContratosDialog view) {

        this.container = Container.getInstancia();
        contratosDAO = container.getBean(IContratoDAO.class);
        recibosDAO = container.getBean(IRecibosDAO.class);
        gastoInmuebleInquilinoDAO = container.getBean(IGastoInmuebleInquilinoDAO.class);
        this.view = view;
        view.setLocationRelativeTo(null);
        inicia();
    }

    public ConsultaContratosController(ConsultaContratosDialog view,ControlDeCajaController cajaController) {

        this.container = Container.getInstancia();
        this.cajaController = cajaController;
        contratosDAO = container.getBean(IContratoDAO.class);
        recibosDAO = container.getBean(IRecibosDAO.class);
        gastoInmuebleInquilinoDAO = container.getBean(IGastoInmuebleInquilinoDAO.class);
        view.btnNuevoContrato.setVisible(false);
        view.btnModificarContrato.setVisible(false);
        view.btnDetallePagos.setVisible(false);
        view.btnDetallePagosInquilino.setVisible(false);
        //view.btnEditar.setVisible(false);
        view.btnEliminar.setVisible(false);
        view.rbPagos.setVisible(false);
        view.rbTodo.setVisible(false);
        this.view = view;
        view.setLocationRelativeTo(null);
        inicia();
    }

    private void inicia() {

        this.view.txtBusqueda.setActionCommand("txtBusqueda");
        this.view.txtBusqueda.addActionListener(this);
        PromptSupport.setPrompt("Buscar Nro. Contrato, por Nombre, Apellido, Nro Documento de Inquilino o Nro. Padrón, Calle, Nro Puerta, Barrio o Ciudad del Inmueble", view.txtBusqueda);

        configTblContrato();
        configTblRecibos();
        configuraTblGastosInquilino();
        accionesBotones();
        view.rbPagos.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                buscaRecibos();
                buscarGastosInquilino();

            }
        });
        view.rbTodo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                buscaRecibos();
                buscarGastosInquilino();
            }
        });
        view.rbPendientes.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                buscaRecibos();
                buscarGastosInquilino();
            }
        });

    }

    public void go() {
        this.view.setVisible(true);
        this.view.toFront();

    }

    private void configTblContrato() {

        ((DefaultTableCellRenderer) view.tblContratos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listContratos = new ArrayList<>();
        listContratos.addAll(contratosDAO.findAll());

        tableModelContratos = new ContratosTableModel(listContratos);
        view.tblContratos.setModel(tableModelContratos);
        view.tblContratos.getColumn("Fecha inicio").setCellRenderer(new MeDateCellRenderer());
        view.tblContratos.getColumn("Fecha fin").setCellRenderer(new MeDateCellRenderer());
        new ButtonColumnDetalles(view.tblContratos, 8) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                consultarPropietarios();
            }

        };

        int[] anchos = {5, 200, 50, 50, 50, 50, 50, 50, 5};

        for (int i = 0; i < view.tblContratos.getColumnCount(); i++) {

            view.tblContratos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }

        listModel = view.tblContratos.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (view.tblContratos.getSelectedRow() != -1) {
                    contratoSeleccionado = listContratos.get(view.tblContratos.getSelectedRow());
                    view.btnModificarContrato.setEnabled(true);
                    view.btnNuevoRecibo.setEnabled(true);
                    buscaRecibos();
                    buscarGastosInquilino();

                } else {
                    listRecibos.clear();

                    tableModeRecibos.fireTableDataChanged();
                    view.btnModificarContrato.setEnabled(false);
                    view.btnNuevoRecibo.setEnabled(false);
                }
            }
        });

        view.tblContratos.setRowHeight(25);

    }

    void configuraTblGastosInquilino() {
        ((DefaultTableCellRenderer) view.tblInquilinos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listGastoInmuebleInquilino = new ArrayList<>();
        tableModelInquilino = new GastoInmuebleInquilinoTableModel(listGastoInmuebleInquilino);
        view.tblInquilinos.setModel(tableModelInquilino);
        view.tblInquilinos.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());
        view.tblInquilinos.removeColumn(view.tblInquilinos.getColumn("Inmueble"));
        view.tblInquilinos.removeColumn(view.tblInquilinos.getColumn("Inquilino"));
        view.tblInquilinos.setToolTipText("Doble click para ver la descripcón del gasto");
        view.tblInquilinos.getColumn("Situación").setCellRenderer(new TableRendererColorSituacion(5));

        view.tblInquilinos.setRowHeight(25);

        int[] anchos = {10, 10, 10, 10, 10, 20};
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

    void configTblRecibos() {
        ((DefaultTableCellRenderer) view.tblRecibos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listRecibos = new ArrayList<>();
        tableModeRecibos = new AlquileresTableModel(listRecibos);

        view.tblRecibos.setModel(tableModeRecibos);
        view.tblRecibos.getColumn("Vencimiento").setCellRenderer(new MeAlquilerColorDateRenderer(8, 7));
        view.tblRecibos.setDefaultRenderer(Object.class, new TableRendererColorMora(8, 7));
        view.tblRecibos.getColumn("Situación").setCellRenderer(new TableRendererColorSituacion(7));

        int[] anchos = {1, 1, 50, 10, 10, 10, 10, 50, 50};

        for (int i = 0; i < view.tblRecibos.getColumnCount(); i++) {

            view.tblRecibos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
        view.tblRecibos.getColumnModel().getColumn(8).setMaxWidth(0);
        view.tblRecibos.getColumnModel().getColumn(8).setMinWidth(0);
        view.tblRecibos.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(0);
        view.tblRecibos.getTableHeader().getColumnModel().getColumn(8).setMinWidth(0);

        view.tblRecibos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (view.tblRecibos.getSelectedRow() != -1) {
                    reciboSeleccionado = listRecibos.get(view.tblRecibos.getSelectedRow());
                    if (reciboSeleccionado.getStatusAlquiler() == Situacion.PENDIENTE) {
                        view.btnDetallePagos.setEnabled(false);
                        view.btnEditar.setEnabled(true);
                        view.btnEliminar.setEnabled(true);
                        view.btnPagar.setEnabled(true);
                    } else if(reciboSeleccionado.getStatusAlquiler() == Situacion.PAGO) {
                        view.btnDetallePagos.setEnabled(true);
                        view.btnEditar.setEnabled(false);
                        view.btnEliminar.setEnabled(false);
                        view.btnPagar.setEnabled(false);
                    } else if(reciboSeleccionado.getStatusAlquiler() == Situacion.CON_SALDO) {
                        view.btnDetallePagos.setEnabled(true);
                        view.btnEditar.setEnabled(false);
                        view.btnEliminar.setEnabled(false);
                        view.btnPagar.setEnabled(true);
                    }

                }else{
                        view.btnDetallePagos.setEnabled(false);
                        view.btnEditar.setEnabled(false);
                        view.btnEliminar.setEnabled(false);
                        view.btnPagar.setEnabled(false);
                    }
            }
        });
        view.tblRecibos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    if (reciboSeleccionado.getStatusAlquiler() != Situacion.PAGO) {
                        pagarReciboSeleccionado();
                    }
                }
            }
        });

        view.tblRecibos.setRowHeight(25);

    }

    void detalleGastoInquilino() {
        GastoInmuebleInquilino seleccionado = listGastoInmuebleInquilino.get(view.tblInquilinos.getSelectedRow());

        DetalleGasto detalle = new DetalleGasto(null, true, seleccionado.getDescripcion(), 1);
        detalle.setVisible(true);
        detalle.toFront();

    }

    private void buscarGastosInquilino() {
        listGastoInmuebleInquilino.clear();
        List<Situacion> listSitacion = new ArrayList<>();

        if (view.rbPagos.isSelected()) {
            listSitacion.add(Situacion.PAGO);
            listGastoInmuebleInquilino.addAll(gastoInmuebleInquilinoDAO.findByInmuebleAndSituacionIn(contratoSeleccionado.getInmueble(), listSitacion));
        } else if (view.rbPendientes.isSelected()) {
            listSitacion.add(Situacion.CON_SALDO);
            listSitacion.add(Situacion.PENDIENTE);
            listGastoInmuebleInquilino.addAll(gastoInmuebleInquilinoDAO.findByInmuebleAndSituacionIn(contratoSeleccionado.getInmueble(), listSitacion));
        } else if (view.rbTodo.isSelected()) {
            listGastoInmuebleInquilino.addAll(gastoInmuebleInquilinoDAO.findByInmueble(contratoSeleccionado.getInmueble()));
        }

        tableModelInquilino.fireTableDataChanged();
    }

    void buscaRecibos() {
        listRecibos.clear();

        List<Situacion> listSitacion = new ArrayList<>();

        if (view.rbPagos.isSelected()) {
            listSitacion.add(Situacion.PAGO);
            listRecibos.addAll(recibosDAO.findByContratoAndSituacionIn(contratoSeleccionado, listSitacion));
        } else if (view.rbPendientes.isSelected()) {
            listSitacion.add(Situacion.CON_SALDO);
            listSitacion.add(Situacion.PENDIENTE);
            listRecibos.addAll(recibosDAO.findByContratoAndSituacionIn(contratoSeleccionado, listSitacion));
        } else if (view.rbTodo.isSelected()) {
            listRecibos.addAll(recibosDAO.findByContrato(contratoSeleccionado));
        }

        tableModeRecibos.fireTableDataChanged();
    }

    private void consultarPropietarios() {
        DetallePropietariosInmueble propietarios = new DetallePropietariosInmueble(null, true, contratoSeleccionado.getInmueble());
        propietarios.setVisible(true);
        propietarios.toFront();
    }

    void pagarReciboSeleccionado() {

        if (JOptionPane.showConfirmDialog(view, "Confirma el pago del recibo seleccionado?", "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            BigDecimal mora = (BigDecimal) view.tblRecibos.getValueAt(view.tblRecibos.getSelectedRow(), 5);
            PagoReciboDlg detallePagoAlquiler = new PagoReciboDlg(null, true, reciboSeleccionado, mora);
            detallePagoAlquiler.setVisible(true);
            detallePagoAlquiler.toFront();
            tableModeRecibos.fireTableDataChanged();

        }
    }

    /*public void actualizaTbl() {
        listContratos.clear();
        listContratos.addAll(contratosDAO.findAll());
        tableModelContratos.fireTableDataChanged();
    }
*/
    void eliminarAlquilerSeleccionado() {
        if (JOptionPane.showConfirmDialog(view, "Confirma que desea elminar el recibo seleccionado?", "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            recibosDAO.deleteRecibo(reciboSeleccionado.getId());

            buscaContratos();
        }
    }

    void modificarAlquilerSeleccionado() {
        DetallesRecibo modificarRecibo = new DetallesRecibo(null, true, reciboSeleccionado);
        modificarRecibo.setVisible(true);
        modificarRecibo.toFront();
        
        buscaRecibos();
    }

    void editaSeleccionado() {
        contratoSeleccionado = listContratos.get(view.tblContratos.getSelectedRow());
        ContratosController editaContrato = new ContratosController(new ContratosDialog(null, true), contratoSeleccionado);
        editaContrato.go();

        buscaRecibos();

    }

    void buscaContratos() {

        listContratos.clear();
        listContratos.addAll(contratosDAO.findByInquilinoOrInmueble(view.txtBusqueda.getText()));
        tableModelContratos.fireTableDataChanged();
    }

    void accionesBotones() {
        view.btnNuevoContrato.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                ContratosDialog viewContrato = new ContratosDialog(null, true);
                ContratosController controller = new ContratosController(viewContrato);

                controller.go();
            }
        });
        view.btnModificarContrato.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                editaSeleccionado();
            }
        });
        view.btnNuevoRecibo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                DetallesRecibo nuevoRecibo = new DetallesRecibo(null, true, contratoSeleccionado);
                nuevoRecibo.setVisible(true);
                nuevoRecibo.toFront();
                buscaRecibos();
            }
        });
        view.btnDetallePagos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                PagosDetalleDlg detalles = new PagosDetalleDlg(null, true, reciboSeleccionado);
                detalles.setVisible(true);
                detalles.toFront();
                buscaRecibos();
            }
        });
        view.btnPagar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                pagarReciboSeleccionado();
                if (cajaController != null) {
                    cajaController.actualizaTbl();
                }
            }
        });
        view.btnEditar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                modificarAlquilerSeleccionado();
            }
        });
        view.btnEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                eliminarAlquilerSeleccionado();
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
        view.btnPagarGastoInquilino.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                PagoGastoInmuebleDialog pagoGastoInmueble = new PagoGastoInmuebleDialog(null, true, gastoInmuebleInquilinoSeleccionado);
                pagoGastoInmueble.setVisible(true);
                pagoGastoInmueble.toFront();
                if (cajaController != null) {
                    cajaController.actualizaTbl();
                }
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {

            case "txtBusqueda":
                buscaContratos();
                break;

            default:
                throw new AssertionError();
        }
    }

}
