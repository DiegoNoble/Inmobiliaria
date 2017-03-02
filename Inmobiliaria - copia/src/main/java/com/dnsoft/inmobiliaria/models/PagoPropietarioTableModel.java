/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.PagoPropietario;
import com.dnsoft.inmobiliaria.beans.Propietario;
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
public class PagoPropietarioTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Propietario", "Valor", "Honorarios", "Mora Propietario", "Mora Inmobiliaria", "Adelanto IRPF", "Retiene Inquilino", "Retiene Inmobiliaria"};
    //lista para a manipulacao do objeto
    private final List<PagoPropietario> listPagoPropietario;

    public PagoPropietarioTableModel() {
        listPagoPropietario = new LinkedList<>();
    }

    public PagoPropietarioTableModel(List<PagoPropietario> listPagoPropietario) {
        this.listPagoPropietario = listPagoPropietario;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listPagoPropietario.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PagoPropietario c = listPagoPropietario.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getPropietario();
            case 1:
                return c.getValor();
            case 2:
                return c.getComision();
            case 3:
                return c.getMorapropietario();
            case 4:
                return c.getMoraInmobiliaria();
            case 5:
                return c.getAdelantoIRPF();
            case 6:
                return c.getRetieneInquilino();
            case 7:
                return c.getRetieneInmobiliaria();
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
                return Propietario.class;
            case 1:
                return BigDecimal.class;
            case 2:
                return BigDecimal.class;
            case 3:
                return BigDecimal.class;
            case 4:
                return BigDecimal.class;
            case 5:
                return BigDecimal.class;
            case 6:
                return Boolean.class;
            case 7:
                return Boolean.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

    public void agregar(PagoPropietario recibos) {
        listPagoPropietario.add(recibos);

        this.fireTableRowsInserted(listPagoPropietario.size() - 1, listPagoPropietario.size() - 1);
    }

    public void eliminar(int row) {
        listPagoPropietario.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, PagoPropietario recibos) {
        listPagoPropietario.set(row, recibos);
        this.fireTableRowsUpdated(row, row);
    }

    public PagoPropietario getCliente(int row) {
        return listPagoPropietario.get(row);
    }

}
