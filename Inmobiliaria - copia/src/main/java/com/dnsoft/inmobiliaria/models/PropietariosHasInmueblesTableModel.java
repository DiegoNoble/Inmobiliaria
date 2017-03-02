/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.PropietarioHasInmueble;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class PropietariosHasInmueblesTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Nombre", "Apellido", "Dirección", "Padrón", "Situación", "Procentage %"};
    //lista para a manipulacao do objeto
    private List<PropietarioHasInmueble> list;

    public PropietariosHasInmueblesTableModel() {
        list = new LinkedList<PropietarioHasInmueble>();
    }

    public PropietariosHasInmueblesTableModel(List<PropietarioHasInmueble> listPropietariosHasPropiedads) {
        this.list = listPropietariosHasPropiedads;
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
        PropietarioHasInmueble c = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getPropietario().getNombre();
            case 1:
                return c.getPropietario().getApellidos();
            case 2:
                return c.getInmueble().getCalle() + " " + c.getInmueble().getNro() + ", " + c.getInmueble().getBarrio() + ", " + c.getInmueble().getCiudad();
            case 3:
                return c.getInmueble().getPadron();
            case 4:
                return c.getInmueble().getStatuspropiedad();
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
                return BigDecimal.class;
            default:
                return null;
        }
    }

   @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        PropietarioHasInmueble c = list.get(rowIndex);
        switch (columnIndex) {
            case 5:
                c.setProcentagePropiedad((BigDecimal) aValue);
            default:
        }
        fireTableCellUpdated(rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 5;

    }

    public void agregar(PropietarioHasInmueble propietariosHasPropiedad) {
        list.add(propietariosHasPropiedad);

        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }

    public void eliminar(int row) {
        list.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, PropietarioHasInmueble propietariosHasPropiedad) {
        list.set(row, propietariosHasPropiedad);
        this.fireTableRowsUpdated(row, row);
    }

    public PropietarioHasInmueble getCliente(int row) {
        return list.get(row);
    }

}
