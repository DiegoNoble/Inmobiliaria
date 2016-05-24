/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.utils;

import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author Diego Noble
 */
public class FrameEstandar extends JFrame {

    Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logoTrans.png"));

    public FrameEstandar() throws HeadlessException {
        setIconImage(icon);
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), "Cancel");

        getRootPane().getActionMap().put("Cancel", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                close();
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                close();
            }
        });
    }

    private void close() {
        java.awt.Toolkit.getDefaultToolkit().beep();
        //if (JOptionPane.showConfirmDialog(rootPane, "¿Desea realmente salir de la aplicación?",
        //      "Sistema", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
        //System.out.println("Salida del sistema.");
        this.dispose();

    }
}
