/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.PropietarioHasInmueble;
import com.dnsoft.inmobiliaria.beans.StatusInmueble;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class PropietariosHasInmueblesTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Nombre", "Apellido", "Dirección", "Padrón", "Status", "Procentage %"};
    //lista para a manipulacao do objeto
    private List<PropietarioHasInmueble> listPropietariosHasPropiedads;

    public PropietariosHasInmueblesTableModel() {
        listPropietariosHasPropiedads = new LinkedList<PropietarioHasInmueble>();
    }

    public PropietariosHasInmueblesTableModel(List<PropietarioHasInmueble> listPropietariosHasPropiedads) {
        this.listPropietariosHasPropiedads = listPropietariosHasPropiedads;
    }

    //numero de linhas
    public int getRowCount() {
        return listPropietariosHasPropiedads.size();
    }

    //numero de colunas
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    public Object getValueAt(int rowIndex, int columnIndex) {
        PropietarioHasInmueble c = listPropietariosHasPropiedads.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getPropietario().getNombre();
            case 1:
                return c.getPropietario().getApellidos();
            case 2:
                return c.getPropiedad().getCalle() + " " + c.getPropiedad().getNro() + ", " + c.getPropiedad().getBarrio() + ", " + c.getPropiedad().getCiudad();
            case 3:
                return c.getPropiedad().getPadron();
            case 4:
                return c.getPropiedad().getStatuspropiedad();
            case 5:
                return c.getProcentagePropiedad();
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
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return Integer.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 5;

    }

    public void agregar(PropietarioHasInmueble propietariosHasPropiedad) {
        listPropietariosHasPropiedads.add(propietariosHasPropiedad);

        this.fireTableRowsInserted(listPropietariosHasPropiedads.size() - 1, listPropietariosHasPropiedads.size() - 1);
    }

    public void eliminar(int row) {
        listPropietariosHasPropiedads.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, PropietarioHasInmueble propietariosHasPropiedad) {
        listPropietariosHasPropiedads.set(row, propietariosHasPropiedad);
        this.fireTableRowsUpdated(row, row);
    }

    public PropietarioHasInmueble getCliente(int row) {
        return listPropietariosHasPropiedads.get(row);
    }

}
