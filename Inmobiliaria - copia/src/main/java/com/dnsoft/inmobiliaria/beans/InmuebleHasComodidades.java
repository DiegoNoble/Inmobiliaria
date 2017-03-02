/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Diego Noble
 */
@Entity
@Table(name = "inmueble_has_comodidades")

public class InmuebleHasComodidades extends AbstractPersistable<Long> {

    @Column(name = "cantidad")
    private Integer cantidad;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comodidad_id")
    private Comodidad comodidad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inmueble_id")
    private Inmueble inmueble;

    public InmuebleHasComodidades() {
    }

    public InmuebleHasComodidades(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public InmuebleHasComodidades(Integer cantidad, Comodidad comodidad, Inmueble inmueble) {
        this.cantidad = cantidad;
        this.comodidad = comodidad;
        this.inmueble = inmueble;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Comodidad getComodidad() {
        return comodidad;
    }

    public void setComodidad(Comodidad comodidad) {
        this.comodidad = comodidad;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

  
}
