/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.Caja;
import com.dnsoft.inmobiliaria.beans.Propietario;
import com.dnsoft.inmobiliaria.beans.TipoReajuste;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class TipoReajusteTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Descripcion", "Periodicidad", "Periodo de generacion", "Coeficiente", "Valor"};
    //lista para a manipulacao do objeto
    private List<TipoReajuste> list;

    public TipoReajusteTableModel() {
        list = new LinkedList<TipoReajuste>();
    }

    public TipoReajusteTableModel(List<TipoReajuste> list) {
        this.list = list;
    }

    //numero de linhas
    public int getRowCount() {
        return list.size();
    }

    //numero de colunas
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    public Object getValueAt(int rowIndex, int columnIndex) {
        TipoReajuste c = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getDescripcion();
            case 1:
                return c.getPeriodicidad();
            case 2:
                return c.getPeriodogeneracion();
            case 3:
                return c.getCoeficiente();
            case 4:
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

    //determina que tipo de objeto cada coluna irï¿½ suportar
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Integer.class;
            case 2:
                return Integer.class;
            case 3:
                return Boolean.class;
            case 4:
                return BigDecimal.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return true;

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        TipoReajuste c = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                c.setDescripcion((String) aValue);
                break;
            case 1:
                c.setPeriodicidad((Integer) aValue);
                break;
            case 2:
                c.setPeriodogeneracion((Integer) aValue);
                break;
            case 3:
                c.setCoeficiente((Boolean) aValue);
                break;
            case 4:
                c.setValor((BigDecimal) aValue);
                break;
        }
        fireTableCellUpdated(rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }

    public void agregar(TipoReajuste tipoReajuste) {
        list.add(tipoReajuste);

        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }

    public void eliminar(int row) {
        list.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, TipoReajuste tipoReajuste) {
        list.set(row, tipoReajuste);
        this.fireTableRowsUpdated(row, row);
    }

    public TipoReajuste getCliente(int row) {
        return list.get(row);
    }

}
