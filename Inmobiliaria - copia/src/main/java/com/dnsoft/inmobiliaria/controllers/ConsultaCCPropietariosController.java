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
import com.dnsoft.inmobiliaria.beans.Propietario;
import com.dnsoft.inmobiliaria.beans.Rubro;
import com.dnsoft.inmobiliaria.daos.ICCPropietarioDAO;
import com.dnsoft.inmobiliaria.daos.IPropietarioDAO;
import com.dnsoft.inmobiliaria.models.CCPropietarioTableModel;
import com.dnsoft.inmobiliaria.models.PropietariosTableModel;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ExportarDatosExcel;
import com.dnsoft.inmobiliaria.views.ConsultaCCPropietarioView;
import com.dnsoft.inmobiliaria.views.CobroDeudaPropietario;
import com.dnsoft.inmobiliaria.views.RetiroPropietario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
public class ConsultaCCPropietariosController implements ActionListener {

    Container container;
    ControlDeCajaController cajaController;
    Propietario propietarioSeleccionado;
    List<Propietario> listPropietarios;
    IPropietarioDAO propietarioDAO;
    ICCPropietarioDAO cCPropietarioDAO;
    CCPropietarioTableModel tableModelCCPesos;
    CCPropietarioTableModel tableModelCCDolares;
    PropietariosTableModel tableModelPropietarios;
    ConsultaCCPropietarioView view;
    List<CCPropietario> listCCPesos;
    List<CCPropietario> listCCDolares;
    BigDecimal saldoPesos;
    BigDecimal saldoDolares;
    Rubro rubro;

    public ConsultaCCPropietariosController(ConsultaCCPropietarioView view) {

        this.view = view;
        view.btnRetiroPesos.setVisible(false);
        view.btnRetiroDolares.setVisible(false);
        view.btnEntregaDolares.setVisible(false);
        view.btnEntregaPesos.setVisible(false);
        inicio();
    }

    public ConsultaCCPropietariosController(ConsultaCCPropietarioView view, Rubro rubro, ControlDeCajaController cajaController) {

        this.view = view;
        this.rubro = rubro;
        this.cajaController = cajaController;
        view.btnRetiroPesos.setVisible(false);
        view.btnRetiroDolares.setVisible(false);
        view.btnEntregaDolares.setVisible(true);
        view.btnEntregaPesos.setVisible(true);

        inicio();
    }

