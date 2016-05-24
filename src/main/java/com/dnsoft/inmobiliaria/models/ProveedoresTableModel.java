/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.Proveedor;
import com.dnsoft.inmobiliaria.beans.TipoDocumento;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class ProveedoresTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Nombre", "Apellido", "CI","Documento","Detalles"};
    //lista para a manipulacao do objeto
    private List<Proveedor> listProveedors;

    public ProveedoresTableModel() {
        listProveedors = new LinkedList<Proveedor>();
    }

    public ProveedoresTableModel(List<Proveedor> listProveedors) {
        this.listProveedors = listProveedors;
    }

    //numero de linhas
    public int getRowCount() {
        return listProveedors.size();
    }

    //numero de colunas
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    public Object getValueAt(int rowIndex, int columnIndex) {
        Proveedor c = listProveedors.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getNombre();
            case 1:
                return c.getApellidos();
            case 2:
                return c.getTipoDocumento();
            case 3:
                return c.getDocumento();
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
                return TipoDocumento.class;
            case 3:
                return String.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return columnIndex==4;

    }

    public void agregar(Proveedor proveedores) {
        listProveedors.add(proveedores);

        this.fireTableRowsInserted(listProveedors.size() - 1, listProveedors.size() - 1);
    }

    public void eliminar(int row) {
        listProveedors.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Proveedor proveedores) {
        listProveedors.set(row, proveedores);
        this.fireTableRowsUpdated(row, row);
    }

    public Proveedor getCliente(int row) {
        return listProveedors.get(row);
    }

}
