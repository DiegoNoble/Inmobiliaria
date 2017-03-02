/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria;

import com.dnsoft.inmobiliaria.views.MenuPrincipalView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Diego Noble
 */
public class Main {

    public static void main(String[] args) {

        try {
            //ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
            MenuPrincipalView mp = new MenuPrincipalView();

            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(mp);
            mp.setVisible(true);
            mp.toFront();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error:" + e, "Error1", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private static void setLogs() throws IOException {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy hh'h'mm'm'ss");
        String agora = format.format(date);

        File out = new File("logs/System.out");
        File err = new File("logs/System.err");
        if (!out.exists()) {
            out.mkdirs();
        }
        if (!err.exists()) {
            err.mkdirs();
        }

        System.setOut(
                new PrintStream(
                        new FileOutputStream("logs/System.out/" + agora + ".txt", true)));

        System.setErr(
                new PrintStream(
                        new FileOutputStream("logs/System.err/" + agora + ".txt", true)));
    }
}
