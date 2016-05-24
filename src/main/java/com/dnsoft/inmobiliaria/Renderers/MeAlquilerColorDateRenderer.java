/**
 * MyDateCellRenderer.java
 *
 * $Id$
 *
 */
package com.dnsoft.inmobiliaria.Renderers;

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
public class MeAlquilerColorDateRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;
    private int columna_patron;
    private int ColSecundaria;

    public MeAlquilerColorDateRenderer(int Colpatron, int ColSecundaria) {
        super();
        setHorizontalAlignment(CENTER);
        this.columna_patron = Colpatron;
        this.ColSecundaria = ColSecundaria;
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
        table.setForeground(Color.black);//color de texto

        if (table.getValueAt(row, ColSecundaria).equals(Situacion.PAGO)) {

            //setBackground(Color.blue);
            setForeground(Color.BLUE);
            setBorder(new LineBorder(Color.BLUE));
        } else if (table.getValueAt(row, ColSecundaria).equals(Situacion.CON_SALDO)) {

            if (table.getValueAt(row, columna_patron).equals(true)) {
                setForeground(Color.black);
            } else {
                setForeground(Color.red);

            }
        } else if (table.getValueAt(row, ColSecundaria).equals(Situacion.PENDIENTE)) {

            if (table.getValueAt(row, columna_patron).equals(true)) {
                setForeground(Color.black);
            } else {
                //setBackground(Color.red);
                setForeground(Color.red);
                setBorder(new LineBorder(Color.red));
            }
        }
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
    }

}
