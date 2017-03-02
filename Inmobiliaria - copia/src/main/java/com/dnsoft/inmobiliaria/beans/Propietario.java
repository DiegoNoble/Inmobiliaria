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

    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "tel")
    private String tel;
    @Column(name = "cel")
    private String cel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipodocumento_id")
    private TipoDocumento tipoDocumento;
    @Column(name = "documento")
    private String documento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calle_id")
    private Calle calle;

    @Column(name = "nro")
    private String nro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barrio_id")
    private Barrio barrio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciudad_id")
    private Ciudad ciudad;

    @Column(name = "email")
    private String email;
    @Column(name = "pagairpf", columnDefinition = "tinyint default false")
    private Boolean retenerIrpf;

    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CuentaBancoPropietario> listCuentas;

    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PropietarioHasInmueble> propietarioHasInmueble;

    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<GastoInmueblePropietario> listGastos;

    public Propietario() {
    }
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public Calle getCalle() {
        return calle;
    }

    public void setCalle(Calle calle) {
        this.calle = calle;
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
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

    @Override
    public String toString() {
        return apellidos + ", " + nombre;
    }

}
