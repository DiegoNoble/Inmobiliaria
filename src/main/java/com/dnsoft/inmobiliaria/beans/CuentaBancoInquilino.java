/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
