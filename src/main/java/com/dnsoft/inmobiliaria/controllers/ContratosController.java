/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.views.ContratosDialog;
import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.DestinoAlquiler;
import com.dnsoft.inmobiliaria.beans.DestinoMoraEnum;
import com.dnsoft.inmobiliaria.beans.GarantiaAlquiler;
import com.dnsoft.inmobiliaria.beans.Iva;
import com.dnsoft.inmobiliaria.beans.Inmueble;
import com.dnsoft.inmobiliaria.beans.Inquilino;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.Situacion;
import com.dnsoft.inmobiliaria.beans.StatusInmueble;
import com.dnsoft.inmobiliaria.beans.TipoContrato;
import com.dnsoft.inmobiliaria.beans.TipoCotizacionContrato;
import com.dnsoft.inmobiliaria.beans.TipoPagoAlquiler;
import com.dnsoft.inmobiliaria.beans.TipoReajuste;
import com.dnsoft.inmobiliaria.daos.IContratoDAO;
import com.dnsoft.inmobiliaria.daos.IDestinoAlquilerDAO;
import com.dnsoft.inmobiliaria.daos.IGarantiaAlquilerDAO;
import com.dnsoft.inmobiliaria.daos.IInmuebleDAO;
import com.dnsoft.inmobiliaria.daos.IRecibosDAO;
import com.dnsoft.inmobiliaria.daos.IIvaDAO;
import com.dnsoft.inmobiliaria.daos.ITipoReajusteDAO;
import com.dnsoft.inmobiliaria.utils.CalculaRecibos;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ControlarEntradaTexto;
import com.dnsoft.inmobiliaria.utils.RollOverButtonsChicos;
import com.dnsoft.inmobiliaria.views.CRUDDestinoAlquiler;
import com.dnsoft.inmobiliaria.views.CRUDGarantiaAlquileres;
import com.dnsoft.inmobiliaria.views.InmueblesDialog;
import com.dnsoft.inmobiliaria.views.InmuebleDetallesDialog_new;
import com.dnsoft.inmobiliaria.views.InquilinoDetallesDlg;
import com.dnsoft.inmobiliaria.views.InquilinosDialog;
import com.dnsoft.inmobiliaria.views.TipoReajusteDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

/**
 *
 * @author Diego Noble
 */
public final class ContratosController implements ActionListener {

    Container container;
    public ContratosDialog view;
    JDesktopPane desktopPane;
    ListSelectionModel listModel;
    Inmueble inmuebleSeleccionado;
    Inquilino inquilinoSeleccionado;
    IContratoDAO contratoDAO;
    IInmuebleDAO inmuebleDAO;
    ITipoReajusteDAO tiporeajusteDAO;
    IGarantiaAlquilerDAO iGarantiaAlquilerDAO;
    IDestinoAlquilerDAO iDestinoAlquilerDAO;
    IRecibosDAO recibosDAO;
    IIvaDAO iivadao;
    Contrato contratoSeleccionado;
    Contrato respaldoContrato;
    Integer cantidadCuotas;
    ConsultaContratosController consultaContratosController;

    public ContratosController(ContratosDialog view, JDesktopPane desktopPane, ConsultaContratosController consultaContratosController) {

        this.container = Container.getInstancia();
        this.consultaContratosController = consultaContratosController;
        this.view = view;
        this.desktopPane = desktopPane;
        view.panelSituacion.setVisible(false);
        inicia();
        this.view.btnGuardarModificaciones.setVisible(false);
        this.view.btnGuardar.setVisible(true);
        view.cbTipoCotizacion.setVisible(false);
        view.txtCotizacion.setVisible(false);
        view.lblCotizacion.setVisible(false);
        view.txtCotizacion.setText("0.00");
        view.lblTipoCotizacion.setVisible(false);
        //documentListener(view.txtValorEntrega);
        habilitaCamposVenta();

        //view.txtValorTotal.setText("0.00");
        //view.txtValorEntrega.setText("0.00");
        // view.txtSaldo.setText("0.00");
    }

