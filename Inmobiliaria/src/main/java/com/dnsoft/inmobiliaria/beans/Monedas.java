/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Diego Noble
 */
@Entity
@Table(name = "monedas")

public class Monedas extends AbstractPersistable<Long> {

    @Column(name = "nombre")
    private String nombre;
    @Column(name = "simbolo")
    private String simbolo;
    @Column(name = "porcentage")
    private Boolean porcentage;

    public Monedas() {
    }

    public Monedas(String nombre, String simbolo, Boolean porcentage) {
        this.nombre = nombre;
        this.simbolo = simbolo;
        this.porcentage = porcentage;
    }



    public String getMoneda() {
        return nombre;
    }

    public void setMoneda(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public Boolean getPorcentage() {
        return porcentage;
    }

    public void setPorcentage(Boolean porcentage) {
        this.porcentage = porcentage;
    }
    

    @Override
    public String toString() {
        return nombre;
    }

}
