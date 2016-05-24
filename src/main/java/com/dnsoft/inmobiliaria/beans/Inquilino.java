/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.beans;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Diego Noble
 */
@Entity
@Table(name = "inquilinos")

public class Inquilino extends AbstractPersistable<Long> {

    @Column(insertable = false, updatable = false)
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "tel")
    private String tel;
    @Column(name = "cel")
    private String cel;
    @Column(name = "documento")
    private String documento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipodocumento_id")
    private TipoDocumento tipoDocumento;
    @Column(name = "agenteretensorirpf", columnDefinition = "tinyint default false")
    private Boolean agenteRetensorIRPF;

    @OneToMany(mappedBy = "inquilino", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<CuentaBancoInquilino> listCuentas;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "inquilino", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<GastoInmuebleInquilino> listGastos;

    private String observaciones;

    @Column(name = "activo", columnDefinition = "tinyint default true")
    private Boolean activo;

    public Inquilino() {
    }

    public Inquilino(Long id, String nombre, String documento, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.activo = activo;
        this.documento = documento;
    }

    public Inquilino(Long id, String nombre,String documento, Boolean activo, String direccion, String tel, String cel ) {
        this.id = id;
        this.nombre = nombre;
        this.tel = tel;
        this.cel = cel;
        this.activo = activo;
        this.direccion = direccion;
        this.documento = documento;
    }
    
    

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Boolean getAgenteRetensorIRPF() {
        return agenteRetensorIRPF;
    }

    public void setAgenteRetensorIRPF(Boolean agenteRetensorIRPF) {
        this.agenteRetensorIRPF = agenteRetensorIRPF;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CuentaBancoInquilino> getListCuentas() {
        return listCuentas;
    }

    public void setListCuentas(List<CuentaBancoInquilino> listCuentas) {
        this.listCuentas = listCuentas;
    }

    public List<GastoInmuebleInquilino> getListGastos() {
        return listGastos;
    }

    public void setListGastos(List<GastoInmuebleInquilino> listGastos) {
        this.listGastos = listGastos;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return nombre + ", " + documento;
    }

}
