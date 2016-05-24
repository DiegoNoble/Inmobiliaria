/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.utils;

import com.dnsoft.inmobiliaria.beans.Recibo;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 *
 * @author Diego Noble
 */
public class CalculaMora {

    Recibo alquier;
    BigDecimal interesMensual;
    BigDecimal importeInicial;

    public CalculaMora(Recibo alquier) {
        this.alquier = alquier;
    }

    /*public BigDecimal calcularMora() {

     //A pagar = (1+ Interes/100) * Importe Inicial
     importeFinal = importeInicial.add(moraGenerada());
     return importeFinal;

     }*/
    public BigDecimal moraGeneradaPagoParcial(BigDecimal pagoParcial) {
        BigDecimal moraGenerada = BigDecimal.valueOf(0);
        int diasAtraso = calculaDiasAtraso();
        importeInicial = pagoParcial;

        if (verificaSiAplicaMora() == false) {
            interesMensual = BigDecimal.valueOf(alquier.getContrato().getPorcentajeMoraTotal());

            //interes = (% interez mensual)*(Dias atraso/30)
            BigDecimal interesAplicar = interesMensual.multiply(BigDecimal.valueOf(diasAtraso).divide(new BigDecimal(30), 4, RoundingMode.CEILING));

            //Mora generada = (importe inicial * interes)/100
            moraGenerada = importeInicial.multiply(interesAplicar).divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.CEILING);
        }
        return moraGenerada;

    }

    public BigDecimal moraGenerada() {
        
        BigDecimal moraGenerada = BigDecimal.valueOf(0.0000);
        int diasAtraso = calculaDiasAtraso();
        importeInicial = alquier.getSaldo();

        if (verificaSiAplicaMora() == false) {
            interesMensual = BigDecimal.valueOf(alquier.getContrato().getPorcentajeMoraTotal());

            //interes = (% interez mensual)*(Dias atraso/30)
            BigDecimal interesAplicar = interesMensual.multiply(BigDecimal.valueOf(diasAtraso).divide(new BigDecimal(30), 4, RoundingMode.CEILING));

            //Mora generada = (importe inicial * interes)/100
            moraGenerada = importeInicial.multiply(interesAplicar).divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.CEILING);
        }
        return moraGenerada;

    }

    public boolean verificaSiAplicaMora() {

       // int tolerancia = alquier.getContrato().getToleranciaMora();
        Date vencimiento = alquier.getFechaVencimiento();

        DateTime vencimientoCalendar = new DateTime(vencimiento);
        DateTime fechaPago = new DateTime(new Date());

        int diasVencidos = Days.daysBetween(vencimientoCalendar, fechaPago).getDays();

        return 1 > diasVencidos;

    }

    private int calculaDiasAtraso() {

        //se aplica mora a partir de esa fecha que incluye la tolerancia
        //int tolerancia = alquier.getContrato().getToleranciaMora();
        Date vencimiento = alquier.getFechaVencimiento();

        Calendar calendarVencimiento = Calendar.getInstance();
        calendarVencimiento.setTime(vencimiento);
        //calendarTolerancia.add(Calendar.DAY_OF_YEAR, tolerancia);

        DateTime fechaVencimiento = new DateTime(calendarVencimiento);
        DateTime fechaPago = new DateTime(new Date());

        int diasAtrasados = Days.daysBetween(fechaVencimiento, fechaPago).getDays();

        return diasAtrasados;
    }

}
