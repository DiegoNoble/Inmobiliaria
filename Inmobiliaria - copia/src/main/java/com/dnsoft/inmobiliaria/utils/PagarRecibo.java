/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.utils;

import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.beans.CCPropietario;
import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.DestinoMoraEnum;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.PagoRecibo;
import com.dnsoft.inmobiliaria.beans.PagoPropietario;
import com.dnsoft.inmobiliaria.beans.Parametros;
import com.dnsoft.inmobiliaria.beans.Propietario;
import com.dnsoft.inmobiliaria.beans.PropietarioHasInmueble;
import com.dnsoft.inmobiliaria.beans.Situacion;
import com.dnsoft.inmobiliaria.beans.TipoPago;
import com.dnsoft.inmobiliaria.daos.IRecibosDAO;
import com.dnsoft.inmobiliaria.daos.ICCPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.IPagoDAO;
import com.dnsoft.inmobiliaria.daos.IPagoPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.IParametrosDAO;
import com.dnsoft.inmobiliaria.daos.IPropietarioHasInmuebleDAO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Diego Noble
 */
public class PagarRecibo {

    List<PagoPropietario> listPagoPropietario;
    PagoPropietario pagoPropietario;
    List<CCPropietario> listCCPropietarios;
    List<PropietarioHasInmueble> listPropietariosDelInmueble;
    Recibo alquiler;
    Contrato contrato;
    Moneda moneda;
    Propietario propietarioSeleccionado;
    PagoRecibo pagoAlquiler;
    Container container;
    IRecibosDAO alquilersDAO;
    IPagoDAO pagoDAO;
    ICCPropietarioDAO cCPropietarioDAO;
    IPagoPropietarioDAO pagoPropietarioDAO;
    IPropietarioHasInmuebleDAO iPropietarioHasInmuebleDAO;
    IParametrosDAO parametrosDAO;
    Parametros parametros;
    BigDecimal valorPropietario;
    BigDecimal valorEntrega;
//    CCPropietario cCPropietarioSeleccionado;
    CalculaAdelantoIRPF calculaAdelantoIRPF;
    CalculaComision calculaComision;
    BigDecimal adelantoIRPF;
    BigDecimal adelantoIRPFSobreMora;
    BigDecimal valorComision;
    BigDecimal valorComisionSobreMora;
    BigDecimal mora;
    BigDecimal porcentagePropiedadInmueble;
    BigDecimal porcentageComision;
    // BigDecimal saldo;
    Boolean verificaMinimoComision;

    public PagarRecibo(Recibo alquiler, BigDecimal valorEntrega, BigDecimal mora) throws Exception {
        this.alquiler = alquiler;
        this.mora = mora;
        this.valorEntrega = valorEntrega;
        this.contrato = alquiler.getContrato();
        this.container = Container.getInstancia();
        alquilersDAO = container.getBean(IRecibosDAO.class);
        pagoDAO = container.getBean(IPagoDAO.class);
        pagoPropietarioDAO = container.getBean(IPagoPropietarioDAO.class);
        iPropietarioHasInmuebleDAO = container.getBean(IPropietarioHasInmuebleDAO.class);
        parametrosDAO = container.getBean(IParametrosDAO.class);
        cCPropietarioDAO = container.getBean(ICCPropietarioDAO.class);
        listCCPropietarios = new ArrayList<>();
        listPagoPropietario = new ArrayList<>();
        calculaAdelantoIRPF = new CalculaAdelantoIRPF();
        calculaComision = new CalculaComision();
        adelantoIRPF = BigDecimal.ZERO;
        moneda = contrato.getMoneda();
        iPropietarioHasInmuebleDAO.findByInmueble(contrato.getInmueble());
        porcentageComision = BigDecimal.valueOf(contrato.getPorcentajeComision());
        valorComisionSobreMora = new BigDecimal(BigInteger.ZERO);
        listPropietariosDelInmueble = iPropietarioHasInmuebleDAO.findByInmueble(contrato.getInmueble());
        parametros = parametrosDAO.findOne(Long.valueOf(1));

    }

