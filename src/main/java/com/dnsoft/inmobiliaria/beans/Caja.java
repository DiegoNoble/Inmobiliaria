/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.beans;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "caja")

public class Caja extends AbstractPersistable<Long> {

    @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
    
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "entrada")
    private BigDecimal entrada;
    @Column(name = "salida")
    private BigDecimal salida;
    @Column(name = "saldo")
    private BigDecimal saldo;
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @JoinColumn(name = "rubro_id")
    @ManyToOne(optional = false)
    private Rubro rubro;
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private Moneda moneda;
    @ManyToOne()
    private PagoRecibo pagoRecibo;
    @ManyToOne()
    private TipoDeCaja tipoDeCaja;

    public Caja() {
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

    public BigDecimal getEntrada() {
        return entrada;
    }

    public void setEntrada(BigDecimal entrada) {
        this.entrada = entrada;
    }

    public BigDecimal getSalida() {
        return salida;
    }

    public void setSalida(BigDecimal salida) {
        this.salida = salida;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public PagoRecibo getPagoRecibo() {
        return pagoRecibo;
    }

    public void setPagoRecibo(PagoRecibo pagoAlquiler) {
        this.pagoRecibo = pagoAlquiler;
    }

    public TipoDeCaja getTipoDeCaja() {
        return tipoDeCaja;
    }

    public void setTipoDeCaja(TipoDeCaja tipoDeCaja) {
        this.tipoDeCaja = tipoDeCaja;
    }

}
