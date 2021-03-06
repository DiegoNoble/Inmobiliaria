/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.utils;

import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.beans.Situacion;
import com.dnsoft.inmobiliaria.beans.TipoContrato;
import com.dnsoft.inmobiliaria.beans.TipoPagoAlquiler;
import com.dnsoft.inmobiliaria.beans.TipoReajuste;
import com.dnsoft.inmobiliaria.beans.TipoReajusteAlquilerEnum;
import com.dnsoft.inmobiliaria.daos.IContratoDAO;
import com.dnsoft.inmobiliaria.daos.IRecibosDAO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class CalculaRecibos {

    IRecibosDAO recibosDAO;
    IContratoDAO contratoDAO;
    Container container;

    public CalculaRecibos() {
        container = Container.getInstancia();
        recibosDAO = container.getBean(IRecibosDAO.class);
        contratoDAO = container.getBean(IContratoDAO.class);
    }

    public List<Recibo> generaRecibos(Contrato nuevoContrato) {

        List<Recibo> listRecibos = new ArrayList<>();

        if (nuevoContrato.getTipoContrato() == TipoContrato.ALQUILER) {

            listRecibos.addAll(contratoAlquiler(nuevoContrato));
            //calculaPeriodosRecibos(nuevoContrato, listRecibos, false);

        } else if (nuevoContrato.getTipoContrato() == TipoContrato.VENTA) {

            listRecibos.addAll(generaRecibosVenta(nuevoContrato));

        }

        return listRecibos;
    }

    private List<Recibo> generaRecibosVenta(Contrato nuevoContrato) {

        try {
            Boolean retur = null;
            int cantidadCuotas1 = nuevoContrato.getCantidadCuotas1();
            int cantidadCuotas2 = nuevoContrato.getCantidadCuotas2();
            int cantidadCuotas3 = nuevoContrato.getCantidadCuotas3();
            List<Recibo> listRecibos = new ArrayList<>();

            Calendar fechaVencimientos = Calendar.getInstance();
            fechaVencimientos.setTime(nuevoContrato.getVenciminetoPrimerCuota());
            BigDecimal valorRecibos = nuevoContrato.getValorCuota();
            BigDecimal valorRecibos2 = nuevoContrato.getValorCuotas2();
            BigDecimal valorRecibos3 = nuevoContrato.getValorCuotas3();

            fechaVencimientos.add(Calendar.DAY_OF_MONTH, nuevoContrato.getToleranciaMora());

            int j = 0;

            for (int i = 0; i <= cantidadCuotas1 - 1; i++) { // genera tantos recibos como el periodo
                j = j + 1;

                if (nuevoContrato.isNew()) {
                    retur = false;
                } else {
                    retur = recibosDAO.findPorNroReciboContrato(nuevoContrato, j);
                }

                if (retur == false) {

                    Recibo recibo = new Recibo();
                    recibo.setNroRecibo(j);
                    recibo.setCantidadRecibos(nuevoContrato.getCantidadCuotas());
                    recibo.setFechaEmision(new Date());
                    recibo.setFechaVencimiento(fechaVencimientos.getTime());
                    recibo.setSituacion(Situacion.PENDIENTE);
                    recibo.setContrato(nuevoContrato);
                    recibo.setSaldo(valorRecibos);
                    recibo.setValor(valorRecibos);
                    recibo.setMoneda(nuevoContrato.getMoneda());
                    recibo.setMora(BigDecimal.ZERO);

                    listRecibos.add(recibo);
                }
                fechaVencimientos.add(Calendar.MONTH, 1);//suma 1 mes para el proximo vencimineto
            }

            for (int i = 0; i <= cantidadCuotas2 - 1; i++) { // genera tantos recibos como el periodo
                j = j + 1;
                if (nuevoContrato.isNew()) {
                    retur = false;
                } else {
                    retur = recibosDAO.findPorNroReciboContrato(nuevoContrato, j);
                }

                if (retur == false) {
                    Recibo recibo = new Recibo();
                    recibo.setNroRecibo(j);
                    recibo.setCantidadRecibos(nuevoContrato.getCantidadCuotas());
                    recibo.setFechaEmision(new Date());
                    recibo.setFechaVencimiento(fechaVencimientos.getTime());
                    recibo.setSituacion(Situacion.PENDIENTE);
                    recibo.setContrato(nuevoContrato);
                    recibo.setSaldo(valorRecibos2);
                    recibo.setValor(valorRecibos2);
                    recibo.setMoneda(nuevoContrato.getMoneda());
                    recibo.setMora(BigDecimal.ZERO);

                    listRecibos.add(recibo);
                }
                fechaVencimientos.add(Calendar.MONTH, 1);//suma 1 mes para el proximo vencimineto
            }
            for (int i = 0; i <= cantidadCuotas3 - 1; i++) { // genera tantos recibos como el periodo
                j = j + 1;
                if (nuevoContrato.isNew()) {
                    retur = false;
                } else {
                    retur = recibosDAO.findPorNroReciboContrato(nuevoContrato, j);
                }
                retur = recibosDAO.findPorNroReciboContrato(nuevoContrato, j);
                if (retur == false) {
                    Recibo recibo = new Recibo();
                    recibo.setNroRecibo(j);
                    recibo.setCantidadRecibos(nuevoContrato.getCantidadCuotas());
                    recibo.setFechaEmision(new Date());
                    recibo.setFechaVencimiento(fechaVencimientos.getTime());
                    recibo.setSituacion(Situacion.PENDIENTE);
                    recibo.setContrato(nuevoContrato);
                    recibo.setSaldo(valorRecibos3);
                    recibo.setValor(valorRecibos3);
                    recibo.setMoneda(nuevoContrato.getMoneda());
                    recibo.setMora(BigDecimal.ZERO);

                    listRecibos.add(recibo);
                }
                fechaVencimientos.add(Calendar.MONTH, 1);//suma 1 mes para el proximo vencimineto
            }

            return listRecibos;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar recibos " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;

    }

    private List<Recibo> contratoAlquiler(Contrato nuevoContrato) {
        List<Recibo> listAlquileres = new ArrayList<>();
        TipoReajusteAlquilerEnum tipoReajusteEnum = nuevoContrato.getTipoReajuste().getTipoReajusteAlquilerEnum();
        TipoReajuste tipoReajuste = nuevoContrato.getTipoReajuste();
        Calendar fechaVencimientos = Calendar.getInstance();

        Calendar fechaFinContrato = Calendar.getInstance();
        fechaFinContrato.setTime(nuevoContrato.getFechaFin());

        Calendar fechaInicioContrato = Calendar.getInstance();
        fechaInicioContrato.setTime(nuevoContrato.getFechaInicio());

        BigDecimal valorAlquiler = nuevoContrato.getValorAlquiler();

        fechaVencimientos.setTime(nuevoContrato.getFechaInicio());
        fechaVencimientos.add(Calendar.DAY_OF_MONTH, nuevoContrato.getToleranciaMora());

        if (nuevoContrato.getTipoPagoAlquiler() == TipoPagoAlquiler.MES_VENCIDO) {
            fechaVencimientos.add(Calendar.MONTH, 1);

        }
        if (tipoReajusteEnum == TipoReajusteAlquilerEnum.COEFICIENTE_VARIABLE) {

            for (int i = 0; i < 12; i++) { // genera tantos recibos como el periodo

                //fechaVencimientos.setTime(nuevoContrato.getFechaInicio());
                Recibo recibo = new Recibo();
                recibo.setFechaEmision(new Date());
                recibo.setFechaVencimiento(fechaVencimientos.getTime());
                recibo.setSituacion(Situacion.PENDIENTE);
                recibo.setContrato(nuevoContrato);
                recibo.setSaldo(valorAlquiler);
                recibo.setValor(valorAlquiler);
                recibo.setMoneda(nuevoContrato.getMoneda());
                listAlquileres.add(recibo);
                fechaVencimientos.add(Calendar.MONTH, 1);//suma 1 mes a la fecha de inicio del contrato
            }

        } else if (tipoReajusteEnum == TipoReajusteAlquilerEnum.MANUAL) {

            Integer meses = new CalculaDiferenciaEnMeses().calculaDiferenciaEnMeses(fechaFinContrato, fechaInicioContrato);
            for (int i = 0; i < meses; i++) { // genera tantos recibos como el periodo

                //fechaVencimientos.setTime(nuevoContrato.getFechaInicio());
                Recibo recibo = new Recibo();
                recibo.setFechaEmision(new Date());
                recibo.setFechaVencimiento(fechaVencimientos.getTime());
                recibo.setSituacion(Situacion.PENDIENTE);
                recibo.setContrato(nuevoContrato);
                recibo.setSaldo(valorAlquiler);
                recibo.setValor(valorAlquiler);
                recibo.setMoneda(nuevoContrato.getMoneda());
                listAlquileres.add(recibo);
                fechaVencimientos.add(Calendar.MONTH, 1);//suma 1 mes a la fecha de inicio del contrato
            }

        } else if (tipoReajusteEnum == TipoReajusteAlquilerEnum.FIJO) {

            Integer meses = new CalculaDiferenciaEnMeses().calculaDiferenciaEnMeses(fechaFinContrato, fechaInicioContrato);
            int contadorPeriodicidad = 0;

            for (int i = 0; i < meses; i++) { // genera tantos recibos como el periodo

                if (contadorPeriodicidad == nuevoContrato.getTipoReajuste().getPeriodicidad()) {
                    contadorPeriodicidad = 0;
                    valorAlquiler = valorAlquiler.add(tipoReajuste.getValor()).setScale(2, RoundingMode.UP);
                }
                //fechaVencimientos.setTime(nuevoContrato.getFechaInicio());
                Recibo recibo = new Recibo();
                recibo.setFechaEmision(new Date());
                recibo.setFechaVencimiento(fechaVencimientos.getTime());
                recibo.setSituacion(Situacion.PENDIENTE);
                recibo.setContrato(nuevoContrato);
                recibo.setSaldo(valorAlquiler);
                recibo.setValor(valorAlquiler);
                recibo.setMoneda(nuevoContrato.getMoneda());
                listAlquileres.add(recibo);
                fechaVencimientos.add(Calendar.MONTH, 1);//suma 1 mes a la fecha de inicio del contrato
                contadorPeriodicidad++;
            }
        } else if (tipoReajusteEnum == TipoReajusteAlquilerEnum.PORCENTAJE) {

            Integer meses = new CalculaDiferenciaEnMeses().calculaDiferenciaEnMeses(fechaFinContrato, fechaInicioContrato);
            int contadorPeriodicidad = 0;

            for (int i = 0; i < meses; i++) { // genera tantos recibos como el periodo

                if (contadorPeriodicidad == nuevoContrato.getTipoReajuste().getPeriodicidad()) {
                    contadorPeriodicidad = 0;

                    valorAlquiler = valorAlquiler.add(valorAlquiler.multiply(tipoReajuste.getValor()).divide(new BigDecimal(100.00))).setScale(2, RoundingMode.UP);
                }
                //fechaVencimientos.setTime(nuevoContrato.getFechaInicio());
                Recibo recibo = new Recibo();
                recibo.setFechaEmision(new Date());
                recibo.setFechaVencimiento(fechaVencimientos.getTime());
                recibo.setSituacion(Situacion.PENDIENTE);
                recibo.setContrato(nuevoContrato);
                recibo.setSaldo(valorAlquiler);
                recibo.setValor(valorAlquiler);
                recibo.setMoneda(nuevoContrato.getMoneda());
                listAlquileres.add(recibo);
                fechaVencimientos.add(Calendar.MONTH, 1);//suma 1 mes a la fecha de inicio del contrato
                contadorPeriodicidad++;
            }
        }

        calculaPeriodosRecibos(nuevoContrato, listAlquileres, false);
        return listAlquileres;
    }

    public List<Recibo> reajusteAlquilerCoeficienteVariable(Contrato contratoSeleccionado, TipoReajuste tipoReajuste, BigDecimal valorReajuste) {

        List<Recibo> listAlquileres = new ArrayList<>();
        BigDecimal valorAlquiler = null;
        BigDecimal valorAlquilerInicioCalculo = contratoSeleccionado.getValorAlquiler();

        Calendar fechaVencimientos = Calendar.getInstance();

        Calendar fechaFinContrato = Calendar.getInstance();
        fechaFinContrato.setTime(contratoSeleccionado.getFechaFin());

        int primerRecibo = recibosDAO.findByContratoOrderByNroReciboDesc(contratoSeleccionado).get(0).getNroRecibo();

        valorAlquiler = contratoSeleccionado.getValorAlquiler().multiply(valorReajuste).setScale(2, RoundingMode.CEILING);
        contratoSeleccionado.setValorAlquiler(valorAlquiler);
        contratoDAO.save(contratoSeleccionado);
        BigDecimal incrementoAlquiler = valorAlquiler.subtract(valorAlquilerInicioCalculo);
        BigDecimal prorrateoIncremento = incrementoAlquiler.divide(new BigDecimal(12).divide(new BigDecimal(tipoReajuste.getPeriodicidad()), 2, RoundingMode.CEILING), 2, RoundingMode.CEILING).setScale(2, RoundingMode.CEILING);

        fechaVencimientos.setTime(contratoSeleccionado.getFechaReajuste());

        if (contratoSeleccionado.getTipoPagoAlquiler() == TipoPagoAlquiler.MES_VENCIDO) {
            fechaVencimientos.add(Calendar.MONTH, 1);
        }

        int contadorPeriodicidad = 0;
        valorAlquilerInicioCalculo = valorAlquilerInicioCalculo.add(prorrateoIncremento);

        for (int i = 0; i < 12; i++) { // genera 12 recibos por el año

            if (tipoReajuste.getPeriodicidad() == 12) {

                Recibo recibo = new Recibo();
                primerRecibo = primerRecibo + 1;
                recibo.setNroRecibo(primerRecibo);
                recibo.setFechaEmision(new Date());
                recibo.setFechaVencimiento(fechaVencimientos.getTime());
                recibo.setSituacion(Situacion.PENDIENTE);
                recibo.setContrato(contratoSeleccionado);
                recibo.setSaldo(valorAlquiler);
                recibo.setValor(valorAlquiler);
                recibo.setMoneda(contratoSeleccionado.getMoneda());
                listAlquileres.add(recibo);
                fechaVencimientos.add(Calendar.MONTH, 1);//suma 1 mes a la fecha de inicio del contrato
                contadorPeriodicidad++;

            } else if (tipoReajuste.getPeriodicidad() != 12) {
                if (contadorPeriodicidad == tipoReajuste.getPeriodicidad()) {
                    contadorPeriodicidad = 0;

                    valorAlquilerInicioCalculo = valorAlquilerInicioCalculo.add(prorrateoIncremento);

                }
                Recibo recibo = new Recibo();
                primerRecibo = primerRecibo + 1;
                recibo.setNroRecibo(primerRecibo);
                recibo.setFechaEmision(new Date());
                recibo.setFechaVencimiento(fechaVencimientos.getTime());
                recibo.setSituacion(Situacion.PENDIENTE);
                recibo.setContrato(contratoSeleccionado);
                recibo.setSaldo(valorAlquilerInicioCalculo);
                recibo.setValor(valorAlquilerInicioCalculo);
                recibo.setMoneda(contratoSeleccionado.getMoneda());
                listAlquileres.add(recibo);
                fechaVencimientos.add(Calendar.MONTH, 1);//suma 1 mes a la fecha de inicio del contrato
                contadorPeriodicidad++;
            }
        }
        calculaPeriodosRecibos(contratoSeleccionado, listAlquileres, true);
        return listAlquileres;
    }

    void calculaPeriodosRecibos(Contrato contrato, List<Recibo> recibos, boolean reajuste) {
        Calendar periodoDesde = Calendar.getInstance();
        if (reajuste == false) {
            periodoDesde.setTime(contrato.getFechaInicio());
        } else {
            periodoDesde.setTime(contrato.getFechaReajuste());
        }

        Calendar periodoHasta = Calendar.getInstance();
        periodoHasta.setTime(contrato.getFechaInicio());
        //periodoHasta.setTime(contrato.getFechaInicio());
        periodoHasta.set(Calendar.DAY_OF_MONTH, periodoDesde.getActualMaximum(Calendar.DAY_OF_MONTH));

        if (contrato.getCierraMes() == true) {

            for (Recibo recibo : recibos) {
                int index = recibos.indexOf(recibo);
                if (reajuste == false) { //si es un calculo por reajuste no setea nro recibo
                    recibo.setNroRecibo(index + 1);
                }
                if (index == 0) { //al primer recibo calcula el cierre de mes

                    int m1 = periodoDesde.get(Calendar.DAY_OF_YEAR);
                    int m2 = periodoHasta.get(Calendar.DAY_OF_YEAR);
                    int diferencia = m2 - m1;
                    BigDecimal primerAlquilerCierraMes = (contrato.getValorAlquiler().multiply(new BigDecimal(diferencia)).divide(new BigDecimal(30), 2, RoundingMode.CEILING));//regla de 3 para calcular importe del recibo
                    recibo.setValor(primerAlquilerCierraMes);
                    recibo.setSaldo(primerAlquilerCierraMes);
                    recibo.setPeriodo_desde(periodoDesde.getTime());
                    recibo.setPeriodo_hasta(periodoHasta.getTime());
                } else {
                    recibo.setCantidadRecibos(recibos.size());
                    periodoDesde.add(Calendar.MONTH, 1); //le añade 1 mes a cada recibo
                    periodoDesde.set(Calendar.DAY_OF_MONTH, 1); //pone como periodo primer dia del mes
                    periodoHasta.add(Calendar.MONTH, 1);
                    //periodoHasta.set(Calendar.DAY_OF_MONTH, periodoHasta.getActualMaximum(Calendar.DAY_OF_MONTH)); //asigna el ultimo dia del mes como fin de periodo
                    recibo.setPeriodo_desde(periodoDesde.getTime());
                    recibo.setPeriodo_hasta(periodoHasta.getTime());
                }
            }
        } else {
            for (Recibo recibo : recibos) {
                int index = recibos.indexOf(recibo);
                if (reajuste == false) { //si es un calculo por reajuste no setea nro recibo
                    recibo.setNroRecibo(index + 1);
                }
                recibo.setCantidadRecibos(recibos.size());

                periodoDesde.add(Calendar.MONTH, 1); //le añade 1 mes a cada recibo
                periodoDesde.set(Calendar.DAY_OF_MONTH, 1); //pone como periodo primer dia del mes
                periodoHasta.add(Calendar.MONTH, 1);
                periodoHasta.set(Calendar.DAY_OF_MONTH, periodoHasta.getActualMaximum(Calendar.DAY_OF_MONTH)); //asigna el ultimo dia del mes como fin de periodo
                recibo.setPeriodo_desde(periodoDesde.getTime());
                recibo.setPeriodo_hasta(periodoHasta.getTime());

            }

        }

    }
}
