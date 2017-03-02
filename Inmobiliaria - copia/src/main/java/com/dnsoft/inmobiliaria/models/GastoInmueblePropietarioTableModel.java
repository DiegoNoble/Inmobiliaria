/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.GastoInmueblePropietario;
import com.dnsoft.inmobiliaria.beans.Inmueble;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.Propietario;
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
public class GastoInmueblePropietarioTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Fecha", "Rubro", "Inmueble", "Propietario", "Moneda", "Valor", "Saldo", "Situación"};
    //lista para a manipulacao do objeto
    private List<GastoInmueblePropietario> list;

    public GastoInmueblePropietarioTableModel() {
        list = new LinkedList<>();
    }

    public GastoInmueblePropietarioTableModel(List<GastoInmueblePropietario> list) {
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
        GastoInmueblePropietario c = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getFecha();
            case 1:
                return c.getRubro();
            case 2:
                return c.getInmueble();
            case 3:
                return c.getPropietario();
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
                return Propietario.class;
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
        return columnIndex == 8;

    }

    public void agregar(GastoInmueblePropietario gastoInmueblePropietario) {
        list.add(gastoInmueblePropietario);

        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }

    public void eliminar(int row) {
        list.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, GastoInmueblePropietario gastoInmueblePropietario) {
        list.set(row, gastoInmueblePropietario);
        this.fireTableRowsUpdated(row, row);
    }

    public GastoInmueblePropietario getCliente(int row) {
        return list.get(row);
    }

}
