/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.utils;

import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

/**
 *
 * @author Diego Noble
 */
public class DecorateDesktopPane extends JDesktopPane {

    private static final long serialVersionUID = -8687762508784540862L;

    private ImageIcon image;
    private MediaTracker tracker;

    public DecorateDesktopPane(String caminhoImagem) {

        image = new ImageIcon(this.getClass().getResource(caminhoImagem));

        tracker = new MediaTracker(this);
        tracker.addImage(image.getImage(), 0);

        try {
            tracker.waitForID(0);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        } // Finaliza o bloco try/catch.  

    } // Finaliza o construtor de objetos da classe JDecoratedDesktopPane.  

    public void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);

        // Desenha a imagem e a centraliza no componente.  
        graphics.drawImage(image.getImage(),
                this.getWidth() / 2 - image.getImage().getWidth(this) / 2,
                this.getHeight() / 2 - image.getImage().getHeight(this) / 2,
                this.getBackground(), null);

    } // Finaliza o método paintComponent.  

} // Finaliza a classe DecoratedDesktopPane. 
