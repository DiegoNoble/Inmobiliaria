/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Diego Noble
 */
@Entity
@Table(name = "pagos_propietarios")

public class PagoPropietario extends AbstractPersistable<Long> {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private BigDecimal valor;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pago_recibo_id")
    private PagoRecibo pagoAlquiler;
    @ManyToOne()
    private Propietario propietario;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fecha;
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private Moneda moneda;
    @Column(name = "adelanto_irpf")
    private BigDecimal adelantoIRPF;
    @Column(name = "retiene_inquilino")
    private Boolean retieneInquilino;
    @Column(name = "retiene_inmobiliaria")
    private Boolean retieneInmobiliaria;
    @Column(name = "comision")
    private BigDecimal comision;
    @Column(name = "mora_inmobiliaria")
    private BigDecimal moraInmobiliaria;
    @Column(name = "mora_propietario")
    private BigDecimal morapropietario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pagoPropietario")
    private List<CCPropietario> listCCPropietarios;

    public PagoPropietario() {
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

    public PagoRecibo getPagoAlquiler() {
        return pagoAlquiler;
    }

    public void setPagoAlquiler(PagoRecibo pagoAlquiler) {
        this.pagoAlquiler = pagoAlquiler;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getAdelantoIRPF() {
        return adelantoIRPF;
    }

    public void setAdelantoIRPF(BigDecimal adelantoIRPF) {
        this.adelantoIRPF = adelantoIRPF;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }


    public Boolean getRetieneInquilino() {
        return retieneInquilino;
    }

    public void setRetieneInquilino(Boolean retieneInquilino) {
        this.retieneInquilino = retieneInquilino;
    }

    public Boolean getRetieneInmobiliaria() {
        return retieneInmobiliaria;
    }

    public void setRetieneInmobiliaria(Boolean retieneInmobiliaria) {
        this.retieneInmobiliaria = retieneInmobiliaria;
    }

    public BigDecimal getMoraInmobiliaria() {
        return moraInmobiliaria;
    }

    public void setMoraInmobiliaria(BigDecimal moraInmobiliaria) {
        this.moraInmobiliaria = moraInmobiliaria;
    }

    public BigDecimal getMorapropietario() {
        return morapropietario;
    }

    public void setMorapropietario(BigDecimal morapropietario) {
        this.morapropietario = morapropietario;
    }

    public List<CCPropietario> getListCCPropietarios() {
        return listCCPropietarios;
    }

    public void setListCCPropietarios(List<CCPropietario> listCCPropietarios) {
        this.listCCPropietarios = listCCPropietarios;
    }

}
