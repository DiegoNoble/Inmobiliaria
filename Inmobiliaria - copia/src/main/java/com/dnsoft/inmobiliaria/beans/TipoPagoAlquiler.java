    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.beans;

/**
 *
 * @author Diego Noble
 */
public enum TipoPagoAlquiler {
    MES_ADELANTADO("Mes adelantado"), MES_VENCIDO("Mes vencido");
    
     private final String string;

    private TipoPagoAlquiler(String name) {
        string = name;
    }

    @Override
    public String toString() {
        return string;
    }
}
