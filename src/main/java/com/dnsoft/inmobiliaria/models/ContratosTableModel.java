/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.TipoContrato;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class ContratosTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Código", "Inquilino", "Inmueble", "Tipo contrato", "Cuota/Alquier", "Activo"};
    //lista para a manipulacao do objeto
    private List<Contrato> listContratos;

    public ContratosTableModel() {
        listContratos = new LinkedList<>();
    }

    public ContratosTableModel(List<Contrato> listContrato) {
        this.listContratos = listContrato;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listContratos.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Contrato c = listContratos.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return c.getId();
            case 1:
                return c.getDescripcionInquilino();
            case 2:
                return c.getDescripcionInmueble();

            case 3:
                return c.getTipoContrato();
            case 4:
                if (c.getTipoContrato() == TipoContrato.VENTA) {
                    return c.getMoneda() + " " + c.getValorCuota();
                } else if (c.getTipoContrato() == TipoContrato.ALQUILER) {
                    return c.getMoneda() + " " + c.getValorAlquiler();
                }
            case 5:
                return c.getActivo();

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
                return Long.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return Boolean.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex
    ) {

        return true;

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
