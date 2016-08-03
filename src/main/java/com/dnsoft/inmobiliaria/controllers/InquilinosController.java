/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.Renderers.TableRendererColorActivo;
import com.dnsoft.inmobiliaria.beans.Inquilino;
import com.dnsoft.inmobiliaria.daos.IInquilinoDAO;
import com.dnsoft.inmobiliaria.models.InquilinosTableModel;
import com.dnsoft.inmobiliaria.utils.ButtonColumnDetalles;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ExportarDatosExcel;
import com.dnsoft.inmobiliaria.views.ConsultaContratosInternal;
import com.dnsoft.inmobiliaria.views.InquilinoDetallesDlg;
import com.dnsoft.inmobiliaria.views.InquilinosDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
public class InquilinosController implements ActionListener {

    Container container;
    public InquilinosDialog view;
    InquilinosTableModel tableModel;
    ListSelectionModel listModel;
    List<Inquilino> listInquilinos;
    IInquilinoDAO inquilinosDAO;
    ContratosController contratosController;
    Inquilino inquilinoSeleccionado;
    JDesktopPane desktopPane;

    public InquilinosController(InquilinosDialog view, JDesktopPane desktopPane) {

        this.container = Container.getInstancia();
        inquilinosDAO = container.getBean(IInquilinoDAO.class);
        this.desktopPane = desktopPane;
        this.view = view;
        this.view.btnAsignar.setVisible(false);
        inicia();
    }

    public InquilinosController(InquilinosDialog view, ContratosController contratosController) {

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
        
         view.btnModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                editaSeleccionado();

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

        view.btnEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int c = JOptionPane.showConfirmDialog(view, "Confirma eliminación del cliente: " + inquilinoSeleccionado.toString(), "Confirmación", JOptionPane.YES_NO_OPTION);
                if (c == 0) {
                    try {
                        inquilinosDAO.delete(inquilinoSeleccionado);
                        actualizaTbl();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "No es posible eliminar el cliente", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });
        view.btnActivarInactivar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int c = JOptionPane.showConfirmDialog(view, "Confirma inactivacion del cliente: " + inquilinoSeleccionado.toString(), "Confirmación", JOptionPane.YES_NO_OPTION);
                if (c == 0) {
                    if (inquilinoSeleccionado.getActivo() == Boolean.FALSE) {
                        inquilinoSeleccionado.setActivo(Boolean.TRUE);
                    } else {
                        inquilinoSeleccionado.setActivo(Boolean.FALSE);
                    }
                    inquilinosDAO.save(inquilinoSeleccionado);
                    actualizaTbl();

                }
                actualizaTbl();
            }
        });
    }

    private void TableModel() {

        ((DefaultTableCellRenderer) view.tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listInquilinos = new ArrayList<Inquilino>();
        listInquilinos.addAll(inquilinosDAO.findAllNombreDocumento());

        tableModel = new InquilinosTableModel(listInquilinos);
        view.tbl.setModel(tableModel);
        
        int[] anchos = {1,300, 100, 100,100,100,10};

            for (int i = 0; i < view.tbl.getColumnCount(); i++) {

                view.tbl.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

            }
            
        view.tbl.setDefaultRenderer(Object.class, new TableRendererColorActivo(3));

        view.tbl.setRowHeight(25);

        view.tbl.getColumn("Activo").setMaxWidth(0);
        view.tbl.getColumn("Activo").setMinWidth(0);
        view.tbl.getColumn("Activo").setPreferredWidth(0);
        view.tbl.getColumn("Activo").setWidth(0);
        listModel = view.tbl.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (view.tbl.getSelectedRow() != -1) {
                    inquilinoSeleccionado = inquilinosDAO.findByInquilinoEager(listInquilinos.get(view.tbl.getSelectedRow()).getId());
                    view.btnAsignar.setEnabled(true);
                    view.btnModificar.setEnabled(true);
                    view.btnEliminar.setEnabled(true);
                    view.btnActivarInactivar.setEnabled(true);
                } else {
                    view.btnAsignar.setEnabled(false);
                    view.btnModificar.setEnabled(false);
                    view.btnEliminar.setEnabled(false);
                    view.btnActivarInactivar.setEnabled(false);
                }
            }
        });
        new ButtonColumnDetalles(view.tbl, 6) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                buscarContratos();
            }

            private void buscarContratos() {
                
                ConsultaContratosInternal contratos = new ConsultaContratosInternal();
                ConsultaContratosController controller = new ConsultaContratosController(contratos,desktopPane);
                contratos.txtBusqueda.setText(inquilinoSeleccionado.toString());
                desktopPane.add(contratos);
                controller.buscaContratos();
                controller.go();
                
            }
        };
        view.tbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    editaSeleccionado();
                }
            }
        });

    }

    void editaSeleccionado() {
        //inquilinoSeleccionado = listInquilinos.get(view.tbl.getSelectedRow());
        InquilinoDetallesDlg editaInquilino = new InquilinoDetallesDlg(null, true, inquilinoSeleccionado);
        editaInquilino.setVisible(true);
        editaInquilino.toFront();
        actualizaTbl();
    }

    public void actualizaTbl() {
        listInquilinos.clear();
        listInquilinos.addAll(inquilinosDAO.findAllNombreDocumento());
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
