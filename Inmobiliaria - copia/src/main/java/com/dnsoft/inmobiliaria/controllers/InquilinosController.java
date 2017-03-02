/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.beans.Inquilino;
import com.dnsoft.inmobiliaria.daos.IInquilinoDAO;
import com.dnsoft.inmobiliaria.models.InquilinosTableModel;
import com.dnsoft.inmobiliaria.utils.ButtonColumnEditar;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ExportarDatosExcel;
import com.dnsoft.inmobiliaria.views.InquilinosDlg;
import com.dnsoft.inmobiliaria.views.InquilinoDetallesDlg;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.jdesktop.swingx.prompt.PromptSupport;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Diego Noble
 */
public class InquilinosController implements ActionListener {

    Container container;
    public InquilinosDlg view;
    InquilinosTableModel tableModel;
    ListSelectionModel listModel;
    List<Inquilino> listInquilinos;
    IInquilinoDAO inquilinosDAO;
    ContratosController contratosController;
    Inquilino inquilinoSeleccionado;
    ListSelectionModel selectionModel;

    public InquilinosController(InquilinosDlg view) {

        this.container = Container.getInstancia();
        inquilinosDAO = container.getBean(IInquilinoDAO.class);

        this.view = view;
        this.view.btnAsignar.setVisible(false);
        inicia();
    }

    public InquilinosController(InquilinosDlg view, ContratosController contratosController) {

        this.container = Container.getInstancia();
        inquilinosDAO = container.getBean(IInquilinoDAO.class);

        this.contratosController = contratosController;
        this.view = view;
        this.view.btnAsignar.setVisible(true);
        this.view.btnExcel.setVisible(false);
        view.btnAsignar.setEnabled(false);

        inicia();
    }

    private void inicia() {

        this.view.txtBusqueda.setActionCommand("txtBusqueda");
        this.view.txtBusqueda.addActionListener(this);
        PromptSupport.setPrompt("Buscar por nombre, apellido o documento", view.txtBusqueda);
        TableModel();
        accionesBotones();
    }

    public void go() {
        this.view.setVisible(true);
        this.view.toFront();

    }

    void accionesBotones() {
        view.btnAsignar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                contratosController.setInquilinoSeleccionado(inquilinoSeleccionado);
                view.dispose();
            }
        });
        view.btnNuevo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                InquilinoDetallesDlg nuevoInquilino = new InquilinoDetallesDlg(null, true);
                nuevoInquilino.setVisible(true);
                nuevoInquilino.toFront();
                actualizaTbl();
            }
        });
        view.btnVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                view.dispose();

            }
        });
        view.btnExcel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                try {
                    ExportarDatosExcel exportar = new ExportarDatosExcel(view.tbl, "Inquilinos");
                    exportar.exportarJTableExcel();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Error al exportar datos " + ex, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void TableModel() {

        ((DefaultTableCellRenderer) view.tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listInquilinos = new ArrayList<>();
        listInquilinos.addAll(inquilinosDAO.findAll());

        tableModel = new InquilinosTableModel(listInquilinos);
        view.tbl.setModel(tableModel);

        new ButtonColumnEditar(view.tbl, 4) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                editaSeleccionado();
            }
        };
        view.tbl.setRowHeight(25);

        listModel = view.tbl.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (view.tbl.getSelectedRow() != -1) {
                    inquilinoSeleccionado = listInquilinos.get(view.tbl.getSelectedRow());
                    view.btnAsignar.setEnabled(true);
                } else {
                    view.btnAsignar.setEnabled(false);
                }
            }
        });

    }

    void editaSeleccionado() {
        inquilinoSeleccionado = listInquilinos.get(view.tbl.getSelectedRow());
        InquilinoDetallesDlg editaInquilino = new InquilinoDetallesDlg(null, true, inquilinoSeleccionado);
        editaInquilino.setVisible(true);
        editaInquilino.toFront();
        actualizaTbl();
    }

    public void actualizaTbl() {
        listInquilinos.clear();
        listInquilinos.addAll(inquilinosDAO.findAll());
        tableModel.fireTableDataChanged();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "txtBusqueda":
                listInquilinos.clear();
                listInquilinos.addAll(inquilinosDAO.findInquilino(view.txtBusqueda.getText()));
                tableModel.fireTableDataChanged();
                break;
            default:
                throw new AssertionError();
        }
    }

}
