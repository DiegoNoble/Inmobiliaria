/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.Recibos;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class RecibosTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Contador", "Código", "Fecha vencimiento", "Valor", "Fecha pago", "Status"};
    //lista para a manipulacao do objeto
    private List<Recibos> listRecibos;

    public RecibosTableModel() {
        listRecibos = new LinkedList<Recibos>();
    }

    public RecibosTableModel(List<Recibos> listRecibos) {
        this.listRecibos = listRecibos;
    }

    //numero de linhas
    public int getRowCount() {
        return listRecibos.size();
    }

    //numero de colunas
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    public Object getValueAt(int rowIndex, int columnIndex) {
        Recibos c = listRecibos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex+1;
            case 1:
                return c.getId();
            case 2:
                return c.getFechaVencimiento();
            case 3:
                return c.getContrato().getMoneda().getMoneda()+" "+c.getValor();
            case 4:
                return c.getFechaPago();
            case 5:
                return c.getPago();
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
                return Integer.class;
            case 1:
                return Long.class;
            case 2:
                return Date.class;
            case 3:
                return String.class;
            case 4:
                return Date.class;
            case 5:
                return Boolean.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

    public void agregar(Recibos recibos) {
        listRecibos.add(recibos);

        this.fireTableRowsInserted(listRecibos.size() - 1, listRecibos.size() - 1);
    }

    public void eliminar(int row) {
        listRecibos.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Recibos recibos) {
        listRecibos.set(row, recibos);
        this.fireTableRowsUpdated(row, row);
    }

    public Recibos getCliente(int row) {
        return listRecibos.get(row);
    }

}
