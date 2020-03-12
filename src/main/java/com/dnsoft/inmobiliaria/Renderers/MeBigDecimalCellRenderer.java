/**
 * MyDateCellRenderer.java
 *
 * $Id$
 *
 */
package com.dnsoft.inmobiliaria.Renderers;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Dyego Souza do Carmo
 * @since
 */
public class MeBigDecimalCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    public MeBigDecimalCellRenderer() {
        super();
        setHorizontalAlignment(RIGHT);

    }

    @Override
    protected void setValue(Object value) {
        if (value != null) {
            BigDecimal formatoRecibido = (BigDecimal) value;
             DecimalFormat formatter = new DecimalFormat("#,##0.00;-#,##0.00");
            String toReturn = formatter.format(formatoRecibido);
            super.setValue(toReturn);

        } else {
            super.setValue("--");
        }
    }

}
