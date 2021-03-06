/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.beans;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Diego Noble
 */
@Entity
@Table(name = "pagos_gastos_inmuebles")

public class PagoGastoInmueble extends AbstractPersistable<Long> {

    @Column(name = "valor")
    private BigDecimal valor;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @ManyToOne
    private GastoInmueblePropietario gastoInmueblePropietario;
    @ManyToOne
    private GastoInmuebleInquilino gastoInmuebleInquilino;
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private Moneda moneda;
    @Enumerated(EnumType.STRING)
    private TipoPago tipoPago;

    public PagoGastoInmueble() {
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public GastoInmueblePropietario getGastoInmueblePropietario() {
        return gastoInmueblePropietario;
    }

    public void setGastoInmueblePropietario(GastoInmueblePropietario gastoInmueblePropietario) {
        this.gastoInmueblePropietario = gastoInmueblePropietario;
    }

    public GastoInmuebleInquilino getGastoInmuebleInquilino() {
        return gastoInmuebleInquilino;
    }

    public void setGastoInmuebleInquilino(GastoInmuebleInquilino gastoInmuebleInquilino) {
        this.gastoInmuebleInquilino = gastoInmuebleInquilino;
    }

    
    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

}
