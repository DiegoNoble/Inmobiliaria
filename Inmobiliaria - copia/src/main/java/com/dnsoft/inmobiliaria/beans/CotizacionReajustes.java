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
@Table(name = "cotizaciones_reajustes")

public class CotizacionReajustes extends AbstractPersistable<Long> {

    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "valor", precision = 19, scale = 4)
    private BigDecimal valor;
    @ManyToOne
    private TipoReajuste tipoReajuste;
    @Enumerated(value = EnumType.STRING)
    @Column(length = 9)
    private Mes mes;

    public CotizacionReajustes() {
    }

    public CotizacionReajustes(Date fecha, BigDecimal valor, TipoReajuste tipoReajuste, Mes mes) {
        this.fecha = fecha;
        this.valor = valor;
        this.tipoReajuste = tipoReajuste;
        this.mes = mes;
    }

    public TipoReajuste getTipoReajuste() {
        return tipoReajuste;
    }

    public void setTipoReajuste(TipoReajuste tipoReajuste) {
        this.tipoReajuste = tipoReajuste;
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

   
    public Mes getMes() {
        return mes;
    }

    public void setMes(Mes mes) {
        this.mes = mes;
    }

    public Date getFecha() {
        return fecha;
    }

}
