/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.Caja;
import com.dnsoft.inmobiliaria.beans.Moneda;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class CajaTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Fecha", "Rubro", "Entrada", "Salida", "Saldo"};
    //lista para a manipulacao do objeto
    private List<Caja> listMovimientos;
    DecimalFormat formatoPeso = new DecimalFormat("$ #,###.00");
    DecimalFormat formatoDolar = new DecimalFormat("U$S #,###.00");
    private static final NumberFormat formatoDolares = NumberFormat.getCurrencyInstance(new Locale("us", "USD"));
    private static final NumberFormat formatoPesos = NumberFormat.getCurrencyInstance(new Locale("uy", "UY"));

    public CajaTableModel() {
        listMovimientos = new LinkedList<Caja>();
    }

    public CajaTableModel(List<Caja> listMovimientos) {
        this.listMovimientos = listMovimientos;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listMovimientos.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DecimalFormat formato = null;
        Caja c = listMovimientos.get(rowIndex);
        if (c.getMoneda() == Moneda.DOLARES) {
            formato = formatoDolar;
        } else {
            formato = formatoPeso;
        }
        switch (columnIndex) {
            case 0:
                return c.getFecha();
            case 1:
                return c.getRubro().getNombre();
            case 2:
                return formato.format(c.getEntrada());
            case 3:
                return formato.format(c.getSalida());
            case 4:
                return formato.format(c.getSaldo());
            default:
                return null;
        }
    }

    //determina o nome das colunas
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    //determina que tipo de objeto cada coluna irï¿½ suportar
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Date.class;
            case 1:
                return String.class;
            case 2:
                return BigDecimal.class;
            case 3:
                return BigDecimal.class;
            case 4:
                return BigDecimal.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

}
