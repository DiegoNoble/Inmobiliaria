/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.DestinoMoraEnum;
import com.dnsoft.inmobiliaria.beans.Inmueble;
import com.dnsoft.inmobiliaria.beans.Inquilino;
import com.dnsoft.inmobiliaria.beans.Monedas;
import com.dnsoft.inmobiliaria.beans.StatusInmueble;
import com.dnsoft.inmobiliaria.beans.TipoReajuste;
import com.dnsoft.inmobiliaria.daos.IContratoDAO;
import com.dnsoft.inmobiliaria.daos.IInmuebleDAO;
import com.dnsoft.inmobiliaria.daos.IMonedaDAO;
import com.dnsoft.inmobiliaria.daos.IRecibosDAO;
import com.dnsoft.inmobiliaria.daos.ITipoReajusteDAO;
import com.dnsoft.inmobiliaria.models.InmuebleTableModel;
import com.dnsoft.inmobiliaria.models.InquilinosTableModel;
import com.dnsoft.inmobiliaria.utils.CalculaRecibos;
import com.dnsoft.inmobiliaria.views.ContratosView;
import com.dnsoft.inmobiliaria.views.InmueblesView;
import com.dnsoft.inmobiliaria.views.InquilinosView;
import com.dnsoft.inmobiliaria.views.detalles.DetalleTipoReajuste;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Diego Noble
 */
public class ContratosController implements ActionListener {

    ApplicationContext applicationContext;
    public ContratosView view;
    InmuebleTableModel tableModelInmueble;
    InquilinosTableModel tableModelInquilino;
    ListSelectionModel listModel;
    Inmueble inmuebleSeleccionado;
    List<Inmueble> inmuebles;
    List<Inquilino> inquilinos;
    Inquilino inquilinoSeleccionado;
    IContratoDAO contratoDAO;
    IMonedaDAO monedaDAO;
    IInmuebleDAO inmuebleDAO;
    ITipoReajusteDAO tiporeajusteDAO;
    IRecibosDAO recibosDAO;
    JDesktopPane desktopPane;
    Contrato contratoSeleccionado;
    ConsultaContratosController consultaContratosController;

    public ContratosController(ContratosView view, JDesktopPane desktopPane) {

        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        contratoDAO = applicationContext.getBean(IContratoDAO.class);
        monedaDAO = applicationContext.getBean(IMonedaDAO.class);
        inmuebleDAO = applicationContext.getBean(IInmuebleDAO.class);
        tiporeajusteDAO = applicationContext.getBean(ITipoReajusteDAO.class);
        recibosDAO = applicationContext.getBean(IRecibosDAO.class);
        this.view = view;
        this.desktopPane = desktopPane;
        inicia();
        configTblInmueble();
        configTblInquilino();
        view.btnGuardarModificaciones.setVisible(false);
        view.btnGuardar.setVisible(true);
    }

    public ContratosController(ContratosView view, JDesktopPane desktopPane, Contrato contratoSeleccionado, ConsultaContratosController consultaContratosController) {

        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        contratoDAO = applicationContext.getBean(IContratoDAO.class);
        monedaDAO = applicationContext.getBean(IMonedaDAO.class);
        inmuebleDAO = applicationContext.getBean(IInmuebleDAO.class);
        tiporeajusteDAO = applicationContext.getBean(ITipoReajusteDAO.class);
        recibosDAO = applicationContext.getBean(IRecibosDAO.class);
        this.view = view;
        this.desktopPane = desktopPane;
        this.desktopPane.add(this.view);
        this.contratoSeleccionado = contratoSeleccionado;
        inicia();
        configTblInmueble();
        configTblInquilino();
        view.btnGuardarModificaciones.setVisible(true);
        view.btnGuardar.setVisible(false);
        this.consultaContratosController = consultaContratosController;

        view.dpInicio.setDate(contratoSeleccionado.getFechaInicio());
        view.dpFIn.setDate(contratoSeleccionado.getFechaFin());
        view.dpExtension.setDate(contratoSeleccionado.getFechaExtencion());
        view.txtValor.setText(contratoSeleccionado.getValorAlquiler().toString());
        view.cbMoneda.setSelectedItem(contratoSeleccionado.getMoneda());
        view.chbAsegurado.setSelected(contratoSeleccionado.getAsegurado());
        view.chbInquilinoAgenteRetencion.setSelected(contratoSeleccionado.getInquilinoAgenteRetencion());
        view.spComision.setValue(contratoSeleccionado.getPorcentajeComision());
        view.cbDestino.setSelectedItem(contratoSeleccionado.getDestinoMora());
        view.spPorcentajeMora.setValue(contratoSeleccionado.getPorcentajeMoraTotal());
        view.spMoraInmobiliaria.setValue(contratoSeleccionado.getPorcentajeMoraInmobiliaria());
        view.spMoraPropietarios.setValue(contratoSeleccionado.getPorcentajeMoraPropietarios());
        view.cbTipoReajustes.setSelectedItem(contratoSeleccionado.getTipoReajuste());
        view.spToleranciaMora.setValue(contratoSeleccionado.getToleranciaMora());
        inquilinos.add(this.contratoSeleccionado.getInquilino());
        inmuebles.add(this.contratoSeleccionado.getInmueble());
        tableModelInmueble.fireTableDataChanged();
        tableModelInquilino.fireTableDataChanged();
    }

