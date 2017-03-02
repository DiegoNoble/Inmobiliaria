/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.utils;

import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.Cotizacion;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.Parametros;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Diego Noble
 */
public final class CalculaComision {

    public BigDecimal calculaComision(BigDecimal inporte, BigDecimal comision, Contrato contrato) {
        BigDecimal Comision = comision;
        BigDecimal iva = contrato.getIva().getValor();
        BigDecimal valor = inporte;
        BigDecimal valorComision = BigDecimal.ZERO;

        //((valor * %Comision)/ 100)*1.22
        valorComision = (valor.multiply(Comision).divide(new BigDecimal(100.00))).multiply(iva).setScale(2, RoundingMode.UP);
        return valorComision;
    }

    public BigDecimal calculaComisionTotal(BigDecimal inporte, BigDecimal comision, Parametros paramentros, Contrato contrato) {

        Moneda moneda = contrato.getMoneda();
        BigDecimal Comision = comision;
        BigDecimal iva = contrato.getIva().getValor();
        BigDecimal valor = inporte;
        BigDecimal valorComision;

        //((valor * %Comision)/ 100)*1.22
        valorComision = (valor.multiply(Comision).divide(new BigDecimal(100.00),2, RoundingMode.CEILING)).multiply(iva).setScale(2, RoundingMode.UP);
        switch (moneda) {
            case DOLARES:
                if (valorComision.doubleValue() < paramentros.getMinimoComisionDolares().doubleValue()) {
                    return paramentros.getMinimoComisionDolares();
                } else {
                    return valorComision;
                }
            //break;
            case PESOS:
                if (valorComision.doubleValue() < paramentros.getMinimoComisionPesos().doubleValue()) {
                    return paramentros.getMinimoComisionPesos();
                } else {
                    return valorComision;
                }
            //break;
            case UNIDADES_INDEXADAS:
                if (valorComision.doubleValue() < paramentros.getMinimoComisionUI().doubleValue()) {
                    return paramentros.getMinimoComisionUI();
                } else {
                    return valorComision;
                }
            // break;
            case UNIDADES_REAJUSTABLES:
                if (valorComision.doubleValue() < paramentros.getMinimoComisionUR().doubleValue()) {
                    return paramentros.getMinimoComisionUR();
                } else {
                    return valorComision;
                }
                //break;
            default:
                throw new AssertionError();
        }

    }
}
