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
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author dnoble
 */
public abstract class ButtonColumnEditar extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {

    JTable table;
    JButton editButton;

    public ButtonColumnEditar(JTable table, int column) {
        super();
        this.table = table;
        editButton = new JButton();
        editButton.setFocusPainted(false);
        editButton.addActionListener(this);
        imagen();

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer(this);
        columnModel.getColumn(column).setCellEditor(this);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        imagen();
        return editButton;
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {
        imagen();
        return editButton;
    }

    @Override
    public Object getCellEditorValue(){
        
        return editButton;
    }

    private void imagen() {
        editButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/editar_normal.png")));
        editButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/editar_chico.png")));
        editButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/editar_grande.png")));
        editButton.setBorder(null);
        editButton.setBorderPainted(false);
        editButton.setContentAreaFilled(false);
        editButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        editButton.setIconTextGap(-3);
        editButton.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        editButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    }
   
}
