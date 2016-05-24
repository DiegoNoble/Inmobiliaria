/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.utils;

import javax.swing.JButton;

/**
 *
 * @author Diego Noble
 */
public class RollOverButtonsChicos {

    JButton button;

    public RollOverButtonsChicos(JButton button) {
        this.button = button;
    }

    public void RollOverButtonBuscar() {
        this.button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar_normal.png")));
        this.button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar_chico.png")));
        this.button.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar_grande.png")));
        this.button.setBorder(null);
        this.button.setBorderPainted(false);
        this.button.setContentAreaFilled(false);
        this.button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        this.button.setIconTextGap(-3);
        this.button.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        this.button.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    }

    public void RollOverButtonQuitar() {
        this.button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete_24normal.png")));
        this.button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete_20chico.png")));
        this.button.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete_28grande.png")));
        this.button.setBorder(null);
        this.button.setBorderPainted(false);
        this.button.setContentAreaFilled(false);
        this.button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        this.button.setIconTextGap(-3);
        this.button.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        this.button.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    }

}
