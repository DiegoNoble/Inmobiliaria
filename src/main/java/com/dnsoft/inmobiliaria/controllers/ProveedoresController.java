/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.beans.Proveedor;
import com.dnsoft.inmobiliaria.daos.IProveedorDAO;
import com.dnsoft.inmobiliaria.models.ProveedoresTableModel;
import com.dnsoft.inmobiliaria.utils.ButtonColumnEditar;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ExportarDatosExcel;
import com.dnsoft.inmobiliaria.views.ProveedorDetallesDlg;
import com.dnsoft.inmobiliaria.views.ProveedoresDlg;
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

/**
 *
 * @author Diego Noble
 */
public class ProveedoresController implements ActionListener {

    Container container;
    public ProveedoresDlg view;
    ProveedoresTableModel tableModel;
    ListSelectionModel listModel;
    List<Proveedor> listProveedores;
    IProveedorDAO proveedorsDAO;
    Proveedor proveedorSeleccionado;
    ListSelectionModel selectionModel;

    public ProveedoresController(ProveedoresDlg view) {

        this.container = Container.getInstancia();
        proveedorsDAO = container.getBean(IProveedorDAO.class);

        this.view = view;
        this.view.btnAsignar.setVisible(false);
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
                //contratosController.setProveedorSeleccionado(proveedorSeleccionado);
                view.dispose();
            }
        });
        view.btnNuevo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                ProveedorDetallesDlg nuevoProveedor = new ProveedorDetallesDlg(null, true);
                nuevoProveedor.setVisible(true);
                nuevoProveedor.toFront();
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
                    ExportarDatosExcel exportar = new ExportarDatosExcel(view.tbl, "Proveedors");
                    exportar.exportarJTableExcel();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Error al exportar datos " + ex, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void TableModel() {

        ((DefaultTableCellRenderer) view.tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listProveedores = new ArrayList<>();
        listProveedores.addAll(proveedorsDAO.findAll());

        tableModel = new ProveedoresTableModel(listProveedores);
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
                    proveedorSeleccionado = listProveedores.get(view.tbl.getSelectedRow());
                    view.btnAsignar.setEnabled(true);
                } else {
                    view.btnAsignar.setEnabled(false);
                }
            }
        });

    }

    void editaSeleccionado() {
        proveedorSeleccionado = listProveedores.get(view.tbl.getSelectedRow());
        ProveedorDetallesDlg editaProveedor = new ProveedorDetallesDlg(null, true, proveedorSeleccionado);
        editaProveedor.setVisible(true);
        editaProveedor.toFront();
        actualizaTbl();
    }

    public void actualizaTbl() {
        listProveedores.clear();
        listProveedores.addAll(proveedorsDAO.findAll());
        tableModel.fireTableDataChanged();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "txtBusqueda":
                listProveedores.clear();
                listProveedores.addAll(proveedorsDAO.findProveedor(view.txtBusqueda.getText()));
                tableModel.fireTableDataChanged();
                break;
            default:
                throw new AssertionError();
        }
    }

}
