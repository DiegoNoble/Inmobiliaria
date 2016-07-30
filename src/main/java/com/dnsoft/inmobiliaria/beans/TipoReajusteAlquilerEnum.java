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
public enum TipoReajusteAlquilerEnum {

    COEFICIENTE_VARIABLE("Coeficiente variable"), FIJO("Fijo"), PORCENTAJE("Porcentaje"),MANUAL("Manual");

    private final String string;

    private TipoReajusteAlquilerEnum(String name) {
        string = name;
    }

    @Override
    public String toString() {
        return string;
    }

}
