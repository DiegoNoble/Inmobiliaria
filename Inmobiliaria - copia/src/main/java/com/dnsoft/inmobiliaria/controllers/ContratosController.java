/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.views.ContratosDialog;
import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.DestinoMoraEnum;
import com.dnsoft.inmobiliaria.beans.Iva;
import com.dnsoft.inmobiliaria.beans.Inmueble;
import com.dnsoft.inmobiliaria.beans.Inquilino;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.StatusInmueble;
import com.dnsoft.inmobiliaria.beans.TipoContrato;
import com.dnsoft.inmobiliaria.beans.TipoPagoAlquiler;
import com.dnsoft.inmobiliaria.beans.TipoReajuste;
import com.dnsoft.inmobiliaria.daos.IContratoDAO;
import com.dnsoft.inmobiliaria.daos.IInmuebleDAO;
import com.dnsoft.inmobiliaria.daos.IRecibosDAO;
import com.dnsoft.inmobiliaria.daos.IIvaDAO;
import com.dnsoft.inmobiliaria.daos.ITipoReajusteDAO;
import com.dnsoft.inmobiliaria.models.InmuebleTableModel;
import com.dnsoft.inmobiliaria.models.InquilinosTableModel;
import com.dnsoft.inmobiliaria.utils.ButtonColumnDetalles;
import com.dnsoft.inmobiliaria.utils.CalculaRecibos;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ControlarEntradaTexto;
import com.dnsoft.inmobiliaria.views.InmueblesDialog;
import com.dnsoft.inmobiliaria.views.InquilinosDlg;
import com.dnsoft.inmobiliaria.views.InmuebleDetallesDialog;
import com.dnsoft.inmobiliaria.views.InquilinoDetallesDlg;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Diego Noble
 */
public class ContratosController implements ActionListener {

    Container container;
    public ContratosDialog view;
    InmuebleTableModel tableModelInmueble;
    InquilinosTableModel tableModelInquilino;
    ListSelectionModel listModel;
    Inmueble inmuebleSeleccionado;
    List<Inmueble> inmuebles;
    List<Inquilino> inquilinos;
    Inquilino inquilinoSeleccionado;
    IContratoDAO contratoDAO;
    IInmuebleDAO inmuebleDAO;
    ITipoReajusteDAO tiporeajusteDAO;
    IRecibosDAO alquileresDAO;
    IIvaDAO iivadao;
    Contrato contratoSeleccionado;

    public ContratosController(ContratosDialog view) {

        this.container = Container.getInstancia();
        this.view = view;
        inicia();
        configTblInmueble();
        configTblInquilino();
        this.view.btnGuardarModificaciones.setVisible(false);
        this.view.btnGuardar.setVisible(true);
        documentListener(view.txtValorEntrega);
        documentListener(view.txtValorTotal);
        //view.txtValorTotal.setText("0.00");
        //view.txtValorEntrega.setText("0.00");
        // view.txtSaldo.setText("0.00");
    }

    public ContratosController(ContratosDialog view, Contrato contratoSeleccionado) {

        this.container = Container.getInstancia();
        this.view = view;
        this.contratoSeleccionado = contratoSeleccionado;
        inicia();
        configTblInmueble();
        configTblInquilino();
        this.view.btnGuardarModificaciones.setVisible(true);
        this.view.btnGuardar.setVisible(false);
        this.view.btnQuitarInmuele.setVisible(false);
        this.view.btnSeleccionarInmuele.setVisible(false);

        muestraDetalles();

        inquilinos.add(this.contratoSeleccionado.getInquilino());
        inmuebles.add(this.contratoSeleccionado.getInmueble());
        tableModelInmueble.fireTableDataChanged();
        tableModelInquilino.fireTableDataChanged();
    }

    private void inicia() {

        this.view.btnNuevoTipo.setActionCommand("btNuevoTipoReajuste");
        this.view.btnNuevoTipo.addActionListener(this);

        this.view.cbTipoContrato.setActionCommand("cbTipoContrato");
        this.view.cbTipoContrato.addActionListener(this);
        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
        view.txtValorTotal.setDocument(new ControlarEntradaTexto(10, chs));

        contratoDAO = container.getBean(IContratoDAO.class);
        inmuebleDAO = container.getBean(IInmuebleDAO.class);
        tiporeajusteDAO = container.getBean(ITipoReajusteDAO.class);
        alquileresDAO = container.getBean(IRecibosDAO.class);
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

        view.cbTipoPagoAlquiler.setModel(new DefaultComboBoxModel(TipoPagoAlquiler.values()));
        view.cbMoneda.setModel(new DefaultComboBoxModel(Moneda.values()));
        view.cbTipoContrato.setModel(new DefaultComboBoxModel(TipoContrato.values()));
        fechas();
        buscaIVAs();
        configuraComboDestino();
        cargaTipoReajuste();
        configuraSpinner();
        accionesBotones();
        habilitaCamposAlquiler();
    }

