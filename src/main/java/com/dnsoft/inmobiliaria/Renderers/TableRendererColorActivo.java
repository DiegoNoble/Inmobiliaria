/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.Renderers;

import com.dnsoft.inmobiliaria.beans.Situacion;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Diego Noble
 */
public class TableRendererColorActivo extends DefaultTableCellRenderer {

    private int ColReferencia;

    public TableRendererColorActivo(int ColReferencia) {
        this.ColReferencia = ColReferencia;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {

        //setBackground(Color.white);//color de fondo
        table.setForeground(Color.black);//color de texto
        //Si la celda corresponde a una fila con estado FALSE, se cambia el color de fondo a rojo
        if (table.getValueAt(row, ColReferencia).equals(Boolean.TRUE)) {

            //setBackground(Color.blue);
            setForeground(Color.BLUE);
            setBorder(new LineBorder(Color.BLUE));

        } else if (table.getValueAt(row, ColReferencia).equals(Boolean.FALSE)) {

            setForeground(Color.red);
            setBorder(new LineBorder(Color.red));
        }
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
    }

}
