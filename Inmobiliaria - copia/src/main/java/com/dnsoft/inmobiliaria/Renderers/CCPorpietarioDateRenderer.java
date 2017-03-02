/**
 * MyDateCellRenderer.java
 *
 * $Id$
 *
 */
package com.dnsoft.inmobiliaria.Renderers;

import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.beans.Situacion;
import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Dyego Souza do Carmo
 * @since
 */
public class CCPorpietarioDateRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;
    private int columna_patron;

    public CCPorpietarioDateRenderer(int Colpatron) {
        super();
        setHorizontalAlignment(CENTER);
        this.columna_patron = Colpatron;
    }

    @Override
    protected void setValue(Object value) {
        if (value != null) {
            Date formatoRecibido = (Date) value;
            String toReturn = new SimpleDateFormat("dd/MM/yyyy").format(formatoRecibido);
            super.setValue(toReturn);

        } else {
            super.setValue("--");
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        setBackground(Color.white);//color de fondo
        table.setForeground(Color.black);//color de texto
        //Si la celda corresponde a una fila con estado FALSE, se cambia el color de fondo a rojo
            if (table.getValueAt(row, columna_patron).toString().contains("Pago")) {
                setBackground(Color.GRAY);
                setBorder(new LineBorder(Color.BLUE));
            }

        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
    }
}
