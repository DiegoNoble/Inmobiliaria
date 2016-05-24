/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.Renderers.CCPorpietarioRenderer;
import com.dnsoft.inmobiliaria.Renderers.MeDateCellRenderer;
import com.dnsoft.inmobiliaria.beans.CCPropietario;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.Parametros;
import com.dnsoft.inmobiliaria.beans.Propietario;
import com.dnsoft.inmobiliaria.beans.Rubro;
import com.dnsoft.inmobiliaria.daos.ICCPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.IParametrosDAO;
import com.dnsoft.inmobiliaria.daos.IPropietarioDAO;
import com.dnsoft.inmobiliaria.models.CCPropietarioTableModel;
import com.dnsoft.inmobiliaria.utils.ActualizaSaldos;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ExportarDatosExcel;
import com.dnsoft.inmobiliaria.views.CobroDeudaPropietario;
import com.dnsoft.inmobiliaria.views.DetalleMovimientosCCPropietario;
import com.dnsoft.inmobiliaria.views.RetiroPropietario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Diego Noble
 */
public class DetalleCCPropietariosController implements ActionListener {

    Container container;
    Propietario propietarioSeleccionado;
    IPropietarioDAO propietarioDAO;
    ICCPropietarioDAO cCPropietarioDAO;
    CCPropietarioTableModel tableModelCCPesos;
    CCPropietarioTableModel tableModelCCDolares;
    ControlDeCajaController cajaController;
    DetalleMovimientosCCPropietario view;
    List<CCPropietario> listCCPesos;
    List<CCPropietario> listCCDolares;
    BigDecimal saldoPesos;
    BigDecimal saldoDolares;
    Rubro rubro;
    Parametros parametros;
    IParametrosDAO parametrosDAO;
    DecimalFormat formatter = new DecimalFormat("###,###,###.00");

    public DetalleCCPropietariosController(DetalleMovimientosCCPropietario view, Propietario propietarioSeleccionado) {

        this.view = view;
        view.btnRetiroPesos.setVisible(true);
        view.btnRetiroDolares.setVisible(true);
        view.btnEntregaDolares.setVisible(true);
        view.btnEntregaPesos.setVisible(true);
        this.propietarioSeleccionado = propietarioSeleccionado;
        view.lblPropietario.setText(propietarioSeleccionado.toString());
        inicio();
    }

    public DetalleCCPropietariosController(DetalleMovimientosCCPropietario view, Rubro rubro, ControlDeCajaController cajaController) {

        this.view = view;
        this.rubro = rubro;
        this.cajaController = cajaController;
        inicio();

        if (parametros.getRubroRetirosPropietarios().equals(rubro)) {
            this.view.btnRetiroPesos.setVisible(true);
            this.view.btnRetiroDolares.setVisible(true);
            this.view.btnEntregaDolares.setVisible(false);
            this.view.btnEntregaPesos.setVisible(false);

            this.view.btnExcelPesos.setVisible(false);
            this.view.btnExcelDolares.setVisible(false);
            this.view.btnPDFDolares.setVisible(false);
            this.view.btnPDFPesos.setVisible(false);

        } else if (parametros.getCobroDeudaPopietario().equals(rubro)) {

            this.view.btnRetiroPesos.setVisible(false);
            this.view.btnRetiroDolares.setVisible(false);
            this.view.btnEntregaDolares.setVisible(true);
            this.view.btnEntregaPesos.setVisible(true);

            this.view.btnExcelPesos.setVisible(false);
            this.view.btnExcelDolares.setVisible(false);
            this.view.btnPDFDolares.setVisible(false);
            this.view.btnPDFPesos.setVisible(false);
        }

    }

    public void go() {
        this.view.setVisible(true);
        this.view.toFront();

    }

    final void inicio() {
        view.btnActualizaSaldoD.setActionCommand("actualizaSaldosD");
        view.btnActualizaSaldoD.addActionListener(this);

        view.btnActualizaSaldoP.setActionCommand("actualizaSaldosP");
        view.btnActualizaSaldoP.addActionListener(this);

        this.container = Container.getInstancia();

        propietarioDAO = container.getBean(IPropietarioDAO.class);
        cCPropietarioDAO = container.getBean(ICCPropietarioDAO.class);
        parametrosDAO = container.getBean(IParametrosDAO.class);
        parametros = parametrosDAO.findAll().get(0);
        fechas();
        configuraTblCCPesos();
        configuraTblCCDolares();
        accionesBotones();
        buscaMovimientosCC();
    }

