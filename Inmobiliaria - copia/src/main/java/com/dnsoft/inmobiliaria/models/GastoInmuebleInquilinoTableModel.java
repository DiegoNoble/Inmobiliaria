/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.GastoInmuebleInquilino;
import com.dnsoft.inmobiliaria.beans.Inmueble;
import com.dnsoft.inmobiliaria.beans.Inquilino;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.Rubro;
import com.dnsoft.inmobiliaria.beans.Situacion;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class GastoInmuebleInquilinoTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Fecha", "Rubro", "Inmueble", "Inquilino", "Moneda", "Valor", "Saldo", "Situación"};
    //lista para a manipulacao do objeto
    private List<GastoInmuebleInquilino> list;

    public GastoInmuebleInquilinoTableModel() {
        list = new LinkedList<>();
    }

    public GastoInmuebleInquilinoTableModel(List<GastoInmuebleInquilino> list) {
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
        GastoInmuebleInquilino c = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getFecha();
            case 1:
                return c.getRubro();
            case 2:
                return c.getInmueble();
            case 3:
                return c.getInquilino();
            case 4:
                return c.getMoneda();
            case 5:
                return c.getValor();
            case 6:
                return c.getSaldo();
            case 7:
                return c.getSituacion();
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
                return Rubro.class;
            case 2:
                return Inmueble.class;
            case 3:
                return Inquilino.class;
            case 4:
                return Moneda.class;
            case 5:
                return BigDecimal.class;
            case 6:
                return BigDecimal.class;
            case 7:
                return Situacion.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex==8;
        
    }


    public void agregar(GastoInmuebleInquilino gastoInmueblePropietario) {
        list.add(gastoInmueblePropietario);

        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }

    public void eliminar(int row) {
        list.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, GastoInmuebleInquilino gastoInmueblePropietario) {
        list.set(row, gastoInmueblePropietario);
        this.fireTableRowsUpdated(row, row);
    }

    public GastoInmuebleInquilino getCliente(int row) {
        return list.get(row);
    }

}
