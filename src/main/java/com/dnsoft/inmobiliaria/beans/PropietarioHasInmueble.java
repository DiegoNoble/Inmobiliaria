/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.beans;

import java.math.BigDecimal;
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
@Table(name = "propietario_has_inmueble")

public class PropietarioHasInmueble extends AbstractPersistable<Long> {

    @Column(name = "porcentagePropiedad")
    private BigDecimal procentagePropiedad;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inmueble_id")
    private Inmueble inmueble;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "propietario_id")
    private Propietario propietario;

    public PropietarioHasInmueble() {
    }

    public PropietarioHasInmueble(BigDecimal procentagePropiedad, Inmueble inmueble, Propietario propietario) {
        this.procentagePropiedad = procentagePropiedad;
        this.inmueble = inmueble;
        this.propietario = propietario;
    }
    public PropietarioHasInmueble(BigDecimal procentagePropiedad, Inmueble inmueble) {
        this.procentagePropiedad = procentagePropiedad;
        this.inmueble = inmueble;
    }

    
    public BigDecimal getProcentagePropiedad() {
        return procentagePropiedad;
    }

    public void setProcentagePropiedad(BigDecimal procentagePropiedad) {
        this.procentagePropiedad = procentagePropiedad;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

}
