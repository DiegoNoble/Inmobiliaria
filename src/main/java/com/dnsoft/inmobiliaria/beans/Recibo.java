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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "recibos")

public class Recibo extends AbstractPersistable<Long> {

    @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
    
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private BigDecimal valor;
    //precision = 19, scale = 4
    @Column(name = "saldo")
    private BigDecimal saldo;
    @Column(name = "fechaVencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;

    @Column(name = "fechaEmision")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmision;

    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @Temporal(TemporalType.DATE)
    private Date periodo_desde;

    @Temporal(TemporalType.DATE)
    private Date periodo_hasta;

    @Enumerated(EnumType.STRING)
    private Moneda moneda;

    @Enumerated(EnumType.STRING)
    private Situacion situacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contrato_id")
    private Contrato contrato;

    private Integer nroRecibo;
    private Integer cantidadRecibos;

    @Column(name = "mora", columnDefinition = "Decimal(19,2) default '0.00'", nullable = true)
    private BigDecimal mora;

    public Recibo() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public BigDecimal getMora() {
        return mora;
    }

    public void setMora(BigDecimal mora) {
        this.mora = mora;
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

    public void setSituacion(Situacion statusAlquiler) {
        this.situacion = statusAlquiler;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public Integer getNroRecibo() {
        return nroRecibo;
    }

    public void setNroRecibo(Integer nroRecibo) {
        this.nroRecibo = nroRecibo;
    }

    public Integer getCantidadRecibos() {
        return cantidadRecibos;
    }

    public void setCantidadRecibos(Integer cantidadRecibos) {
        this.cantidadRecibos = cantidadRecibos;
    }

    public Date getPeriodo_desde() {
        return periodo_desde;
    }

    public void setPeriodo_desde(Date periodo_desde) {
        this.periodo_desde = periodo_desde;
    }

    public Date getPeriodo_hasta() {
        return periodo_hasta;
    }

    public void setPeriodo_hasta(Date periodo_hasta) {
        this.periodo_hasta = periodo_hasta;
    }

}
