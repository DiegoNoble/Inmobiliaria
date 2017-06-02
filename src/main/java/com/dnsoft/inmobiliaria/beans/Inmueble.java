/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.beans;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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

    @Column(name = "fechaconstruccion")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaconstruccion;

    @Column(name = "areapredio", length = 25)
    private String areapredio;

    @Column(name = "areaedificada", length = 25)
    private String areaedificada;

    @Column(name = "documentacion")
    private String documentacion;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "fraccionamiento", length = 25)
    private String fraccionamiento;

    @Column(name = "activo", columnDefinition = "tinyint default true")
    private Boolean activo;

    @Column(name = "mansana", length = 25)
    private String manzana;

    @Column(name = "solar", length = 25)
    private String solar;

    @ManyToOne()
    @JoinColumn(name = "tipoinmueble_id")
    private TipoInmueble tipoinmueble;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "calle_id")
    private Calle calle;

    @Column(name = "nro")
    private String nro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "barrio_id")
    private Barrio barrio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ciudad_id")
    private Ciudad ciudad;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusInmueble", columnDefinition = "char(20) default 'DISPONIBLE'")
    private StatusInmueble statusInmueble;

    @OneToMany(mappedBy = "inmueble", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@LazyCollection(LazyCollectionOption.FALSE)
    private List<PropietarioHasInmueble> propietarioHasPropiedad;

    @Column(name = "cod_referencia", length = 25)
    private String codReferencia;

    public Inmueble() {
    }

    public Inmueble(String fraccionamiento, String manzana, String solar, Calle calle, String nro, Barrio barrio, Ciudad ciudad) {
        this.fraccionamiento = fraccionamiento;
        this.manzana = manzana;
        this.solar = solar;
        this.calle = calle;
        this.nro = nro;
        this.barrio = barrio;
        this.ciudad = ciudad;
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

    public List<PropietarioHasInmueble> getPropietarioHasPropiedad() {
        return propietarioHasPropiedad;
    }

    public void setPropietarioHasPropiedad(List<PropietarioHasInmueble> propietarioHasPropiedad) {
        this.propietarioHasPropiedad = propietarioHasPropiedad;
    }

    public StatusInmueble getStatusInmueble() {
        return statusInmueble;
    }

    public void setStatusInmueble(StatusInmueble statuspropiedad) {
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getCodReferencia() {
        return codReferencia;
    }

    public void setCodReferencia(String codReferencia) {
        this.codReferencia = codReferencia;
    }

    @Override
    public String toString() {

        if (tipoinmueble.getNombre().equals("Terreno")) {
            String padronToReturn = "";
            String manzanaToReturn = "";
            String solarToReturn = "";
            if (padron == null || "".equals(padron)) {
                padron = "";
            } else {
                padronToReturn = "Padrón ".concat(padron);
            }

            if (manzana == null || "".equals(manzana)) {
                manzana = "";
            } else {

                manzanaToReturn = "Manz. ".concat(manzana);
            }

            if (solar == null || "".equals(solar)) {
                solar = "";
            } else {

                solarToReturn = "Solar ".concat(solar);
            }
            return fraccionamiento + " " + manzanaToReturn + " " + solarToReturn + " " + padronToReturn;
        } else {
            return calle + " " + nro;
        }
    }

}
