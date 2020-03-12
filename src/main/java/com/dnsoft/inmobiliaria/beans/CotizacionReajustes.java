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
import javax.persistence.TemporalType;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Diego Noble
 */
@Entity
@Table(name = "cotizaciones_reajustes")

public class CotizacionReajustes extends AbstractPersistable<Long> {

    @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
    
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "valor", precision = 19, scale = 4)
    private BigDecimal valor;
    @ManyToOne
    private TipoReajuste tipoReajuste;

    @Column(name = "periodo")
    @Temporal(TemporalType.DATE)
    private Date periodo;

    public CotizacionReajustes() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public CotizacionReajustes(Date fecha, BigDecimal valor, TipoReajuste tipoReajuste, Date periodo) {
        this.fecha = fecha;
        this.valor = valor;
        this.tipoReajuste = tipoReajuste;
        this.periodo = periodo;
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

    public Date getFecha() {
        return fecha;
    }

    public Date getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Date periodo) {
        this.periodo = periodo;
    }

}
