/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.utils;

import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.Recibos;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Diego Noble
 */
public class CalculaRecibos {

    public List<Recibos> generaRecibos(Contrato nuevoContrato) {

        List<Recibos> listRecibos = new ArrayList<>();
        Calendar fechaVencimientos = Calendar.getInstance();

        Calendar fechaFinContrato = Calendar.getInstance();
        fechaFinContrato.setTime(nuevoContrato.getFechaFin());

        int mesesDeDiferencia = new CalculaDiferenciaEnMeses().CalculaDiferenciaEnMeses(fechaFinContrato, fechaVencimientos);

        int mes = 0;
        int periodoReajuste = nuevoContrato.getTipoReajuste().getPeriodicidad();
        int contadorPeriodoReajuste = 0;
        BigDecimal valorAlquiler = nuevoContrato.getValorAlquiler();
        for (int i = 0; i < nuevoContrato.getTipoReajuste().getPeriodogeneracion() && i <= mesesDeDiferencia; i++) { // genera tantos recibos como el periodo
            mes++;

            if (periodoReajuste == contadorPeriodoReajuste) {
                if (nuevoContrato.getTipoReajuste().getCoeficiente() == true) {
                    BigDecimal reajuste = ((new BigDecimal(100)).add(nuevoContrato.getTipoReajuste().getValor())).divide(new BigDecimal(100));

                    valorAlquiler = valorAlquiler.multiply(reajuste);
                    contadorPeriodoReajuste = 0;
                } else if (nuevoContrato.getTipoReajuste().getCoeficiente() == false) {
                    BigDecimal reajuste = nuevoContrato.getTipoReajuste().getValor();
                    valorAlquiler = valorAlquiler.add(reajuste);
                    contadorPeriodoReajuste = 0;
                }

            }

            fechaVencimientos.setTime(nuevoContrato.getFechaInicio());
            fechaVencimientos.add(Calendar.MONTH, mes);//suma 1 mes a la fecha de inicio del contrato
            Recibos recibo = new Recibos();
            recibo.setFechaEmision(new Date());
            recibo.setFechaPago(null);
            recibo.setFechaVencimiento(fechaVencimientos.getTime());
            recibo.setPago(false);
            recibo.setContrato(nuevoContrato);

            recibo.setValor(valorAlquiler);
            listRecibos.add(recibo);
            contadorPeriodoReajuste++;
        }

        return listRecibos;
    }

}