    private void inicia() {

        this.view.btnGuardar.setActionCommand("btnGuardar");
        this.view.btnGuardarModificaciones.setActionCommand("btnGuardarModificaciones");
        this.view.btnNuevoTipo.setActionCommand("btNuevoTipoReajuste");
        this.view.btnSeleccionarInmuele.setActionCommand("seleccionarInmueble");
        this.view.btnSeleccionarInquilino.setActionCommand("seleccionarInquilino");
        this.view.btnQuitarInmuele.setActionCommand("quitarInmueble");
        this.view.btnQuitarInquilino.setActionCommand("quitarInquilino");

        this.view.btnNuevoTipo.addActionListener(this);
        this.view.btnGuardar.addActionListener(this);
        this.view.btnGuardarModificaciones.addActionListener(this);
        this.view.btnSeleccionarInmuele.addActionListener(this);
        this.view.btnSeleccionarInquilino.addActionListener(this);
        this.view.btnQuitarInmuele.addActionListener(this);
        this.view.btnQuitarInquilino.addActionListener(this);
        double min = 0.00;
        double value = 0.00;
        double max = 100;
        double stepSize = 0.01;
        view.spComision.setModel(new SpinnerNumberModel(value, min, max, stepSize));
        view.spMoraInmobiliaria.setModel(new SpinnerNumberModel(00.00, min, max, stepSize));
        view.spMoraPropietarios.setModel(new SpinnerNumberModel(00.00, min, max, stepSize));
        view.spPorcentajeMora.setModel(new SpinnerNumberModel(value, min, max, stepSize));

        view.setSize(desktopPane.getSize());
        fechas();
        configuraComboDestino();
        cargaMonedas();
        cargaTipoReajuste();
        configuraSpinner();
    }

    public void go() {
        this.view.setVisible(true);
        this.view.toFront();

    }

