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
@Table(name = "cuenta_banco_inquilino")

public class CuentaBancoInquilino extends AbstractPersistable<Long> {

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
    @JoinColumn(name = "inquilino_id")
    private Inquilino inquilino;

    public CuentaBancoInquilino() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public CuentaBancoInquilino(String nro, Banco banco, Inquilino inquiilino) {
        this.nro = nro;
        this.banco = banco;
        this.inquilino = inquiilino;
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

    public Inquilino getInquiilino() {
        return inquilino;
    }

    public void setInquiilino(Inquilino inquiilino) {
        this.inquilino = inquiilino;
    }

    @Override
    public String toString() {
        return nro;
    }

}
