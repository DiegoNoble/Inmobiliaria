/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.Banco;
import com.dnsoft.inmobiliaria.beans.CuentaBancoInquilino;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class CuentasBancosInquilinoTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Numero cuenta","Banco"};
    //lista para a manipulacao do objeto
    private List<CuentaBancoInquilino> list;

    public CuentasBancosInquilinoTableModel() {
        list = new LinkedList<CuentaBancoInquilino>();
    }

    public CuentasBancosInquilinoTableModel(List<CuentaBancoInquilino> list) {
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
        CuentaBancoInquilino c = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getNro();
            case 1:
                return c.getBanco();
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
                return Banco.class;
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
        CuentaBancoInquilino c = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                c.setNro((String) aValue);
                break;
            case 1:
                c.setBanco((Banco) aValue);
                break;
        }
        fireTableCellUpdated(rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }

    public void agregar(CuentaBancoInquilino cuenta) {
        list.add(cuenta);

        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }

    public void eliminar(int row) {
        list.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, CuentaBancoInquilino cuenta) {
        list.set(row, cuenta);
        this.fireTableRowsUpdated(row, row);
    }

    public CuentaBancoInquilino getCliente(int row) {
        return list.get(row);
    }

}
