/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.beans.Situacion;
import com.dnsoft.inmobiliaria.utils.CalculaMora;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class AlquileresTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Nro", "Cód", "Vencimiento", "Valor", "Saldo", "Mora", "Saldo total", "Situación", "Auxiliar",};
    //lista para a manipulacao do objeto
    private final List<Recibo> listAlquileres;
    private CalculaMora calcularMora;

    public AlquileresTableModel() {
        listAlquileres = new LinkedList<>();
    }

    public AlquileresTableModel(List<Recibo> listAlquileres) {
        this.listAlquileres = listAlquileres;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listAlquileres.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Recibo c = listAlquileres.get(rowIndex);
        calcularMora = new CalculaMora(c);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return c.getId();
            case 2:
                return c.getFechaVencimiento();
            case 3:
                return c.getValor();
            case 4:
                return c.getSaldo();
            case 5:
                return calcularMora.moraGenerada();
            case 6:
                return c.getSaldo().add(calcularMora.moraGenerada());
            case 7:
                return c.getStatusAlquiler();
            case 8:
                return calcularMora.verificaSiAplicaMora();

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
                return Integer.class;
            case 1:
                return Long.class;
            case 2:
                return Date.class;
            case 3:
                return BigDecimal.class;
            case 4:
                return BigDecimal.class;
            case 5:
                return BigDecimal.class;
            case 6:
                return BigDecimal.class;
            case 7:
                return Situacion.class;
            case 8:
                return Boolean.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

    public void agregar(Recibo recibos) {
        listAlquileres.add(recibos);

        this.fireTableRowsInserted(listAlquileres.size() - 1, listAlquileres.size() - 1);
    }

    public void eliminar(int row) {
        listAlquileres.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Recibo recibos) {
        listAlquileres.set(row, recibos);
        this.fireTableRowsUpdated(row, row);
    }

    public Recibo getCliente(int row) {
        return listAlquileres.get(row);
    }

}
