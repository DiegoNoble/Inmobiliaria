/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.Renderers.MeTimeCellRenderer;
import com.dnsoft.inmobiliaria.beans.Caja;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.Parametros;
import com.dnsoft.inmobiliaria.beans.TipoDeCaja;
import com.dnsoft.inmobiliaria.beans.TipoMovimiento;
import com.dnsoft.inmobiliaria.daos.ICajaDAO;
import com.dnsoft.inmobiliaria.daos.IParametrosDAO;
import com.dnsoft.inmobiliaria.daos.ITipoDeCajaDAO;
import com.dnsoft.inmobiliaria.models.CajaTableModel;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ExportarDatosExcel;
import com.dnsoft.inmobiliaria.views.ConsultaCCPropietarioView;
import com.dnsoft.inmobiliaria.views.ConsultaContratosDialog;
import com.dnsoft.inmobiliaria.views.ControlDeCajaView;
import com.dnsoft.inmobiliaria.views.InmueblesDialog;
import com.dnsoft.inmobiliaria.views.DetalleMovimiento;
import com.dnsoft.inmobiliaria.views.DetalleMovimientoCajaInmobiliaria;
import com.dnsoft.inmobiliaria.views.TransferenciasDeCaja;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Diego Noble
 */
public class ControlDeCajaController implements ActionListener {

    Container container;
    ControlDeCajaView view;
    CajaTableModel tableModel;
    ListSelectionModel listModel;
    List<Caja> listMovimientos;
    ICajaDAO cajaDAO;
    IParametrosDAO parametrosDAO;
    ITipoDeCajaDAO tipoDeCajaDAO;
    Parametros parametros;
    Caja movimientoSeleccionado;
    JDesktopPane desktopPane;

    public ControlDeCajaController(ControlDeCajaView view, JDesktopPane desktopPane) {

        this.container = Container.getInstancia();
        this.desktopPane = desktopPane;
        this.view = view;
        inicia();
    }

    private void inicia() {

        accionesBotones();
        this.view.dpFecha.setDate(new Date());
        this.view.dpFecha.setEditable(false);
        cajaDAO = container.getBean(ICajaDAO.class);
        parametrosDAO = container.getBean(IParametrosDAO.class);
        tipoDeCajaDAO = container.getBean(ITipoDeCajaDAO.class);
        parametros = parametrosDAO.findAll().get(0);
        TableModel();
        cargaMonedas();
        cargaTiposDeCaja();
        saldos();
        actualizaTbl();
    }

    public void go() {
        view.btnAlquileresGastos.setActionCommand("btnAlquileresGastos");
        view.btnAlquileresGastos.addActionListener(this);

        view.btnAportes.setActionCommand("btnAportes");
        view.btnAportes.addActionListener(this);

        view.btnIRPF_IVA.setActionCommand("btnIRPF_IVA");
        view.btnIRPF_IVA.addActionListener(this);

        view.btnImpuestos.setActionCommand("btnImpuestos");
        view.btnImpuestos.addActionListener(this);

        view.btnInsumosCocinaLimpieza.setActionCommand("btnInsumosCocinaLimpieza");
        view.btnInsumosCocinaLimpieza.addActionListener(this);

        view.btnInsumosOficina.setActionCommand("btnInsumosOficina");
        view.btnInsumosOficina.addActionListener(this);

        view.btnObrasMantenimiento.setActionCommand("btnObrasMantenimiento");
        view.btnObrasMantenimiento.addActionListener(this);

        view.btnPagoFacturas.setActionCommand("btnPagoFacturas");
        view.btnPagoFacturas.addActionListener(this);

        view.btnRetirosPatronales.setActionCommand("btnRetirosPatronales");
        view.btnRetirosPatronales.addActionListener(this);

        view.btnRetirosPropietarios.setActionCommand("btnRetirosPropietarios");
        view.btnRetirosPropietarios.addActionListener(this);

        view.btnVales.setActionCommand("btnVales");
        view.btnVales.addActionListener(this);

        view.btnSueldos.setActionCommand("btnSueldos");
        view.btnSueldos.addActionListener(this);

        view.btnTransferencias.setActionCommand("btnTransferencias");
        view.btnTransferencias.addActionListener(this);

        view.btnGastosPropietarios.setActionCommand("btnPagoDeudaPropietarios");
        view.btnGastosPropietarios.addActionListener(this);

        this.view.setVisible(true);
        this.view.toFront();

    }

