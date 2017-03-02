/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.beans;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "historiales")

public class Historiales extends AbstractPersistable<Long> {

    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fechaEmision")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmision;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "costo")
    private Float costo;
    @Column(name = "responsable")
    private String responsable;
    @Column(name = "pago")
    private Boolean pago;
    @Column(name = "fechaPago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;
    @JoinColumn(name = "contrato_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contrato contratoId;
    @JoinColumn(name = "tipohistorico_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Tipohistoricos tipohistoricoId;

    public Historiales() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Float getCosto() {
        return costo;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Boolean getPago() {
        return pago;
    }

    public void setPago(Boolean pago) {
        this.pago = pago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Contrato getContratoId() {
        return contratoId;
    }

    public void setContratoId(Contrato contratoId) {
        this.contratoId = contratoId;
    }

    public Tipohistoricos getTipohistoricoId() {
        return tipohistoricoId;
    }

    public void setTipohistoricoId(Tipohistoricos tipohistoricoId) {
        this.tipohistoricoId = tipohistoricoId;
    }

}
