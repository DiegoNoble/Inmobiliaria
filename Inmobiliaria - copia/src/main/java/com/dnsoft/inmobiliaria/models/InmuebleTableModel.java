/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.Inmueble;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class InmuebleTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Dirección","Tipo de contrato","Valor referencia", "Situación","Detalles"};
    //lista para a manipulacao do objeto
    private List<Inmueble> listInmuebles;

    public InmuebleTableModel() {
        listInmuebles = new LinkedList<Inmueble>();
    }

    public InmuebleTableModel(List<Inmueble> listPropiedades) {
        this.listInmuebles = listPropiedades;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listInmuebles.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Inmueble c = listInmuebles.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getCalle()+" "+c.getNro()+", "+c.getBarrio()+", "+c.getCiudad();
            case 1:
                return c.getTipoContrato();
            case 2:
                return c.getValorReferencia();
            case 3:
                return c.getStatuspropiedad();
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
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return columnIndex ==4|| columnIndex ==5;

    }

    public void agregar(Inmueble propiedad) {
        listInmuebles.add(propiedad);

        this.fireTableRowsInserted(listInmuebles.size() - 1, listInmuebles.size() - 1);
    }

    public void eliminar(int row) {
        listInmuebles.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Inmueble propiedad) {
        listInmuebles.set(row, propiedad);
        this.fireTableRowsUpdated(row, row);
    }

    public Inmueble getCliente(int row) {
        return listInmuebles.get(row);
    }

}
