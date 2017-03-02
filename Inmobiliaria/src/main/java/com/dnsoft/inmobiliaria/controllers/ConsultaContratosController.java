/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.Renderers.MeDateCellRenderer;
import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.Recibos;
import com.dnsoft.inmobiliaria.daos.IContratoDAO;
import com.dnsoft.inmobiliaria.daos.IRecibosDAO;
import com.dnsoft.inmobiliaria.models.ContratosTableModel;
import com.dnsoft.inmobiliaria.models.RecibosTableModel;
import com.dnsoft.inmobiliaria.utils.CalculaRecibos;
import com.dnsoft.inmobiliaria.utils.PagaRecibo;
import com.dnsoft.inmobiliaria.views.ConsultaContratoView;
import com.dnsoft.inmobiliaria.views.ContratosView;
import com.dnsoft.inmobiliaria.views.detalles.DetallesRecibo;
import com.dnsoft.inmobiliaria.views.detalles.MovimientodeCajaRecibo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
public class ConsultaContratosController implements ActionListener {

    ApplicationContext applicationContext;
    public ConsultaContratoView view;
    ContratosTableModel tableModelContratos;
    List<Contrato> listContratos;
    Contrato contratoSeleccionado;
    IContratoDAO contratosDAO;
    IRecibosDAO recibosDAO;
    ListSelectionModel listModel;
    List<Recibos> listRecibos;
    Recibos reciboSeleccionado;
    RecibosTableModel tableModeRecibos;
    JDesktopPane jDesktopPane;

    public ConsultaContratosController(ConsultaContratoView view, JDesktopPane jDesktopPane) {

        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        contratosDAO = applicationContext.getBean(IContratoDAO.class);
        recibosDAO = applicationContext.getBean(IRecibosDAO.class);

        this.view = view;
        this.jDesktopPane = jDesktopPane;
        this.jDesktopPane.add(this.view);
        inicia();
    }

    private void inicia() {

        this.view.btnEliminarRecibo.setActionCommand("btnEliminarRecibo");
        this.view.btnModificarRecibo.setActionCommand("btnModificarRecibo");
        this.view.btnPagarRecibo.setActionCommand("btnPagarRecibo");
        this.view.btnNuevoContrato.setActionCommand("btnNuevoContrato");
        this.view.btnNuevoRecibo.setActionCommand("btnNuevoRecibo");
        this.view.btnModificarContrato.setActionCommand("btnModificarContrato");
        this.view.txtBusqueda.setActionCommand("txtBusqueda");

        this.view.btnEliminarRecibo.addActionListener(this);
        this.view.btnModificarRecibo.addActionListener(this);
        this.view.btnPagarRecibo.addActionListener(this);
        this.view.btnNuevoContrato.addActionListener(this);
        this.view.btnNuevoRecibo.addActionListener(this);
        this.view.btnModificarContrato.addActionListener(this);
        this.view.txtBusqueda.addActionListener(this);

        configTblContrato();
        configTblRecibos();
    }

    public void go() {
        this.view.setVisible(true);
        this.view.toFront();

    }

