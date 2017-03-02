/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.utils;

import java.util.Calendar;

public class CalculaDiferenciaEnMeses {

    public int calculaDiferenciaEnMeses(Calendar fechaMayor, Calendar fechaMenor) {
        int months = (fechaMayor.get(Calendar.YEAR) - fechaMenor.get(Calendar.YEAR)) * 12
                + (fechaMayor.get(Calendar.MONTH) - fechaMenor.get(Calendar.MONTH))
                + (fechaMayor.get(Calendar.DAY_OF_MONTH) >= fechaMenor.get(Calendar.DAY_OF_MONTH) ? 0 : -1);
        return months;
    }

}
