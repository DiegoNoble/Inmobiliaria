/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.beans;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Diego Noble
 */
@Entity
@Table(name = "comodidades")

public class Comodidad extends AbstractPersistable<Long> {

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "comodidad", cascade = CascadeType.ALL)
    private List<InmuebleHasComodidades> inmuebleHasComodidades;

    public Comodidad() {
    }

    public Comodidad(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<InmuebleHasComodidades> getInmuebleHasComodidades() {
        return inmuebleHasComodidades;
    }

    public void setInmuebleHasComodidades(List<InmuebleHasComodidades> inmuebleHasComodidades) {
        this.inmuebleHasComodidades = inmuebleHasComodidades;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
