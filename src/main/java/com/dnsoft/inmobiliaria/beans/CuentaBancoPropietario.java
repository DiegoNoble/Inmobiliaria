/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Diego Noble
 */
@Entity
@Table(name = "cuenta_banco_propietario")

public class CuentaBancoPropietario extends AbstractPersistable<Long> {

    @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
    
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "nro")
    private String nro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "banco_id")
    private Banco banco;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "propietario_id")
    private Propietario propietario;

    public CuentaBancoPropietario() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public CuentaBancoPropietario(String nro, Banco banco, Propietario propietario) {
        this.nro = nro;
        this.banco = banco;
        this.propietario = propietario;
    }

    @Override
    public String toString() {
        return nro;
    }

}
