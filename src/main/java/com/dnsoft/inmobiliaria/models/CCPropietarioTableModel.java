/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.TipoPago;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Tuple;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class CCPropietarioTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Fecha", "Detalle", "Tipo pago", "Débitos", "Créditos", "Saldo"};
    //lista para a manipulacao do objeto
    private final List<Tuple> list;

    public CCPropietarioTableModel() {
        list = new LinkedList<>();
    }

    public CCPropietarioTableModel(List<Tuple> list) {
        this.list = list;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return list.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Tuple c = list.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return c.get(0);
            case 1:
                return c.get(1);
            case 2:
                if (c.get(1).toString().startsWith("Terreno")) {

                    if (c.get(2).equals(TipoPago.TOTAL)) {
                        return "Total";
                    } else if (c.get(2).equals(TipoPago.PARCIAL)) {
                        return "Parcial";
                    } else if (c.get(2).equals(TipoPago.SALDO)) {
                        return "Saldo";
                    }
                } else {

                    return "";
                }

            case 3:
                return c.get(3);
            case 4:
                return c.get(4);
            case 5:
                return c.get(5);
            default:
                return null;
        }

    }

    //determina o nome das colunas
    @Override
    public String getColumnName(int column
    ) {
        return colunas[column];
    }

    //determina que tipo de objeto cada coluna irï¿½ suportar
    @Override
    public Class<?> getColumnClass(int columnIndex
    ) {
        switch (columnIndex) {
            case 0:
                return Date.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
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
    public boolean isCellEditable(int rowIndex, int columnIndex
    ) {

        return false;
    }

    public void agregar(Tuple gastoInmueblePropietario) {
        list.add(gastoInmueblePropietario);

        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }

    public void atualizar(int row, Tuple gastoInmueblePropietario) {
        list.set(row, gastoInmueblePropietario);
        this.fireTableRowsUpdated(row, row);
    }

    public Tuple getCliente(int row) {
        return list.get(row);
    }

}
