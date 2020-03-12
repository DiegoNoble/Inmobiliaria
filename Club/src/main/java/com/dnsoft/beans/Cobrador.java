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
@Table(name = "tbcobrador")

public class Cobrador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "FECHAINGRESO")
    @Temporal(TemporalType.DATE)
    private Date fechaingreso;


    public Cobrador() {
    }

    public Cobrador(Integer id) {
        this.id = id;
    }

    public Cobrador(Integer id, String nombre, Date fechaingreso) {
        this.id = id;
        this.nombre = nombre;
        this.fechaingreso = fechaingreso;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
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
        if (!(object instanceof Cobrador)) {
            return false;
        }
        Cobrador other = (Cobrador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

   
}
