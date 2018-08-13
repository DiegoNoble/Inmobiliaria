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
public enum Moneda {
    PESOS("$U"), DOLARES("U$S"),UNIDADES_INDEXADAS("U.I."), UNIDADES_REAJUSTABLES("U.R.");
    
     private final String string;

    private Moneda(String name) {
        string = name;
    }

    @Override
    public String toString() {
        return string;
    }
   
}
