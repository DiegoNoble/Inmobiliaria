/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "tbcaja")

public class Caja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "CONCEPTO")
    private String concepto;
    @Column(name = "FECHA_MOVIMIENTO")
    @Temporal(TemporalType.DATE)
    private Date fechaMovimiento;
    @Column(name = "ENTRADA")
    private Double entrada;
    @Column(name = "SALIDA")
    private Double salida;
    @JoinColumn(name = "SECTOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Sectores sectores;
    @JoinColumn(name = "RUBRO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Rubro rubro;

    public Caja() {
    }
        
    public Caja(Integer id) {
        this.id = id;
    }

    public Caja(Integer id, String concepto, Date fechaMovimiento, Double entrada, Double salida, Sectores tbsectores, Rubro tbrubros) {
        this.id = id;
        this.concepto = concepto;
        this.fechaMovimiento = fechaMovimiento;
        this.entrada = entrada;
        this.salida = salida;
        this.sectores = tbsectores;
        this.rubro = tbrubros;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public Double getEntrada() {
        return entrada;
    }

    public void setEntrada(Double entrada) {
        this.entrada = entrada;
    }

    public Double getSalida() {
        return salida;
    }

    public void setSalida(Double salida) {
        this.salida = salida;
    }

    public Sectores getSectores() {
        return sectores;
    }

    public void setSectores(Sectores sectores) {
        this.sectores = sectores;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Caja other = (Caja) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
    
}
