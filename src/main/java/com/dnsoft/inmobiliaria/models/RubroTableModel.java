/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.Rubro;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class RubroTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Nombre", "Descripción"};
    //lista para a manipulacao do objeto
    private List<Rubro> list;

    public RubroTableModel() {
        list = new LinkedList<>();
    }

    public RubroTableModel(List<Rubro> list) {
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
        Rubro c = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getNombre();
            case 1:
                return c.getDescripcion();

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
                return String.class;

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
        Rubro c = list.get(rowIndex);

        switch (columnIndex) {
            case 0:
                c.setNombre((String) aValue);
                break;
            case 1:
                c.setDescripcion((String) aValue);
                break;
        }
        fireTableCellUpdated(rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }

    public void agregar(Rubro tipoGasto) {
        list.add(tipoGasto);

        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }

    public void eliminar(int row) {
        list.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Rubro tipoGasto) {
        list.set(row, tipoGasto);
        this.fireTableRowsUpdated(row, row);
    }

    public Rubro getCliente(int row) {
        return list.get(row);
    }

}