    private void verificaRetencionIRPF(BigDecimal valor) {
        //Propietario paga IRPF?
        if (propietarioSeleccionado.getRetenerIrpf() == true) {

            //Prioridad a retencion por Inquilino
            if (contrato.getInquilino().getAgenteRetensorIRPF() == true) {
                pagoPropietario.setRetieneInquilino(Boolean.TRUE);
                pagoPropietario.setRetieneInmobiliaria(Boolean.FALSE);
                //calcula monto irpf
                adelantoIRPF = calculaAdelantoIRPF.calculaIRPF(valor);
                pagoPropietario.setAdelantoIRPF(adelantoIRPF);
            } else {
                //Si no retiene inquilino, retiene inmobiliaria
                pagoPropietario.setRetieneInmobiliaria(Boolean.TRUE);
                pagoPropietario.setRetieneInquilino(Boolean.FALSE);
                //calcula monto irpf
                adelantoIRPF = calculaAdelantoIRPF.calculaIRPF(valor);
                pagoPropietario.setAdelantoIRPF(adelantoIRPF);
            }
        } else {
            pagoPropietario.setRetieneInmobiliaria(Boolean.FALSE);
            pagoPropietario.setRetieneInquilino(Boolean.FALSE);
            //calcula monto irpf
            adelantoIRPF = BigDecimal.ZERO;
            pagoPropietario.setAdelantoIRPF(adelantoIRPF);
        }
    }

