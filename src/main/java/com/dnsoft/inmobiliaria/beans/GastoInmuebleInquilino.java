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
@Table(name = "gasto_inmueble_inquilino")

public class GastoInmuebleInquilino extends AbstractPersistable<Long> {

    @Column(name = "fecha")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private Moneda moneda;
    private BigDecimal valor;
    private BigDecimal saldo;
    @Enumerated(EnumType.STRING)
    private Situacion situacion;
    @ManyToOne(fetch = FetchType.LAZY)
    private Inmueble inmueble;
    @ManyToOne(fetch = FetchType.LAZY)
    private Inquilino inquilino;
    @ManyToOne(fetch = FetchType.LAZY)
    private Rubro rubro;
    private String descripcion;
    @ManyToOne(fetch = FetchType.LAZY)
    private Proveedor proveedor;

    public GastoInmuebleInquilino() {
    }

    public GastoInmuebleInquilino(Date fecha, Inquilino inquilino) {
        this.fecha = fecha;
        this.inquilino = inquilino;
    }

    public GastoInmuebleInquilino(Date fecha, Inmueble inmueble, Inquilino inquilino) {
        this.fecha = fecha;
        this.inmueble = inmueble;
        this.inquilino = inquilino;
    }

    public GastoInmuebleInquilino(Date fecha, Moneda moneda, BigDecimal valor, Inmueble inmueble, Inquilino inquilino, Rubro rubro) {
        this.fecha = fecha;
        this.moneda = moneda;
        this.valor = valor;
        this.inmueble = inmueble;
        this.inquilino = inquilino;
        this.rubro = rubro;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Situacion getSituacion() {
        return situacion;
    }

    public void setSituacion(Situacion situacion) {
        this.situacion = situacion;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public String toString() {
        return descripcion;
    }

}
