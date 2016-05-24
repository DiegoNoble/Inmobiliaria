/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Diego Noble
 */
public final class CalculaAdelantoIRPF {


    public BigDecimal calculaIRPF(BigDecimal valorSinDescuentos) {
        
        BigDecimal irpf = BigDecimal.valueOf(10.5);
        BigDecimal valor = valorSinDescuentos;
        BigDecimal valorIRPF;
        BigDecimal baseCalculo = new BigDecimal(100);

        //((valor * %Comision)/ 100)*1.22
        valorIRPF = ((valor.multiply(irpf)).divide(baseCalculo)).setScale(2, RoundingMode.UP);

        return valorIRPF;
    }

}
