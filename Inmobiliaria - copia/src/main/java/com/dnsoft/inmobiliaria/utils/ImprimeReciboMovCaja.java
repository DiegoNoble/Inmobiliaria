/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.utils;

import com.dnsoft.inmobiliaria.beans.Caja;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Diego Noble
 */
public class ImprimeReciboMovCaja {

    Caja movimientoCaja;
    String nombreCliente;
    String concepto;

    public ImprimeReciboMovCaja(Caja movimientoCaja, String nombreCliente, String concepto) {
        this.movimientoCaja = movimientoCaja;
        this.nombreCliente = nombreCliente;
        this.concepto = concepto;
    }

    public void imprimieReciboEntrada() {
        try {
            InputStream resource = getClass().getClassLoader().getResourceAsStream("reportes/Recibo_mov_caja.jasper");

            BufferedImage logo = ImageIO.read(getClass().getResource("/imagenes/logo.png"));

            HashMap parametros = new HashMap<>();

            parametros.put("cliente", nombreCliente);
            parametros.put("referencia", movimientoCaja.getRubro().toString());
            parametros.put("concepto", concepto);
            parametros.put("fecha", movimientoCaja.getFecha());
            parametros.put("moneda", movimientoCaja.getMoneda().toString());
            parametros.put("valor", movimientoCaja.getEntrada());
            parametros.put("id", movimientoCaja.getId());
            parametros.put("logo", logo);
            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, new JREmptyDataSource());
            JasperViewer reporte = new JasperViewer(jasperPrint, false);
            reporte.setVisible(true);

            reporte.toFront();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al imprimir recibo "+ex,"Error",JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    void imprimieReciboSalida() {
        try {
            InputStream resource = getClass().getClassLoader().getResourceAsStream("reportes/Recibo_mov_caja.jasper");

            BufferedImage logo = ImageIO.read(getClass().getResource("/imagenes/logo.png"));

            HashMap parametros = new HashMap<>();

            parametros.put("cliente", nombreCliente);
            parametros.put("referencia", movimientoCaja.getRubro().toString());
            parametros.put("concepto", concepto);
            parametros.put("fecha", movimientoCaja.getFecha());
            parametros.put("valor", movimientoCaja.getSalida());
            parametros.put("id", movimientoCaja.getId());
            parametros.put("logo", logo);
            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, new JREmptyDataSource());
            JasperViewer reporte = new JasperViewer(jasperPrint, false);
            reporte.setVisible(true);

            reporte.toFront();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
