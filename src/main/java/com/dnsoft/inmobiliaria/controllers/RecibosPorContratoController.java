/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.Renderers.MeAlquilerColorDateRenderer;
import com.dnsoft.inmobiliaria.Renderers.MeDateCellRenderer;
import com.dnsoft.inmobiliaria.Renderers.TableRendererColorMora;
import com.dnsoft.inmobiliaria.Renderers.TableRendererColorSituacion;
import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.beans.GastoInmuebleInquilino;
import com.dnsoft.inmobiliaria.beans.PagoRecibo;
import com.dnsoft.inmobiliaria.beans.Situacion;
import com.dnsoft.inmobiliaria.beans.TipoContrato;
import com.dnsoft.inmobiliaria.daos.ICCPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.ICajaDAO;
import com.dnsoft.inmobiliaria.daos.IContratoDAO;
import com.dnsoft.inmobiliaria.daos.IRecibosDAO;
import com.dnsoft.inmobiliaria.daos.IGastoInmuebleInquilinoDAO;
import com.dnsoft.inmobiliaria.daos.IPagoPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.IPagoReciboDAO;
import com.dnsoft.inmobiliaria.models.RecibosTableModel;
import com.dnsoft.inmobiliaria.models.GastoInmuebleInquilinoTableModel;
import com.dnsoft.inmobiliaria.utils.AjustaColumnas;
import com.dnsoft.inmobiliaria.utils.CalculaMora;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ImprimeRecibo;
import com.dnsoft.inmobiliaria.views.DetalleGasto;
import com.dnsoft.inmobiliaria.views.PagosDetalleDlg;
import com.dnsoft.inmobiliaria.views.DetallePagosGastosInmueble;
import com.dnsoft.inmobiliaria.views.DetallesRecibo;
import com.dnsoft.inmobiliaria.views.PagoGastoInmuebleDialog;
import com.dnsoft.inmobiliaria.views.PagoReciboDlg1;
import com.dnsoft.inmobiliaria.views.RecibosPorContratoFrame;
import com.dnsoft.inmobiliaria.views.RegistrarReciboComoPago;
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

/**
 *
 * @author Diego Noble
 */
public class RecibosPorContratoController implements ActionListener {

    Container container;
    Contrato contratoSeleccionado;
    IContratoDAO contratosDAO;
    RecibosPorContratoFrame view;
    List<Contrato> listContratos;
    IRecibosDAO recibosDAO;
    IGastoInmuebleInquilinoDAO gastoInmuebleInquilinoDAO;
    ListSelectionModel listModel;
    List<Recibo> listRecibos;
    Recibo reciboSeleccionado;
    RecibosTableModel tableModeRecibos;
    List<GastoInmuebleInquilino> listGastoInmuebleInquilino;
    GastoInmuebleInquilinoTableModel tableModelInquilino;
    GastoInmuebleInquilino gastoInmuebleInquilinoSeleccionado;

    IPagoReciboDAO pagoReciboDAO;
    IPagoPropietarioDAO pagoPropietarioDAO;
    ICCPropietarioDAO cCPropietarioDAO;
    ICajaDAO cajaDAO;

    public RecibosPorContratoController(RecibosPorContratoFrame view, Contrato contratoSeleccionado) {

        this.container = Container.getInstancia();
        recibosDAO = container.getBean(IRecibosDAO.class);
        contratosDAO = container.getBean(IContratoDAO.class);
        gastoInmuebleInquilinoDAO = container.getBean(IGastoInmuebleInquilinoDAO.class);
        pagoReciboDAO = container.getBean(IPagoReciboDAO.class);
        pagoPropietarioDAO = container.getBean(IPagoPropietarioDAO.class);
        cCPropietarioDAO = container.getBean(ICCPropietarioDAO.class);
        this.view = view;
        this.contratoSeleccionado = contratoSeleccionado;
        view.setLocationRelativeTo(null);
        inicia();
    }

    private void inicia() {

        configTblRecibos();
        configuraTblGastosInquilino();
        accionesBotones();
        view.lblContrato.setText(contratoSeleccionado.getId() + ", " + contratoSeleccionado.getDescripcionInquilino() + ", " + contratoSeleccionado.getDescripcionInmueble());
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
        buscaRecibos();
    }

