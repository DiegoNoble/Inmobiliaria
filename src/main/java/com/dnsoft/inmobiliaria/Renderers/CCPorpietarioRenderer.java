/**
 * MyDateCellRenderer.java
 *
 * $Id$
 *
 */
package com.dnsoft.inmobiliaria.Renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Dyego Souza do Carmo
 * @since
 */
public class CCPorpietarioRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;
    private int columna_patron;

    public CCPorpietarioRenderer(int Colpatron) {
        super();
        //setHorizontalAlignment(CENTER);
        this.columna_patron = Colpatron;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        setBackground(Color.white);//color de fondo
        table.setForeground(Color.black);//color de texto
        //Si la celda corresponde a una fila con estado FALSE, se cambia el color de fondo a rojo
        if (table.getValueAt(row, columna_patron).toString().contains("Terreno")) {
            setBackground(Color.orange);
            setBorder(new LineBorder(Color.BLUE,20));
        }else if (table.getValueAt(row, columna_patron).toString().contains("RETIRO")) {
            setBackground(Color.lightGray);
            setBorder(new LineBorder(Color.RED,50));
        }

        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
    }
}
