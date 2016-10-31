/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JDialog;

public class OptionPaneEstandar implements KeyListener {

    private JDialog dialog = null;

    public OptionPaneEstandar(JDialog dialog) {
        System.out.println("Dialog!");
        this.dialog = dialog;
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if(dialog.getSize().getHeight()>screenSize.getHeight()){
            dialog.getSize().setSize(dialog.getWidth(), screenSize.getHeight()-100);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("Tecla apretada");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("entro");
        if (e.getKeyCode() == 27) {//27 es el código de la tecla esc
            System.out.println("Adios!");
            dialog.setVisible(false);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Después de soltar la tecla!");
    }

}
