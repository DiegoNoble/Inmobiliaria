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
public enum DestinoGastoEnum {

    PROPIETARIO("Propietario"), INQUILINO("Inquilino"), INMOBILIARIA("Inmobiliaria"), PRORATEADO("Prorrateado en %");

    private final String string;

    private DestinoGastoEnum(String name) {
        string = name;
    }

    @Override
    public String toString() {
        return string;
    }

}
