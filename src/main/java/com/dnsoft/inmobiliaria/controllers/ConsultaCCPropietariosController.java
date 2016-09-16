/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.Renderers.TableRendererColorActivo;
import com.dnsoft.inmobiliaria.beans.CCPropietario;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.Parametros;
import com.dnsoft.inmobiliaria.beans.Propietario;
import com.dnsoft.inmobiliaria.beans.Rubro;
import com.dnsoft.inmobiliaria.daos.ICCPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.IParametrosDAO;
import com.dnsoft.inmobiliaria.daos.IPropietarioDAO;
import com.dnsoft.inmobiliaria.models.PropietariosTableModel;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.views.ConsultaCCPropietarioView;
import com.dnsoft.inmobiliaria.views.DetalleMovimientosCCPropietario;
import com.dnsoft.inmobiliaria.views.PropietariosDetalleDlg;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ConsultaCCPropietariosController implements ActionListener {

    Container container;
    ControlDeCajaController cajaController;
    Propietario propietarioSeleccionado;
    List<Propietario> listPropietarios;
    IPropietarioDAO propietarioDAO;
    ICCPropietarioDAO cCPropietarioDAO;
    PropietariosTableModel tableModelPropietarios;
    ConsultaCCPropietarioView view;
    Rubro rubro;
    Parametros parametros;
    IParametrosDAO parametrosDAO;

    public ConsultaCCPropietariosController(ConsultaCCPropietarioView view) {

        this.view = view;

        inicio();
    }

    public ConsultaCCPropietariosController(ConsultaCCPropietarioView view, Rubro rubro, ControlDeCajaController cajaController) {

        this.view = view;
        this.rubro = rubro;
        this.cajaController = cajaController;
        inicio();

    }

    public void go() {
        try {
            this.view.setVisible(true);
            this.view.toFront();
            view.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(ConsultaCCPropietariosController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void saldos() {
        DecimalFormat formatter = new DecimalFormat("###,###,###.00");
        BigDecimal saldoPesos = new BigDecimal(BigInteger.ZERO);
        BigDecimal saldoDolares = new BigDecimal(BigInteger.ZERO);;
        for (Propietario propietario : listPropietarios) {
            CCPropietario ccPesos = cCPropietarioDAO.findUltimoMovimiento(Moneda.PESOS, propietario);
            if (ccPesos != null) {
                saldoPesos = saldoPesos.add(ccPesos.getSaldo());
            }
            CCPropietario ccDolares = cCPropietarioDAO.findUltimoMovimiento(Moneda.DOLARES, propietario);
            if (ccDolares != null) {
                saldoDolares = saldoDolares.add(ccDolares.getSaldo());
            }
        }
        view.txtDolares.setText(formatter.format(saldoDolares));
        view.txtPesos.setText(formatter.format(saldoPesos));
    }

    final void inicio() {
        this.container = Container.getInstancia();
        PromptSupport.setPrompt("Buscar por nombre, apellido o documento", view.txtBusqueda);
        this.view.txtBusqueda.setActionCommand("txtBusqueda");
        this.view.txtBusqueda.addActionListener(this);
        propietarioDAO = container.getBean(IPropietarioDAO.class);
        cCPropietarioDAO = container.getBean(ICCPropietarioDAO.class);
        parametrosDAO = container.getBean(IParametrosDAO.class);
        parametros = parametrosDAO.findAll().get(0);
        configuraTblPropietarios();
        buscarPropietarios();
        accionesBotones();
        saldos();
    }

    final void configuraTblPropietarios() {
        ((DefaultTableCellRenderer) view.tblPropietario.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listPropietarios = new ArrayList<>();

        tableModelPropietarios = new PropietariosTableModel(listPropietarios);
        view.tblPropietario.setModel(tableModelPropietarios);
        view.tblPropietario.setRowHeight(25);
        view.tblPropietario.getColumn("Activo").setMaxWidth(0);
        view.tblPropietario.getColumn("Activo").setMinWidth(0);
        view.tblPropietario.getColumn("Activo").setPreferredWidth(0);
        view.tblPropietario.getColumn("Activo").setWidth(0);
        view.tblPropietario.setDefaultRenderer(Object.class, new TableRendererColorActivo(2));
        ListSelectionModel listModel = view.tblPropietario.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (view.tblPropietario.getSelectedRow() != -1) {
                    view.btnCuenta.setEnabled(true);
                    view.btnPropietario.setEnabled(true);
                } else {
                    view.btnCuenta.setEnabled(false);
                    view.btnPropietario.setEnabled(false);
                }
            }
        });

        view.tblPropietario.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    detallesCCPropietario();
                }
            }
        });

    }

    void buscarPropietarios() {
        listPropietarios.clear();
        listPropietarios.addAll(propietarioDAO.findPropietario(view.txtBusqueda.getText()));
        tableModelPropietarios.fireTableDataChanged();
    }

    void detallesCCPropietario() {

        propietarioSeleccionado = listPropietarios.get(view.tblPropietario.getSelectedRow());
        DetalleMovimientosCCPropietario movimientosCCPropietario = new DetalleMovimientosCCPropietario(null, false);
        DetalleCCPropietariosController controller = new DetalleCCPropietariosController(movimientosCCPropietario, propietarioSeleccionado);
        controller.go();

    }

    void verPropietarioSeleccionado() {
        try {
            propietarioSeleccionado = propietarioDAO.findByPropietarioEager(listPropietarios.get(view.tblPropietario.getSelectedRow()).getId());
            PropietariosDetalleDlg propietario = new PropietariosDetalleDlg(null, true, propietarioSeleccionado);
            propietario.setVisible(true);
            propietario.toFront();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    void accionesBotones() {
        view.botonVolver1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                ConsultaCCPropietariosController.this.view.dispose();
            }
        });

        view.btnCuenta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                detallesCCPropietario();
            }
        });

        view.btnPropietario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                verPropietarioSeleccionado();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e
    ) {
        String comando = e.getActionCommand();

        switch (comando) {

            case "txtBusqueda":
                buscarPropietarios();
                break;

            default:
                throw new AssertionError();
        }
    }

}
