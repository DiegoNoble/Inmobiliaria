/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.beans;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Diego Noble
 */
@Entity
@Table(name = "parametros")

public class Parametros extends AbstractPersistable<Long> {

    @Column(name = "valor_minimo_comision_pesos")
    private BigDecimal minimoComisionPesos;

    @Column(name = "valor_minimo_comision_dolares")
    private BigDecimal minimoComisionDolares;

    @Column(name = "valor_minimo_comision_ui")
    private BigDecimal minimoComisionUI;

    @Column(name = "valor_minimo_comision_UR")
    private BigDecimal minimoComisionUR;

    @Column(name = "porcentage_retencion_irfp")
    private BigDecimal porcentageRetencionIRPF;
    @ManyToOne
    Rubro rubroAlquileres;
    @ManyToOne
    Rubro rubroAportes;
    @ManyToOne
    Rubro rubroRetirosPropietarios;
    @ManyToOne
    Rubro rubroRetirosPatronales;
    @ManyToOne
    Rubro rubroVales;
    @ManyToOne
    Rubro rubroTransferencias;
    @ManyToOne
    Rubro rubroInsumosOficina;
    @ManyToOne
    Rubro rubroInsumosCocinaLimpieza;
    @ManyToOne
    Rubro rubroSueldos;
    @ManyToOne
    Rubro rubroImpuestos;
    @ManyToOne
    Rubro rubroObrasMantenimiento;
    @ManyToOne
    Rubro rubroPagoFacturas;
    @ManyToOne
    Rubro cobroDeudaPopietario;

    public Parametros() {
    }

    public BigDecimal getMinimoComisionPesos() {
        return minimoComisionPesos;
    }

    public Rubro getRubroAlquileres() {
        return rubroAlquileres;
    }

    public void setRubroAlquileres(Rubro rubroPagoAlquileres) {
        this.rubroAlquileres = rubroPagoAlquileres;
    }

    public void setMinimoComisionPesos(BigDecimal minimoComision) {
        this.minimoComisionPesos = minimoComision;
    }

    public BigDecimal getPorcentageRetencionIRPF() {
        return porcentageRetencionIRPF;
    }

    public void setPorcentageRetencionIRPF(BigDecimal porcentageRetencionIRPF) {
        this.porcentageRetencionIRPF = porcentageRetencionIRPF;
    }

    public Rubro getRubroAportes() {
        return rubroAportes;
    }

    public void setRubroAportes(Rubro rubroAportes) {
        this.rubroAportes = rubroAportes;
    }

    public Rubro getRubroRetirosPropietarios() {
        return rubroRetirosPropietarios;
    }

    public void setRubroRetirosPropietarios(Rubro rubroRetirosPropietarios) {
        this.rubroRetirosPropietarios = rubroRetirosPropietarios;
    }

    public Rubro getRubroRetirosPatronales() {
        return rubroRetirosPatronales;
    }

    public void setRubroRetirosPatronales(Rubro rubroRetirosPatronales) {
        this.rubroRetirosPatronales = rubroRetirosPatronales;
    }

    public Rubro getRubroVales() {
        return rubroVales;
    }

    public void setRubroVales(Rubro rubroVales) {
        this.rubroVales = rubroVales;
    }

    public Rubro getRubroTransferencias() {
        return rubroTransferencias;
    }

    public void setRubroTransferencias(Rubro rubroTransferencias) {
        this.rubroTransferencias = rubroTransferencias;
    }

    public Rubro getRubroInsumosOficina() {
        return rubroInsumosOficina;
    }

    public void setRubroInsumosOficina(Rubro rubroInsumosOficina) {
        this.rubroInsumosOficina = rubroInsumosOficina;
    }

    public Rubro getRubroInsumosCocinaLimpieza() {
        return rubroInsumosCocinaLimpieza;
    }

    public void setRubroInsumosCocinaLimpieza(Rubro rubroInsumosCocinaLimpieza) {
        this.rubroInsumosCocinaLimpieza = rubroInsumosCocinaLimpieza;
    }

    public Rubro getRubroSueldos() {
        return rubroSueldos;
    }

    public void setRubroSueldos(Rubro rubroSueldos) {
        this.rubroSueldos = rubroSueldos;
    }

    public Rubro getRubroImpuestos() {
        return rubroImpuestos;
    }

    public void setRubroImpuestos(Rubro rubroImpuestos) {
        this.rubroImpuestos = rubroImpuestos;
    }

    public Rubro getRubroObrasMantenimiento() {
        return rubroObrasMantenimiento;
    }

    public void setRubroObrasMantenimiento(Rubro rubroObrasMantenimiento) {
        this.rubroObrasMantenimiento = rubroObrasMantenimiento;
    }

    public Rubro getRubroPagoFacturas() {
        return rubroPagoFacturas;
    }

    public void setRubroPagoFacturas(Rubro rubroPagoFacturas) {
        this.rubroPagoFacturas = rubroPagoFacturas;
    }

    public BigDecimal getMinimoComisionDolares() {
        return minimoComisionDolares;
    }

    public void setMinimoComisionDolares(BigDecimal minimoComisionDolares) {
        this.minimoComisionDolares = minimoComisionDolares;
    }

    public BigDecimal getMinimoComisionUI() {
        return minimoComisionUI;
    }

    public void setMinimoComisionUI(BigDecimal minimoComisionUI) {
        this.minimoComisionUI = minimoComisionUI;
    }

    public BigDecimal getMinimoComisionUR() {
        return minimoComisionUR;
    }

    public void setMinimoComisionUR(BigDecimal minimoComisionUR) {
        this.minimoComisionUR = minimoComisionUR;
    }

    public Rubro getCobroDeudaPopietario() {
        return cobroDeudaPopietario;
    }

    public void setCobroDeudaPopietario(Rubro cobroDeudaPopietario) {
        this.cobroDeudaPopietario = cobroDeudaPopietario;
    }
    
    

}
