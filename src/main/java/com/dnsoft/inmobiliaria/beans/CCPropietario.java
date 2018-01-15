/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.beans;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Diego Noble
 */
@Entity
@Table(name = "cc_propietario")

public class CCPropietario extends AbstractPersistable<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    private Propietario propietario;
    private BigDecimal debito;
    private BigDecimal credito;
    private BigDecimal saldo;
    private String descipcion;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    @Enumerated(EnumType.STRING)
    private Moneda moneda;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PagoPropietario pagoPropietario;
    @ManyToOne(fetch = FetchType.LAZY)
    private GastoInmueblePropietario gastoInmueblePropietario;

    public CCPropietario() {
    }

    public CCPropietario(BigDecimal debito, BigDecimal credito, BigDecimal saldo, Moneda moneda, Propietario propietario) {
        this.debito = debito;
        this.credito = credito;
        this.saldo = saldo;
        this.moneda = moneda;
        this.propietario = propietario;
    }

    public CCPropietario(Propietario propietario, BigDecimal debito, BigDecimal credito, BigDecimal saldo, String descipcion, Date fecha, Moneda moneda, GastoInmueblePropietario gastoInmueblePropietario) {
        this.propietario = propietario;
        this.debito = debito;
        this.credito = credito;
        this.saldo = saldo;
        this.descipcion = descipcion;
        this.fecha = fecha;
        this.moneda = moneda;
        this.gastoInmueblePropietario = gastoInmueblePropietario;
    }

    public CCPropietario(Propietario propietario, BigDecimal debito, BigDecimal credito, BigDecimal saldo, String descipcion, Date fecha, Moneda moneda, PagoPropietario pagoPropietario) {
        this.propietario = propietario;
        this.debito = debito;
        this.credito = credito;
        this.saldo = saldo;
        this.descipcion = descipcion;
        this.fecha = fecha;
        this.moneda = moneda;
        this.pagoPropietario = pagoPropietario;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getDebito() {
        return debito;
    }

    public void setDebito(BigDecimal debito) {
        this.debito = debito;
    }

    public BigDecimal getCredito() {
        return credito;
    }

    public void setCredito(BigDecimal credito) {
        this.credito = credito;
    }

    public String getDescipcion() {
        return descipcion;
    }

    public void setDescipcion(String descipcion) {
        this.descipcion = descipcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public PagoPropietario getPagoPropietario() {
        return pagoPropietario;
    }

    public void setPagoPropietario(PagoPropietario pagoPropietario) {
        this.pagoPropietario = pagoPropietario;
    }

    public GastoInmueblePropietario getGastoInmueblePropietario() {
        return gastoInmueblePropietario;
    }

    public void setGastoInmueblePropietario(GastoInmueblePropietario gastoInmueblePropietario) {
        this.gastoInmueblePropietario = gastoInmueblePropietario;
    }

}
