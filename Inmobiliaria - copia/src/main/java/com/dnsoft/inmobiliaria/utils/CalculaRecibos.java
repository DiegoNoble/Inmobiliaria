/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.utils;

import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.beans.Situacion;
import com.dnsoft.inmobiliaria.beans.StatusInmueble;
import com.dnsoft.inmobiliaria.beans.TipoContrato;
import com.dnsoft.inmobiliaria.beans.TipoReajusteAlquilerEnum;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego Noble
 */
public class CalculaRecibos {

    public List<Recibo> generaRecibos(Contrato nuevoContrato) {

        List<Recibo> listRecibos = new ArrayList<>();

        if (nuevoContrato.getTipoContrato() == TipoContrato.ALQUILER) {

            if (nuevoContrato.getTipoReajuste().getTipoReajusteAlquilerEnum() == TipoReajusteAlquilerEnum.FIJO) {

                listRecibos.addAll(contratoAlquilerConReajusteFijo(nuevoContrato));

            } else if (nuevoContrato.getTipoReajuste().getTipoReajusteAlquilerEnum() == TipoReajusteAlquilerEnum.COEFICIENTE_VARIABLE) {

                listRecibos.addAll(contratoAlquilerConReajusteVariable(nuevoContrato));

            }

        } else if (nuevoContrato.getTipoContrato() == TipoContrato.VENTA) {

            listRecibos.addAll(generaRecibosVenta(nuevoContrato));

        }

        return listRecibos;
    }

