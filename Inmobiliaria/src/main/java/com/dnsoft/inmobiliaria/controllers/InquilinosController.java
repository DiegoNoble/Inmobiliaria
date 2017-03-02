/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.beans.Inquilino;
import com.dnsoft.inmobiliaria.daos.IInquilinoDAO;
import com.dnsoft.inmobiliaria.models.InquilinosTableModel;
import com.dnsoft.inmobiliaria.views.InquilinosView;
import com.dnsoft.inmobiliaria.views.detalles.DetalleInquilino;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
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
public class InquilinosController implements ActionListener {

    ApplicationContext applicationContext;
    public InquilinosView view;
    InquilinosTableModel tableModel;
    ListSelectionModel listModel;
    List<Inquilino> listInquilinos;
    IInquilinoDAO inquilinosDAO;
    ContratosController contratosController;
    Inquilino inquilinoSeleccionado;
    ListSelectionModel selectionModel;

    public InquilinosController(InquilinosView view) {

        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        inquilinosDAO = applicationContext.getBean(IInquilinoDAO.class);

        this.view = view;
        this.view.btnAsignar.setVisible(false);
        inicia();
    }

    public InquilinosController(InquilinosView view, ContratosController contratosController) {

        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        inquilinosDAO = applicationContext.getBean(IInquilinoDAO.class);

        this.contratosController = contratosController;
        this.view = view;
        this.view.btnAsignar.setVisible(true);
        view.btnAsignar.setEnabled(false);

        inicia();
    }

    private void inicia() {

        this.view.btnVolver.setActionCommand("btnVolver");
        this.view.btnAsignar.setActionCommand("btnAsignar");
        this.view.btnNuevo.setActionCommand("btnNuevo");
        this.view.btnEditar.setActionCommand("btnEditar");
        this.view.txtBusqueda.setActionCommand("txtBusqueda");

        this.view.btnVolver.addActionListener(this);
        this.view.btnAsignar.addActionListener(this);
        this.view.btnNuevo.addActionListener(this);
        this.view.btnEditar.addActionListener(this);
        this.view.txtBusqueda.addActionListener(this);

        TableModel();
    }

    public void go() {
        this.view.setVisible(true);
        this.view.toFront();

    }

    private void TableModel() {

        ((DefaultTableCellRenderer) view.tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listInquilinos = new ArrayList<Inquilino>();
        listInquilinos.addAll(inquilinosDAO.findAll());

        tableModel = new InquilinosTableModel(listInquilinos);
        view.tbl.setModel(tableModel);

        //clientesView.tblClientes.getColumn("Nombre").setCellRenderer(new MeDefaultCellRenderer());
        //clientesView.tblClientes.getColumn("Apellido").setCellRenderer(new MeDefaultCellRenderer());
        view.tbl.setRowHeight(25);

        view.tbl.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    view.btnAsignar.setEnabled(true);
                    editaSeleccionado();

                }
            }
        });
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
        DetalleInquilino editaInquilino = new DetalleInquilino(null, true, inquilinosDAO, this, inquilinoSeleccionado);
        editaInquilino.setVisible(true);
        editaInquilino.toFront();
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

            case "btnNuevo":

                DetalleInquilino nuevoInquilino = new DetalleInquilino(null, true, inquilinosDAO, this);
                nuevoInquilino.setVisible(true);
                nuevoInquilino.toFront();

                break;

            case "btnEditar":
                editaSeleccionado();

                break;
            case "txtBusqueda":

                listInquilinos.clear();
                listInquilinos.addAll(inquilinosDAO.findInquilino(view.txtBusqueda.getText()));
                tableModel.fireTableDataChanged();
                break;
            case "btnAsignar":

                contratosController.setInquilinoSeleccionado(inquilinoSeleccionado);
                
                break;
            case "btnVolver":
                view.dispose();

                break;
            default:
                throw new AssertionError();
        }
    }

}
