/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.Renderers.MeTimeCellRenderer;
import com.dnsoft.inmobiliaria.beans.Caja;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.TipoDeCaja;
import com.dnsoft.inmobiliaria.daos.ICajaDAO;
import com.dnsoft.inmobiliaria.daos.ITipoDeCajaDAO;
import com.dnsoft.inmobiliaria.models.CajaTableModel;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.ExportarDatosExcel;
import com.dnsoft.inmobiliaria.views.ConsultaDeCajaView;
import com.dnsoft.inmobiliaria.views.DetalleMovimiento;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Diego Noble
 */
public class ConsultaDeCajaController implements ActionListener {

    Container container;
    ConsultaDeCajaView view;
    CajaTableModel tableModel;
    ListSelectionModel listModel;
    List<Caja> listMovimientos;
    ICajaDAO cajaDAO;
    ITipoDeCajaDAO tipoDeCajaDAO;
    Caja movimientoSeleccionado;
    JDesktopPane desktopPane;

    public ConsultaDeCajaController(ConsultaDeCajaView view, JDesktopPane desktopPane) {

        this.container = Container.getInstancia();
        cajaDAO = container.getBean(ICajaDAO.class);
        tipoDeCajaDAO = container.getBean(ITipoDeCajaDAO.class);
        this.desktopPane = desktopPane;

        this.view = view;
        
        cargaMonedas();
        inicia();
    }

    void fechas(){
        
        Calendar fechaFin = Calendar.getInstance();
        fechaFin.add(Calendar.DAY_OF_MONTH, 1);
        this.view.dpFin.setDate(fechaFin.getTime());
        this.view.dpInicio.setDate(new Date());
    }
    private void inicia() {

        accionesBotones();
        
        fechas();
        cargaTiposDeCaja();
        TableModel();
        actualizaTbl();
        saldos();
    }

    public void go() {
        this.view.setVisible(true);
        this.view.toFront();

    }

    void accionesBotones() {

        view.btnExcel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                try {
                    ExportarDatosExcel exportar = new ExportarDatosExcel(view.tblMovimientos, "Movimientos de Caja");
                    exportar.exportarJTableExcel();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Error al exportar datos " + ex, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.view.dpInicio.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                actualizaTbl();
            }
        });

        this.view.dpFin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                actualizaTbl();
            }
        });
    }

    final void cargaMonedas() {
        //view.cbMoneda.setModel(new DefaultComboBoxModel(Moneda.values()));
        view.cbMoneda.addItem(Moneda.PESOS);
        view.cbMoneda.addItem(Moneda.DOLARES);
        view.cbMoneda.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                actualizaTbl();
            }
        });
    }

    private void TableModel() {

        ((DefaultTableCellRenderer) view.tblMovimientos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listMovimientos = new ArrayList<>();
        tableModel = new CajaTableModel(listMovimientos);
        view.tblMovimientos.setModel(tableModel);
        view.tblMovimientos.getColumn("Fecha").setCellRenderer(new MeTimeCellRenderer());
        view.tblMovimientos.setRowHeight(25);
        int[] anchos = {50, 100, 20, 20, 20};

        for (int i = 0; i < view.tblMovimientos.getColumnCount(); i++) {

            view.tblMovimientos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }

        view.tblMovimientos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    detalles();

                }
            }
        });

    }

    void saldos() {
        try {
            BigDecimal saldo = cajaDAO.findUltimoMovimiento((Moneda) view.cbMoneda.getSelectedItem(), (TipoDeCaja) view.cbTipoDeCaja.getSelectedItem()).getSaldo();
            if (listMovimientos.isEmpty()) {
                view.txtSaldoAnterior.setText(saldo.toString());
            } else {
                Caja ultmoMovimientoList = listMovimientos.get(listMovimientos.size() - 1);
                BigDecimal saldoAnterior = (ultmoMovimientoList.getSaldo().subtract(ultmoMovimientoList.getEntrada())).add(ultmoMovimientoList.getSalida());
                view.txtSaldoAnterior.setText(saldoAnterior.toString());
            }

            view.txtSaldo.setText(saldo.toString());
        } catch (Exception e) {
            view.txtSaldoAnterior.setText("0.00");
            view.txtSaldo.setText("0.00");
        }
    }

    void detalles() {

        movimientoSeleccionado = listMovimientos.get(view.tblMovimientos.getSelectedRow());
        DetalleMovimiento detalle = new DetalleMovimiento(null, true, movimientoSeleccionado);
        detalle.setVisible(true);
        detalle.toFront();

    }

    public void actualizaTbl() {
        listMovimientos.clear();
        listMovimientos.addAll(cajaDAO.findByMonedaAndTipoDeCajaAndFechaBetweenOrderByFechaDesc((Moneda) view.cbMoneda.getSelectedItem(), (TipoDeCaja) view.cbTipoDeCaja.getSelectedItem(),view.dpInicio.getDate(), view.dpFin.getDate()));
        tableModel.fireTableDataChanged();
        saldos();
    }
    
     private void cargaTiposDeCaja() {
        view.cbTipoDeCaja.removeAllItems();
        for (TipoDeCaja tipoDeCaja : tipoDeCajaDAO.findAll()) {
            view.cbTipoDeCaja.addItem(tipoDeCaja);
        }
        view.cbTipoDeCaja.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                actualizaTbl();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {

            default:
                throw new AssertionError();
        }
    }

}
