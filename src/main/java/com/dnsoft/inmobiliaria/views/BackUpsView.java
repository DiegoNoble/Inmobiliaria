/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.views;

import com.dnsoft.inmobiliaria.beans.Parametros;
import com.dnsoft.inmobiliaria.daos.IParametrosDAO;
import com.dnsoft.inmobiliaria.utils.BackupDB;
import com.dnsoft.inmobiliaria.utils.Container;
import com.dnsoft.inmobiliaria.utils.LeeProperties;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class BackUpsView extends java.awt.Frame {

    IParametrosDAO parametrosDAO;
    Parametros parametros;
    JFileChooser backup_path;
    LeeProperties props = new LeeProperties();

    Container container;

    public BackUpsView() {
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
        this.container = Container.getInstancia();
        parametrosDAO = container.getBean(IParametrosDAO.class);;
        parametros = (Parametros) parametrosDAO.findAll().get(0);
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane2 = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        txtBackupPath = new javax.swing.JTextField();
        tbGuardar = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(500, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        setLayout(new java.awt.GridBagLayout());

        txtLog.setColumns(20);
        txtLog.setRows(5);
        jScrollPane2.setViewportView(txtLog);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jScrollPane2, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jButton1, gridBagConstraints);

        txtBackupPath.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtBackupPath, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jPanel5, gridBagConstraints);

        tbGuardar.setText("Guardar");
        tbGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(tbGuardar, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        this.dispose();
    }//GEN-LAST:event_exitForm

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            backup_path = new JFileChooser();
            //backup_pathFoto.setCurrentDirectory(new File("C:/Fotos Socios/"));
            backup_path.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            backup_path.setDialogTitle("Cargar pedido XML");
            backup_path.showOpenDialog(this);
            //String foto = "C:/Fotos Socios/" + backup_pathFoto.getSelectedFile().getName();
            txtBackupPath.setText(backup_path.getSelectedFile().getPath());

        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error" + error, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void tbGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbGuardarActionPerformed

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    BackupDB backupDB = new BackupDB(parametros.getMySql_Path(), parametros.getNombreBasesDatos(), txtBackupPath.getText(), txtLog);
                } catch (Exception e) {
                    e.printStackTrace();
                    txtLog.append(e.getMessage());
                }
            }
        }).start();
    }//GEN-LAST:event_tbGuardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton tbGuardar;
    private javax.swing.JTextField txtBackupPath;
    public javax.swing.JTextArea txtLog;
    // End of variables declaration//GEN-END:variables

}
