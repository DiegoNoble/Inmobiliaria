/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.PagoRecibo;
import com.dnsoft.inmobiliaria.beans.TipoPago;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class PagoAlquilerTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Fecha", "Tipo de Pago", "Valor", "Honorarios", "Aplica Mora", "Anular Pago"};
    //lista para a manipulacao do objeto
    private final List<PagoRecibo> listPagoAlquileres;

    public PagoAlquilerTableModel() {
        listPagoAlquileres = new LinkedList<>();
    }

    public PagoAlquilerTableModel(List<PagoRecibo> listPagoAlquileres) {
        this.listPagoAlquileres = listPagoAlquileres;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listPagoAlquileres.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PagoRecibo c = listPagoAlquileres.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getFecha();
            case 1:
                return c.getTipoPago();
            case 2:
                return c.getValor();
            case 3:
                return c.getComisionTotal();
            case 4:
                return c.getAmplicaMora();
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
                return TipoPago.class;
            case 2:
                return BigDecimal.class;
            case 3:
                return BigDecimal.class;
            case 4:
                return Boolean.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return columnIndex == 5;

    }

    public void agregar(PagoRecibo recibos) {
        listPagoAlquileres.add(recibos);

        this.fireTableRowsInserted(listPagoAlquileres.size() - 1, listPagoAlquileres.size() - 1);
    }

    public void eliminar(int row) {
        listPagoAlquileres.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, PagoRecibo recibos) {
        listPagoAlquileres.set(row, recibos);
        this.fireTableRowsUpdated(row, row);
    }

    public PagoRecibo getCliente(int row) {
        return listPagoAlquileres.get(row);
    }

}
