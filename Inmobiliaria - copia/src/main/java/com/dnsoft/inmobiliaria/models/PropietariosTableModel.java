/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.Propietario;
import com.dnsoft.inmobiliaria.beans.TipoDocumento;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class PropietariosTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Nombre", "Apellido", "Documento", "Detalles"};
    //lista para a manipulacao do objeto
    private List<Propietario> listPropietarios;

    public PropietariosTableModel() {
        listPropietarios = new LinkedList<Propietario>();
    }

    public PropietariosTableModel(List<Propietario> listPropietarios) {
        this.listPropietarios = listPropietarios;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listPropietarios.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Propietario c = listPropietarios.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getNombre();
            case 1:
                return c.getApellidos();
            case 2:
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
                return String.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return columnIndex == 3;

    }

    public void agregar(Propietario propietarios) {
        listPropietarios.add(propietarios);

        this.fireTableRowsInserted(listPropietarios.size() - 1, listPropietarios.size() - 1);
    }

    public void eliminar(int row) {
        listPropietarios.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Propietario propietarios) {
        listPropietarios.set(row, propietarios);
        this.fireTableRowsUpdated(row, row);
    }

    public Propietario getCliente(int row) {
        return listPropietarios.get(row);
    }

}