    void fechas() {
        view.dpFin.setFormats("dd/MM/yyyy");
        view.dpInicio.setFormats("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, 1);
        view.dpFin.setDate(c.getTime());

        c.add(Calendar.MONTH, -1);
        view.dpInicio.setDate(c.getTime());

        view.dpFin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                buscaMovimientosCC();
            }
        });

        view.dpInicio.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                buscaMovimientosCC();
            }
        });
    }

    final void configuraTblCCPesos() {
        ((DefaultTableCellRenderer) view.tblCCPesos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listCCPesos = new ArrayList<>();

        tableModelCCPesos = new CCPropietarioTableModel(listCCPesos);
        view.tblCCPesos.setModel(tableModelCCPesos);
        view.tblCCPesos.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());
        view.tblCCPesos.setDefaultRenderer(Object.class, new CCPorpietarioRenderer(1));

        int[] anchos = {5, 400, 5, 5, 5};

        for (int i = 0; i < view.tblCCPesos.getColumnCount(); i++) {

            view.tblCCPesos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
        view.tblCCPesos.setRowHeight(16);
    }

    final void configuraTblCCDolares() {
        ((DefaultTableCellRenderer) view.tblCCDolares.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listCCDolares = new ArrayList<>();

        tableModelCCDolares = new CCPropietarioTableModel(listCCDolares);
        view.tblCCDolares.setModel(tableModelCCDolares);
        view.tblCCDolares.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());
        view.tblCCDolares.setDefaultRenderer(Object.class, new CCPorpietarioRenderer(1));

        int[] anchos = {5, 400, 5, 5, 5};

        for (int i = 0; i < view.tblCCDolares.getColumnCount(); i++) {

            view.tblCCDolares.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
        view.tblCCDolares.setRowHeight(16);

    }

    void buscaMovimientosCC() {

        listCCPesos.clear();
        listCCPesos.addAll(cCPropietarioDAO.findByPropietarioAndMonedaAndFechaBetween(propietarioSeleccionado, Moneda.PESOS, view.dpInicio.getDate(), view.dpFin.getDate()));
        tableModelCCPesos.fireTableDataChanged();
        List<CCPropietario> cuentaCorriente = cCPropietarioDAO.findByPropietarioAndMonedaOrderByIdAsc(propietarioSeleccionado, Moneda.PESOS);
        if (!cuentaCorriente.isEmpty()) {

            saldoPesos = cuentaCorriente.get(cuentaCorriente.size() - 1).getSaldo();
            view.txtSaldoPesos.setText(formatter.format(saldoPesos));
        } else {

            view.txtSaldoPesos.setText(BigDecimal.ZERO.toString());
            saldoPesos = BigDecimal.ZERO;
        }

        listCCDolares.clear();
        listCCDolares.addAll(cCPropietarioDAO.findByPropietarioAndMonedaAndFechaBetween(propietarioSeleccionado, Moneda.DOLARES, view.dpInicio.getDate(), view.dpFin.getDate()));
        tableModelCCDolares.fireTableDataChanged();
        List<CCPropietario> cuentaCorrienteDolares = cCPropietarioDAO.findByPropietarioAndMonedaOrderByIdAsc(propietarioSeleccionado, Moneda.DOLARES);
        if (!cuentaCorrienteDolares.isEmpty()) {

            saldoDolares = cuentaCorrienteDolares.get(cuentaCorrienteDolares.size() - 1).getSaldo();
            view.txtSaldoDolares.setText(formatter.format(saldoDolares));
        } else {

            view.txtSaldoDolares.setText(BigDecimal.ZERO.toString());
            saldoDolares = BigDecimal.ZERO;
        }

    }

    void informePesos() {

        try {

            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listCCPesos);
            InputStream resource = getClass().getClassLoader().getResourceAsStream("reportes/CCPropietario.jasper");

            BufferedImage logo = ImageIO.read(getClass().getResource("/imagenes/logo.png"));

            HashMap parametros = new HashMap<>();
            parametros.put("propietario", propietarioSeleccionado.toString());
            parametros.put("moneda", Moneda.PESOS.toString());
            parametros.put("fechaInicio", view.dpInicio.getDate());
            parametros.put("fechaFin", view.dpFin.getDate());
            parametros.put("saldo", saldoPesos.toString());
            parametros.put("logo", logo);

            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, beanCollectionDataSource);
            JasperViewer.viewReport(jasperPrint, false);

            //JRExporter exporter = new JRXlsExporter();
            //exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            //exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("reporte2XLS2.xls"));
        } catch (JRException ex) {
            Logger.getLogger(DetalleCCPropietariosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DetalleCCPropietariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void informeDolares() {

        try {

            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listCCDolares);
            InputStream resource = getClass().getClassLoader().getResourceAsStream("reportes/CCPropietario.jasper");

            BufferedImage logo = ImageIO.read(getClass().getResource("/imagenes/logo.png"));

            HashMap parametros = new HashMap<>();
            parametros.put("propietario", propietarioSeleccionado.toString());
            parametros.put("moneda", Moneda.DOLARES.toString());
            parametros.put("fechaInicio", view.dpInicio.getDate());
            parametros.put("fechaFin", view.dpFin.getDate());
            parametros.put("saldo", saldoDolares.toString());
            parametros.put("logo", logo);

            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, beanCollectionDataSource);
            JasperViewer.viewReport(jasperPrint, false);

            //JRExporter exporter = new JRXlsExporter();
            //exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            //exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("reporte2XLS2.xls"));
        } catch (JRException ex) {
            Logger.getLogger(DetalleCCPropietariosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DetalleCCPropietariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void accionesBotones() {
        view.btnExcelPesos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                try {
                    ExportarDatosExcel exportar = new ExportarDatosExcel(view.tblCCPesos, "Cuenta Corriente");
                    exportar.exportarJTableExcel();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Error al exportar datos " + ex, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        view.btnPDFPesos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                informePesos();
            }
        });

        view.btnExcelDolares.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                try {
                    ExportarDatosExcel exportar = new ExportarDatosExcel(view.tblCCDolares, "Cuenta Corriente");
                    exportar.exportarJTableExcel();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Error al exportar datos " + ex, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        view.btnPDFDolares.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                informeDolares();
            }
        });

        view.btnRetiroPesos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                RetiroPropietario retiro = new RetiroPropietario(null, true, parametros.getRubroRetirosPropietarios(), propietarioSeleccionado, Moneda.PESOS, saldoPesos);
                retiro.setVisible(true);
                retiro.toFront();
                buscaMovimientosCC();
                if (cajaController != null) {
                    cajaController.actualizaTbl();
                }

            }
        }
        );

        view.btnRetiroDolares.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent evt
                    ) {
                        RetiroPropietario retiro = new RetiroPropietario(null, true, parametros.getRubroRetirosPropietarios(), propietarioSeleccionado, Moneda.DOLARES, saldoDolares);
                        retiro.setVisible(true);
                        retiro.toFront();
                        buscaMovimientosCC();
                        if (cajaController != null) {
                            cajaController.actualizaTbl();
                        }
                    }
                }
        );

        view.btnEntregaPesos.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent evt
                    ) {
                        CobroDeudaPropietario retiro = new CobroDeudaPropietario(null, true, parametros.getCobroDeudaPopietario(), propietarioSeleccionado, Moneda.PESOS);
                        retiro.setVisible(true);
                        retiro.toFront();
                        buscaMovimientosCC();
                        if (cajaController != null) {
                            cajaController.actualizaTbl();
                        }

                    }
                }
        );
        view.btnEntregaDolares.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent evt
                    ) {
                        CobroDeudaPropietario retiro = new CobroDeudaPropietario(null, true, parametros.getCobroDeudaPopietario(), propietarioSeleccionado, Moneda.DOLARES);
                        retiro.setVisible(true);
                        retiro.toFront();
                        buscaMovimientosCC();
                        if (cajaController != null) {
                            cajaController.actualizaTbl();
                        }

                    }
                }
        );
        view.botonVolver1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                DetalleCCPropietariosController.this.view.dispose();
            }
        }
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {

            case "actualizaSaldosD":

                ActualizaSaldos as = new ActualizaSaldos();
                cCPropietarioDAO.save(as.ActualizaSaldosPropietarios(listCCDolares));

                break;

            case "actualizaSaldosP":

                ActualizaSaldos asp = new ActualizaSaldos();
                cCPropietarioDAO.save(asp.ActualizaSaldosPropietarios(listCCPesos));

                break;

            default:
                throw new AssertionError();
        }
    }

}
