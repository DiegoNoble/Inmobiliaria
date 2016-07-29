/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.PagoRecibo;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class PagoReciboTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Fecha", "Cód. Recibo", "Moneda", "Valor", "Mora", "Comisión"};
    //lista para a manipulacao do objeto
    private final List<PagoRecibo> listPagos;

    public PagoReciboTableModel() {
        listPagos = new LinkedList<>();
    }

    public PagoReciboTableModel(List<PagoRecibo> listPagos) {
        this.listPagos = listPagos;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listPagos.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PagoRecibo c = listPagos.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return c.getFecha();
            case 1:
                return c.getRecibo().getId();
            case 2:
                return c.getMoneda();
            case 3:
                return c.getValor();
            case 4:
                return c.getMora();
            case 5:
                return c.getComisionTotal();
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
                return Date.class;
            case 1:
                return Integer.class;
            case 2:
                return Moneda.class;
            case 3:
                return BigDecimal.class;
            case 4:
                return BigDecimal.class;
            case 5:
                return BigDecimal.class;

            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;
    }

    public void agregar(PagoRecibo recibos) {
        listPagos.add(recibos);

        this.fireTableRowsInserted(listPagos.size() - 1, listPagos.size() - 1);
    }

    public void eliminar(int row) {
        listPagos.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, PagoRecibo recibos) {
        listPagos.set(row, recibos);
        this.fireTableRowsUpdated(row, row);
    }

    public PagoRecibo getCliente(int row) {
        return listPagos.get(row);
    }

}