    void saldos() {
        try {
            BigDecimal saldo = cajaDAO.findUltimoMovimiento((Moneda) view.cbMoneda.getSelectedItem(), (TipoDeCaja) view.cbTipoDeCaja.getSelectedItem()).getSaldo();
            if (listMovimientos.isEmpty()) {
                view.txtSaldoAnterior.setText(saldo.toString());
            } else {
                Caja ultmoMovimientoList = listMovimientos.get(listMovimientos.size() - 1);
                BigDecimal saldoAnterior = (ultmoMovimientoList.getSaldo().subtract(ultmoMovimientoList.getEntrada())).add(ultmoMovimientoList.getSalida());
                view.txtSaldoAnterior.setText(saldoAnterior.toString());
            }

            view.txtSaldo.setText(saldo.toString());
        } catch (Exception e) {
            view.txtSaldoAnterior.setText("0.00");
            view.txtSaldo.setText("0.00");
        }
    }

    void accionesBotones() {

        view.btnEntradas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                view.menuEntradas.show(view.btnEntradas, view.btnEntradas.getWidth() / 2, view.btnEntradas.getHeight() / 2);

                actualizaTbl();
            }
        });

        view.btnSalidas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                view.menuSalidas.show(view.btnSalidas, view.btnSalidas.getWidth() / 2, view.btnSalidas.getHeight() / 2);

                actualizaTbl();
            }
        });
        view.btnExcel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                try {
                    ExportarDatosExcel exportar = new ExportarDatosExcel(view.tblMovimientos, "Movimientos de Caja");
                    exportar.exportarJTableExcel();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Error al exportar datos " + ex, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    final void cargaMonedas() {
        view.cbMoneda.addItem(Moneda.PESOS);
        view.cbMoneda.addItem(Moneda.DOLARES);
        view.cbMoneda.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                actualizaTbl();
            }
        });
    }

    private void TableModel() {

        ((DefaultTableCellRenderer) view.tblMovimientos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listMovimientos = new ArrayList<>();
        listMovimientos.addAll(cajaDAO.findByFechaAfterOrFechaEqualAndMonedaAndTipoDeCajaOrderByFechaDesc(view.dpFecha.getDate(), (Moneda) view.cbMoneda.getSelectedItem(), (TipoDeCaja) view.cbTipoDeCaja.getSelectedItem()));

        tableModel = new CajaTableModel(listMovimientos);
        view.tblMovimientos.setModel(tableModel);
        view.tblMovimientos.getColumn("Fecha").setCellRenderer(new MeTimeCellRenderer());
        view.tblMovimientos.setRowHeight(25);
        int[] anchos = {50, 100, 20, 20, 20};

        for (int i = 0; i < view.tblMovimientos.getColumnCount(); i++) {

            view.tblMovimientos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }

        view.tblMovimientos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    detalles();

                }
            }
        });

    }

    void detalles() {

        movimientoSeleccionado = listMovimientos.get(view.tblMovimientos.getSelectedRow());
        DetalleMovimiento detalle = new DetalleMovimiento(null, true, movimientoSeleccionado);
        detalle.setVisible(true);
        detalle.toFront();

    }

    public void actualizaTbl() {
        listMovimientos.clear();
        listMovimientos.addAll(cajaDAO.findByFechaAfterOrFechaEqualAndMonedaAndTipoDeCajaOrderByFechaDesc(view.dpFecha.getDate(), (Moneda) view.cbMoneda.getSelectedItem(), (TipoDeCaja) view.cbTipoDeCaja.getSelectedItem()));
        tableModel.fireTableDataChanged();
        saldos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "btnAlquileresGastos":
                ConsultaContratosDialog consultaContratosDialog = new ConsultaContratosDialog(null, false);
                ConsultaContratosController controller = new ConsultaContratosController(consultaContratosDialog, this);
                controller.go();
                break;
            case "btnAportes":
                DetalleMovimientoCajaInmobiliaria aportes = new DetalleMovimientoCajaInmobiliaria(null, true, parametros.getRubroAportes(), TipoMovimiento.ENTRADA, this, (TipoDeCaja) view.cbTipoDeCaja.getSelectedItem());
                aportes.setVisible(true);
                aportes.toFront();

                break;
            case "btnIRPF_IVA":

                break;
            case "btnImpuestos":
                DetalleMovimientoCajaInmobiliaria impuestos = new DetalleMovimientoCajaInmobiliaria(null, true, parametros.getRubroImpuestos(), TipoMovimiento.SALIDA, this, (TipoDeCaja) view.cbTipoDeCaja.getSelectedItem());
                impuestos.setVisible(true);
                impuestos.toFront();
                break;
            case "btnInsumosCocinaLimpieza":
                DetalleMovimientoCajaInmobiliaria insumosCocinaLimpieza = new DetalleMovimientoCajaInmobiliaria(null, true, parametros.getRubroInsumosCocinaLimpieza(), TipoMovimiento.SALIDA, this, (TipoDeCaja) view.cbTipoDeCaja.getSelectedItem());
                insumosCocinaLimpieza.setVisible(true);
                insumosCocinaLimpieza.toFront();
                break;
            case "btnInsumosOficina":
                DetalleMovimientoCajaInmobiliaria insumosOficina = new DetalleMovimientoCajaInmobiliaria(null, true, parametros.getRubroInsumosOficina(), TipoMovimiento.SALIDA, this, (TipoDeCaja) view.cbTipoDeCaja.getSelectedItem());
                insumosOficina.setVisible(true);
                insumosOficina.toFront();
                break;
            case "btnObrasMantenimiento":
                InmueblesDialog inmueblesDialog = new InmueblesDialog(null, true);
                InmueblesController inmueblesController = new InmueblesController(inmueblesDialog, this, (TipoDeCaja) view.cbTipoDeCaja.getSelectedItem());
                inmueblesController.go();
                actualizaTbl();

                break;
            case "btnPagoFacturas":
                DetalleMovimientoCajaInmobiliaria PagoFacturas = new DetalleMovimientoCajaInmobiliaria(null, true, parametros.getRubroPagoFacturas(), TipoMovimiento.SALIDA, this, (TipoDeCaja) view.cbTipoDeCaja.getSelectedItem());
                PagoFacturas.setVisible(true);
                PagoFacturas.toFront();
                break;
            case "btnSueldos":
                DetalleMovimientoCajaInmobiliaria Sueldos = new DetalleMovimientoCajaInmobiliaria(null, true, parametros.getRubroSueldos(), TipoMovimiento.SALIDA, this, (TipoDeCaja) view.cbTipoDeCaja.getSelectedItem());
                Sueldos.setVisible(true);
                Sueldos.toFront();
                break;
            case "btnTransferencias":
                TransferenciasDeCaja Transferencias = new TransferenciasDeCaja(null, true, parametros.getRubroTransferencias(), TipoMovimiento.SALIDA, this, (TipoDeCaja) view.cbTipoDeCaja.getSelectedItem());
                Transferencias.setVisible(true);
                Transferencias.toFront();
                break;
            case "btnVales":
                DetalleMovimientoCajaInmobiliaria Vales = new DetalleMovimientoCajaInmobiliaria(null, true, parametros.getRubroVales(), TipoMovimiento.SALIDA, this, (TipoDeCaja) view.cbTipoDeCaja.getSelectedItem());
                Vales.setVisible(true);
                Vales.toFront();
                break;
            case "btnRetirosPatronales":
                DetalleMovimientoCajaInmobiliaria btnRetirosPatronales = new DetalleMovimientoCajaInmobiliaria(null, true, parametros.getRubroRetirosPatronales(), TipoMovimiento.SALIDA, this, (TipoDeCaja) view.cbTipoDeCaja.getSelectedItem());
                btnRetirosPatronales.setVisible(true);
                btnRetirosPatronales.toFront();
                break;
            case "btnRetirosPropietarios":
                ConsultaCCPropietarioView ccpropietario = new ConsultaCCPropietarioView();
                ConsultaCCPropietariosController control = new ConsultaCCPropietariosController(ccpropietario, parametros.getRubroRetirosPropietarios(), this);
                this.desktopPane.add(ccpropietario);
                control.go();
                break;
            case "btnPagoDeudaPropietarios":
                ConsultaCCPropietarioView ccpropietarioCobroGastos = new ConsultaCCPropietarioView();
                ConsultaCCPropietariosController controllerCobroGastos = new ConsultaCCPropietariosController(ccpropietarioCobroGastos, parametros.getCobroDeudaPopietario(), this);
                this.desktopPane.add(ccpropietarioCobroGastos);
                controllerCobroGastos.go();
                actualizaTbl();
                break;
            default:
                throw new AssertionError();
        }
    }

    private void cargaTiposDeCaja() {
        view.cbTipoDeCaja.removeAllItems();
        for (TipoDeCaja tipoDeCaja : tipoDeCajaDAO.findAll()) {
            view.cbTipoDeCaja.addItem(tipoDeCaja);
        }
        view.cbTipoDeCaja.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                actualizaTbl();
            }
        });
    }
}
