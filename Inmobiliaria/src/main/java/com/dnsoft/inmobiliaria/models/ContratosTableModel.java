/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.Contrato;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class ContratosTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Código", "Cliente", "Inmueble", "Valor inicial", "Fecha inicio", "Fecha fin", "Fecha extension"};
    //lista para a manipulacao do objeto
    private List<Contrato> listContratos;

    public ContratosTableModel() {
        listContratos = new LinkedList<Contrato>();
    }

    public ContratosTableModel(List<Contrato> listContrato) {
        this.listContratos = listContrato;
    }

    //numero de linhas
    public int getRowCount() {
        return listContratos.size();
    }

    //numero de colunas
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    public Object getValueAt(int rowIndex, int columnIndex) {
        Contrato c = listContratos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getId();
            case 1:
                return c.getInquilino().getApellidos() + ", " + c.getInquilino().getNombre();
            case 2:
                return c.getInmueble().getCalle()+" "+c.getInmueble().getNro();
            case 3:
                return c.getMoneda().getMoneda() + " " + c.getValorAlquiler();
            case 4:
                return c.getFechaInicio();
            case 5:
                return c.getFechaFin();
            case 6:
                return c.getFechaExtencion();
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
                return Long.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return Date.class;
            case 5:
                return Date.class;
            case 6:
                return Date.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

    public void agregar(Contrato contrato) {
        listContratos.add(contrato);

        this.fireTableRowsInserted(listContratos.size() - 1, listContratos.size() - 1);
    }

    public void eliminar(int row) {
        listContratos.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Contrato contrato) {
        listContratos.set(row, contrato);
        this.fireTableRowsUpdated(row, row);
    }

    public Contrato getCliente(int row) {
        return listContratos.get(row);
    }

}
