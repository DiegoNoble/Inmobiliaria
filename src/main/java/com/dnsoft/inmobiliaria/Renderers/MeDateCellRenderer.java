/**
 * MyDateCellRenderer.java
 *
 * $Id$
 *
 */
package com.dnsoft.inmobiliaria.Renderers;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Dyego Souza do Carmo
 * @since
 */
public class MeDateCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    public MeDateCellRenderer() {
        super();
        setHorizontalAlignment(CENTER);

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

}