    private void configTblContrato() {

        ((DefaultTableCellRenderer) view.tblContratos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listContratos = new ArrayList<Contrato>();
        listContratos.addAll(contratosDAO.findAll());

        tableModelContratos = new ContratosTableModel(listContratos);
        view.tblContratos.setModel(tableModelContratos);
        view.tblContratos.getColumn("Fecha inicio").setCellRenderer(new MeDateCellRenderer());
        view.tblContratos.getColumn("Fecha fin").setCellRenderer(new MeDateCellRenderer());
        view.tblContratos.getColumn("Fecha extension").setCellRenderer(new MeDateCellRenderer());
        view.tblContratos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        int[] anchos = {5, 200, 50, 50, 50, 50, 50};

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
                    listRecibos.clear();
                    listRecibos.addAll(listContratos.get(view.tblContratos.getSelectedRow()).getRecibosList());
                    tableModeRecibos.fireTableDataChanged();
                } else {
                    view.btnModificarContrato.setEnabled(false);
                    view.btnNuevoRecibo.setEnabled(false);
                }
            }
        });

        view.tblContratos.setRowHeight(25);

        view.tblContratos.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    editaSeleccionado();
                }
            }
        });

    }

    void configTblRecibos() {
        ((DefaultTableCellRenderer) view.tblRecibos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listRecibos = new ArrayList<Recibos>();
        tableModeRecibos = new RecibosTableModel(listRecibos);

        view.tblRecibos.setModel(tableModeRecibos);
        view.tblRecibos.getColumn("Fecha vencimiento").setCellRenderer(new MeDateCellRenderer());
        view.tblRecibos.getColumn("Fecha pago").setCellRenderer(new MeDateCellRenderer());
        int[] anchos = {5, 5, 50, 50, 50, 50};

        for (int i = 0; i < view.tblRecibos.getColumnCount(); i++) {

            view.tblRecibos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
        view.tblRecibos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (view.tblRecibos.getSelectedRow() != -1) {
                    reciboSeleccionado = listRecibos.get(view.tblRecibos.getSelectedRow());
                    if (reciboSeleccionado.getPago() == false) {
                        habilitaBotonesrecibos();
                    } else {
                        deshabilitaBotonesrecibos();
                    }
                }
            }
        });

        view.tblRecibos.setRowHeight(25);

        view.tblRecibos.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    if (reciboSeleccionado.getPago() == false) {
                        pagarReciboSeleccionado();
                    }

                }
            }
        });

    }

    void deshabilitaBotonesrecibos() {
        view.btnPagarRecibo.setEnabled(false);
        view.btnEliminarRecibo.setEnabled(false);
        view.btnModificarRecibo.setEnabled(false);
    }

    void habilitaBotonesrecibos() {
        view.btnPagarRecibo.setEnabled(true);
        view.btnEliminarRecibo.setEnabled(true);
        view.btnModificarRecibo.setEnabled(true);
    }

    void pagarReciboSeleccionado() {

        if (JOptionPane.showConfirmDialog(view, "Confirma el pago del recibo seleccionado?", "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            new PagaRecibo(reciboSeleccionado).pagarRecibo();
            tableModeRecibos.fireTableDataChanged();
            MovimientodeCajaRecibo movimientodeCajaRecibo = new MovimientodeCajaRecibo(null, true, reciboSeleccionado, this);
            movimientodeCajaRecibo.setVisible(true);
            movimientodeCajaRecibo.toFront();
        }
    }

    public void actualizaTbl() {
        listContratos.clear();
        listContratos.addAll(contratosDAO.findAll());
        tableModelContratos.fireTableDataChanged();
    }

    void editaSeleccionado() {
        contratoSeleccionado = listContratos.get(view.tblContratos.getSelectedRow());
        ContratosController editaContrato = new ContratosController(new ContratosView(), this.jDesktopPane, contratoSeleccionado, this);
        editaContrato.go();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {

            case "btnNuevoContrato":
                ContratosView viewContrato = new ContratosView();
                ContratosController controller = new ContratosController(viewContrato, this.jDesktopPane);

                this.jDesktopPane.add(viewContrato);
                controller.go();

                break;

            case "btnModificarContrato":

                editaSeleccionado();

                break;
            case "txtBusqueda":

                listContratos.clear();
                listContratos.addAll(contratosDAO.findByInquilino(view.txtBusqueda.getText()));
                tableModelContratos.fireTableDataChanged();
                break;
            case "btnPagarRecibo":
                pagarReciboSeleccionado();
                break;

            case "btnNuevoRecibo":
                DetallesRecibo nuevoRecibo = new DetallesRecibo(null, true, this, contratoSeleccionado);
                nuevoRecibo.setVisible(true);
                nuevoRecibo.toFront();
                listRecibos.clear();
                listRecibos.addAll(recibosDAO.findByContrato(contratoSeleccionado));
                tableModeRecibos.fireTableDataChanged();
                deshabilitaBotonesrecibos();

                break;
            case "btnEliminarRecibo":
                if (JOptionPane.showConfirmDialog(view, "Confirma que desea elminar el recibo seleccionado?", "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    recibosDAO.delete(reciboSeleccionado);
                    listRecibos.clear();
                    listRecibos.addAll(recibosDAO.findByContrato(contratoSeleccionado));
                    tableModeRecibos.fireTableDataChanged();
                    deshabilitaBotonesrecibos();
                }
                break;
            case "btnModificarRecibo":
                DetallesRecibo modificarRecibo = new DetallesRecibo(null, true, this, reciboSeleccionado);
                modificarRecibo.setVisible(true);
                modificarRecibo.toFront();
                listRecibos.clear();
                listRecibos.addAll(recibosDAO.findByContrato(contratoSeleccionado));
                tableModeRecibos.fireTableDataChanged();
                deshabilitaBotonesrecibos();

                break;

            default:
                throw new AssertionError();
        }
    }

}
