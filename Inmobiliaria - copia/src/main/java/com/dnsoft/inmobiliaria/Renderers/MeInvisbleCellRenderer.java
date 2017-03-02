/**
 * MyDateCellRenderer.java
 *
 * $Id$
 *
 */
package com.dnsoft.inmobiliaria.Renderers;

import javax.swing.table.DefaultTableCellRenderer;

/**
 * 
 * @author Dyego Souza do Carmo
 * @since 
 */
public class MeInvisbleCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    /**
     * Construtor padrão
     *
     * @author Dyego Souza do Carmo
     * @version 1.0, 
     */
    public MeInvisbleCellRenderer() {
        super();
        setHorizontalAlignment(CENTER);
    }



    @Override
    protected void setValue(Object value) {
        if (value != null) {
            super.setValue(0.0);
            
        } else {
            super.setValue(0.0);
        }
    }
}
