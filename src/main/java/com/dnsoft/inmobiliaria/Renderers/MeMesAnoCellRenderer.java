/**
 * MyDateCellRenderer.java
 *
 * $Id$
 *
 */
package com.dnsoft.inmobiliaria.Renderers;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Dyego Souza do Carmo
 * @since
 */
public class MeMesAnoCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    public MeMesAnoCellRenderer() {
        super();
        setHorizontalAlignment(CENTER);

    }

    @Override
    protected void setValue(Object value) {
        if (value != null) {
            Date formatoRecibido = (Date) value;
            Calendar fecha = Calendar.getInstance();
            fecha.setTime(formatoRecibido);

            String toReturn = fecha.getDisplayName(Calendar.MONTH, Calendar.LONG,Locale.getDefault()) + " " + fecha.get(Calendar.YEAR);
            super.setValue(toReturn);

        } else {
            super.setValue("--");
        }
    }

}
