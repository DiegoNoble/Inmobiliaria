/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.beans;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Diego Noble
 */
@Entity
@Table(name = "tiporeajuste")

public class TipoReajuste extends AbstractPersistable<Long> {

    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "periodicidad")
    private Integer periodicidad;
    @Column(name = "periodogeneracion")
    private Integer periodogeneracion;
    @Column(name = "valor")
    private BigDecimal valor;
    @Column(name = "coeficiente")
    private Boolean coeficiente;

    public TipoReajuste() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(Integer periodicidad) {
        this.periodicidad = periodicidad;
    }

    public Integer getPeriodogeneracion() {
        return periodogeneracion;
    }

    public void setPeriodogeneracion(Integer periodogeneracion) {
        this.periodogeneracion = periodogeneracion;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Boolean getCoeficiente() {
        return coeficiente;
    }

    public void setCoeficiente(Boolean coeficiente) {
        this.coeficiente = coeficiente;
    }

    @Override
    public String toString() {
        return descripcion;
    }

}
