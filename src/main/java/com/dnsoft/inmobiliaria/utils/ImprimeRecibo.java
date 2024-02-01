/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.utils;

import com.dnsoft.inmobiliaria.beans.CCPropietario;
import com.dnsoft.inmobiliaria.beans.Caja;
import static com.dnsoft.inmobiliaria.beans.Moneda.UNIDADES_REAJUSTABLES;
import com.dnsoft.inmobiliaria.beans.PagoRecibo;
import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.beans.TipoCotizacionContrato;
import com.dnsoft.inmobiliaria.daos.IPagoReciboDAO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
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
public class ImprimeRecibo {

    Caja movimientoCaja;
    String nombreCliente;
    String proveedor;
    String detalle;
    String inmueble;
    IPagoReciboDAO pagoReciboDAO;
    Container container;

    public ImprimeRecibo() {
        container = Container.getInstancia();
        pagoReciboDAO = container.getBean(IPagoReciboDAO.class);
    }

    public ImprimeRecibo(Caja movimientoCaja, String nombreCliente, String detalle, String proveedor, String inmueble) {
        this.movimientoCaja = movimientoCaja;
        this.nombreCliente = nombreCliente;
        this.detalle = detalle;
        this.proveedor = proveedor;
        this.inmueble = inmueble;
        container = Container.getInstancia();
        pagoReciboDAO = container.getBean(IPagoReciboDAO.class);
    }

    public void imprimieReciboEntrada() {
        try {
            InputStream resource = getClass().getClassLoader().getResourceAsStream("reportes/Recibo_mov_caja.jasper");

            BufferedImage logo = ImageIO.read(getClass().getResource("/imagenes/logo.png"));

            HashMap parametros = new HashMap<>();

            parametros.put("cliente", nombreCliente);
            parametros.put("rubro", movimientoCaja.getRubro().toString());
            parametros.put("detalle", detalle);
            parametros.put("fecha", movimientoCaja.getFecha());
            parametros.put("moneda", movimientoCaja.getMoneda().toString());
            parametros.put("valor", movimientoCaja.getEntrada());
            parametros.put("id", movimientoCaja.getId());
            parametros.put("proveedor", proveedor);
            parametros.put("inmueble", inmueble);
            parametros.put("tipo", "ENTRADA");

            parametros.put("logo", logo);
            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, new JREmptyDataSource());
            JasperViewer reporte = new JasperViewer(jasperPrint, false);
            reporte.setVisible(true);

            reporte.toFront();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al imprimir recibo " + ex, "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void imprimieReciboSalida() {
        try {
            InputStream resource = getClass().getClassLoader().getResourceAsStream("reportes/Recibo_mov_caja.jasper");

            BufferedImage logo = ImageIO.read(getClass().getResource("/imagenes/logo.png"));

            HashMap parametros = new HashMap<>();

            parametros.put("cliente", nombreCliente);
            parametros.put("rubro", movimientoCaja.getRubro().toString());
            parametros.put("detalle", detalle);
            parametros.put("fecha", movimientoCaja.getFecha());
            parametros.put("moneda", movimientoCaja.getMoneda().toString());
            parametros.put("valor", movimientoCaja.getSalida());
            parametros.put("id", movimientoCaja.getId());
            parametros.put("logo", logo);
            parametros.put("proveedor", proveedor);
            parametros.put("inmueble", inmueble);
            parametros.put("tipo", "SALIDA");
            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, new JREmptyDataSource());
            JasperViewer reporte = new JasperViewer(jasperPrint, false);
            reporte.setVisible(true);

            reporte.toFront();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al imprimir recibo " + ex, "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }
    }

