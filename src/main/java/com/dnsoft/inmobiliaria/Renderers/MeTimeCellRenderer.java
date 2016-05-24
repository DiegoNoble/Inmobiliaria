/**
 * MyTimeCellRenderer.java
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
public class MeTimeCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    
   
    public MeTimeCellRenderer(){
        super();
    }

    @Override
    protected void setValue(Object value) {
        if (value != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date valuefOfdate = (Date) value;
            super.setValue(sdf.format(valuefOfdate));
        } else {
        super.setValue(value);
        }
    }



}
