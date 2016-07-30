/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.beans;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Diego Noble
 */
@Entity
@Table(name = "tipo_reajuste")

public class TipoReajuste extends AbstractPersistable<Long> {

    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "periodicidad")
    private Integer periodicidad;

    private BigDecimal valor;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private TipoReajusteAlquilerEnum tipoReajusteAlquilerEnum;

    public TipoReajuste() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public TipoReajusteAlquilerEnum getTipoReajusteAlquilerEnum() {
        return tipoReajusteAlquilerEnum;
    }

    public void setTipoReajusteAlquilerEnum(TipoReajusteAlquilerEnum tipoReajusteAlquilerEnum) {
        this.tipoReajusteAlquilerEnum = tipoReajusteAlquilerEnum;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
