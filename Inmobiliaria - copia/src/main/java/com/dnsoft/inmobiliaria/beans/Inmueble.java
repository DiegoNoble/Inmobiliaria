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
@Table(name = "inmueble")

public class Inmueble extends AbstractPersistable<Long> {

    @Column(name = "padron")
    private String padron;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorReferencia")
    private BigDecimal valorReferencia;

    @Column(name = "fechaconstruccion")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaconstruccion;

    @OneToMany(mappedBy = "inmueble", cascade = CascadeType.ALL)
    private List<InmuebleHasComodidades> inmuebleHasComodidades;

    @Column(name = "areapredio", length = 25)
    private String areapredio;

    @Column(name = "llaves", length = 25)
    private String llaves;

    @Column(name = "areaedificada", length = 25)
    private String areaedificada;

    @Column(name = "documentacion")
    private String documentacion;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "fraccionamiento", length = 25)
    private String fraccionamiento;

    @Column(name = "mansana", length = 25)
    private String manzana;

    @Column(name = "solar", length = 25)
    private String solar;

    @ManyToOne()
    @JoinColumn(name = "tipoinmueble_id")
    private TipoInmueble tipoinmueble;

    @ManyToOne()
    @JoinColumn(name = "calle_id")
    private Calle calle;

    @Column(name = "nro")
    private String nro;

    @ManyToOne()
    @JoinColumn(name = "barrio_id")
    private Barrio barrio;

    @ManyToOne()
    @JoinColumn(name = "ciudad_id")
    private Ciudad ciudad;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusInmueble", columnDefinition = "char(20) default 'DISPONIBLE'")
    private StatusInmueble statusInmueble;

    @OneToMany(mappedBy = "inmueble", cascade = CascadeType.ALL)
    //@LazyCollection(LazyCollectionOption.FALSE)
    private List<PropietarioHasInmueble> propietarioHasPropiedad;
    
    @Enumerated(EnumType.STRING)
    private TipoContrato tipoContrato;

    public Inmueble() {
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public String getPadron() {
        return padron;
    }

    public void setPadron(String padron) {
        this.padron = padron;
    }

    public BigDecimal getValorReferencia() {
        return valorReferencia;
    }

    public void setValorReferencia(BigDecimal valorReferencia) {
        this.valorReferencia = valorReferencia;
    }

    public List<PropietarioHasInmueble> getPropietarioHasPropiedad() {
        return propietarioHasPropiedad;
    }

    public void setPropietarioHasPropiedad(List<PropietarioHasInmueble> propietarioHasPropiedad) {
        this.propietarioHasPropiedad = propietarioHasPropiedad;
    }

    public StatusInmueble getStatuspropiedad() {
        return statusInmueble;
    }

    public void setStatuspropiedad(StatusInmueble statuspropiedad) {
        this.statusInmueble = statuspropiedad;
    }

    public Calle getCalle() {
        return calle;
    }

    public void setCalle(Calle calle) {
        this.calle = calle;
    }

    public Barrio getBarrio() {
        return barrio;
    }

    public void setBarrio(Barrio barrio) {
        this.barrio = barrio;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public String getAreapredio() {
        return areapredio;
    }

    public void setAreapredio(String areapredio) {
        this.areapredio = areapredio;
    }

    public String getAreaedificada() {
        return areaedificada;
    }

    public void setAreaedificada(String areaedificada) {
        this.areaedificada = areaedificada;
    }

    public String getDocumentacion() {
        return documentacion;
    }

    public void setDocumentacion(String documentacion) {
        this.documentacion = documentacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFraccionamiento() {
        return fraccionamiento;
    }

    public void setFraccionamiento(String fraccionamiento) {
        this.fraccionamiento = fraccionamiento;
    }

    public String getManzana() {
        return manzana;
    }

    public void setManzana(String manzana) {
        this.manzana = manzana;
    }

    public String getSolar() {
        return solar;
    }

    public void setSolar(String solar) {
        this.solar = solar;
    }

    public TipoInmueble getTipoinmueble() {
        return tipoinmueble;
    }

    public void setTipoinmueble(TipoInmueble tipoinmueble) {
        this.tipoinmueble = tipoinmueble;
    }

    public Date getFechaconstruccion() {
        return fechaconstruccion;
    }

    public void setFechaconstruccion(Date fechaconstruccion) {
        this.fechaconstruccion = fechaconstruccion;
    }

    public String getLlaves() {
        return llaves;
    }

    public void setLlaves(String llaves) {
        this.llaves = llaves;
    }

    public List<InmuebleHasComodidades> getInmuebleHasComodidades() {
        return inmuebleHasComodidades;
    }

    public void setInmuebleHasComodidades(List<InmuebleHasComodidades> inmuebleHasComodidades) {
        this.inmuebleHasComodidades = inmuebleHasComodidades;
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    
    @Override
    public String toString() {
        return calle + " " + nro + " Padrón " + padron;
    }

}
