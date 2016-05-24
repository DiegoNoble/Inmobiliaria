/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.models;

import com.dnsoft.inmobiliaria.beans.Inquilino;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class InquilinosTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Nombre", "Documento", "Activo", "Dirección", "Contacto", "Contratos"};
    //lista para a manipulacao do objeto
    private List<Inquilino> listInquilinos;

    public InquilinosTableModel() {
        listInquilinos = new LinkedList<Inquilino>();
    }

    public InquilinosTableModel(List<Inquilino> listInquilinos) {
        this.listInquilinos = listInquilinos;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listInquilinos.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Inquilino c = listInquilinos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getNombre();
            case 1:
                return c.getDocumento();
            case 2:
                return c.getActivo();
            case 3:
                return c.getDireccion();
            case 4:
                //return "Tel "+c.getTel()+", Cel "+c.getCel();
                String tel = "";
                String cel = "";
                if (c.getTel() == null || "".equals(c.getTel())) {
                    c.setTel("");
                } else {
                    tel = "Tel ".concat(c.getTel());
                }
                if (c.getCel() == null || "".equals(c.getCel())) {
                    c.setCel("");
                } else {
                    cel = " Cel ".concat(c.getCel());
                }
                return tel + cel;
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
                return String.class;
            case 1:
                return String.class;
            case 2:
                return Boolean.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex
    ) {

        return true;

    }

    public void agregar(Inquilino inquilinos) {
        listInquilinos.add(inquilinos);

        this.fireTableRowsInserted(listInquilinos.size() - 1, listInquilinos.size() - 1);
    }

    public void eliminar(int row) {
        listInquilinos.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Inquilino inquilinos) {
        listInquilinos.set(row, inquilinos);
        this.fireTableRowsUpdated(row, row);
    }

    public Inquilino getCliente(int row) {
        return listInquilinos.get(row);
    }

}
