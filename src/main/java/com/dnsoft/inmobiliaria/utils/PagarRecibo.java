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
import com.dnsoft.inmobiliaria.beans.TipoContrato;
import com.dnsoft.inmobiliaria.beans.TipoCotizacionContrato;
import com.dnsoft.inmobiliaria.beans.TipoPago;
import com.dnsoft.inmobiliaria.daos.IRecibosDAO;
import com.dnsoft.inmobiliaria.daos.ICCPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.IPagoDAO;
import com.dnsoft.inmobiliaria.daos.IPagoPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.IParametrosDAO;
import com.dnsoft.inmobiliaria.daos.IPropietarioHasInmuebleDAO;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego Noble
 */
public class PagarRecibo {

    List<Propietario> listPropietarios;
    List<PagoPropietario> listPagoPropietario;
    PagoPropietario pagoPropietario;
    List<CCPropietario> listCCPropietarios;
    List<PropietarioHasInmueble> listPropietariosDelInmueble;
    Recibo recibo;
    Contrato contrato;
    Moneda monedaContrato;
    Moneda monedaPago;
    Propietario propietarioSeleccionado;
    PagoRecibo pagoRecibo;
    Container container;
    IRecibosDAO alquilersDAO;
    IPagoDAO pagoDAO;
    ICCPropietarioDAO cCPropietarioDAO;
    IPagoPropietarioDAO pagoPropietarioDAO;
    IPropietarioHasInmuebleDAO iPropietarioHasInmuebleDAO;
    IParametrosDAO parametrosDAO;
    //ICotizacionDAO cotizacionDAO;
    Parametros parametros;
    BigDecimal valorPropietario;
    BigDecimal valorEntrega;
    BigDecimal cotizacioPago;
    //CCPropietario cCPropietarioSeleccionado;
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
    TipoCotizacionContrato tipoCotizacionDelPago;
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    public PagarRecibo(Recibo recibo, BigDecimal valorEntrega, BigDecimal mora, Moneda monedaPago, TipoCotizacionContrato tipoCotizacionDelPago, BigDecimal cotizacionPago) throws Exception {
        this.recibo = recibo;
        this.mora = mora;
        this.valorEntrega = valorEntrega;
        this.contrato = recibo.getContrato();
        this.container = Container.getInstancia();
        alquilersDAO = container.getBean(IRecibosDAO.class);
        pagoDAO = container.getBean(IPagoDAO.class);
        pagoPropietarioDAO = container.getBean(IPagoPropietarioDAO.class);
        iPropietarioHasInmuebleDAO = container.getBean(IPropietarioHasInmuebleDAO.class);
        parametrosDAO = container.getBean(IParametrosDAO.class);
        cCPropietarioDAO = container.getBean(ICCPropietarioDAO.class);
        //cotizacionDAO = container.getBean(ICotizacionDAO.class);

        listPropietarios = new ArrayList<>();
        listCCPropietarios = new ArrayList<>();
        listPagoPropietario = new ArrayList<>();
        calculaAdelantoIRPF = new CalculaAdelantoIRPF();
        calculaComision = new CalculaComision();
        adelantoIRPF = BigDecimal.ZERO;
        this.monedaContrato = contrato.getMoneda();
        this.monedaPago = monedaPago;
        this.tipoCotizacionDelPago = tipoCotizacionDelPago;
        this.cotizacioPago = cotizacionPago;
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
        pagoRecibo = new PagoRecibo();
        recibo.setSaldo(recibo.getSaldo().subtract(valorEntrega));

        if (recibo.getSaldo().compareTo(BigDecimal.ZERO) != 0) {
            recibo.setSituacion(Situacion.CON_SALDO);
            pagoRecibo.setTipoPago(TipoPago.PARCIAL);

        } else {
            recibo.setSituacion(Situacion.PAGO);
            recibo.setFechaPago(new Date());
            pagoRecibo.setTipoPago(TipoPago.TOTAL);

        }
        BigDecimal comisionTotal = calculaComision.calculaComisionTotal(recibo.getValor(), porcentageComision, parametros, contrato);
        verificaMinimoComision = comisionTotal.doubleValue() < parametros.getMinimoComisionPesos().doubleValue(); //verifica si aplica comision minima
        if (verificaMinimoComision = true) {
            pagoRecibo.setComisionTotal(valorEntrega.multiply(comisionTotal).divide(recibo.getValor(), 2, RoundingMode.CEILING));
        } else {
            pagoRecibo.setComisionTotal(parametros.getMinimoComisionPesos());
        }

        pagoRecibo.setRecibo(recibo);
        pagoRecibo.setCotizacion(cotizacioPago);
        pagoRecibo.setFecha(new Date());
        pagoRecibo.setValor(valorEntrega);//pensar si se suma mora

        if (mora.compareTo(BigDecimal.ZERO) != 0) {
            pagoRecibo.setAmplicaMora(Boolean.TRUE);
        } else {
            pagoRecibo.setAmplicaMora(Boolean.FALSE);
        }

        pagoRecibo.setMoneda(recibo.getMoneda());
        pagoRecibo.setRecibo(recibo);

        for (PropietarioHasInmueble propietarios : listPropietariosDelInmueble) {

            pagoPropietario = new PagoPropietario();
            propietarioSeleccionado = propietarios.getPropietario();
            listPropietarios.add(propietarioSeleccionado);

            pagoPropietario.setPagoRecibo(pagoRecibo);//vincula el alquiler pago

            //calcula el valor correspondiente al propietario segun su % de propiedad (valor * %/ 100 redondea 2 decimales)
            porcentagePropiedadInmueble = propietarios.getProcentagePropiedad();
            valorPropietario = valorEntrega.multiply(porcentagePropiedadInmueble).divide(new BigDecimal(100)).setScale(2, RoundingMode.CEILING);

            if (mora.compareTo(BigDecimal.ZERO) != 0) {
                pagoRecibo.setMora(mora);
                DestinoMoraEnum destinoMora = contrato.getDestinoMora();
                switch (destinoMora) {

                    case INMOBILIARIA:

                        pagoPropietario.setMoraInmobiliaria(mora.multiply(porcentagePropiedadInmueble).divide(new BigDecimal(100)).setScale(2, RoundingMode.CEILING));
                        pagoPropietario.setMorapropietario(BigDecimal.valueOf(0));
                        //calcula comisiones
                        //if (verificaMinimoComision = true) {
                        valorComision = pagoRecibo.getComisionTotal().multiply(porcentagePropiedadInmueble).divide(new BigDecimal(100)).setScale(2, RoundingMode.CEILING);
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
                        valorComision = pagoRecibo.getComisionTotal().multiply(porcentagePropiedadInmueble).divide(new BigDecimal(100)).setScale(2, RoundingMode.CEILING);
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
                        valorComision = pagoRecibo.getComisionTotal().multiply(porcentagePropiedadInmueble).divide(new BigDecimal(100)).setScale(2, RoundingMode.CEILING);
                        //} else {
                        //    valorComision = calculaComision.calculaComision(valorPropietario, porcentageComision, parametros);
                        //}

                        valorComisionSobreMora = calculaComision.calculaComision(moraPropietarioSeleccionado, porcentageComision, contrato);

                        break;
                }
            } else {
                pagoRecibo.setMora(mora);
                pagoPropietario.setMoraInmobiliaria(mora);
                pagoPropietario.setMorapropietario(mora);
                //calcula comisiones
                if (verificaMinimoComision = true) {
                    valorComision = pagoRecibo.getComisionTotal().multiply(porcentagePropiedadInmueble).divide(new BigDecimal(100)).setScale(2, RoundingMode.CEILING);
                } else {
                    valorComision = calculaComision.calculaComision(valorPropietario, porcentageComision, contrato);
                }

            }
            verificaRetencionIRPF(valorPropietario);
            pagoPropietario.setMoneda(monedaContrato);
            pagoPropietario.setFecha(new Date());
            pagoPropietario.setValor(valorPropietario);
            pagoPropietario.setComision(valorComision.add(valorComisionSobreMora));
            pagoPropietario.setAdelantoIRPF(adelantoIRPF);
            pagoPropietario.setPropietario(propietarioSeleccionado);

            //incluye a el pago al propietario en la lista para la persistencia
            listPagoPropietario.add(pagoPropietario);

        }

        alquilersDAO.save(recibo);

        monedaCCPorpietario();
        imprimieRecibo();
        return pagoRecibo;
    }

