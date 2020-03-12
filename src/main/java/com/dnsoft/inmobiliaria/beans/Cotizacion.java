/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Diego Noble
 */
@Entity
@Table(name = "cotizaciones")

public class Cotizacion extends AbstractPersistable<Long> implements Serializable {

    
    @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
    
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "valor", precision = 19, scale = 4)
    private BigDecimal valor;
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private Moneda moneda;

    public Cotizacion() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Cotizacion(BigDecimal valor) {
        this.valor = valor;
    }

    public Cotizacion(Date fecha, Moneda monedas) {
        this.fecha = fecha;
        this.moneda = monedas;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public Date getFecha() {
        return fecha;
    }

}