    void configuraSpinner() {
        view.spMoraInmobiliaria.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {

                Double nuevoValorInmobiliaria = Double.parseDouble(view.spMoraInmobiliaria.getModel().getValue().toString());

                view.spMoraPropietarios.setValue(100.00 - nuevoValorInmobiliaria);
            }
        });
        view.spMoraPropietarios.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {

                Double nuevoValorPropietarios = Double.parseDouble(view.spMoraPropietarios.getModel().getValue().toString());

                view.spMoraInmobiliaria.setValue(100.00 - nuevoValorPropietarios);
            }
        });
    }

    void configuraComboDestino() {

        view.spMoraInmobiliaria.setValue(0.00);
        view.spMoraPropietarios.setValue(100.00);
        view.spMoraInmobiliaria.setEnabled(false);
        view.spMoraPropietarios.setEnabled(false);

        view.cbDestino.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent ie) {
                if (view.cbDestino.getSelectedItem() == DestinoMoraEnum.PRORATEADO) {

                    view.spMoraInmobiliaria.setValue(50.00);
                    view.spMoraPropietarios.setValue(50.00);
                    view.spMoraInmobiliaria.setEnabled(true);
                    view.spMoraPropietarios.setEnabled(true);

                } else if (view.cbDestino.getSelectedItem() == DestinoMoraEnum.INMOBILIARIA) {

                    view.spMoraInmobiliaria.setValue(100.00);
                    view.spMoraPropietarios.setValue(0.00);
                    view.spMoraInmobiliaria.setEnabled(false);
                    view.spMoraPropietarios.setEnabled(false);

                } else if (view.cbDestino.getSelectedItem() == DestinoMoraEnum.PROPIETARIO) {

                    view.spMoraInmobiliaria.setValue(0.00);
                    view.spMoraPropietarios.setValue(100.00);
                    view.spMoraInmobiliaria.setEnabled(false);
                    view.spMoraPropietarios.setEnabled(false);
                }
            }
        });

    }

    void fechas() {
        view.dpInicio.setDate(new Date());

        Calendar c = Calendar.getInstance();
        c.setTime(view.dpInicio.getDate());
        c.add(Calendar.YEAR, 2);
        view.dpFIn.setDate(c.getTime());
        c.add(Calendar.YEAR, 1);
        view.dpExtension.setDate(c.getTime());
    }

    void cargaMonedas() {
        List<Monedas> monedas = monedaDAO.findAll();
        for (Monedas moneda : monedas) {
            view.cbMoneda.addItem(moneda);
        }
    }

    void cargaTipoReajuste() {
        view.cbTipoReajustes.removeAllItems();
        List<TipoReajuste> reajustes = tiporeajusteDAO.findAll();
        for (TipoReajuste tipo : reajustes) {
            view.cbTipoReajustes.addItem(tipo);
        }

    }

    private void configTblInmueble() {

        ((DefaultTableCellRenderer) view.tblInmueble.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        inmuebles = new ArrayList<Inmueble>();

        tableModelInmueble = new InmuebleTableModel(inmuebles);
        view.tblInmueble.setModel(tableModelInmueble);
        view.tblInmueble.setAutoCreateRowSorter(true);

        view.tblInmueble.setRowHeight(25);

    }

    private void configTblInquilino() {

        ((DefaultTableCellRenderer) view.tblInquilino.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        inquilinos = new ArrayList<Inquilino>();

        tableModelInquilino = new InquilinosTableModel(inquilinos);
        view.tblInquilino.setModel(tableModelInquilino);
        view.tblInquilino.setAutoCreateRowSorter(true);

        view.tblInquilino.setRowHeight(25);

    }

    public void setInmuebleSeleccionado(Inmueble inmuebleSeleccionado) {

        this.inmuebleSeleccionado = inmuebleSeleccionado;
        this.inmuebles.clear();
        this.inmuebles.add(inmuebleSeleccionado);
        this.tableModelInmueble.fireTableDataChanged();

    }

    public void setInquilinoSeleccionado(Inquilino inquilinoSeleccionado) {
        this.inquilinoSeleccionado = inquilinoSeleccionado;
        this.inquilinos.clear();
        this.inquilinos.add(inquilinoSeleccionado);
        this.tableModelInquilino.fireTableDataChanged();
    }

    void guardarContrato(Contrato contrato) {
        try {
            if (inmuebleSeleccionado == null || inquilinoSeleccionado == null) {
                JOptionPane.showMessageDialog(view, "Debe seleccionar un inquilino y un inmueble", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (JOptionPane.showConfirmDialog(view, "Confirma el contrato?", "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                    contrato.setFechaInicio(view.dpInicio.getDate());
                    contrato.setFechaFin(view.dpFIn.getDate());
                    contrato.setValorAlquiler(new BigDecimal(view.txtValor.getText()));
                    contrato.setMoneda((Monedas) view.cbMoneda.getSelectedItem());
                    contrato.setInquilino(inquilinoSeleccionado);
                    contrato.setAsegurado(view.chbAsegurado.isSelected());
                    contrato.setInquilinoAgenteRetencion(view.chbInquilinoAgenteRetencion.isSelected());
                    contrato.setPorcentajeComision((Double) view.spComision.getValue());
                    contrato.setDestinoMora((DestinoMoraEnum) view.cbDestino.getSelectedItem());
                    contrato.setFechaExtencion(view.dpExtension.getDate());
                    contrato.setPorcentajeMoraTotal((Double) view.spPorcentajeMora.getValue());
                    contrato.setPorcentajeMoraInmobiliaria((Double) view.spMoraInmobiliaria.getValue());
                    contrato.setPorcentajeMoraPropietarios((Double) view.spMoraPropietarios.getValue());
                    contrato.setTipoReajuste((TipoReajuste) view.cbTipoReajustes.getSelectedItem());
                    contrato.setToleranciaMora((Integer) view.spToleranciaMora.getValue());

                    contrato.setInmueble(inmuebleSeleccionado);

                    contratoDAO.save(contrato);
                    inmuebleSeleccionado.setStatuspropiedad(StatusInmueble.ALQUILADA);

                    recibosDAO.save(new CalculaRecibos().generaRecibos(contrato));

                    JOptionPane.showMessageDialog(view, "El contrato se genero correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                    if (consultaContratosController != null) {
                        consultaContratosController.actualizaTbl();
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Error de formatos: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {

            case "seleccionarInmueble":

                InmueblesView inmuebleView = new InmueblesView();
                InmueblesController inmuebleController = new InmueblesController(inmuebleView, this.desktopPane, this);

                this.desktopPane.add(inmuebleView);
                inmuebleController.go();
                break;

            case "quitarInmueble":
                inmuebles.clear();
                tableModelInmueble.fireTableDataChanged();
                break;
            case "seleccionarInquilino":

                InquilinosView inquilinosView = new InquilinosView();
                InquilinosController inquilinoController = new InquilinosController(inquilinosView, this);

                this.desktopPane.add(inquilinosView);
                inquilinoController.go();

                break;
            case "quitarInquilino":

                inquilinos.clear();
                tableModelInquilino.fireTableDataChanged();

                break;
            case "btnGuardar":
                guardarContrato(new Contrato());
                this.view.dispose();
                break;
            case "btnGuardarModificaciones":
                setInmuebleSeleccionado(contratoSeleccionado.getInmueble());
                setInquilinoSeleccionado(contratoSeleccionado.getInquilino());
                guardarContrato(contratoSeleccionado);
                break;
            case "btNuevoTipoReajuste":
                DetalleTipoReajuste nuevoTipoReajuste = new DetalleTipoReajuste(null, true, this.view);
                nuevoTipoReajuste.setVisible(true);
                nuevoTipoReajuste.toFront();
                cargaTipoReajuste();
                if (contratoSeleccionado != null) {
                    view.cbTipoReajustes.setSelectedItem(contratoSeleccionado.getTipoReajuste());
                }
                break;
            default:
                throw new AssertionError();
        }
    }

}
