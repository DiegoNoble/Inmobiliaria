/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.Cotizaciones;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class CotizacionTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Fecha", "Valor",""};
    //lista para a manipulacao do objeto
    private List<Cotizaciones> listCotizacion;

    public CotizacionTableModel() {
        listCotizacion = new LinkedList<Cotizaciones>();
    }

    public CotizacionTableModel(List<Cotizaciones> listCotizacion) {
        this.listCotizacion = listCotizacion;
    }

    //numero de linhas
    public int getRowCount() {
        return listCotizacion.size();
    }

    //numero de colunas
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conter� do objeto
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cotizaciones c = listCotizacion.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getFecha();
            case 1:
                return c.getValor();
            default:
                return null;
        }
    }

    //determina o nome das colunas
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    //determina que tipo de objeto cada coluna ir� suportar
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Date.class;
            case 1:
                return BigDecimal.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return ((Cotizaciones) getCotizacion(rowIndex)).isNew();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Cotizaciones c = listCotizacion.get(rowIndex);
        switch (columnIndex) {
            case 0:
                c.setFecha((Date) aValue);
                break;
            case 1:
                c.setValor((BigDecimal) aValue);
                break;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void agregar(Cotizaciones cotizacion) {
        listCotizacion.add(cotizacion);

        this.fireTableRowsInserted(listCotizacion.size() - 1, listCotizacion.size() - 1);
    }

    public void eliminar(int row) {
        listCotizacion.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Cotizaciones cotizacion) {
        listCotizacion.set(row, cotizacion);
        this.fireTableRowsUpdated(row, row);
    }

    public Cotizaciones getCotizacion(int row) {
        return listCotizacion.get(row);
    }
    
    

}
