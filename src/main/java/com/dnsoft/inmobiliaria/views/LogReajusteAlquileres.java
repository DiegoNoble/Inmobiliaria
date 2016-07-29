/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.CotizacionReajustes;
import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.daos.IContratoDAO;
import com.dnsoft.inmobiliaria.daos.IRecibosDAO;
import com.dnsoft.inmobiliaria.utils.CalculaRecibos;
import com.dnsoft.inmobiliaria.utils.Container;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author dnoble
 */
public class LogReajusteAlquileres extends javax.swing.JDialog {

    CotizacionReajustes cotizacionReajustes;
    IContratoDAO contratoDAO;
    IRecibosDAO recibosDAO;
    Container container;

    public LogReajusteAlquileres(java.awt.Frame parent, boolean modal, CotizacionReajustes cotizacionReajustes) {
        super(parent, modal);
        //this.setLocationRelativeTo(null);

        this.container = Container.getInstancia();
        contratoDAO = container.getBean(IContratoDAO.class);
        recibosDAO = container.getBean(IRecibosDAO.class);
        this.cotizacionReajustes = cotizacionReajustes;
        initComponents();
        accionesBotones();

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logoTrans.png")));
        DefaultCaret caret = (DefaultCaret) jTextArea1.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        new Thread() {
            @Override
            public void run() {
                ajustarContratos();

            }
        }.start();

    }

    void ajustarContratos() {

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        BigDecimal valorReajuste = cotizacionReajustes.getValor();

        Calendar fecha = Calendar.getInstance();
        fecha.setTime(cotizacionReajustes.getPeriodo());
        Integer month = fecha.get(Calendar.MONTH);
        Integer year = fecha.get(Calendar.YEAR);

        List<Contrato> contratosReajustar = contratoDAO.findByTipoReauste(cotizacionReajustes.getTipoReajuste(), year, month + 1);
        jTextArea1.append("Iniciando proceso, por favor espere! \n");
        jTextArea1.append("-----------------------------------\n");
        for (Contrato contratoSeleccionado : contratosReajustar) {

            jTextArea1.append("\n Ajustando contrato: " + contratoSeleccionado.getId() + "\n");

            Calendar fechaReajuste = Calendar.getInstance();
            fechaReajuste.setTime(contratoSeleccionado.getFechaReajuste());
            fechaReajuste.add(Calendar.YEAR, 1);
            contratoSeleccionado.setFechaReajuste(fechaReajuste.getTime());
            List<Recibo> listRecibosAjustados = new CalculaRecibos().reajusteAlquiler(contratoSeleccionado, cotizacionReajustes.getTipoReajuste(), valorReajuste);
            jTextArea1.append("\t Generando recibos: \n");
            for (Recibo recibo : listRecibosAjustados) {
                int index = listRecibosAjustados.indexOf(recibo) + 1;
                recibosDAO.save(recibo);
                jTextArea1.append("\t \t" + index + " Recibo: " + recibo.getId() + " Nro. recibo " + recibo.getNroRecibo() + " Vencimiento: " + formato.format(recibo.getFechaVencimiento()) + " Valor " + recibo.getMoneda() + " " + recibo.getValor() + "\n");
            }

        }
        jTextArea1.append("\n -----------------------------------");
        jTextArea1.append("\n Proceso finalizado correctamente!");
        if (contratosReajustar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No existen contratos habilitados para ajustar");

        } else {
            JOptionPane.showMessageDialog(this, "Los contratos se ajustaron correctamente");
        }

    }

    void accionesBotones() {
        botonVolver1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                dispose();
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        botonVolver1 = new botones.BotonVolver();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Aguarde un momento por favor");
        setPreferredSize(new java.awt.Dimension(800, 700));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Log proceso reajuste");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(botonVolver1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BotonVolver botonVolver1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

}