    public void go() {
        try {
            this.view.setVisible(true);
            this.view.toFront();
            view.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(ConsultaCCPropietariosController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    final void inicio() {
        this.container = Container.getInstancia();
        this.view.txtBusqueda.setActionCommand("txtBusqueda");
        this.view.txtBusqueda.addActionListener(this);
        propietarioDAO = container.getBean(IPropietarioDAO.class);
        cCPropietarioDAO = container.getBean(ICCPropietarioDAO.class);
        fechas();
        configuraTblPropietarios();
        configuraTblCCPesos();
        configuraTblCCDolares();
        buscarPropietarios();
        accionesBotones();
    }

    void fechas() {
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

    final void configuraTblPropietarios() {
        ((DefaultTableCellRenderer) view.tblPropietario.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listPropietarios = new ArrayList<>();

        tableModelPropietarios = new PropietariosTableModel(listPropietarios);
        view.tblPropietario.setModel(tableModelPropietarios);
        view.tblPropietario.removeColumn(view.tblPropietario.getColumn("Detalles"));
        //view.tblPropietario.setRowHeight(25);

        ListSelectionModel selectionModel = view.tblPropietario.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {

                if (view.tblPropietario.getSelectedRow() != -1) {
                    buscaMovimientosCC();
                    view.btnRetiroPesos.setEnabled(true);
                    view.btnEntregaPesos.setEnabled(true);
                    view.btnEntregaDolares.setEnabled(true);
                } else {
                    view.btnRetiroPesos.setEnabled(false);
                    view.btnEntregaPesos.setEnabled(false);
                    view.btnEntregaDolares.setEnabled(false);
                    listCCPesos.clear();
                    tableModelCCPesos.fireTableDataChanged();
                }
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

        int[] anchos = {5, 250, 5, 5, 5};

        for (int i = 0; i < view.tblCCPesos.getColumnCount(); i++) {

            view.tblCCPesos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }

    }

    final void configuraTblCCDolares() {
        ((DefaultTableCellRenderer) view.tblCCDolares.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listCCDolares = new ArrayList<>();

        tableModelCCDolares = new CCPropietarioTableModel(listCCDolares);
        view.tblCCDolares.setModel(tableModelCCDolares);
        view.tblCCDolares.getColumn("Fecha").setCellRenderer(new MeDateCellRenderer());
        view.tblCCDolares.setDefaultRenderer(Object.class, new CCPorpietarioRenderer(1));

        int[] anchos = {5, 250, 5, 5, 5};

        for (int i = 0; i < view.tblCCDolares.getColumnCount(); i++) {

            view.tblCCDolares.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }

    }

    void buscarPropietarios() {
        listPropietarios.clear();
        listPropietarios.addAll(propietarioDAO.findPropietario(view.txtBusqueda.getText()));
        tableModelPropietarios.fireTableDataChanged();
    }

    void buscaMovimientosCC() {

        propietarioSeleccionado = listPropietarios.get(view.tblPropietario.getSelectedRow());
        listCCPesos.clear();
        listCCPesos.addAll(cCPropietarioDAO.findByPropietarioAndMonedaAndFechaBetween(propietarioSeleccionado, Moneda.PESOS, view.dpInicio.getDate(), view.dpFin.getDate()));
        tableModelCCPesos.fireTableDataChanged();
        List<CCPropietario> cuentaCorriente = cCPropietarioDAO.findByPropietarioAndMonedaOrderByIdAsc(propietarioSeleccionado, Moneda.PESOS);
        if (!cuentaCorriente.isEmpty()) {

            saldoPesos = cuentaCorriente.get(cuentaCorriente.size() - 1).getSaldo();
            view.txtSaldoPesos.setText(saldoPesos.toString());
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
            view.txtSaldoDolares.setText(saldoDolares.toString());
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
            parametros.put("propietario", propietarioDAO.findOne(Long.valueOf(1)).toString());
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
            Logger.getLogger(ConsultaCCPropietariosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultaCCPropietariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void informeDolares() {

        try {

            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listCCDolares);
            InputStream resource = getClass().getClassLoader().getResourceAsStream("reportes/CCPropietario.jasper");

            BufferedImage logo = ImageIO.read(getClass().getResource("/imagenes/logo.png"));

            HashMap parametros = new HashMap<>();
            parametros.put("propietario", propietarioDAO.findOne(Long.valueOf(1)).toString());
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
            Logger.getLogger(ConsultaCCPropietariosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultaCCPropietariosController.class.getName()).log(Level.SEVERE, null, ex);
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
                RetiroPropietario retiro = new RetiroPropietario(null, true, rubro, propietarioSeleccionado, Moneda.PESOS, saldoPesos);
                retiro.setVisible(true);
                retiro.toFront();
                buscaMovimientosCC();
                cajaController.actualizaTbl();
            }
        });

        view.btnRetiroDolares.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                RetiroPropietario retiro = new RetiroPropietario(null, true, rubro, propietarioSeleccionado, Moneda.DOLARES, saldoDolares);
                retiro.setVisible(true);
                retiro.toFront();
                buscaMovimientosCC();
                cajaController.actualizaTbl();
            }
        });

        view.btnEntregaPesos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                CobroDeudaPropietario retiro = new CobroDeudaPropietario(null, true, rubro, propietarioSeleccionado, Moneda.PESOS);
                retiro.setVisible(true);
                retiro.toFront();
                buscaMovimientosCC();
                cajaController.actualizaTbl();
            }
        });
        view.btnEntregaDolares.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                CobroDeudaPropietario retiro = new CobroDeudaPropietario(null, true, rubro, propietarioSeleccionado, Moneda.DOLARES);
                retiro.setVisible(true);
                retiro.toFront();
                buscaMovimientosCC();
                cajaController.actualizaTbl();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e
    ) {
        String comando = e.getActionCommand();

        switch (comando) {

            case "txtBusqueda":

                buscarPropietarios();

                break;

            default:
                throw new AssertionError();
        }
    }

}
