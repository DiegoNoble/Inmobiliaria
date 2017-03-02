/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import jxl.CellView;
import jxl.Workbook;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Number;

/**
 *
 * @author Diego Noble
 */
public class ExportarDatosExcel {

    private String file;
    private JTable tabla;
    private String nombreHoja;

    public ExportarDatosExcel(JTable tabla, String nombreHoja) throws Exception {
        this.tabla = tabla;
        this.nombreHoja = nombreHoja;

    }

    public void exportarJTableExcel() {

        if (tabla.getRowCount() > 0) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
            chooser.setFileFilter(filter);
            chooser.setDialogTitle("Guardar archivo");
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

                file = chooser.getSelectedFile().toString().concat(".xls");

                try {
                    if (exportar() == true) {
                        JOptionPane.showMessageDialog(null, "Los datos fueron exportados a excel en el directorio seleccionado", "Mensaje de Informacion", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Hubo un error " + e.getMessage(), " Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay datos para exportar", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean exportar() {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(file)));
            WritableWorkbook w = Workbook.createWorkbook(out);
            CellView cell = new CellView();

            WritableSheet s = w.createSheet(nombreHoja, 0);

            TableModel model = tabla.getModel();

            for (int i = 0; i < tabla.getColumnCount(); i++) {
                Label column = new Label(i, 0, model.getColumnName(i));
                s.addCell(column);
                cell = s.getColumnView(i);
                cell.setAutosize(true);
                s.setColumnView(i, cell);

                for (int j = 0; j < tabla.getRowCount(); j++) {

                    Object object = tabla.getValueAt(j, i);

                    Boolean validaFecha = DateUtil.isValidDate(String.valueOf(object));
                    if (object != null) {
                        if (validaObjetoNumerico(object.toString())) {
                            s.addCell(new Number(i, j + 1, Double.valueOf(String.valueOf(object))));
                        } else if (validaFecha) {
                            s.addCell(new DateTime(i, j + 1, (Date) object));
                        } else if (!validaObjetoNumerico(object.toString()) || !validaFecha) {
                            s.addCell(new Label(i, j + 1, String.valueOf(object)));
                        }
                    }
                }

            }
            w.write();
            w.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    boolean validaObjetoNumerico(String str) {

        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }
}
