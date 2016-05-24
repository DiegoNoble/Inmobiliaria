/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.utils;

import javax.swing.JTable;

/**
 *
 * @author Diego Noble
 */
public class AjustaColumnas {
    
    public AjustaColumnas() {
        
    }

    public void ajustar(JTable jTable, int[] anchos) {

        for (int i = 0; i < jTable.getColumnCount(); i++) {

            jTable.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }
}
