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
@Table(name = "tipohistoricos")

public class Tipohistoricos extends AbstractPersistable<Long> {

    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "tipohistoricocol")
    private String tipohistoricocol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
    private List<Historiales> historialesList;

    public Tipohistoricos() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipohistoricocol() {
        return tipohistoricocol;
    }

    public void setTipohistoricocol(String tipohistoricocol) {
        this.tipohistoricocol = tipohistoricocol;
    }

    public List<Historiales> getHistorialesList() {
        return historialesList;
    }

    public void setHistorialesList(List<Historiales> historialesList) {
        this.historialesList = historialesList;
    }

}