    public void go() {
        this.view.setVisible(true);
        this.view.toFront();

    }

    void configuraTblGastosInquilino() {
        ((DefaultTableCellRenderer) view.tblInquilinos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listGastoInmuebleInquilino = new ArrayList<>();
        tableModelInquilino = new GastoInmuebleInquilinoTableModel(listGastoInmuebleInquilino);
        view.tblInquilinos.setModel(tableModelInquilino);
        view.tblInquilinos.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());
        view.tblInquilinos.removeColumn(view.tblInquilinos.getColumn("Inmueble"));
        view.tblInquilinos.removeColumn(view.tblInquilinos.getColumn("Inquilino"));
        view.tblInquilinos.setToolTipText("Doble click para ver la descripción del gasto");
        view.tblInquilinos.getColumn("Situación").setCellRenderer(new TableRendererColorSituacion(5));

        view.tblInquilinos.setRowHeight(20);

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
        tableModeRecibos = new RecibosTableModel(listRecibos);

        view.tblRecibos.setModel(tableModeRecibos);
        view.tblRecibos.getColumn("Vencimiento").setCellRenderer(new MeAlquilerColorDateRenderer(10, 9));
        view.tblRecibos.getColumn("Fecha pago").setCellRenderer(new MeDateCellRenderer());
        view.tblRecibos.setDefaultRenderer(Object.class, new TableRendererColorMora(10, 9));
        view.tblRecibos.getColumn("Situación").setCellRenderer(new TableRendererColorSituacion(9));
        if (contratoSeleccionado.getTipoContrato() == TipoContrato.VENTA) {
            view.tblRecibos.getColumnModel().getColumn(3).setMaxWidth(0);
            view.tblRecibos.getColumnModel().getColumn(3).setMinWidth(0);
            view.tblRecibos.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(0);
            view.tblRecibos.getTableHeader().getColumnModel().getColumn(3).setMinWidth(0);
        }

        int[] anchos = {1, 1, 90, 130, 40, 40, 40, 40, 40, 90, 1};

        for (int i = 0; i < view.tblRecibos.getColumnCount(); i++) {

            view.tblRecibos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
        view.tblRecibos.getColumnModel().getColumn(10).setMaxWidth(0);
        view.tblRecibos.getColumnModel().getColumn(10).setMinWidth(0);
        view.tblRecibos.getTableHeader().getColumnModel().getColumn(10).setMaxWidth(0);
        view.tblRecibos.getTableHeader().getColumnModel().getColumn(10).setMinWidth(0);

        view.tblRecibos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (view.tblRecibos.getSelectedRow() != -1) {
                    reciboSeleccionado = listRecibos.get(view.tblRecibos.getSelectedRow());

                    if (reciboSeleccionado.getSituacion() == Situacion.PENDIENTE) {
                        view.btnEditar.setEnabled(true);
                        view.btnAnularPago.setEnabled(false);
                        view.btnEliminar.setEnabled(true);
                        view.btnPagar.setEnabled(true);
                        view.btnImprimeRecibo.setEnabled(false);
                        view.btnRegistarComoPago.setEnabled(true);
                    } else if (reciboSeleccionado.getSituacion() == Situacion.PAGO) {
                        view.btnAnularPago.setEnabled(true);
                        view.btnRegistarComoPago.setEnabled(true);
                        view.btnEditar.setEnabled(false);
                        view.btnEliminar.setEnabled(false);
                        view.btnPagar.setEnabled(false);
                        view.btnRegistarComoPago.setEnabled(false);
                        view.btnImprimeRecibo.setEnabled(true);
                    } else if (reciboSeleccionado.getSituacion() == Situacion.CON_SALDO) {
                        view.btnEditar.setEnabled(false);
                        view.btnAnularPago.setEnabled(true);
                        view.btnEliminar.setEnabled(false);
                        view.btnPagar.setEnabled(true);
                        view.btnRegistarComoPago.setEnabled(true);
                        view.btnImprimeRecibo.setEnabled(true);
                    }

                } else {
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
                    if (reciboSeleccionado.getSituacion() != Situacion.PAGO) {
                        pagarReciboSeleccionado();
                    }
                }
            }
        });

        view.tblRecibos.setRowHeight(20);

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
            listGastoInmuebleInquilino.addAll(gastoInmuebleInquilinoDAO.findOtrosGastosInquilinos(contratoSeleccionado.getInquilino(), Situacion.PAGO));
        } else if (view.rbPendientes.isSelected()) {
            listSitacion.add(Situacion.CON_SALDO);
            listSitacion.add(Situacion.PENDIENTE);
            listGastoInmuebleInquilino.addAll(gastoInmuebleInquilinoDAO.findByInmuebleAndSituacionIn(contratoSeleccionado.getInmueble(), listSitacion));
            listGastoInmuebleInquilino.addAll(gastoInmuebleInquilinoDAO.findOtrosGastosInquilinos(contratoSeleccionado.getInquilino(), Situacion.CON_SALDO));
            listGastoInmuebleInquilino.addAll(gastoInmuebleInquilinoDAO.findOtrosGastosInquilinos(contratoSeleccionado.getInquilino(), Situacion.PENDIENTE));
        } else if (view.rbTodo.isSelected()) {
            listGastoInmuebleInquilino.addAll(gastoInmuebleInquilinoDAO.findByInmueble(contratoSeleccionado.getInmueble()));
            listGastoInmuebleInquilino.addAll(gastoInmuebleInquilinoDAO.findOtrosGastosInquilinos(contratoSeleccionado.getInquilino(), Situacion.CON_SALDO));
            listGastoInmuebleInquilino.addAll(gastoInmuebleInquilinoDAO.findOtrosGastosInquilinos(contratoSeleccionado.getInquilino(), Situacion.PENDIENTE));
            listGastoInmuebleInquilino.addAll(gastoInmuebleInquilinoDAO.findOtrosGastosInquilinos(contratoSeleccionado.getInquilino(), Situacion.PAGO));
        }

        tableModelInquilino.fireTableDataChanged();
    }

    void buscaRecibos() {
        listRecibos.clear();

        List<Situacion> listSitacion = new ArrayList<>();

        if (view.rbPagos.isSelected()) {
            listSitacion.add(Situacion.PAGO);
            listRecibos.addAll(recibosDAO.findByContratoAndSituacionInOrderByFechaVencimientoAsc(contratoSeleccionado, listSitacion));

        } else if (view.rbPendientes.isSelected()) {
            listSitacion.add(Situacion.CON_SALDO);
            listSitacion.add(Situacion.PENDIENTE);
            listRecibos.addAll(recibosDAO.findByContratoAndSituacionInOrderByFechaVencimientoAsc(contratoSeleccionado, listSitacion));
            for (Recibo recibo : listRecibos) {
                CalculaMora calculaMora = new CalculaMora(recibo);
                if (recibo.getSituacion() != Situacion.PAGO) {
                    recibo.setMora(calculaMora.moraGenerada());
                }

            }
        } else if (view.rbTodo.isSelected()) {
            listRecibos.addAll(recibosDAO.findByContratoOrderByFechaVencimientoAsc(contratoSeleccionado));
            for (Recibo recibo : listRecibos) {
                CalculaMora calculaMora = new CalculaMora(recibo);
                 if (recibo.getSituacion() != Situacion.PAGO) {
                    recibo.setMora(calculaMora.moraGenerada());
                }
            }
        }

        tableModeRecibos.fireTableDataChanged();
    }

    void pagarReciboSeleccionado() {

        if (recibosDAO.findRecibosPendientesAnteriores(reciboSeleccionado.getContrato(), reciboSeleccionado.getNroRecibo()) == Boolean.TRUE) {
            JOptionPane.showMessageDialog(null, "Existen cuotas pendientes anteriores a la seleccionada!", "ATENCIÓN", JOptionPane.WARNING_MESSAGE);
        }
        BigDecimal mora = (BigDecimal) view.tblRecibos.getValueAt(view.tblRecibos.getSelectedRow(), 7);

        PagoReciboDlg1 detallePagoAlquiler = new PagoReciboDlg1(null, true, reciboSeleccionado, mora);
        detallePagoAlquiler.setVisible(true);
        detallePagoAlquiler.toFront();

        tableModeRecibos.fireTableDataChanged();

    }

    void eliminarAlquilerSeleccionado() {
        if (JOptionPane.showConfirmDialog(view, "Confirma que desea elminar el recibo seleccionado?", "ConfirmaciÃ³n", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            recibosDAO.deleteRecibo(reciboSeleccionado.getId());

            buscaRecibos();

        }
    }

    void modificarAlquilerSeleccionado() {

        DetallesRecibo modificarRecibo = new DetallesRecibo(null, true, reciboSeleccionado);
        modificarRecibo.setVisible(true);
        modificarRecibo.toFront();

        buscaRecibos();
    }

    void imprimieRecibo() {

        try {
//            contratoDAO.findByContrato(pagoRecibo.get)
           /* if (pagoRecibo.getRecibo().getContrato().getTipoContrato() == TipoContrato.ALQUILER) {

             new ImprimeRecibo().imprimieReciboAlquiler(pagoRecibo, pagoRecibo.getCotizacion());

             } else {*/
            PagoRecibo pagoRecibo = pagoReciboDAO.findByRecibo(reciboSeleccionado).get(0);
            new ImprimeRecibo().imprimieReciboPagoCuota(pagoRecibo, pagoRecibo.getCotizacion());
            //  }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "No es posible imprimir este recibo ", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    void accionesBotones() {

        view.btnNuevoRecibo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                DetallesRecibo nuevoRecibo = new DetallesRecibo(null, true, contratoSeleccionado);
                nuevoRecibo.setVisible(true);
                nuevoRecibo.toFront();
                buscaRecibos();
            }
        });
        /* view.btnDetallePagos.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent evt) {
         PagosDetalleDlg detalles = new PagosDetalleDlg(null, false, reciboSeleccionado);
         detalles.setVisible(true);
         detalles.toFront();
         buscaRecibos();
         }
         });*/
        view.btnPagar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                pagarReciboSeleccionado();
                buscaRecibos();
                /*if (cajaController != null) {
                 cajaController.actualizaTbl();
                 view.dispose();
                 }*/
                //view.dispose();
            }
        });
        view.btnEditar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                modificarAlquilerSeleccionado();
                buscaRecibos();
            }
        });
        view.btnEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                eliminarAlquilerSeleccionado();
                buscaRecibos();
            }
        });
        view.btnDetallePagosInquilino.addMouseListener(new MouseAdapter() {
            @Override

            public void mouseClicked(MouseEvent evt) {
                DetallePagosGastosInmueble detallePagosGastosInmueble = new DetallePagosGastosInmueble(null, false, gastoInmuebleInquilinoSeleccionado);
                detallePagosGastosInmueble.setVisible(true);
                detallePagosGastosInmueble.toFront();
                buscaRecibos();
            }
        });
        view.btnPagarGastoInquilino.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                PagoGastoInmuebleDialog pagoGastoInmueble = new PagoGastoInmuebleDialog(null, true, gastoInmuebleInquilinoSeleccionado);
                pagoGastoInmueble.setVisible(true);
                pagoGastoInmueble.toFront();
                buscaRecibos();
                /*if (cajaController != null) {
                 cajaController.actualizaTbl();
                 }*/
            }
        });

        view.btnRegistarComoPago.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                RegistrarReciboComoPago pago = new RegistrarReciboComoPago(null, true, reciboSeleccionado);
                pago.setVisible(true);
                pago.toFront();
                buscaRecibos();
            }
        });

        view.btnVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                view.dispose();
            }
        });

        view.btnAnularPago.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                PagosDetalleDlg detalles = new PagosDetalleDlg(null, false, reciboSeleccionado);
                detalles.setVisible(true);
                detalles.toFront();
                buscaRecibos();
            }
        });
        view.btnImprimeRecibo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                imprimieRecibo();
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {

            default:
                throw new AssertionError();
        }
    }
}
