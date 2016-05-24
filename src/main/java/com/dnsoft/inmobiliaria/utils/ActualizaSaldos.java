/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.utils;

import com.dnsoft.inmobiliaria.beans.CCPropietario;
import com.dnsoft.inmobiliaria.beans.Caja;
import java.math.BigDecimal;
import java.util.List;

public class ActualizaSaldos {

    List<CCPropietario> ccToReturnPropietario;
    List<Caja> cajaToReturn;

    public List<CCPropietario> ActualizaSaldosPropietarios(List<CCPropietario> CuentaCorrientePropietario) {

        BigDecimal saldo = BigDecimal.ZERO;
        this.ccToReturnPropietario = CuentaCorrientePropietario;

        for (CCPropietario movimiento : CuentaCorrientePropietario) {
            BigDecimal credito = movimiento.getCredito();

            BigDecimal debito = movimiento.getDebito();

            saldo = credito.subtract(debito).add(saldo);

            movimiento.setSaldo(saldo);
        }
        return ccToReturnPropietario;
    }

public List<Caja> ActualizaSaldosCaja(List<Caja> movimientosCaja) {

        BigDecimal saldo = BigDecimal.ZERO;
        this.cajaToReturn = movimientosCaja;

        for (Caja movimiento : movimientosCaja) {
            BigDecimal credito = movimiento.getSalida();

            BigDecimal debito = movimiento.getEntrada();

            saldo = credito.subtract(debito).add(saldo);

            movimiento.setSaldo(saldo);
        }
        return cajaToReturn;
    }
  

 
}
