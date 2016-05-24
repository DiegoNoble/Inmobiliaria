/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.beans;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Diego Noble
 */
@Entity
@Table(name = "pagos_recibos")

public class PagoRecibo extends AbstractPersistable<Long> {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private BigDecimal valor;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "comisionTotal")
    private BigDecimal comisionTotal;
    @Column(name = "aplicamora")
    private Boolean amplicaMora;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recibo_id", referencedColumnName = "id")
    private Recibo recibo;
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private Moneda moneda;
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private TipoPago tipoPago;
    @Column(name = "mora", columnDefinition = "Decimal(19,2) default '0.00'",nullable = true)
    private BigDecimal mora;
    @Column(name = "cotizacion",precision = 19, scale = 4)
    private BigDecimal cotizacion;

    public PagoRecibo() {
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getAmplicaMora() {
        return amplicaMora;
    }

    public void setAmplicaMora(Boolean amplicaMora) {
        this.amplicaMora = amplicaMora;
    }

    public Recibo getRecibo() {
        return recibo;
    }

    public BigDecimal getComisionTotal() {
        return comisionTotal;
    }

    public void setComisionTotal(BigDecimal comisionTotal) {
        this.comisionTotal = comisionTotal;
    }

    public void setRecibo(Recibo recibo) {
        this.recibo = recibo;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public BigDecimal getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(BigDecimal cotizacion) {
        this.cotizacion = cotizacion;
    }

    public BigDecimal getMora() {
        return mora;
    }

    public void setMora(BigDecimal mora) {
        this.mora = mora;
    }

}
