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
 * @author Diego Noble
 */
@Entity
@Table(name = "cc_cobrador")
public class CcCobrador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha_movimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaMovimiento;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "debito")
    private Double debito;
    @Column(name = "credito")
    private Double credito;
    @JoinColumn(name = "ID_COBRADOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cobrador cobrador;

    public Cobrador getCobrador() {
        return cobrador;
    }

    public void setCobrador(Cobrador cobrador) {
        this.cobrador = cobrador;
    }

    public CcCobrador(Integer id, Date fechaMovimiento, String descripcion, Double debito, Double credito, Cobrador cobrador) {
        this.id = id;
        this.fechaMovimiento = fechaMovimiento;
        this.descripcion = descripcion;
        this.debito = debito;
        this.credito = credito;
        this.cobrador = cobrador;
    }

    public CcCobrador() {
    }

    public CcCobrador(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getDebito() {
        return debito;
    }

    public void setDebito(Double debito) {
        this.debito = debito;
    }

    public Double getCredito() {
        return credito;
    }

    public void setCredito(Double credito) {
        this.credito = credito;
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
        if (!(object instanceof CcCobrador)) {
            return false;
        }
        CcCobrador other = (CcCobrador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.club.BEANS.CcCobrador[ id=" + id + " ]";
    }
    
}
