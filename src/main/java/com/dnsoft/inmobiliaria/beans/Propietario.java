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
@Table(name = "propietarios")

public class Propietario extends AbstractPersistable<Long> {

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipodocumento_id")
    private TipoDocumento tipoDocumento;
    @Column(name = "documento")
    private String documento;

    @Column(name = "email")
    private String email;
    @Column(name = "pagairpf", columnDefinition = "tinyint default false")
    private Boolean retenerIrpf;

    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CuentaBancoPropietario> listCuentas;

    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PropietarioHasInmueble> propietarioHasInmueble;

    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<GastoInmueblePropietario> listGastos;

    @Column(name = "activo", columnDefinition = "tinyint default true")
    private Boolean activo;
    
    @Column(name = "cod_referencia", length = 25)
    private String codReferencia;

    public Propietario() {
    }

    public Propietario(Long id, String codReferencia, String nombre, String documento, Boolean activo, String direccion, String tel, String cel) {
        this.id = id;
        this.codReferencia = codReferencia;
        this.nombre = nombre;
        this.documento = documento;
        this.activo = activo;
        this.direccion = direccion;
        this.tel = tel;
        this.cel = cel;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Boolean getRetenerIrpf() {
        return retenerIrpf;
    }

    public void setRetenerIrpf(Boolean retenerIrpf) {
        this.retenerIrpf = retenerIrpf;
    }

    public List<PropietarioHasInmueble> getPropietarioHasInmueble() {
        return propietarioHasInmueble;
    }

    public void setPropietarioHasInmueble(List<PropietarioHasInmueble> propietarioHasInmueble) {
        this.propietarioHasInmueble = propietarioHasInmueble;
    }

    public List<CuentaBancoPropietario> getListCuentas() {
        return listCuentas;
    }

    public void setListCuentas(List<CuentaBancoPropietario> listCuentas) {
        this.listCuentas = listCuentas;
    }

    public List<GastoInmueblePropietario> getListGastos() {
        return listGastos;
    }

    public void setListGastos(List<GastoInmueblePropietario> listGastos) {
        this.listGastos = listGastos;
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
        return nombre;
    }

}
