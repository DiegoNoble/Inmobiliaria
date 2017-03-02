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
public enum Mes {
    ENERO("Enero"),
    FEBRERO("Febrero"),
    MARZO("Marzo"),
    ABRIL("Abril"),
    MAYO("Mayo"),
    JUNIO("Junio"),
    JULIO("Julio"),
    AGOSTO("Agosto"),
    SETIEMBRE("Setiembre"),
    OCTUBRE("Octubre"),
    NOVIEMBRE("Noviembre"),
    DICIEMBRE("Diciembre");
    
     private final String string;

    private Mes(String name) {
        string = name;
    }

    @Override
    public String toString() {
        return string.toString();
    }


}