    public void imprimieReciboRetiroPropietario(CCPropietario retiro) {
        try {
            InputStream resource = getClass().getClassLoader().getResourceAsStream("reportes/Recibo_Retiro_new.jasper");

            BufferedImage logo = ImageIO.read(getClass().getResource("/imagenes/logo.png"));
            BufferedImage logoBN = ImageIO.read(getClass().getResource("/imagenes/logoBN.png"));

            HashMap parametros = new HashMap<>();

            parametros.put("cliente", retiro.getPropietario().toString());
            parametros.put("fecha", retiro.getFecha());
            parametros.put("moneda", retiro.getMoneda().toString());
            parametros.put("valor", retiro.getDebito());
            parametros.put("id", retiro.getId());

            parametros.put("logo", logo);
            parametros.put("logoBN", logoBN);
            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, new JREmptyDataSource());
            JasperViewer reporte = new JasperViewer(jasperPrint, false);
            reporte.setVisible(true);

            reporte.toFront();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al imprimir recibo " + ex, "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void imprimieReciboPagoCuota(PagoRecibo pagoCuota, BigDecimal cotizacion) {
        try {
            InputStream resource = getClass().getClassLoader().getResourceAsStream("reportes/Recibo_Pago_Cuota_new.jasper");

            BufferedImage logo = ImageIO.read(getClass().getResource("/imagenes/logo.png"));
            BufferedImage logoBN = ImageIO.read(getClass().getResource("/imagenes/logoBN.png"));

            HashMap parametros = new HashMap<>();

            PagoRecibo pago = pagoReciboDAO.findFetch(pagoCuota.getId());
            Recibo recibo = pago.getRecibo();
            parametros.put("cliente", recibo.getContrato().getInquilino().toString());
            parametros.put("cuota", recibo.getNroRecibo());
            parametros.put("total_cuotas", recibo.getCantidadRecibos());
            parametros.put("inmueble", recibo.getContrato().getInmueble().toString());
            parametros.put("fecha", pago.getFecha());
            parametros.put("vencimiento", recibo.getFechaVencimiento());
            parametros.put("moneda", recibo.getMoneda().toString());
            parametros.put("valor", recibo.getValor());
            parametros.put("total", recibo.getValor());

            parametros.put("entrega", pago.getValor());
            parametros.put("saldo", recibo.getValor().subtract(pago.getValor()));

            parametros.put("contrato", recibo.getContrato().getId());
            BigDecimal mora = pago.getMora();
            if (mora == null) {
                mora = BigDecimal.ZERO;
            }
            parametros.put("mora", mora);
            DecimalFormat formato;
            switch (pago.getMoneda()) {
                case PESOS:
                    formato = new DecimalFormat("#,##0.00");
                    break;
                case DOLARES:
                    formato = new DecimalFormat("#,##0.00");
                    break;
                case UNIDADES_INDEXADAS:
                    formato = new DecimalFormat("#,##0.0000");
                    break;
                case UNIDADES_REAJUSTABLES:
                    formato = new DecimalFormat("#,##0.00");
                    break;
                default:
                    throw new AssertionError();
            } 
            parametros.put("cotizacionImp", formato.format(cotizacion));

            parametros.put("cotizacion", cotizacion);

            parametros.put("equivalencia", cotizacion.multiply(recibo.getValor()));
            parametros.put("id", recibo.getId());
            parametros.put("logo", logo);
            parametros.put("logoBN", logoBN);

            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, new JREmptyDataSource());
            JasperViewer reporte = new JasperViewer(jasperPrint, false);
            reporte.setVisible(true);

            reporte.toFront();
            reporte.isFocused();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al imprimir recibo " + ex, "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }
    }

    public void imprimieReciboAlquiler(PagoRecibo pagoAlquiler, BigDecimal cotizacion) {

        try {
            InputStream resource = getClass().getClassLoader().getResourceAsStream("reportes/Recibo_Pago_Alquiler.jasper");

            BufferedImage logo = ImageIO.read(getClass().getResource("/imagenes/logo.png"));

            HashMap parametros = new HashMap<>();
            Recibo recibo = pagoAlquiler.getRecibo();
            parametros.put("cliente", recibo.getContrato().getInquilino().toString());
            parametros.put("desde", recibo.getPeriodo_desde());
            parametros.put("hasta", recibo.getPeriodo_hasta());
            parametros.put("inmueble", recibo.getContrato().getInmueble().toString());
            parametros.put("fecha", new Date());
            parametros.put("vencimiento", recibo.getFechaVencimiento());
            parametros.put("moneda", recibo.getMoneda().toString());
            parametros.put("valor", pagoAlquiler.getValor());
            parametros.put("total", recibo.getValor());
            if (recibo.getContrato().getTipoCotizacionContrato() == TipoCotizacionContrato.FIJA) {
                parametros.put("cotizacion", recibo.getContrato().getCotizacion());
            } else {
                parametros.put("cotizacion", cotizacion);
            }
            parametros.put("equivalencia", cotizacion.multiply(pagoAlquiler.getValor()));
            parametros.put("id", recibo.getId());
            parametros.put("logo", logo);

            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, new JREmptyDataSource());
            JasperViewer reporte = new JasperViewer(jasperPrint, false);
            reporte.setVisible(true);

            reporte.toFront();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al imprimir recibo " + ex, "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }
    }

}
