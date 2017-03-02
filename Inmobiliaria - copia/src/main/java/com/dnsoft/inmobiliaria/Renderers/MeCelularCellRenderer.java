/**
 * MyTimeCellRenderer.java
 *
 * $Id$
 *
 */
package com.dnsoft.inmobiliaria.Renderers;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;

/**
 * 
 * @author Dyego Souza do Carmo
 * @since 
 */
public class MeCelularCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    
   
    public MeCelularCellRenderer(){
        super();
    }

    @Override
    protected void setValue(Object value) {
        if (value != null) {
            try {
                MaskFormatter f = new MaskFormatter("   -   -  ");
                super.setValue(f);
            } catch (ParseException ex) {
                Logger.getLogger(MeCelularCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
        super.setValue(value);
        }
    }



}
