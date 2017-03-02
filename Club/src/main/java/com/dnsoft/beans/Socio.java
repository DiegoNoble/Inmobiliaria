/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "tbsocio")

public class Socio implements Serializable {
    private static final long serialVersionUID = 1L;
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "FECHANACIMIENTO")
    @Temporal(TemporalType.DATE)
    private Date fechanacimiento;
    @Column(name = "SEXO")
    private String sexo;
    @Column(name = "ESTADOCIVIL")
    private String estadocivil;
    @Column(name = "PROFESION")
    private String profesion;
    @Column(name = "NACIONALIDAD")
    private String nacionalidad;
    @Column(name = "CIUDAD")
    private String ciudad;
    @Column(name = "CI")
    private String ci;
    @Column(name = "FECHAINGRESO")
    @Temporal(TemporalType.DATE)
    private Date fechaingreso;
    @Column(name = "SITUACION")
    private String situacion;
    @Column(name = "BARRIO")
    private String barrio;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "FOTO")
    private String foto;
    @Column(name = "HISTORIA")
    private String historia;
    @JoinColumn(name = "IDCOBRADOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cobrador Cobrador;    
    @JoinColumn(name = "IDCATEGORIA", referencedColumnName = "ID")
    @ManyToOne(optional=false)
    private Categoria Categoria;
    @Lob
    @Column(name="huella", nullable=false, columnDefinition="blob")
    private byte[] huella;
    private Integer tamano;
    private Integer calidad;

    public byte[] getHuella() {
        return huella;
    }

    public void setHuella(byte[] huella) {
        this.huella = huella;
    }

    public Integer getTamano() {
        return tamano;
    }

    public void setTamano(Integer tamano) {
        this.tamano = tamano;
    }

    public Integer getCalidad() {
        return calidad;
    }

    public void setCalidad(Integer calidad) {
        this.calidad = calidad;
    }


    public Socio() {
    }

    public Socio(Integer id) {
        this.id = id;
    }

    public Socio(Integer id, String nombre, String direccion, Date fechanacimiento, String sexo, String estadocivil, String profesion, String nacionalidad, String ciudad, String ci, Date fechaingreso, String situacion, String barrio, String telefono, String email, String foto, String historia, com.club.BEANS.Cobrador Cobrador, com.club.BEANS.Categoria Categoria) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechanacimiento = fechanacimiento;
        this.sexo = sexo;
        this.estadocivil = estadocivil;
        this.profesion = profesion;
        this.nacionalidad = nacionalidad;
        this.ciudad = ciudad;
        this.ci = ci;
        this.fechaingreso = fechaingreso;
        this.situacion = situacion;
        this.barrio = barrio;
        this.telefono = telefono;
        this.email = email;
        this.foto = foto;
        this.historia = historia;
        this.Cobrador = Cobrador;
        this.Categoria = Categoria;
    }

       public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }


    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    
    public com.club.BEANS.Cobrador getCobrador() {
        return Cobrador;
    }

    public void setCobrador(com.club.BEANS.Cobrador Cobrador) {
        this.Cobrador = Cobrador;
    }

    public com.club.BEANS.Categoria getCategoria() {
        return Categoria;
    }

    public void setCategoria(com.club.BEANS.Categoria Categoria) {
        this.Categoria = Categoria;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Socio other = (Socio) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
}
