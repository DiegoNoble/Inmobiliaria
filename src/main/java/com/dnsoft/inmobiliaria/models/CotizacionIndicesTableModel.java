/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.CotizacionReajustes;
import com.dnsoft.inmobiliaria.beans.Mes;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class CotizacionIndicesTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Aplica al", "Fecha registro", "Valor"};
    //lista para a manipulacao do objeto
    private List<CotizacionReajustes> listCotizacionIndices;

    public CotizacionIndicesTableModel() {
        listCotizacionIndices = new LinkedList<>();
    }

    public CotizacionIndicesTableModel(List<CotizacionReajustes> listCotizacionIndices) {
        this.listCotizacionIndices = listCotizacionIndices;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listCotizacionIndices.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }
    

    //define o que cada coluna conter� do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CotizacionReajustes c = listCotizacionIndices.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getPeriodo();
            case 1:
                return c.getFecha();
            case 2:
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
                return Date.class;
            case 2:
                return BigDecimal.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return ((CotizacionReajustes) getCotizacionIndices(rowIndex)).isNew();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        CotizacionReajustes c = listCotizacionIndices.get(rowIndex);
        switch (columnIndex) {
            case 0:
                c.setPeriodo((Date) aValue);
                break;
            case 1:
                c.setFecha((Date) aValue);
                break;
            case 2:
                c.setValor((BigDecimal) aValue);
                break;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void agregar(CotizacionReajustes cotizacion) {
        listCotizacionIndices.add(cotizacion);

        this.fireTableRowsInserted(listCotizacionIndices.size() - 1, listCotizacionIndices.size() - 1);
    }

    public void eliminar(int row) {
        listCotizacionIndices.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, CotizacionReajustes cotizacion) {
        listCotizacionIndices.set(row, cotizacion);
        this.fireTableRowsUpdated(row, row);
    }

    public CotizacionReajustes getCotizacionIndices(int row) {
        return listCotizacionIndices.get(row);
    }

}