    private List<Recibo> generaRecibosVenta(Contrato nuevoContrato) {

        try {

            List<Recibo> listRecibos = new ArrayList<>();

            Calendar fechaFinContrato = Calendar.getInstance(); // inicia variable
            fechaFinContrato.setTime(nuevoContrato.getFechaFin()); //asigna valor segun el contrato

            Calendar fechaInicioContrato = Calendar.getInstance(); //idem arriba
            fechaInicioContrato.setTime(nuevoContrato.getFechaInicio());

            int mesesDeDiferencia = new CalculaDiferenciaEnMeses().calculaDiferenciaEnMeses(fechaFinContrato, fechaInicioContrato); //pasa a entero la diferencia
            //entre las fechas, este entero representa la cantidad de recibos a emitir     
            Calendar fechaVencimientos = Calendar.getInstance();
            BigDecimal valorRecibos = nuevoContrato.getSaldoCompra().divide(BigDecimal.valueOf(mesesDeDiferencia), 2, RoundingMode.CEILING).setScale(2, RoundingMode.CEILING);
            //divide el salto del contrato entre la cantidad de recibos, este será el valor de cada cuota

            BigDecimal valorEntrega = nuevoContrato.getValorEntrega();

            if (valorEntrega.doubleValue() != 0.0 && valorEntrega != null) { //en caso que exista una entrega genera un recibo individual
                Recibo reciboEntrega = new Recibo();
                reciboEntrega.setFechaEmision(new Date());
                reciboEntrega.setFechaVencimiento(nuevoContrato.getFechaInicio());
                reciboEntrega.setStatusAlquiler(Situacion.PENDIENTE);
                reciboEntrega.setContrato(nuevoContrato);
                reciboEntrega.setSaldo(nuevoContrato.getValorEntrega());
                reciboEntrega.setValor(nuevoContrato.getValorEntrega());
                reciboEntrega.setMoneda(nuevoContrato.getMoneda());
                listRecibos.add(reciboEntrega);
            }

            fechaVencimientos.setTime(nuevoContrato.getFechaInicio());
            fechaVencimientos.add(Calendar.DAY_OF_MONTH, nuevoContrato.getToleranciaMora());

            for (int i = 0; i <= mesesDeDiferencia - 1; i++) { // genera tantos recibos como el periodo

                fechaVencimientos.add(Calendar.MONTH, 1);//suma 1 mes a la fecha de inicio del contrato(más la tolerancia)

                Recibo recibo = new Recibo();
                recibo.setFechaEmision(new Date());
                recibo.setFechaVencimiento(fechaVencimientos.getTime());
                recibo.setStatusAlquiler(Situacion.PENDIENTE);
                recibo.setContrato(nuevoContrato);
                recibo.setSaldo(valorRecibos);
                recibo.setValor(valorRecibos);
                recibo.setMoneda(nuevoContrato.getMoneda());

                listRecibos.add(recibo);
            }
            return listRecibos;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar recibos " + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return null;

    }

    private List<Recibo> contratoAlquilerConReajusteFijo(Contrato nuevoContrato) {
        List<Recibo> listAlquileres = new ArrayList<>();

        Calendar fechaVencimientos = Calendar.getInstance();

        Calendar fechaFinContrato = Calendar.getInstance();
        fechaFinContrato.setTime(nuevoContrato.getFechaFin());

        int mesesDeDiferencia = new CalculaDiferenciaEnMeses().calculaDiferenciaEnMeses(fechaFinContrato, fechaVencimientos);
        int mes = 0;
        int periodoReajuste = nuevoContrato.getTipoReajuste().getPeriodicidad();
        int contadorPeriodoReajuste = 0;
        BigDecimal valorAlquiler = nuevoContrato.getValorAlquiler();

        fechaVencimientos.setTime(nuevoContrato.getFechaInicio());
        fechaVencimientos.add(Calendar.DAY_OF_MONTH, nuevoContrato.getToleranciaMora());
        for (int i = 0; i < nuevoContrato.getTipoReajuste().getPeriodogeneracion() && i <= mesesDeDiferencia; i++) { // genera tantos recibos como el periodo
            mes++;

            if (periodoReajuste == contadorPeriodoReajuste) {

                BigDecimal reajuste = nuevoContrato.getTipoReajuste().getValor();
                valorAlquiler = valorAlquiler.add(reajuste);
                contadorPeriodoReajuste = 0;

            }

            fechaVencimientos.setTime(nuevoContrato.getFechaInicio());
            fechaVencimientos.add(Calendar.MONTH, mes);//suma 1 mes a la fecha de inicio del contrato
            Recibo recibo = new Recibo();
            recibo.setFechaEmision(new Date());
            recibo.setFechaVencimiento(fechaVencimientos.getTime());
            recibo.setStatusAlquiler(Situacion.PENDIENTE);
            recibo.setContrato(nuevoContrato);
            recibo.setSaldo(valorAlquiler);
            recibo.setValor(valorAlquiler);
            recibo.setMoneda(nuevoContrato.getMoneda());
            listAlquileres.add(recibo);
            contadorPeriodoReajuste++;
        }
        return listAlquileres;
    }

    private List<Recibo> contratoAlquilerConReajusteVariable(Contrato nuevoContrato) {
        List<Recibo> listAlquileres = new ArrayList<>();

        Calendar fechaFinContrato = Calendar.getInstance(); // inicia variable
        fechaFinContrato.setTime(nuevoContrato.getFechaFin()); //asigna valor segun el contrato

        Calendar fechaInicioContrato = Calendar.getInstance(); //idem arriba
        fechaInicioContrato.setTime(nuevoContrato.getFechaInicio());

        int mesesDeDiferencia = new CalculaDiferenciaEnMeses().calculaDiferenciaEnMeses(fechaFinContrato, fechaInicioContrato); //pasa a entero la diferencia
        //entre las fechas, este entero representa la cantidad de recibos a emitir     
        Calendar fechaVencimientos = Calendar.getInstance();
        BigDecimal valorAlquiler = nuevoContrato.getValorAlquiler();

        fechaVencimientos.setTime(nuevoContrato.getFechaInicio());
        fechaVencimientos.add(Calendar.DAY_OF_MONTH, nuevoContrato.getToleranciaMora());
        
        for (int i = 0; i <= mesesDeDiferencia - 1; i++) { // genera tantos recibos como el periodo

            fechaVencimientos.add(Calendar.MONTH, 1);//suma 1 mes a la fecha de inicio del contrato(más la tolerancia)

            Recibo recibo = new Recibo();
            recibo.setFechaEmision(new Date());
            recibo.setFechaVencimiento(fechaVencimientos.getTime());
            recibo.setStatusAlquiler(Situacion.PENDIENTE);
            recibo.setContrato(nuevoContrato);
            recibo.setSaldo(valorAlquiler);
            recibo.setValor(valorAlquiler);
            recibo.setMoneda(nuevoContrato.getMoneda());
            listAlquileres.add(recibo);
        }
        return listAlquileres;

    }
}
