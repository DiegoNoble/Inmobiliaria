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
import javax.persistence.OneToOne;
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

    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;

    @Column(name = "fecha_extencion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaExtencion;

    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;

    @Column(name = "activo", columnDefinition = "tinyint default true")
    private Boolean activo;

    @Column(name = "valorAlquiler")
    private BigDecimal valorAlquiler;
    
    @Column(name = "saldoCompra")
    private BigDecimal saldoCompra;
    
    @Column(name = "valorTotal")
    private BigDecimal valorTotal;
    
    @Column(name = "valorEntrega")
    private BigDecimal valorEntrega;

    @Column(name = "porcentaje_comision")
    private Double porcentajeComision;

    @Column(name = "asegurado")
    private Boolean asegurado;

    @Column(name = "porcentaje_mora_total")
    private Double porcentajeMoraTotal;

    @Column(name = "porcentaje_mora_propietarios")
    private Double porcentajeMoraPropietarios;

    @Column(name = "porcentaje_mora_inmobiliaria")
    private Double porcentajeMoraInmobiliaria;

    @Column(name = "tolerancia_mora")
    private Integer toleranciaMora;

    @Column(name = "destino_mora")
    @Enumerated(EnumType.STRING)
    private DestinoMoraEnum destinoMora;

    @OneToMany(mappedBy = "contrato", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Recibo> recibosList;

    @JoinColumn(name = "inquilino_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Inquilino inquilino;

    @JoinColumn(name = "inmueble_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Inmueble inmueble;

    @Enumerated(EnumType.STRING)
    private Moneda moneda;

    @JoinColumn(name = "tiporeajuste_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoReajuste tipoReajuste;

    @Enumerated(EnumType.STRING)
    private TipoContrato tipoContrato;

    @Enumerated(EnumType.STRING)
    private TipoPagoAlquiler tipoPagoAlquiler;

    @OneToOne
    private Iva iva;

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

  

    public List<Recibo> getRecibosList() {
        return recibosList;
    }

    public void setRecibosList(List<Recibo> recibosList) {
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

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
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

    public DestinoMoraEnum getDestinoMora() {
        return destinoMora;
    }

    public void setDestinoMora(DestinoMoraEnum destinomora) {
        this.destinoMora = destinomora;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public TipoPagoAlquiler getTipoPagoAlquiler() {
        return tipoPagoAlquiler;
    }

    public void setTipoPagoAlquiler(TipoPagoAlquiler tipoPagoAlquiler) {
        this.tipoPagoAlquiler = tipoPagoAlquiler;
    }

    public Iva getIva() {
        return iva;
    }

    public void setIva(Iva iva) {
        this.iva = iva;
    }

    public BigDecimal getValorAlquiler() {
        return valorAlquiler;
    }

    public void setValorAlquiler(BigDecimal valorAlquiler) {
        this.valorAlquiler = valorAlquiler;
    }

    public BigDecimal getSaldoCompra() {
        return saldoCompra;
    }

    public void setSaldoCompra(BigDecimal saldoCompra) {
        this.saldoCompra = saldoCompra;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorEntrega() {
        return valorEntrega;
    }

    public void setValorEntrega(BigDecimal valorEntrega) {
        this.valorEntrega = valorEntrega;
    }

   

    
}
