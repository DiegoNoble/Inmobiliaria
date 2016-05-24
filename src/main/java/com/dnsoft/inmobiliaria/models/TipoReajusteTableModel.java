/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.TipoReajuste;
import com.dnsoft.inmobiliaria.beans.TipoReajusteAlquilerEnum;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class TipoReajusteTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Nombre", "Descripción", "Tipo"};
    //lista para a manipulacao do objeto
    private List<TipoReajuste> list;

    public TipoReajusteTableModel() {
        list = new LinkedList<TipoReajuste>();
    }

    public TipoReajusteTableModel(List<TipoReajuste> list) {
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
        TipoReajuste c = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getNombre();
            case 1:
                return c.getDescripcion();
            case 2:
                return c.getTipoReajusteAlquilerEnum();
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
            case 2:
                return TipoReajusteAlquilerEnum.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return true;

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
