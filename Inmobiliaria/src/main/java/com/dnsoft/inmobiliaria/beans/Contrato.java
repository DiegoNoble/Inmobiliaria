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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Diego Noble
 */
@Entity
@Table(name = "contratos")

public class Contrato extends AbstractPersistable<Long> {

    @Column(name = "fechainicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;

    @Column(name = "fechaextencion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaExtencion;

    @Column(name = "fechafin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;

    @Column(name = "valorAlquiler")
    private BigDecimal valorAlquiler;

    @Column(name = "porcentajecomision")
    private Double porcentajeComision;

    @Column(name = "asegurado")
    private Boolean asegurado;

    @Column(name = "porcentajemoratotal")
    private Double porcentajeMoraTotal;

    @Column(name = "porcentajemorapropietarios")
    private Double porcentajeMoraPropietarios;

    @Column(name = "porcentajemorainmobiliaria")
    private Double porcentajeMoraInmobiliaria;

    @Column(name = "toleranciamora")
    private Integer toleranciaMora;

    @Column(name = "inquilinoagenteretencion")
    private Boolean inquilinoAgenteRetencion;

    @Column(name = "destinomora")
    @Enumerated(EnumType.STRING)
    private DestinoMoraEnum destinoMora;

    @OneToMany(mappedBy = "contrato", fetch = FetchType.EAGER)
    private List<Recibos> recibosList;

    @JoinColumn(name = "inquilino_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Inquilino inquilino;

    @JoinColumn(name = "inmueble_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Inmueble inmueble;

    @JoinColumn(name = "moneda_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Monedas moneda;

    @JoinColumn(name = "tiporeajuste_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoReajuste tipoReajuste;

    public Contrato() {
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechafin) {
        this.fechaFin = fechafin;
    }

    public BigDecimal getValorAlquiler() {
        return valorAlquiler;
    }

    public void setValorAlquiler(BigDecimal valorAlquiler) {
        this.valorAlquiler = valorAlquiler;
    }

    public List<Recibos> getRecibosList() {
        return recibosList;
    }

    public void setRecibosList(List<Recibos> recibosList) {
        this.recibosList = recibosList;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Monedas getMoneda() {
        return moneda;
    }

    public void setMoneda(Monedas moneda) {
        this.moneda = moneda;
    }

    public TipoReajuste getTipoReajuste() {
        return tipoReajuste;
    }

    public void setTipoReajuste(TipoReajuste tiporeajuste) {
        this.tipoReajuste = tiporeajuste;
    }

    public Date getFechaExtencion() {
        return fechaExtencion;
    }

    public void setFechaExtencion(Date fechaextencion) {
        this.fechaExtencion = fechaextencion;
    }

    public Double getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(Double porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }


    public Boolean getAsegurado() {
        return asegurado;
    }

    public void setAsegurado(Boolean asegurado) {
        this.asegurado = asegurado;
    }

    public Double getPorcentajeMoraTotal() {
        return porcentajeMoraTotal;
    }

    public void setPorcentajeMoraTotal(Double porcentajeMoraTotal) {
        this.porcentajeMoraTotal = porcentajeMoraTotal;
    }

    public Double getPorcentajeMoraPropietarios() {
        return porcentajeMoraPropietarios;
    }

    public void setPorcentajeMoraPropietarios(Double porcentajeMoraPropietarios) {
        this.porcentajeMoraPropietarios = porcentajeMoraPropietarios;
    }

    public Double getPorcentajeMoraInmobiliaria() {
        return porcentajeMoraInmobiliaria;
    }

    public void setPorcentajeMoraInmobiliaria(Double porcentajeMoraInmobiliaria) {
        this.porcentajeMoraInmobiliaria = porcentajeMoraInmobiliaria;
    }

    public Integer getToleranciaMora() {
        return toleranciaMora;
    }

    public void setToleranciaMora(Integer toleranciamora) {
        this.toleranciaMora = toleranciamora;
    }

    public Boolean getInquilinoAgenteRetencion() {
        return inquilinoAgenteRetencion;
    }

    public void setInquilinoAgenteRetencion(Boolean inquilinoagenteretencion) {
        this.inquilinoAgenteRetencion = inquilinoagenteretencion;
    }

    public DestinoMoraEnum getDestinoMora() {
        return destinoMora;
    }

    public void setDestinoMora(DestinoMoraEnum destinomora) {
        this.destinoMora = destinomora;
    }

}
