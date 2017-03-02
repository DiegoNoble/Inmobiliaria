/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.utils;

import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author dnoble
 */
public abstract class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {

    JTable table;
    JButton renderButton;
    JButton editButton;
    String textoBoton;
    String textoAccion;

    public ButtonColumn(JTable table, int column, String textoBoton, String textoAccion) {
        super();
        this.table = table;
        renderButton = new JButton();
        this.textoAccion = textoAccion;
        this.textoBoton = textoBoton;
        editButton = new JButton();
        editButton.setFocusPainted(false);
        editButton.addActionListener(this);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer(this);
        columnModel.getColumn(column).setCellEditor(this);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (hasFocus) {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        } else if (isSelected) {
            renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        } else {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        }

        renderButton.setText((value == null) ? textoBoton : value.toString());
        return renderButton;
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {
        textoAccion = (value == null) ? textoAccion : value.toString();
        editButton.setText(textoAccion);
        return editButton;
    }

    @Override
    public Object getCellEditorValue() {
        return textoAccion;
    }

    /*@Override
     public void actionPerformed(ActionEvent e)  
     {  
     fireEditingStopped();  
     System.out.println( e.getActionCommand() + " : " + table.getSelectedRow());  
     System.out.println("click prueba!!");
             
     }*/
}