    public ContratosController(ContratosDialog view, Contrato contratoSeleccionado,ConsultaContratosController consultaContratosController) {

        this.container = Container.getInstancia();
        this.consultaContratosController = consultaContratosController;
        this.view = view;
        this.respaldoContrato = contratoSeleccionado;
        this.view.panelSituacion.setVisible(true);
        this.view.cbTipoContrato.setEnabled(false);
        inicia();
        this.contratoSeleccionado = contratoSeleccionado;
        this.view.btnGuardarModificaciones.setVisible(true);
        this.view.btnGuardar.setVisible(false);
        this.view.btnRemoveInmueble.setVisible(false);
        this.view.btnRemoveInquilino.setVisible(false);

        muestraDetalles();

        view.txtInquilino.setText(this.contratoSeleccionado.getInquilino().toString());
        view.txtInmueble.setText(this.contratoSeleccionado.getInmueble().toString());
    }

    private void inicia() {

        view.setLocationRelativeTo(null);
        this.view.btnNuevoTipoReajuste.setActionCommand("btNuevoTipoReajuste");
        this.view.btnNuevoTipoReajuste.addActionListener(this);

        this.view.btnNuevoTipoGarantia.setActionCommand("btNuevoTipoGarantia");
        this.view.btnNuevoTipoGarantia.addActionListener(this);

        this.view.btnInactivar.setActionCommand("btnInactivar");
        this.view.btnInactivar.addActionListener(this);

        this.view.btnNuevoTipoDestino.setActionCommand("btnNuevoTipoDestino");
        this.view.btnNuevoTipoDestino.addActionListener(this);

        this.view.btnInmueble.setActionCommand("btnInmueble");
        this.view.btnInmueble.addActionListener(this);

        this.view.btnInquilino.setActionCommand("btnInquilino");
        this.view.btnInquilino.addActionListener(this);

        this.view.btnRemoveInmueble.setActionCommand("btnRemoveInmueble");
        this.view.btnRemoveInmueble.addActionListener(this);

        this.view.btnRemoveInquilino.setActionCommand("btnRemoveInquilino");
        this.view.btnRemoveInquilino.addActionListener(this);

        this.view.cbTipoContrato.setActionCommand("cbTipoContrato");
        this.view.cbTipoContrato.addActionListener(this);

        this.view.cbTipoCotizacion.setActionCommand("cbTipoCotizacion");
        this.view.cbTipoCotizacion.addActionListener(this);

        this.view.cbMoneda.setActionCommand("cbMoneda");
        this.view.cbMoneda.addActionListener(this);

        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
        Character chsInt[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        view.txtValorTotal.setDocument(new ControlarEntradaTexto(10, chs));
        view.txtMontoGarantia.setDocument(new ControlarEntradaTexto(10, chs));

        view.txtValorCuotas1.setDocument(new ControlarEntradaTexto(10, chs));
        view.txtValorCuotas2.setDocument(new ControlarEntradaTexto(10, chs));
        view.txtValorCuotas3.setDocument(new ControlarEntradaTexto(10, chs));

        view.txtCantidadCuotas1.setDocument(new ControlarEntradaTexto(3, chsInt));
        view.txtCantidadCuotas2.setDocument(new ControlarEntradaTexto(3, chsInt));
        view.txtCantidadCuotas3.setDocument(new ControlarEntradaTexto(3, chsInt));
        documentListener(view.txtValorTotal);
        documentListener(view.txtValorAlquiler);
        documentListener(view.txtValorCuotas1);
        documentListener(view.txtValorCuotas2);
        documentListener(view.txtValorCuotas3);

        contratoDAO = container.getBean(IContratoDAO.class);
        inmuebleDAO = container.getBean(IInmuebleDAO.class);
        iDestinoAlquilerDAO = container.getBean(IDestinoAlquilerDAO.class);
        iGarantiaAlquilerDAO = container.getBean(IGarantiaAlquilerDAO.class);
        tiporeajusteDAO = container.getBean(ITipoReajusteDAO.class);
        recibosDAO = container.getBean(IRecibosDAO.class);
        iivadao = container.getBean(IIvaDAO.class);

        view.cbTipoPagoAlquiler.setModel(new DefaultComboBoxModel(TipoPagoAlquiler.values()));
        double min = 0.00;
        double value = 0.00;
        double max = 100;
        double stepSize = 0.01;
        view.spComision.setModel(new SpinnerNumberModel(value, min, max, stepSize));

        view.spMoraInmobiliaria.setModel(new SpinnerNumberModel(00.00, min, max, stepSize));
        view.spMoraPropietarios.setModel(new SpinnerNumberModel(00.00, min, max, stepSize));
        view.spPorcentajeMora.setModel(new SpinnerNumberModel(value, min, max, stepSize));

        //.spCuotas.setModel(new SpinnerNumberModel(1, 1, 500, 1));
        view.cbTipoPagoAlquiler.setModel(new DefaultComboBoxModel(TipoPagoAlquiler.values()));
        view.cbMoneda.setModel(new DefaultComboBoxModel(Moneda.values()));
        view.cbMonedaGarantia.setModel(new DefaultComboBoxModel(Moneda.values()));
        view.cbTipoContrato.setModel(new DefaultComboBoxModel(TipoContrato.values()));
        view.cbTipoCotizacion.setModel(new DefaultComboBoxModel(TipoCotizacionContrato.values()));
        new RollOverButtonsChicos(view.btnInquilino).RollOverButtonBuscar();
        new RollOverButtonsChicos(view.btnInmueble).RollOverButtonBuscar();
        new RollOverButtonsChicos(view.btnRemoveInmueble).RollOverButtonQuitar();
        new RollOverButtonsChicos(view.btnRemoveInquilino).RollOverButtonQuitar();
        fechas();
        buscaIVAs();
        configuraComboDestino();
        cargaTipoReajuste();
        cargaDestinoAlquiler();
        cargaGarantiasAlquiler();
        configuraSpinner();
        accionesBotones();
        habilitaCamposAlquiler();
        configuraFormatterSpinners(view.spComision);
        //configuraFormatterSpinners(view.spCuotas);
        configuraFormatterSpinners(view.spMoraInmobiliaria);
        configuraFormatterSpinners(view.spMoraPropietarios);
        configuraFormatterSpinners(view.spPorcentajeMora);
        configuraFormatterSpinners(view.spToleranciaMora);
    }

    public void go() {
        this.view.setVisible(true);
        this.view.toFront();

    }

    void configuraFormatterSpinners(JSpinner spinner) {
        //final JSpinner spinner = new JSpinner();
        JComponent comp = spinner.getEditor();
        JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
        DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
        formatter.setCommitsOnValidEdit(true);

    }

    void accionesBotones() {

        view.btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                guardarContrato(new Contrato());
            }
        }
        );

        view.btnGuardarModificaciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (inmuebleSeleccionado == null) {
                    setInmuebleSeleccionado(contratoSeleccionado.getInmueble());
                } else {
                    contratoSeleccionado.setInmueble(inmuebleSeleccionado);
                }

                if (inquilinoSeleccionado == null) {
                    setInquilinoSeleccionado(contratoSeleccionado.getInquilino());
                } else {

                    contratoSeleccionado.setInquilino(inquilinoSeleccionado);
                }
                guardarContrato(contratoSeleccionado);
            }
        }
        );
        view.btnVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                ContratosController.this.view.dispose();
            }
        }
        );
    }

    void configuraSpinner() {
        view.spMoraInmobiliaria.setVerifyInputWhenFocusTarget(true);
        view.spMoraInmobiliaria.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {

                Double nuevoValorInmobiliaria = Double.parseDouble(view.spMoraInmobiliaria.getModel().getValue().toString());

                view.spMoraPropietarios.setValue(100.00 - nuevoValorInmobiliaria);
            }
        });
        view.spMoraPropietarios.setVerifyInputWhenFocusTarget(true);
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
        view.dpVencimiento.setDate(new Date());

        Calendar c = Calendar.getInstance();
        c.setTime(view.dpInicio.getDate());
        c.add(Calendar.YEAR, 1);
        view.dpFIn.setDate(c.getTime());
        c.add(Calendar.YEAR, 1);
        view.dpExtension.setDate(c.getTime());

    }

    public void cargaTipoReajuste() {
        view.cbTipoReajustes.removeAllItems();
        List<TipoReajuste> reajustes = tiporeajusteDAO.findAll();
        for (TipoReajuste tipo : reajustes) {
            view.cbTipoReajustes.addItem(tipo);
        }

    }

    public void cargaGarantiasAlquiler() {
        view.cbTipoGarantia.removeAllItems();
        List<GarantiaAlquiler> garantias = iGarantiaAlquilerDAO.findAll();
        for (GarantiaAlquiler garantia : garantias) {
            view.cbTipoGarantia.addItem(garantia);
        }

    }

    public void cargaDestinoAlquiler() {
        view.cbDestinoAlquiler.removeAllItems();
        List<DestinoAlquiler> destinos = iDestinoAlquilerDAO.findAll();
        for (DestinoAlquiler destino : destinos) {
            view.cbDestinoAlquiler.addItem(destino);
        }

    }

    void detallesInquilino() {
        InquilinoDetallesDlg editaInquilino = new InquilinoDetallesDlg(null, true, inquilinoSeleccionado);
        editaInquilino.setVisible(true);
        editaInquilino.toFront();
    }

    void detallesInmueble() {
        InmuebleDetallesDialog_new editaInquilino = new InmuebleDetallesDialog_new(null, true, inmuebleSeleccionado);
        editaInquilino.setVisible(true);
        editaInquilino.toFront();
    }

    public void setInmuebleSeleccionado(Inmueble inmuebleSeleccionado) {

        this.inmuebleSeleccionado = inmuebleSeleccionado;

        view.txtInmueble.setText(inmuebleSeleccionado.toString());

    }

    public void setInquilinoSeleccionado(Inquilino inquilinoSeleccionado) {
        this.inquilinoSeleccionado = inquilinoSeleccionado;
        view.txtInquilino.setText(inquilinoSeleccionado.toString());

    }

    void guardarContrato(Contrato contrato) {
        try {
            if (inmuebleSeleccionado == null || inquilinoSeleccionado == null) {
                JOptionPane.showMessageDialog(view, "Debe seleccionar un inquilino y un inmueble", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (JOptionPane.showConfirmDialog(view, "Confirma el contrato?", "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                    contrato.setMoneda((Moneda) view.cbMoneda.getSelectedItem());
                    if (view.cbMoneda.getSelectedItem() != Moneda.PESOS) {
                        contrato.setTipoCotizacionContrato((TipoCotizacionContrato) view.cbTipoCotizacion.getSelectedItem());
                        contrato.setCotizacion(BigDecimal.valueOf(new Double(view.txtCotizacion.getText())));
                    } else {
                        contrato.setTipoCotizacionContrato(TipoCotizacionContrato.FIJA);
                        contrato.setCotizacion(BigDecimal.valueOf(new Double(1)));
                    }
                    contrato.setInquilino(inquilinoSeleccionado);
                    contrato.setDescripcionInquilino(inquilinoSeleccionado.toString());
                    contrato.setDescripcionInmueble(inmuebleSeleccionado.toString());
                    contrato.setInmueble(inmuebleSeleccionado);

                    contrato.setPorcentajeComision((Double) view.spComision.getValue());
                    contrato.setDestinoMora((DestinoMoraEnum) view.cbDestino.getSelectedItem());
                    contrato.setActivo(Boolean.TRUE);

                    if (view.cbTipoContrato.getSelectedItem() == TipoContrato.ALQUILER) {

                        contrato.setFechaInicio(view.dpInicio.getDate());
                        contrato.setFechaFin(view.dpFIn.getDate());
                        contrato.setFechaExtencion(view.dpExtension.getDate());
                        contrato.setValorAlquiler(BigDecimal.valueOf(new Double(view.txtValorAlquiler.getText())));
                        contrato.setValorAlquilerInicioContrato(BigDecimal.valueOf(new Double(view.txtValorAlquiler.getText())));
                        contrato.setTipoReajuste((TipoReajuste) view.cbTipoReajustes.getSelectedItem());
                        inmuebleSeleccionado.setStatusInmueble(StatusInmueble.ALQUILADA);
                        contrato.setAsegurado(view.chbAsegurado.isSelected());
                        contrato.setTipoPagoAlquiler((TipoPagoAlquiler) view.cbTipoPagoAlquiler.getSelectedItem());
                        contrato.setCierraMes(view.chbCierraMes.isSelected());

                        contrato.setDestinoAlquiler((DestinoAlquiler) view.cbDestinoAlquiler.getSelectedItem());
                        contrato.setGarantiaAlquiler((GarantiaAlquiler) view.cbTipoGarantia.getSelectedItem());
                        contrato.setMonedaGarantia((Moneda) view.cbMonedaGarantia.getSelectedItem());
                        try {
                            contrato.setMontoGarantia(BigDecimal.valueOf(new Double(view.txtMontoGarantia.getText())));
                        } catch (Exception e) {
                            contrato.setMontoGarantia(new BigDecimal(0));
                        }
                        contrato.setObsGarantia(view.txtObsGarantia.getText());

                        Calendar fechaReajuste = Calendar.getInstance();
                        fechaReajuste.setTime(view.dpInicio.getDate());
                        fechaReajuste.add(Calendar.YEAR, 1);
                        fechaReajuste.add(Calendar.DAY_OF_MONTH, (Integer) view.spToleranciaMora.getValue());
                        contrato.setFechaReajuste(fechaReajuste.getTime());

                    } else if (view.cbTipoContrato.getSelectedItem() == TipoContrato.VENTA) {
                        calculaSaldo();
                        contrato.setValorTotal(new BigDecimal(view.txtValorTotal.getText()));
                        contrato.setCantidadCuotas(cantidadCuotas);
                        contrato.setCantidadCuotas1(Integer.valueOf(view.txtCantidadCuotas1.getText()));
                        contrato.setCantidadCuotas2(Integer.valueOf(view.txtCantidadCuotas2.getText()));
                        contrato.setCantidadCuotas3(Integer.valueOf(view.txtCantidadCuotas3.getText()));

                        contrato.setValorCuota(BigDecimal.valueOf(Double.parseDouble(view.txtValorCuotas1.getText())));
                        contrato.setValorCuotas2(BigDecimal.valueOf(Double.parseDouble(view.txtValorCuotas2.getText())));
                        contrato.setValorCuotas3(BigDecimal.valueOf(Double.parseDouble(view.txtValorCuotas3.getText())));

                        inmuebleSeleccionado.setStatusInmueble(StatusInmueble.VENDIDA);
                        contrato.setVenciminetoPrimerCuota(view.dpVencimiento.getDate());
                        //contrato.setCantidadCuotas((Integer) contrato.getCantidadCuotas() + contrato.getCantidadCuotas1() + contrato.getCantidadCuotas2());

                    }

                    //contrato.setActivo(view.chbActivo.isSelected());
                    contrato.setObservaciones(view.txtObservaciones.getText());
                    contrato.setPorcentajeMoraTotal((Double) view.spPorcentajeMora.getValue());
                    contrato.setPorcentajeMoraInmobiliaria((Double) view.spMoraInmobiliaria.getValue());
                    contrato.setPorcentajeMoraPropietarios((Double) view.spMoraPropietarios.getValue());

                    contrato.setToleranciaMora((Integer) view.spToleranciaMora.getValue());
                    contrato.setTipoContrato((TipoContrato) view.cbTipoContrato.getSelectedItem());

                    contrato.setIva((Iva) view.cbIVA.getSelectedItem());

                    if (contrato.isNew()) {

                        contrato.setRecibosList(new CalculaRecibos().generaRecibos(contrato));
                        contrato.setInmueble(inmuebleSeleccionado);
                        // contrato.setDescripcionInmueble(inmuebleSeleccionado.toString());
                        contratoDAO.save(contrato);
                        //inmuebleSeleccionado.setStatuspropiedad(StatusInmueble.VENDIDA);
                        inmuebleDAO.save(inmuebleSeleccionado);
                    } else {
                        if (JOptionPane.showConfirmDialog(view, "Desea volver a generar recibos pendientes?", "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                            recibosDAO.deleteInBatch(recibosDAO.findByContratoAndSituacion(contrato, Situacion.PENDIENTE));
                            contrato.setRecibosList(new CalculaRecibos().generaRecibos(contrato));

                            contratoDAO.save(contrato);

                        } else {
                            contratoDAO.save(contrato);
                        }

                    }

                    //alquileresDAO.save(new CalculaAlquileres().generaRecibos(contrato));
                    JOptionPane.showMessageDialog(view, "El contrato se genero/modifico correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                    if(consultaContratosController!=null){
                        consultaContratosController.buscaContratos();
                    }
                    this.view.dispose();
                    /*if (consultaContratosController != null) {
                     consultaContratosController.actualizaTbl();
                     }*/
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error al guardar contrato" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void habilitaCamposVenta() {
        view.dpExtension.setVisible(false);
        view.panelVenta.setVisible(true);
        view.panelAlquiler.setVisible(false);
        view.lblExtension.setVisible(false);
        view.cbTipoReajustes.setVisible(false);
        view.btnNuevoTipoReajuste.setVisible(false);
        /*view.btnNuevoTipoGarantia.setVisible(false);
         view.btnNuevoTipoDestino.setVisible(false);
         view.txtObsGarantia.setVisible(true);
         view.cbTipoGarantia.setVisible(true);
         view.cbDestinoAlquiler.setVisible(true);
         view.txtMontoGarantia.setVisible(true);*/
        view.panelDestinoAlquiler.setVisible(false);
        view.panelGarantiaAlquiler.setVisible(false);
        view.lblTipoReajuste.setVisible(false);
        view.cbTipoReajustes.setSelectedItem(null);
        view.chbAsegurado.setVisible(false);
        view.cbTipoPagoAlquiler.setVisible(false);
        view.chbCierraMes.setVisible(false);
        view.dpVencimiento.setVisible(true);
        view.txtValorCuotas1.setVisible(true);
        view.txtValorCuotas2.setVisible(true);
        view.txtValorCuotas3.setVisible(true);
        view.dpInicio.setVisible(false);
        view.dpFIn.setVisible(false);
        view.txtCantidadCuotas1.setVisible(true);
        view.txtCantidadCuotas2.setVisible(true);
        view.txtCantidadCuotas3.setVisible(true);
        view.jlFin.setVisible(false);
        view.jlInicio.setVisible(false);
        view.jLTipoDepago.setVisible(false);
        view.jlVencimineto.setVisible(true);
    }

    void habilitaCamposAlquiler() {
        view.dpExtension.setVisible(true);
        view.panelVenta.setVisible(false);
        view.panelAlquiler.setVisible(true);
        view.lblExtension.setVisible(true);
        view.cbTipoReajustes.setVisible(true);
        view.btnNuevoTipoReajuste.setVisible(true);
        view.panelDestinoAlquiler.setVisible(true);
        view.panelGarantiaAlquiler.setVisible(true);
        view.lblTipoReajuste.setVisible(true);
        view.chbAsegurado.setVisible(true);
        view.cbTipoPagoAlquiler.setVisible(true);
        view.chbCierraMes.setVisible(true);
        view.dpVencimiento.setVisible(false);
        view.txtValorCuotas1.setVisible(false);
        view.txtValorCuotas2.setVisible(false);
        view.txtValorCuotas3.setVisible(false);
        view.txtCantidadCuotas1.setVisible(false);
        view.txtCantidadCuotas2.setVisible(false);
        view.txtCantidadCuotas3.setVisible(false);
        view.dpInicio.setVisible(true);
        view.dpFIn.setVisible(true);
        view.jlFin.setVisible(true);
        view.jLTipoDepago.setVisible(true);
        view.jlVencimineto.setVisible(false);
        view.jlInicio.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {

            case "cbTipoContrato":
                if (view.cbTipoContrato.getSelectedItem() == TipoContrato.VENTA) {
                    habilitaCamposVenta();
                } else if (view.cbTipoContrato.getSelectedItem() == TipoContrato.ALQUILER) {
                    habilitaCamposAlquiler();
                }
                break;

            case "cbMoneda":
                if (view.cbMoneda.getSelectedItem() != Moneda.PESOS) {
                    view.cbTipoCotizacion.setVisible(true);
                    view.txtCotizacion.setVisible(true);
                    view.lblCotizacion.setVisible(true);
                    view.lblTipoCotizacion.setVisible(true);
                    view.txtCotizacion.setText("0.00");
                } else {
                    view.cbTipoCotizacion.setVisible(false);
                    view.txtCotizacion.setVisible(false);
                    view.lblCotizacion.setVisible(false);
                    view.lblTipoCotizacion.setVisible(false);
                    view.txtCotizacion.setText("0.00");
                }
                break;

            case "cbTipoCotizacion":
                if (view.cbTipoCotizacion.getSelectedItem() == TipoCotizacionContrato.FIJA) {
                    view.txtCotizacion.setVisible(true);
                    view.lblCotizacion.setVisible(true);
                    view.lblTipoCotizacion.setVisible(true);
                    view.txtCotizacion.setText("0.00");
                } else {
                    view.txtCotizacion.setVisible(false);
                    view.lblCotizacion.setVisible(false);
                    view.txtCotizacion.setText("0.00");
                }
                break;

            case "btNuevoTipoReajuste":
                TipoReajusteDialog nuevoTipoReajuste = new TipoReajusteDialog(null, false, this.view, this);
                nuevoTipoReajuste.setVisible(true);
                nuevoTipoReajuste.toFront();
                cargaTipoReajuste();
                if (contratoSeleccionado != null) {
                    view.cbTipoReajustes.setSelectedItem(contratoSeleccionado.getTipoReajuste());
                }
                break;
            case "btnRemoveInquilino":
                inquilinoSeleccionado = new Inquilino();
                view.txtInquilino.setText("");
                break;

            case "btnRemoveInmueble":

                inmuebleSeleccionado = new Inmueble();
                view.txtInmueble.setText("");
                break;

            case "btnInquilino":
                InquilinosDialog inquilinosView = new InquilinosDialog(null, true);
                InquilinosController inquilinoController = new InquilinosController(inquilinosView, ContratosController.this);

                inquilinoController.go();
                break;

            case "btnInmueble":
                InmueblesDialog inmuebleView = new InmueblesDialog(null, true);
                InmueblesController inmuebleController = new InmueblesController(inmuebleView, ContratosController.this);

                inmuebleController.go();
                break;

            case "btNuevoTipoGarantia":
                CRUDGarantiaAlquileres garantias = new CRUDGarantiaAlquileres(null, true, null, this);
                garantias.setVisible(true);
                garantias.toFront();
                break;

            case "btnNuevoTipoDestino":
                CRUDDestinoAlquiler destino = new CRUDDestinoAlquiler(null, true, null, this);
                destino.setVisible(true);
                destino.toFront();
                break;

            case "btnInactivar":
                Date fecha = view.dpFechaInactivacion.getDate();
                if (fecha == null) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar la fecha de inactivación", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    contratoSeleccionado.setFechaInactivacion(fecha);
                    contratoSeleccionado.setActivo(false);
                    contratoDAO.save(contratoSeleccionado);
                    JOptionPane.showMessageDialog(view, "El contrato se inactivo correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                }

                break;

            default:
                throw new AssertionError();
        }
    }

    private void buscaIVAs() {
        view.cbIVA.removeAllItems();
        for (Iva iva : iivadao.findAll()) {
            view.cbIVA.addItem(iva);
        }
    }

    void documentListener(JTextField textField) {

        textField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
                calculaSaldo();
            }

            @Override
            public void focusLost(FocusEvent fe) {
                calculaSaldo();
            }

        });
    }

    public void calculaSaldo() {
        try {
            if (view.txtValorCuotas1.getText().isEmpty()) {
                view.txtValorCuotas1.setText("0");
            }
            if (view.txtValorCuotas2.getText().isEmpty()) {
                view.txtValorCuotas2.setText("0");
            }
            if (view.txtValorCuotas3.getText().isEmpty()) {
                view.txtValorCuotas3.setText("0");
            }
            if (view.txtValorTotal.getText().isEmpty()) {
                view.txtValorTotal.setText("0");
            }
            if (view.txtCantidadCuotas1.getText().isEmpty()) {
                view.txtCantidadCuotas1.setText("0");
            }
            if (view.txtCantidadCuotas2.getText().isEmpty()) {
                view.txtCantidadCuotas2.setText("0");
            }
            if (view.txtCantidadCuotas3.getText().isEmpty()) {
                view.txtCantidadCuotas3.setText("0");
            }
            Integer cuotas = Integer.valueOf(view.txtCantidadCuotas1.getText());
            Integer cuotas1 = Integer.valueOf(view.txtCantidadCuotas2.getText());
            Integer cuotas2 = Integer.valueOf(view.txtCantidadCuotas3.getText());
            Double valorCuotas = Double.valueOf(view.txtValorCuotas1.getText());
            Double valorCuotas1 = Double.valueOf(view.txtValorCuotas2.getText());
            Double valorCuotas2 = Double.valueOf(view.txtValorCuotas3.getText());
            cantidadCuotas = cuotas + cuotas1 + cuotas2;
            BigDecimal valorTotal = BigDecimal.valueOf((valorCuotas * cuotas) + (valorCuotas1 * cuotas1) + (valorCuotas2 * cuotas2)).setScale(2, RoundingMode.UP);
            view.txtValorTotal.setText(valorTotal.toString());
            //BigDecimal.valueOf(new Double(view.txtValorTotal.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void muestraDetalles() {
        this.view.lblContrato.setText("Contrato Nro: " + contratoSeleccionado.getId());
        this.view.dpInicio.setDate(contratoSeleccionado.getFechaInicio());
        this.view.dpFIn.setDate(contratoSeleccionado.getFechaFin());

        this.view.chbActivo.setSelected(contratoSeleccionado.getActivo());

        this.view.dpFechaInactivacion.setDate(contratoSeleccionado.getFechaInactivacion());
        if (contratoSeleccionado.getTipoContrato() == TipoContrato.VENTA) {
            this.view.txtValorTotal.setText(contratoSeleccionado.getValorTotal().toString());
            this.view.txtValorCuotas1.setText(contratoSeleccionado.getValorCuota().toString());
            this.view.txtValorCuotas2.setText(contratoSeleccionado.getValorCuotas2().toString());
            this.view.txtValorCuotas3.setText(contratoSeleccionado.getValorCuotas3().toString());
            this.view.txtCantidadCuotas1.setText(contratoSeleccionado.getCantidadCuotas1().toString());
            this.view.txtCantidadCuotas2.setText(contratoSeleccionado.getCantidadCuotas2().toString());
            this.view.txtCantidadCuotas3.setText(contratoSeleccionado.getCantidadCuotas3().toString());
            this.view.dpVencimiento.setDate(contratoSeleccionado.getVenciminetoPrimerCuota());
        } else if (contratoSeleccionado.getTipoContrato() == TipoContrato.ALQUILER) {
            //this.view.cbTipoReajustes.setSelectedItem(contratoSeleccionado.getTipoReajuste());
            this.view.txtValorAlquiler.setText(contratoSeleccionado.getValorAlquiler().toString());
            this.view.dpExtension.setDate(contratoSeleccionado.getFechaExtencion());
            this.view.chbCierraMes.setSelected(contratoSeleccionado.getCierraMes());
            this.view.chbAsegurado.setSelected(contratoSeleccionado.getAsegurado());
            this.view.cbTipoPagoAlquiler.setSelectedItem(contratoSeleccionado.getTipoPagoAlquiler());

            view.cbDestinoAlquiler.setSelectedItem(contratoSeleccionado.getDestinoAlquiler());
            view.cbTipoReajustes.setSelectedItem(contratoSeleccionado.getTipoReajuste());
            view.cbTipoGarantia.setSelectedItem(contratoSeleccionado.getGarantiaAlquiler());
            view.cbMonedaGarantia.setSelectedItem(contratoSeleccionado.getMonedaGarantia());
            view.txtMontoGarantia.setText(contratoSeleccionado.getMontoGarantia().toString());
            view.txtObsGarantia.setText(contratoSeleccionado.getObsGarantia());
        }
        this.view.cbMoneda.setSelectedItem(contratoSeleccionado.getMoneda());
        this.view.cbTipoCotizacion.setSelectedItem(contratoSeleccionado.getTipoCotizacionContrato());
        this.view.txtCotizacion.setText(contratoSeleccionado.getCotizacion().toString());
        this.view.txtObservaciones.setText(contratoSeleccionado.getObservaciones());
        this.view.spComision.setValue(contratoSeleccionado.getPorcentajeComision());
        this.view.cbDestino.setSelectedItem(contratoSeleccionado.getDestinoMora());
        this.view.spPorcentajeMora.setValue(contratoSeleccionado.getPorcentajeMoraTotal());
        this.view.spMoraInmobiliaria.setValue(contratoSeleccionado.getPorcentajeMoraInmobiliaria());
        this.view.spMoraPropietarios.setValue(contratoSeleccionado.getPorcentajeMoraPropietarios());

        this.view.spToleranciaMora.setValue(contratoSeleccionado.getToleranciaMora());

        this.view.cbIVA.setSelectedItem(contratoSeleccionado.getIva());
        this.view.cbTipoContrato.setSelectedItem(contratoSeleccionado.getTipoContrato());
        this.view.cbTipoPagoAlquiler.setSelectedItem(contratoSeleccionado.getTipoPagoAlquiler());
    }
}
