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
public enum Situacion {
    PAGO("Pago"), PENDIENTE("Pendiente"), CON_SALDO("Con saldo");
    
     private final String string;

    private Situacion(String name) {
        string = name;
    }

    @Override
    public String toString() {
        return string;
    }
}