    public PagoRecibo pagarRecibo() {
        pagoAlquiler = new PagoRecibo();
        alquiler.setSaldo(alquiler.getSaldo().subtract(valorEntrega));

        if (alquiler.getSaldo().compareTo(BigDecimal.ZERO) != 0) {
            alquiler.setStatusAlquiler(Situacion.CON_SALDO);
            pagoAlquiler.setTipoPago(TipoPago.PARCIAL);

        } else {
            alquiler.setStatusAlquiler(Situacion.PAGO);
            pagoAlquiler.setTipoPago(TipoPago.TOTAL);
        }
        BigDecimal comisionTotal = calculaComision.calculaComisionTotal(alquiler.getValor(), porcentageComision, parametros,contrato);
        verificaMinimoComision = comisionTotal.doubleValue() < parametros.getMinimoComisionPesos().doubleValue(); //verifica si aplica comision minima
        if (verificaMinimoComision = true) {
            pagoAlquiler.setComisionTotal(valorEntrega.multiply(comisionTotal).divide(alquiler.getValor()));
        } else {
            pagoAlquiler.setComisionTotal(parametros.getMinimoComisionPesos());
        }

        pagoAlquiler.setRecibo(alquiler);
        pagoAlquiler.setFecha(new Date());
        pagoAlquiler.setValor(valorEntrega);//pensar si se suma mora

        if (mora.compareTo(BigDecimal.ZERO)
                != 0) {
            pagoAlquiler.setAmplicaMora(Boolean.TRUE);
        } else {
            pagoAlquiler.setAmplicaMora(Boolean.FALSE);
        }

        pagoAlquiler.setMoneda(alquiler.getMoneda());
        pagoAlquiler.setRecibo(alquiler);

        for (PropietarioHasInmueble propietarios : listPropietariosDelInmueble) {

            pagoPropietario = new PagoPropietario();
            propietarioSeleccionado = propietarios.getPropietario();

            pagoPropietario.setPagoAlquiler(pagoAlquiler);//vincula el alquiler pago

            //calcula el valor correspondiente al propietario segun su % de propiedad (valor * %/ 100 redondea 2 decimales)
            porcentagePropiedadInmueble = propietarios.getProcentagePropiedad();
            valorPropietario = valorEntrega.multiply(porcentagePropiedadInmueble).divide(new BigDecimal(100)).setScale(2, RoundingMode.CEILING);

            if (mora.compareTo(BigDecimal.ZERO) != 0) {
                DestinoMoraEnum destinoMora = contrato.getDestinoMora();
                switch (destinoMora) {

                    case INMOBILIARIA:

                        pagoPropietario.setMoraInmobiliaria(mora.multiply(porcentagePropiedadInmueble).divide(new BigDecimal(100)).setScale(2, RoundingMode.CEILING));
                        pagoPropietario.setMorapropietario(BigDecimal.valueOf(0));
                        //calcula comisiones
                        //if (verificaMinimoComision = true) {
                        valorComision = pagoAlquiler.getComisionTotal().multiply(porcentagePropiedadInmueble).divide(new BigDecimal(100)).setScale(2, RoundingMode.CEILING);
                        // } else {
                        //   valorComision = calculaComision.calculaComision(valorPropietario, porcentageComision, parametros);
                        // }

                        break;
                    case PROPIETARIO:

                        pagoPropietario.setMoraInmobiliaria(BigDecimal.valueOf(0));
                        BigDecimal moraPropietario = mora.multiply(porcentagePropiedadInmueble).divide(new BigDecimal(100)).setScale(2, RoundingMode.CEILING);
                        pagoPropietario.setMorapropietario(moraPropietario);
                        //calcula comisiones
                        // if (verificaMinimoComision = true) {
                        valorComision = pagoAlquiler.getComisionTotal().multiply(porcentagePropiedadInmueble).divide(new BigDecimal(100)).setScale(2, RoundingMode.CEILING);
                        //} else {
                        //    valorComision = calculaComision.calculaComision(valorPropietario, porcentageComision, parametros);
                        // }

                        valorComisionSobreMora = calculaComision.calculaComision(moraPropietario, porcentageComision, contrato);

                        break;

                    case PRORATEADO:

                        Double porcentageMoraInmobiliaria = contrato.getPorcentajeMoraInmobiliaria();
                        pagoPropietario.setMoraInmobiliaria(mora.multiply(BigDecimal.valueOf(porcentageMoraInmobiliaria)).divide(BigDecimal.valueOf(100)).multiply(porcentagePropiedadInmueble).divide(BigDecimal.valueOf(100)));

                        Double porcentagePropietarios = contrato.getPorcentajeMoraPropietarios();

                        BigDecimal moraPropietarioSeleccionado = mora.multiply(BigDecimal.valueOf(porcentagePropietarios)).divide(BigDecimal.valueOf(100)).multiply(porcentagePropiedadInmueble).divide(BigDecimal.valueOf(100));
                        //moraPropietarioSeleccionado.multiply(porcentagePropiedadInmueble).divide(BigDecimal.valueOf(100));

                        pagoPropietario.setMorapropietario(moraPropietarioSeleccionado);

                        // if (verificaMinimoComision = true) {
                        valorComision = pagoAlquiler.getComisionTotal().multiply(porcentagePropiedadInmueble).divide(new BigDecimal(100)).setScale(2, RoundingMode.CEILING);
                        //} else {
                        //    valorComision = calculaComision.calculaComision(valorPropietario, porcentageComision, parametros);
                        //}

                        valorComisionSobreMora = calculaComision.calculaComision(moraPropietarioSeleccionado, porcentageComision, contrato);

                        break;
                }
            } else {
                pagoPropietario.setMoraInmobiliaria(mora);
                pagoPropietario.setMorapropietario(mora);
                //calcula comisiones
                if (verificaMinimoComision = true) {
                    valorComision = pagoAlquiler.getComisionTotal().multiply(porcentagePropiedadInmueble).divide(new BigDecimal(100)).setScale(2, RoundingMode.CEILING);
                } else {
                    valorComision = calculaComision.calculaComision(valorPropietario, porcentageComision, contrato);
                }

            }
            verificaRetencionIRPF(valorPropietario);
            pagoPropietario.setMoneda(moneda);
            pagoPropietario.setFecha(new Date());
            pagoPropietario.setValor(valorPropietario);
            pagoPropietario.setComision(valorComision.add(valorComisionSobreMora));
            pagoPropietario.setAdelantoIRPF(adelantoIRPF);
            pagoPropietario.setPropietario(propietarioSeleccionado);

            //incluye a el pago al propietario en la lista para la persistencia
            listPagoPropietario.add(pagoPropietario);

        }

        alquilersDAO.save(alquiler);

        ajustaCC();
        imprimieRecibo();
        return pagoAlquiler;
    }

