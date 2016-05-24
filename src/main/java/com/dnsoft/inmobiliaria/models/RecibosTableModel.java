/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.beans.Situacion;
import com.dnsoft.inmobiliaria.utils.CalculaMora;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
    private final String[] colunas = new String[]{"Nro. Cuota", "Cód", "Vencimiento", "Período", "Fecha pago", "Valor", "Saldo", "Mora", "Saldo total", "Situación", "Auxiliar"};
    //lista para a manipulacao do objeto
    private final List<Recibo> listAlquileres;
    private CalculaMora calcularMora;

    public RecibosTableModel() {
        listAlquileres = new LinkedList<>();
    }

    public RecibosTableModel(List<Recibo> listAlquileres) {
        this.listAlquileres = listAlquileres;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listAlquileres.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Recibo c = listAlquileres.get(rowIndex);
        calcularMora = new CalculaMora(c);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        //Boolean ajustaMora = (Boolean) getValueAt(rowIndex, 10);

        // Double mora = (Double) getValueAt(rowIndex, 6);
        switch (columnIndex) {
            case 0:
                return c.getNroRecibo();
            case 1:
                return c.getId();
            case 2:
                return c.getFechaVencimiento();

            case 3:
                if (c.getPeriodo_desde() != null || c.getPeriodo_hasta() != null) {
                    return formato.format(c.getPeriodo_desde()) + " al " + formato.format(c.getPeriodo_hasta());
                } else {
                    return "--";
                }
            case 4:
                return c.getFechaPago();
            case 5:
                return c.getMoneda()+" "+c.getValor();
            case 6:
                return c.getMoneda()+" "+c.getSaldo();
            case 7:
                return c.getMora();
            case 8:
                if (c.getSituacion() != Situacion.PAGO) {
                    return c.getSaldo().add(c.getMora());
                } else {
                    return c.getSaldo();
                }
            case 9:
                return c.getSituacion();
            case 10:
                return calcularMora.verificaSiAplicaMora();

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
                return String.class;
            case 5:
                return String.class;
            case 6:
                return String.class;
            case 7:
                return BigDecimal.class;
            case 8:
                return BigDecimal.class;
            case 9:
                return Situacion.class;
            case 10:
                return Boolean.class;

            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        Recibo c = listAlquileres.get(rowIndex);

        switch (columnIndex) {
            case 7:
                c.setMora((BigDecimal) aValue);
                break;
        }
        fireTableCellUpdated(rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
       
        Recibo c = listAlquileres.get(rowIndex);
        if (c.getSituacion() != Situacion.PAGO) {
            return columnIndex == 7;
        } else {
            return false;
        }
    }

    public void agregar(Recibo recibos) {
        listAlquileres.add(recibos);

        this.fireTableRowsInserted(listAlquileres.size() - 1, listAlquileres.size() - 1);
    }

    public void eliminar(int row) {
        listAlquileres.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Recibo recibos) {
        listAlquileres.set(row, recibos);
        this.fireTableRowsUpdated(row, row);
    }

    public Recibo getCliente(int row) {
        return listAlquileres.get(row);
    }

}
