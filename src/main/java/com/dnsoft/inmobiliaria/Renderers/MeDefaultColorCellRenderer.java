/**
 * MyDateCellRenderer.java
 *
 * $Id$
 *
 */
package com.dnsoft.inmobiliaria.Renderers;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Dyego Souza do Carmo
 * @since
 */
public class MeDefaultColorCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    /**
     * Construtor padrão
     *
     * @author Dyego Souza do Carmo
     * @version 1.0,
     */
    public MeDefaultColorCellRenderer() {
        super();
        setHorizontalAlignment(CENTER);
        setBackground(Color.red);
    }

    @Override
    protected void setValue(Object value) {
        if (value != null) {

            super.setValue(value);

        } else {
            super.setValue(new BigDecimal(BigInteger.ZERO));
        }
    }
}