    void monedaCCPorpietario() {
        switch (monedaPago) {
            case PESOS:
                ajustaCC(cotizacioPago, Moneda.PESOS);
                break;
            case DOLARES:
                if (tipoCotizacionDelPago == TipoCotizacionContrato.FIJA) {
                    ajustaCC(cotizacioPago, monedaPago);
                } else if (tipoCotizacionDelPago == TipoCotizacionContrato.OFICIAL) {
                    ajustaCC(new BigDecimal(BigInteger.ONE), Moneda.DOLARES);
                }
                break;
            case UNIDADES_INDEXADAS:
                ajustaCC(cotizacioPago, Moneda.PESOS);
                break;
            case UNIDADES_REAJUSTABLES:
                ajustaCC(cotizacioPago, Moneda.PESOS);
                break;
            default:
                throw new AssertionError();
        }
    }

    void ajustaCC(BigDecimal cotizacion, Moneda monedaCC) {
        List<CCPropietario> ccPropietarioToBD = new ArrayList<>();
        //Busca CC del Propietario segun la moneda del contrato, si no existe la crea
        for (PagoPropietario pagoPropietarioSeleccionado : listPagoPropietario) {
            Propietario propietario = pagoPropietarioSeleccionado.getPropietario();

            listCCPropietarios = new ArrayList<>();
            BigDecimal saldoAnterior = BigDecimal.ZERO;
            CCPropietario findUltimoMovimiento;
            findUltimoMovimiento = cCPropietarioDAO.findUltimoMovimiento(monedaCC, propietario);
            if (findUltimoMovimiento != null) {
                saldoAnterior = findUltimoMovimiento.getSaldo();
            } else {
                saldoAnterior = BigDecimal.ZERO;
            }
            //cCPropietarioSeleccionado = cCPropietarioDAO.findUltimoMovimiento(moneda, propietario);

            CCPropietario movimientoPago = new CCPropietario();
            movimientoPago.setPropietario(propietario);
            movimientoPago.setDebito(BigDecimal.ZERO);
            movimientoPago.setCredito(pagoPropietarioSeleccionado.getValor().multiply(cotizacion));
            if (contrato.getTipoContrato() == TipoContrato.VENTA) {
                movimientoPago.setDescipcion("Terreno " + contrato.getInmueble() + ", (" + contrato.getId() + ")\n Cuota " + recibo.getNroRecibo() + "/" + recibo.getCantidadRecibos() + " (venc." + formato.format(recibo.getFechaVencimiento()) + ")");
            } else if (contrato.getTipoContrato() == TipoContrato.ALQUILER) {
                movimientoPago.setDescipcion("Inmueble " + contrato.getInmueble() + "(" + contrato.getId() + ")" + "\n Alquier " + recibo.getPeriodo_desde() + "-" + recibo.getPeriodo_hasta() + "(venc." + formato.format(recibo.getFechaVencimiento()) + ")");
            }
            movimientoPago.setFecha(new Date());
            movimientoPago.setMoneda(monedaCC);
            movimientoPago.setPagoPropietario(pagoPropietarioSeleccionado);
            listCCPropietarios.add(movimientoPago);

            if (pagoPropietarioSeleccionado.getMorapropietario().doubleValue() != 0) {

                CCPropietario movimientoMora = new CCPropietario();
                movimientoMora.setPropietario(propietario);
                movimientoMora.setDebito(BigDecimal.ZERO);
                movimientoMora.setCredito(pagoPropietarioSeleccionado.getMorapropietario().multiply(cotizacion));
                movimientoMora.setDescipcion("Mora ");
                movimientoMora.setFecha(new Date());
                movimientoMora.setMoneda(monedaCC);
                movimientoMora.setPagoPropietario(pagoPropietarioSeleccionado);
                listCCPropietarios.add(movimientoMora);
            }

            if (pagoPropietarioSeleccionado.getAdelantoIRPF().doubleValue() != 0) {

                CCPropietario movimientoIRPF = new CCPropietario();
                movimientoIRPF.setPropietario(propietario);
                movimientoIRPF.setDebito(pagoPropietarioSeleccionado.getAdelantoIRPF().multiply(cotizacion));
                movimientoIRPF.setCredito(BigDecimal.ZERO);
                movimientoIRPF.setDescipcion("Adelanto IRPF " + contrato.getInmueble().toString());
                movimientoIRPF.setFecha(new Date());
                movimientoIRPF.setMoneda(monedaCC);
                movimientoIRPF.setPagoPropietario(pagoPropietarioSeleccionado);
                listCCPropietarios.add(movimientoIRPF);
            }
            if (pagoPropietarioSeleccionado.getComision().doubleValue() != 0) {
                CCPropietario movimientoComision = new CCPropietario();
                movimientoComision.setPropietario(propietario);
                movimientoComision.setDebito(pagoPropietarioSeleccionado.getComision().multiply(cotizacion));
                movimientoComision.setCredito(BigDecimal.ZERO);
                movimientoComision.setDescipcion("Honorarios ");
                movimientoComision.setFecha(new Date());
                movimientoComision.setMoneda(monedaCC);
                movimientoComision.setPagoPropietario(pagoPropietarioSeleccionado);
                listCCPropietarios.add(movimientoComision);
            }
            for (CCPropietario cc : listCCPropietarios) {

                cc.setSaldo(cc.getCredito().subtract(cc.getDebito()).add(saldoAnterior));
                saldoAnterior = cc.getSaldo();
            }
            ccPropietarioToBD.addAll(listCCPropietarios);
        }
        cCPropietarioDAO.save(ccPropietarioToBD);
        /*
         ActualizaSaldos acSaldo = new ActualizaSaldos();

         for (Propietario propietario : listPropietarios) {
         List<CCPropietario> ccPropietario = cCPropietarioDAO.findByPropietarioAndMonedaOrderByIdAsc(propietario, monedaCC);

         cCPropietarioDAO.save(acSaldo.ActualizaSaldosPropietarios(ccPropietario));

         }*/
    }

    void imprimieRecibo() {
        Recibo ajustaMoraRecibo = recibo;
        ajustaMoraRecibo.setMora(pagoDAO.findSumMora(recibo));
        alquilersDAO.save(ajustaMoraRecibo);

        try {
            if (contrato.getTipoContrato() == TipoContrato.ALQUILER) {

                new ImprimeRecibo().imprimieReciboAlquiler(pagoRecibo, pagoRecibo.getCotizacion());

            } else {

                new ImprimeRecibo().imprimieReciboPagoCuota(pagoRecibo, pagoRecibo.getCotizacion());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al imprimir recibo " + ex, "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

}
