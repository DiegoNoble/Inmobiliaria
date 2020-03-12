/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.beans;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Diego Noble
 */
@Entity
@Table(name = "retiros")

public class Retiro extends AbstractPersistable<Long> {

    @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
    
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "fecha")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    @ManyToOne
    private Recibo alquiler;
    private BigDecimal valor;

    public Retiro() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Recibo getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(Recibo alquiler) {
        this.alquiler = alquiler;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

}
