/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.Inquilino;
import com.dnsoft.inmobiliaria.beans.TipoDocumento;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class InquilinosTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Nombre", "Apellido", "CI"};
    //lista para a manipulacao do objeto
    private List<Inquilino> listInquilinos;

    public InquilinosTableModel() {
        listInquilinos = new LinkedList<Inquilino>();
    }

    public InquilinosTableModel(List<Inquilino> listInquilinos) {
        this.listInquilinos = listInquilinos;
    }

    //numero de linhas
    public int getRowCount() {
        return listInquilinos.size();
    }

    //numero de colunas
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    public Object getValueAt(int rowIndex, int columnIndex) {
        Inquilino c = listInquilinos.get(rowIndex);
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

        return false;

    }

    public void agregar(Inquilino inquilinos) {
        listInquilinos.add(inquilinos);

        this.fireTableRowsInserted(listInquilinos.size() - 1, listInquilinos.size() - 1);
    }

    public void eliminar(int row) {
        listInquilinos.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Inquilino inquilinos) {
        listInquilinos.set(row, inquilinos);
        this.fireTableRowsUpdated(row, row);
    }

    public Inquilino getCliente(int row) {
        return listInquilinos.get(row);
    }

}
