/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.controllers;

import com.dnsoft.inmobiliaria.Renderers.MeTimeCellRenderer;
import com.dnsoft.inmobiliaria.beans.Caja;
import com.dnsoft.inmobiliaria.beans.Monedas;
import com.dnsoft.inmobiliaria.beans.Rubro;
import com.dnsoft.inmobiliaria.daos.ICajaDAO;
import com.dnsoft.inmobiliaria.daos.IMonedaDAO;
import com.dnsoft.inmobiliaria.daos.IRubroDAO;
import com.dnsoft.inmobiliaria.models.CajaTableModel;
import com.dnsoft.inmobiliaria.views.ControlDeCajaView;
import com.dnsoft.inmobiliaria.views.detalles.DetalleMovimientoCaja;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Diego Noble
 */
public class ControlDeCajaController implements ActionListener {

    ApplicationContext applicationContext;
    public ControlDeCajaView view;
    CajaTableModel tableModel;
    ListSelectionModel listModel;
    List<Caja> listMovimientos;
    ICajaDAO cajaDAO;
    IMonedaDAO monedaDAO;
    IRubroDAO rubroDAO;
    ControlDeCajaView controlDeCajaController;
    Caja movimientoSeleccionado;

    public ControlDeCajaController(ControlDeCajaView view) {

        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        cajaDAO = applicationContext.getBean(ICajaDAO.class);
        monedaDAO = applicationContext.getBean(IMonedaDAO.class);
        rubroDAO = applicationContext.getBean(IRubroDAO.class);

        this.view = view;

        cargaMonedas();
        cargaRubros();
        inicia();
    }

    private void inicia() {

        this.view.btnCancelar.setActionCommand("btnCancelar");
        this.view.btnRegistrar.setActionCommand("btnRegistrar");
        this.view.btnNuevoRubro.setActionCommand("btnNuevoRubro");

        this.view.btnCancelar.addActionListener(this);
        this.view.btnNuevoRubro.addActionListener(this);
        this.view.btnRegistrar.addActionListener(this);
        this.view.dpFecha.setDate(new Date());
        this.view.dpFecha.setEditable(false);

        TableModel();
    }

    public void go() {
        this.view.setVisible(true);
        this.view.toFront();

    }

    void cargaMonedas() {
        List<Monedas> monedas = monedaDAO.findAll();
        for (Monedas moneda : monedas) {
            view.cbMoneda.addItem(moneda);
        }

        view.cbMoneda.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                actualizaTbl();
            }
        });
    }

    void cargaRubros() {
        List<Rubro> rubros = rubroDAO.findAll();
        for (Rubro rubro : rubros) {
            view.cbRubro.addItem(rubro);
        }
    }

    private void TableModel() {

        ((DefaultTableCellRenderer) view.tblMovimientos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listMovimientos = new ArrayList<Caja>();
        listMovimientos.addAll(cajaDAO.findByFechaAfterOrFechaEqualAndMonedaOrderByFechaDesc(view.dpFecha.getDate(), (Monedas) view.cbMoneda.getSelectedItem()));

        tableModel = new CajaTableModel(listMovimientos);
        view.tblMovimientos.setModel(tableModel);
        view.tblMovimientos.getColumn("Fecha").setCellRenderer(new MeTimeCellRenderer());
        view.tblMovimientos.setRowHeight(25);
         int[] anchos = {50, 100, 20, 20, 20};

        for (int i = 0; i < view.tblMovimientos.getColumnCount(); i++) {

            view.tblMovimientos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }

        view.tblMovimientos.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    detalles();

                }
            }
        });

    }

    void detalles() {
        movimientoSeleccionado = listMovimientos.get(view.tblMovimientos.getSelectedRow());
        DetalleMovimientoCaja detalle = new DetalleMovimientoCaja(null, true, movimientoSeleccionado, view);
        detalle.setVisible(true);
        detalle.toFront();
    }

    public void actualizaTbl() {
        listMovimientos.clear();
        listMovimientos.addAll(cajaDAO.findByFechaAfterOrFechaEqualAndMonedaOrderByFechaDesc(view.dpFecha.getDate(), (Monedas) view.cbMoneda.getSelectedItem()));
        tableModel.fireTableDataChanged();
    }

    void guardaMovimiento() {
        try {
            BigDecimal valor = BigDecimal.valueOf((Long) view.txtValor.getValue());
            Caja movimiento = new Caja();
            movimiento.setDescripcion(view.txtDescripcion.getText());
            movimiento.setMoneda((Monedas) view.cbMoneda.getSelectedItem());
            movimiento.setRubro((Rubro) view.cbRubro.getSelectedItem());
            movimiento.setFecha(new Date());
            if (view.rbEntrada.isSelected()) {

                movimiento.setEntrada(valor);
                movimiento.setSalida(BigDecimal.ZERO);
                movimiento.setSaldo(calculaSaldo().add(valor));
            } else if (view.rbSalida.isSelected()) {
                movimiento.setSalida(valor);
                movimiento.setEntrada(BigDecimal.ZERO);
                movimiento.setSaldo(calculaSaldo().subtract(valor));
            }
            cajaDAO.save(movimiento);
            actualizaTbl();
            JOptionPane.showMessageDialog(view, "Movimiento guardado correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(view, "Debe completar los campos requeridos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    BigDecimal calculaSaldo() {
        BigDecimal toReturn;
        Caja ultimoMovimiento = new Caja();
        ultimoMovimiento = cajaDAO.findUltimoMovimiento(((Monedas) view.cbMoneda.getSelectedItem()).getId());
        if (ultimoMovimiento == null) {
            toReturn = BigDecimal.ZERO;
        } else {
            toReturn = ultimoMovimiento.getSaldo();
        }
        return toReturn;

    }

    void limpiaCampos() {
        view.txtValor.setText("");
        view.txtDescripcion.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {

            case "btnRegistrar":
                guardaMovimiento();
                break;

            case "btnCancelar":
                detalles();

                break;
            case "btnNuevoRubro":
                limpiaCampos();
                break;

            default:
                throw new AssertionError();
        }
    }

}