    void ajustaCC() {
        //Busca CC del Propietario segun la moneda del contrato, si no existe la crea
        for (PagoPropietario pagoPropietarioSeleccionado : listPagoPropietario) {
            Propietario propietario = pagoPropietarioSeleccionado.getPropietario();
            //cCPropietarioSeleccionado = cCPropietarioDAO.findUltimoMovimiento(moneda, propietario);

            CCPropietario movimientoPago = new CCPropietario();
            movimientoPago.setPropietario(propietario);
            movimientoPago.setDebito(BigDecimal.ZERO);
            movimientoPago.setCredito(pagoPropietarioSeleccionado.getValor());
            movimientoPago.setDescipcion("Pago Alquiler Nro: " + alquiler.getId() + " " + contrato.getInmueble().toString() + " " + contrato.getInquilino().toString());
            movimientoPago.setFecha(new Date());
            movimientoPago.setMoneda(moneda);
            movimientoPago.setPagoPropietario(pagoPropietarioSeleccionado);
            listCCPropietarios.add(movimientoPago);

            if (pagoPropietarioSeleccionado.getAdelantoIRPF().doubleValue() != 0) {

                CCPropietario movimientoIRPF = new CCPropietario();
                movimientoIRPF.setPropietario(propietario);
                movimientoIRPF.setDebito(pagoPropietarioSeleccionado.getAdelantoIRPF());
                movimientoIRPF.setCredito(BigDecimal.ZERO);
                movimientoIRPF.setDescipcion("Adelanto IRPF " + contrato.getInmueble().toString());
                movimientoIRPF.setFecha(new Date());
                movimientoIRPF.setMoneda(moneda);
                movimientoIRPF.setPagoPropietario(pagoPropietarioSeleccionado);
                listCCPropietarios.add(movimientoIRPF);
            }

            CCPropietario movimientoComision = new CCPropietario();
            movimientoComision.setPropietario(propietario);
            movimientoComision.setDebito(pagoPropietarioSeleccionado.getComision());
            movimientoComision.setCredito(BigDecimal.ZERO);
            movimientoComision.setDescipcion("Honorarios ");
            movimientoComision.setFecha(new Date());
            movimientoComision.setMoneda(moneda);
            movimientoComision.setPagoPropietario(pagoPropietarioSeleccionado);
            listCCPropietarios.add(movimientoComision);

            if (pagoPropietarioSeleccionado.getMorapropietario().doubleValue() != 0) {

                CCPropietario movimientoMora = new CCPropietario();
                movimientoMora.setPropietario(propietario);
                movimientoMora.setDebito(BigDecimal.ZERO);
                movimientoMora.setCredito(pagoPropietarioSeleccionado.getMorapropietario());
                movimientoMora.setDescipcion("Mora ");
                movimientoMora.setFecha(new Date());
                movimientoMora.setMoneda(moneda);
                movimientoMora.setPagoPropietario(pagoPropietarioSeleccionado);
                listCCPropietarios.add(movimientoMora);
            }
        }

        cCPropietarioDAO.save(listCCPropietarios);

        ActualizaSaldos acSaldo = new ActualizaSaldos();

        for (CCPropietario cc : listCCPropietarios) {

            List<CCPropietario> ccPropietario = cCPropietarioDAO.findByPropietarioAndMonedaOrderByIdAsc(cc.getPropietario(), moneda);

            cCPropietarioDAO.save(acSaldo.ActualizaSaldosPropietarios(ccPropietario));
        }

    }

    void imprimieRecibo() {
        try {

            InputStream resource = getClass().getClassLoader().getResourceAsStream("reportes/Recibo1.jasper");

            BufferedImage logo = ImageIO.read(getClass().getResource("/imagenes/logo.png"));

            HashMap parametros = new HashMap<>();

            parametros.put("inquilino", pagoAlquiler.getRecibo().getContrato().getInquilino().toString());
            parametros.put("direccion", pagoAlquiler.getRecibo().getContrato().getInmueble().toString());
            parametros.put("fecha", pagoAlquiler.getFecha());
            parametros.put("saldo", pagoAlquiler.getRecibo().getSaldo().toString());
            parametros.put("valor", pagoAlquiler.getValor().toString());
            parametros.put("idPago", pagoAlquiler.getId().toString());
            parametros.put("logo", logo);
            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, new JREmptyDataSource());
            JasperViewer reporte = new JasperViewer(jasperPrint, false);
            reporte.setVisible(true);
            
            reporte.toFront();
            

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
