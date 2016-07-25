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

    @Column(insertable = false, updatable = false)
    private Long id;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column(name = "fecha_eUPDxtencion")
    @Temporal(TemporalType.DATE)
    private Date fechaExtencion;

    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @Column(name = "fecha_inactivacion")
    @Temporal(TemporalType.DATE)
    private Date fechaInactivacion;

    @Column(name = "vencimiento_primer_cuota")
    @Temporal(TemporalType.DATE)
    private Date venciminetoPrimerCuota;

    @Column(name = "activo", columnDefinition = "tinyint default true")
    private Boolean activo;

    @Column(name = "valorAlquiler")
    private BigDecimal valorAlquiler;

    @Column(name = "valorTotal")
    private BigDecimal valorTotal;

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

    @OneToMany(mappedBy = "contrato", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
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
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoReajuste tipoReajuste;

    @JoinColumn(name = "garantia_alquiler_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private GarantiaAlquiler garantiaAlquiler;
    private Moneda monedaGarantia;
    private BigDecimal montoGarantia;
    private String obsGarantia;

    @JoinColumn(name = "destino_alquiler_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private DestinoAlquiler destinoAlquiler;

    @Enumerated(EnumType.STRING)
    private TipoContrato tipoContrato;

    @Enumerated(EnumType.STRING)
    private TipoPagoAlquiler tipoPagoAlquiler;

    @Enumerated(EnumType.STRING)
    private TipoCotizacionContrato tipoCotizacionContrato;

    private BigDecimal cotizacion;

    private Boolean cierraMes;

    private String observaciones;

    private String descripcionInquilino;

    private String descripcionInmueble;

    private Integer cantidadCuotas;
    private BigDecimal valorCuota;

    private Integer cantidadCuotas1;
    private Integer cantidadCuotas2;
    private Integer cantidadCuotas3;

    private BigDecimal valorCuotas2;
    private BigDecimal valorCuotas3;

    @OneToOne
    private Iva iva;

    public Contrato() {

    }

    public Contrato(Long id, String descripcionInquilino, String descripcionInmueble, TipoContrato tipoContrato, BigDecimal valorCuota, BigDecimal valorAlquiler, Moneda moneda, Boolean activo) {
        this.id = id;
        this.descripcionInquilino = descripcionInquilino;
        this.activo = activo;
        this.valorAlquiler = valorAlquiler;
        this.descripcionInmueble = descripcionInmueble;
        this.moneda = moneda;
        this.tipoContrato = tipoContrato;
        this.valorCuota = valorCuota;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcionInquilino() {
        return descripcionInquilino;
    }

    public void setDescripcionInquilino(String descripcionInquilino) {
        this.descripcionInquilino = descripcionInquilino;
    }

    public String getDescripcionInmueble() {
        return descripcionInmueble;
    }

    public void setDescripcionInmueble(String descripcionInmueble) {
        this.descripcionInmueble = descripcionInmueble;
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

    public Date getVenciminetoPrimerCuota() {
        return venciminetoPrimerCuota;
    }

    public void setVenciminetoPrimerCuota(Date venciminetoPrimerCuota) {
        this.venciminetoPrimerCuota = venciminetoPrimerCuota;
    }

    public Integer getCantidadCuotas() {
        return cantidadCuotas;
    }

    public void setCantidadCuotas(Integer cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
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

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Boolean getCierraMes() {
        return cierraMes;
    }

    public void setCierraMes(Boolean cierraMes) {
        this.cierraMes = cierraMes;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public BigDecimal getValorCuota() {
        return valorCuota;
    }

    public void setValorCuota(BigDecimal valorCuota) {
        this.valorCuota = valorCuota;
    }

    public TipoCotizacionContrato getTipoCotizacionContrato() {
        return tipoCotizacionContrato;
    }

    public void setTipoCotizacionContrato(TipoCotizacionContrato tipoCotizacionContrato) {
        this.tipoCotizacionContrato = tipoCotizacionContrato;
    }

    public BigDecimal getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(BigDecimal cotizacion) {
        this.cotizacion = cotizacion;
    }

    public BigDecimal getValorCuotas2() {
        return valorCuotas2;
    }

    public void setValorCuotas2(BigDecimal valorCuotas2) {
        this.valorCuotas2 = valorCuotas2;
    }

    public Integer getCantidadCuotas1() {
        return cantidadCuotas1;
    }

    public void setCantidadCuotas1(Integer cantidadCuotas1) {
        this.cantidadCuotas1 = cantidadCuotas1;
    }

    public Integer getCantidadCuotas2() {
        return cantidadCuotas2;
    }

    public void setCantidadCuotas2(Integer cantidadCuotas2) {
        this.cantidadCuotas2 = cantidadCuotas2;
    }

    public Integer getCantidadCuotas3() {
        return cantidadCuotas3;
    }

    public void setCantidadCuotas3(Integer cantidadCuotas3) {
        this.cantidadCuotas3 = cantidadCuotas3;
    }

    public BigDecimal getValorCuotas3() {
        return valorCuotas3;
    }

    public void setValorCuotas3(BigDecimal valorCuotas3) {
        this.valorCuotas3 = valorCuotas3;
    }

    public GarantiaAlquiler getGarantiaAlquiler() {
        return garantiaAlquiler;
    }

    public void setGarantiaAlquiler(GarantiaAlquiler garantiaAlquiler) {
        this.garantiaAlquiler = garantiaAlquiler;
    }

    public Moneda getMonedaGarantia() {
        return monedaGarantia;
    }

    public void setMonedaGarantia(Moneda monedaGarantia) {
        this.monedaGarantia = monedaGarantia;
    }

    public BigDecimal getMontoGarantia() {
        return montoGarantia;
    }

    public void setMontoGarantia(BigDecimal montoGarantia) {
        this.montoGarantia = montoGarantia;
    }

    public String getObsGarantia() {
        return obsGarantia;
    }

    public void setObsGarantia(String obsGarantia) {
        this.obsGarantia = obsGarantia;
    }

    public DestinoAlquiler getDestinoAlquiler() {
        return destinoAlquiler;
    }

    public void setDestinoAlquiler(DestinoAlquiler destinoAlquiler) {
        this.destinoAlquiler = destinoAlquiler;
    }

    public Date getFechaInactivacion() {
        return fechaInactivacion;
    }

    public void setFechaInactivacion(Date fechaInactivacion) {
        this.fechaInactivacion = fechaInactivacion;
    }

    @Override
    public String toString() {

        return getId() + " " + getActivo() + " " + getValorAlquiler() + " " + getValorCuota() + " " + getInquilino().getNombre() + " " + getInmueble().toString() + " " + getMoneda() + " " + getTipoContrato();
    }

}
