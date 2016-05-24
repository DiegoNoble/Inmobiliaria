/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.PagoGastoInmueble;
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
public class PagoGastoInmuebleTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Fecha", "Tipo de Pago", "Valor"};
    //lista para a manipulacao do objeto
    private final List<PagoGastoInmueble> listPagos;

    public PagoGastoInmuebleTableModel() {
        listPagos = new LinkedList<>();
    }

    public PagoGastoInmuebleTableModel(List<PagoGastoInmueble> listPagos) {
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
        PagoGastoInmueble c = listPagos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getFecha();
            case 1:
                return c.getTipoPago();
            case 2:
                return c.getValor();
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
                
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

    public void agregar(PagoGastoInmueble recibos) {
        listPagos.add(recibos);

        this.fireTableRowsInserted(listPagos.size() - 1, listPagos.size() - 1);
    }

    public void eliminar(int row) {
        listPagos.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, PagoGastoInmueble recibos) {
        listPagos.set(row, recibos);
        this.fireTableRowsUpdated(row, row);
    }

    public PagoGastoInmueble getCliente(int row) {
        return listPagos.get(row);
    }

}
