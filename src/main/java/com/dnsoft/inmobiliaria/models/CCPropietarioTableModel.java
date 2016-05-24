/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.CCPropietario;
import com.dnsoft.inmobiliaria.beans.Propietario;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class CCPropietarioTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Fecha", "Detalle", "Débitos", "Créditos", "Saldo"};
    //lista para a manipulacao do objeto
    private final List<CCPropietario> list;

    public CCPropietarioTableModel() {
        list = new LinkedList<>();
    }

    public CCPropietarioTableModel(List<CCPropietario> list) {
        this.list = list;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return list.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CCPropietario c = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getFecha();
            case 1:
                return c.getDescipcion();
            case 2:
                return c.getDebito();
            case 3:
                return c.getCredito();
            case 4:
                return c.getSaldo();
            default:
                return null;
        }

    }

    //determina o nome das colunas
    @Override
    public String getColumnName(int column
    ) {
        return colunas[column];
    }

    //determina que tipo de objeto cada coluna irï¿½ suportar
    @Override
    public Class<?> getColumnClass(int columnIndex
    ) {
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

    public void agregar(CCPropietario gastoInmueblePropietario) {
        list.add(gastoInmueblePropietario);

        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }

    public void eliminar(int row) {
        list.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, CCPropietario gastoInmueblePropietario) {
        list.set(row, gastoInmueblePropietario);
        this.fireTableRowsUpdated(row, row);
    }

    public CCPropietario getCliente(int row) {
        return list.get(row);
    }

}
