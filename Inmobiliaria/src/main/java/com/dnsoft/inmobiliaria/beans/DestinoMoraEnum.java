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
public enum DestinoMoraEnum {

    PROPIETARIO("Propietario"), INMOBILIARIA("Inmobiliaria"), PRORATEADO("Prorrateado en %");

    private String string;

    private DestinoMoraEnum(String name) {
        string = name;
    }

    public String toString() {
        return string;
    }

}
