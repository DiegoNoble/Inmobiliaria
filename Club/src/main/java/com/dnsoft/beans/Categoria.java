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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "tbcategoria")

public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "DEFINICION")
    private String definicion;
    @Column(name = "FECHACRIACION")
    @Temporal(TemporalType.DATE)
    private Date fechacriacion;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MENSUALIDAD")
    private Double mensualidad;
 

    public Categoria() {
    }

    public Categoria(Integer id) {
        this.id = id;
    }

    public Categoria(Integer id, String definicion, Date fechacriacion, String descripcion, Double mensualidad) {
        this.id = id;
        this.definicion = definicion;
        this.fechacriacion = fechacriacion;
        this.descripcion = descripcion;
        this.mensualidad = mensualidad;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDefinicion() {
        return definicion;
    }

    public void setDefinicion(String definicion) {
        this.definicion = definicion;
    }

    public Date getFechacriacion() {
        return fechacriacion;
    }

    public void setFechacriacion(Date fechacriacion) {
        this.fechacriacion = fechacriacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getMensualidad() {
        return mensualidad;
    }

    public void setMensualidad(Double mensualidad) {
        this.mensualidad = mensualidad;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return definicion;
    }
    
}
