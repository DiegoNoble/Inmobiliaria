/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.Ciudad;
import com.dnsoft.inmobiliaria.beans.Comodidad;
import com.dnsoft.inmobiliaria.beans.InmuebleHasComodidades;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class InmueblesHasComodidadesTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Comodidad", "Cantidad"};
    //lista para a manipulacao do objeto
    private List<InmuebleHasComodidades> list;

    public InmueblesHasComodidadesTableModel() {
        list = new LinkedList<InmuebleHasComodidades>();
    }

    public InmueblesHasComodidadesTableModel(List<InmuebleHasComodidades> list) {
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
        InmuebleHasComodidades c = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getComodidad();
            case 1:
                return c.getCantidad();

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
                return Comodidad.class;
            case 1:
                return Integer.class;
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        InmuebleHasComodidades c = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                c.setComodidad((Comodidad) aValue);
                break;
            case 1:
                c.setCantidad((Integer) aValue);
                break;

        }
        fireTableCellUpdated(rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;

    }

    public void agregar(InmuebleHasComodidades inmuebleHasComodidades) {
        list.add(inmuebleHasComodidades);

        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }

   

    public void eliminar(int row) {
        list.remove(row);
        this.fireTableDataChanged();
    }

    public void atualizar(int row, InmuebleHasComodidades inmuebleHasComodidades) {
        list.set(row, inmuebleHasComodidades);
        this.fireTableRowsUpdated(row, row);
    }

    public InmuebleHasComodidades getCliente(int row) {
        return list.get(row);
    }

}
