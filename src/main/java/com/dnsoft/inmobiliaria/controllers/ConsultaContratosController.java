/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.views.ContratosDialog;
import com.dnsoft.inmobiliaria.Renderers.TableRendererConsultaContratos;
import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.Inmueble;
import com.dnsoft.inmobiliaria.beans.Inquilino;
import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.beans.TipoContrato;
import com.dnsoft.inmobiliaria.daos.IContratoDAO;
import com.dnsoft.inmobiliaria.daos.IGastoInmuebleInquilinoDAO;
import com.dnsoft.inmobiliaria.daos.IInmuebleDAO;
import com.dnsoft.inmobiliaria.daos.IInquilinoDAO;
import com.dnsoft.inmobiliaria.daos.IRecibosDAO;
import com.dnsoft.inmobiliaria.models.ContratosTableModel;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ControlarEntradaTexto;
import com.dnsoft.inmobiliaria.views.ConsultaContratosInternal;
import com.dnsoft.inmobiliaria.views.DetallePropietariosInmueble;
import com.dnsoft.inmobiliaria.views.InmuebleDetallesDialog_new;
import com.dnsoft.inmobiliaria.views.InquilinoDetallesDlg;
import com.dnsoft.inmobiliaria.views.RecibosPorContratoFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JDesktopPane;
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
    ConsultaContratosInternal view;
    JDesktopPane desktopPane;
    ContratosTableModel tableModelContratos;
    List<Contrato> listContratos;
    Contrato contratoSeleccionado;
    IContratoDAO contratosDAO;
    IInmuebleDAO inmuebleDAO;
    IInquilinoDAO inquilinoDAO;
    IRecibosDAO recibosDAO;
    IGastoInmuebleInquilinoDAO gastoInmuebleInquilinoDAO;
    ListSelectionModel listModel;
    ControlDeCajaController cajaController;

    public ConsultaContratosController(ConsultaContratosInternal view, JDesktopPane desktopPane) {

        this.container = Container.getInstancia();
        contratosDAO = container.getBean(IContratoDAO.class);
        gastoInmuebleInquilinoDAO = container.getBean(IGastoInmuebleInquilinoDAO.class);
        inmuebleDAO = container.getBean(IInmuebleDAO.class);
        inquilinoDAO = container.getBean(IInquilinoDAO.class);
        this.view = view;
        this.desktopPane = desktopPane;
        //view.setLocationRelativeTo(null);
        inicia();
    }

    public ConsultaContratosController(ConsultaContratosInternal view, ControlDeCajaController cajaController) {

        this.container = Container.getInstancia();
        this.cajaController = cajaController;
        contratosDAO = container.getBean(IContratoDAO.class);
        gastoInmuebleInquilinoDAO = container.getBean(IGastoInmuebleInquilinoDAO.class);
        inmuebleDAO = container.getBean(IInmuebleDAO.class);
        inquilinoDAO = container.getBean(IInquilinoDAO.class);
        view.btnNuevoContrato.setVisible(false);
        view.btnModificarContrato.setVisible(false);
        view.btnPagarConsultarRecibos.setEnabled(false);
        this.view = view;
        //view.setSize(view.getMaximumSize());
        //view.setLocationRelativeTo(null);
        inicia();
    }

    private void inicia() {

        recibosDAO = container.getBean(IRecibosDAO.class);
        this.view.txtBusqueda.setActionCommand("txtBusqueda");
        this.view.txtBusqueda.addActionListener(this);
        this.view.txtBusquedaPorNroContrato.setActionCommand("txtBusquedaPorNroContrato");
        this.view.txtBusquedaPorNroContrato.addActionListener(this);
        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-'};
        view.txtBusquedaPorNroContrato.setDocument(new ControlarEntradaTexto(10, chs));
        PromptSupport.setPrompt("Buscar Nro. Contrato, por Nombre, Apellido, Nro Documento de Inquilino o Nro. Padrón, Calle, Nro Puerta, Barrio o Ciudad del Inmueble", view.txtBusqueda);

        configTblContrato();
        accionesBotones();

    }

    public void go() {
        this.view.setVisible(true);
        this.view.toFront();

    }

    private void configTblContrato() {

        ((DefaultTableCellRenderer) view.tblContratos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listContratos = new ArrayList<>();
        //listContratos.addAll(contratosDAO.findByInquilinoOrInmueble(view.cbActivos.isSelected(), view.txtBusqueda.getText()));
        tableModelContratos = new ContratosTableModel(listContratos);
        view.tblContratos.setModel(tableModelContratos);
        buscaContratos();
        view.tblContratos.setDefaultRenderer(Object.class, new TableRendererConsultaContratos(5));

        int[] anchos = {5, 400, 250, 50, 50, 5};

        for (int i = 0; i < view.tblContratos.getColumnCount(); i++) {

            view.tblContratos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }

        view.tblContratos.getColumn("Activo").setMaxWidth(0);
        view.tblContratos.getColumn("Activo").setMinWidth(0);
        view.tblContratos.getColumn("Activo").setPreferredWidth(0);
        view.tblContratos.getColumn("Activo").setWidth(0);

        listModel = view.tblContratos.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (view.tblContratos.getSelectedRow() != -1) {

                    contratoSeleccionado = listContratos.get(view.tblContratos.getSelectedRow());
                    view.btnModificarContrato.setEnabled(true);
                    view.btnPagarConsultarRecibos.setEnabled(true);
                    view.btnActivarInactivar.setEnabled(true);

                } else {
                    view.btnPagarConsultarRecibos.setEnabled(false);
                    view.btnModificarContrato.setEnabled(false);
                    view.btnActivarInactivar.setEnabled(false);
                }
            }
        });

        view.tblContratos.setRowHeight(20);

        view.tblContratos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    if (view.tblContratos.getSelectedColumn() == 0) {
                        consultaRecibosGastos();
                    } else if (view.tblContratos.getSelectedColumn() == 1) {
                        Inquilino inquilino = inquilinoDAO.findByInquilinoEager(contratosDAO.findByContrato(contratoSeleccionado.getId()).getInquilino().getId());
                        InquilinoDetallesDlg editaInquilino = new InquilinoDetallesDlg(null, true, inquilino, contratoSeleccionado);
                        editaInquilino.setVisible(true);
                        editaInquilino.toFront();
                        buscaContratos();
                    } else if (view.tblContratos.getSelectedColumn() == 2) {
                        Inmueble inmueble = inmuebleDAO.findByInmuebleEager(contratosDAO.findByContrato(contratoSeleccionado.getId()).getInmueble().getId());

                        InmuebleDetallesDialog_new editaInmueble = new InmuebleDetallesDialog_new(null, true, inmueble, contratoSeleccionado);
                        editaInmueble.setVisible(true);
                        editaInmueble.toFront();
                        buscaContratos();
                    }

                }
            }
        });

    }

    private void consultarPropietarios() {
        DetallePropietariosInmueble propietarios = new DetallePropietariosInmueble(null, true, contratoSeleccionado.getInmueble());
        propietarios.setVisible(true);
        propietarios.toFront();
    }

    void editaSeleccionado() {
        if (contratoSeleccionado.getTipoContrato() == TipoContrato.VENTA) {
            contratoSeleccionado = contratosDAO.findByContrato(listContratos.get(view.tblContratos.getSelectedRow()).getId());
            ContratosController editaContrato = new ContratosController(new ContratosDialog(null, true), contratoSeleccionado, this);
            editaContrato.go();
        } else if (contratoSeleccionado.getTipoContrato() == TipoContrato.ALQUILER) {
            contratoSeleccionado = contratosDAO.findContratoAlquiler(listContratos.get(view.tblContratos.getSelectedRow()).getId());
            ContratosController editaContrato = new ContratosController(new ContratosDialog(null, true), contratoSeleccionado, this);
            editaContrato.go();
        }
        buscaContratos();

    }

    void buscaContratos() {

        listContratos.clear();
        //listContratos.addAll(contratosDAO.findByInquilinoOrInmueble( view.cbActivos.isSelected(),view.txtBusqueda.getText()));
        listContratos.addAll(contratosDAO.findAllRapido(view.txtBusqueda.getText()));
        tableModelContratos.fireTableDataChanged();
    }

    void buscaContratosContrato() {

        listContratos.clear();
        //listContratos.addAll(contratosDAO.findByInquilinoOrInmueble( view.cbActivos.isSelected(),view.txtBusqueda.getText()));
        listContratos.addAll(contratosDAO.findByContratoLike(Long.valueOf(view.txtBusquedaPorNroContrato.getText())));
        tableModelContratos.fireTableDataChanged();
    }

    void accionesBotones() {
        view.btnNuevoContrato.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                ContratosDialog viewContrato = new ContratosDialog(null, false);
                ContratosController controller = new ContratosController(viewContrato, desktopPane, ConsultaContratosController.this);

                controller.go();
                buscaContratos();

            }
        });

        view.brnVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                view.dispose();
            }
        });

        view.btnModificarContrato.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                editaSeleccionado();
            }
        });

        view.btnEliminarInmueble.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                contratoSeleccionado = contratosDAO.findByContrato(listContratos.get(view.tblContratos.getSelectedRow()).getId());
                int c = JOptionPane.showConfirmDialog(null, "Confirma eliminación del contrato: " + contratoSeleccionado.toString(), "Confirmación", JOptionPane.YES_NO_OPTION);
                if (c == 0) {
                    try {
                        List<Recibo> recibos = recibosDAO.findByContratoOrderByNroReciboDesc(contratoSeleccionado);
                        for (Recibo recibo : recibos) {
                            recibosDAO.deleteRecibo(recibo.getId());
                        }

                        //contratoSeleccionado = contratosDAO.findByContrato(listContratos.get(view.tblContratos.getSelectedRow()).getId());
                        contratosDAO.delete(contratoSeleccionado);
                        buscaContratos();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "No es posible eliminar el inmueble", "Error", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                    }
                }

            }
        });

        view.btnPagarConsultarRecibos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                consultaRecibosGastos();
            }
        });

        view.btnActivarInactivar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                contratoSeleccionado = contratosDAO.findByContrato(listContratos.get(view.tblContratos.getSelectedRow()).getId());
                int c = JOptionPane.showConfirmDialog(view, "Confirma inactivacion del contrato: " + contratoSeleccionado.toString(), "Confirmación", JOptionPane.YES_NO_OPTION);
                if (c == 0) {
                    if (contratoSeleccionado.getActivo() == Boolean.FALSE) {
                        contratoSeleccionado.setActivo(Boolean.TRUE);
                        contratoSeleccionado.setFechaInactivacion(null);
                    } else {
                        contratoSeleccionado.setActivo(Boolean.FALSE);
                        contratoSeleccionado.setFechaInactivacion(new Date());
                    }
                    contratosDAO.save(contratoSeleccionado);
                    buscaContratos();

                }
                buscaContratos();
            }
        });
    }

    void consultaRecibosGastos() {
        RecibosPorContratoFrame view = new RecibosPorContratoFrame();
        RecibosPorContratoController controller = new RecibosPorContratoController(view, contratoSeleccionado);
        controller.go();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {

            case "txtBusqueda":
                buscaContratos();
                break;

            case "txtBusquedaPorNroContrato":
                buscaContratosContrato();
                break;
            default:
                throw new AssertionError();
        }
    }

}