    public void go() {
        this.view.setVisible(true);
        this.view.toFront();

    }

    void accionesBotones() {
        view.btnSeleccionarInmuele.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                InmueblesDialog inmuebleView = new InmueblesDialog(null, true);
                InmueblesController inmuebleController = new InmueblesController(inmuebleView, ContratosController.this);

                inmuebleController.go();
            }
        });
        view.btnQuitarInmuele.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                inmuebles.clear();
                tableModelInmueble.fireTableDataChanged();
            }
        });
        view.btnSeleccionarInquilino.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                InquilinosDlg inquilinosView = new InquilinosDlg(null, true);
                InquilinosController inquilinoController = new InquilinosController(inquilinosView, ContratosController.this);

                inquilinoController.go();

            }
        });
        view.btnQuitarInquilino.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                inquilinos.clear();
                tableModelInquilino.fireTableDataChanged();
            }
        }
        );

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
                setInmuebleSeleccionado(contratoSeleccionado.getInmueble());
                setInquilinoSeleccionado(contratoSeleccionado.getInquilino());
                guardarContrato(contratoSeleccionado);
            }
        }
        );
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
        c.add(Calendar.YEAR, 1);
        view.dpFIn.setDate(c.getTime());
        c.add(Calendar.YEAR, 1);
        view.dpExtension.setDate(c.getTime());
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

        inmuebles = new ArrayList<>();

        tableModelInmueble = new InmuebleTableModel(inmuebles);
        view.tblInmueble.setModel(tableModelInmueble);

        new ButtonColumnDetalles(view.tblInmueble, 4) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                detallesInmueble();
            }
        };
        view.tblInmueble.setRowHeight(25);
    }

    private void configTblInquilino() {

        ((DefaultTableCellRenderer) view.tblInquilino.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        inquilinos = new ArrayList<>();

        tableModelInquilino = new InquilinosTableModel(inquilinos);
        view.tblInquilino.setModel(tableModelInquilino);

        new ButtonColumnDetalles(view.tblInquilino, 4) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                detallesInquilino();
            }
        };
        view.tblInquilino.setRowHeight(25);
    }

    void detallesInquilino() {
        inquilinoSeleccionado = inquilinos.get(view.tblInquilino.getSelectedRow());
        InquilinoDetallesDlg editaInquilino = new InquilinoDetallesDlg(null, true, inquilinoSeleccionado);
        editaInquilino.setVisible(true);
        editaInquilino.toFront();
    }

    void detallesInmueble() {
        Inmueble editar = inmuebles.get(view.tblInmueble.getSelectedRow());
        InmuebleDetallesDialog editaInquilino = new InmuebleDetallesDialog(null, true, editar);
        editaInquilino.setVisible(true);
        editaInquilino.toFront();
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

                    contrato.setMoneda((Moneda) view.cbMoneda.getSelectedItem());
                    contrato.setInquilino(inquilinoSeleccionado);

                    contrato.setAsegurado(view.chbAsegurado.isSelected());
                    contrato.setPorcentajeComision((Double) view.spComision.getValue());
                    contrato.setDestinoMora((DestinoMoraEnum) view.cbDestino.getSelectedItem());
                    contrato.setActivo(Boolean.TRUE);
                    if (view.cbTipoContrato.getSelectedItem() == TipoContrato.ALQUILER) {
                        contrato.setFechaExtencion(view.dpExtension.getDate());
                        contrato.setValorAlquiler(BigDecimal.valueOf(new Double(view.txtValorAlquiler.getText())));
                        contrato.setTipoReajuste((TipoReajuste) view.cbTipoReajustes.getSelectedItem());
                        inmuebleSeleccionado.setStatuspropiedad(StatusInmueble.ALQUILADA);

                    } else if (view.cbTipoContrato.getSelectedItem() == TipoContrato.VENTA) {
                        contrato.setValorTotal(new BigDecimal(view.txtValorTotal.getText()));
                        contrato.setSaldoCompra(BigDecimal.valueOf(Double.parseDouble(view.txtSaldo.getText())));
                        contrato.setValorEntrega(BigDecimal.valueOf(Double.parseDouble(view.txtValorEntrega.getText())));
                        inmuebleSeleccionado.setStatuspropiedad(StatusInmueble.VENDIDA);
                    }
                    contrato.setPorcentajeMoraTotal((Double) view.spPorcentajeMora.getValue());
                    contrato.setPorcentajeMoraInmobiliaria((Double) view.spMoraInmobiliaria.getValue());
                    contrato.setPorcentajeMoraPropietarios((Double) view.spMoraPropietarios.getValue());

                    contrato.setToleranciaMora((Integer) view.spToleranciaMora.getValue());
                    contrato.setTipoContrato((TipoContrato) view.cbTipoContrato.getSelectedItem());
                    contrato.setTipoPagoAlquiler((TipoPagoAlquiler) view.cbTipoPagoAlquiler.getSelectedItem());
                    contrato.setIva((Iva) view.cbIVA.getSelectedItem());

                    if (contrato.isNew()) {

                        contrato.setRecibosList(new CalculaRecibos().generaRecibos(contrato));
                        contrato.setInmueble(inmuebleSeleccionado);
                        contratoDAO.save(contrato);
                        //inmuebleSeleccionado.setStatuspropiedad(StatusInmueble.VENDIDA);
                        inmuebleDAO.save(inmuebleSeleccionado);
                    }else{
                        contratoDAO.save(contrato);
                    }

                    //alquileresDAO.save(new CalculaAlquileres().generaRecibos(contrato));
                    JOptionPane.showMessageDialog(view, "El contrato se genero correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);

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
        view.btnNuevoTipo.setVisible(false);
        view.lblTipoReajuste.setVisible(false);
    }

    void habilitaCamposAlquiler() {
        view.dpExtension.setVisible(true);
        view.panelVenta.setVisible(false);
        view.panelAlquiler.setVisible(true);
        view.lblExtension.setVisible(true);
        view.cbTipoReajustes.setVisible(true);
        view.btnNuevoTipo.setVisible(true);
        view.lblTipoReajuste.setVisible(true);
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

            case "btNuevoTipoReajuste":
                TipoReajusteDialog nuevoTipoReajuste = new TipoReajusteDialog(null, true, this.view);
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

            public void calculaSaldo() {
                if (view.txtValorEntrega.getText().isEmpty()) {
                    view.txtValorEntrega.setText("0.0");
                }
                if (view.txtValorTotal.getText().isEmpty()) {
                    view.txtValorTotal.setText("0.0");
                }
                if (view.txtSaldo.getText().isEmpty()) {
                    view.txtSaldo.setText("0.0");
                }
                BigDecimal saldo = BigDecimal.valueOf(new Double(view.txtSaldo.getText()));
                BigDecimal entrega = BigDecimal.valueOf(new Double(view.txtValorEntrega.getText()));
                BigDecimal total = BigDecimal.valueOf(new Double(view.txtValorTotal.getText()));
                saldo = total.subtract(entrega);
                view.txtSaldo.setText(saldo.toString());
            }
        });
    }

    private void muestraDetalles() {
        this.view.dpInicio.setDate(contratoSeleccionado.getFechaInicio());
        this.view.dpFIn.setDate(contratoSeleccionado.getFechaFin());

        if (contratoSeleccionado.getTipoContrato() == TipoContrato.VENTA) {
            this.view.txtValorTotal.setText(contratoSeleccionado.getValorTotal().toString());
            this.view.txtValorEntrega.setText(contratoSeleccionado.getValorEntrega().toString());
            this.view.txtSaldo.setText(contratoSeleccionado.getSaldoCompra().toString());
        } else if (contratoSeleccionado.getTipoContrato() == TipoContrato.ALQUILER) {
            this.view.cbTipoReajustes.setSelectedItem(contratoSeleccionado.getTipoReajuste());
            this.view.txtValorAlquiler.setText(contratoSeleccionado.getValorAlquiler().toString());
            this.view.dpExtension.setDate(contratoSeleccionado.getFechaExtencion());
        }
        this.view.cbMoneda.setSelectedItem(contratoSeleccionado.getMoneda());
        this.view.chbAsegurado.setSelected(contratoSeleccionado.getAsegurado());
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
