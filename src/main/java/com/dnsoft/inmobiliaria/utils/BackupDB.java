/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JTextArea;

/**
 *
 * @author Diego
 */
public class BackupDB {

    // Constantes da classe
    private static String SEPARATOR = File.separator;
    private String MYSQL_PATH;
    private String BackUp_Path;
    private String nombreArchivo;

    // Lista dos bancos de dados a serem "backupeados"; se desejar adicionar mais,
    // basta colocar o nome separado por espaços dos outros nomes
    private String DATABASES;
    private List<String> dbList = new ArrayList<String>();
    LeeProperties props;

    public BackupDB(String MySql_Path, String nombreBasesDatos, String BackUp_Path, JTextArea txtLog) {
        props = new LeeProperties();

        this.MYSQL_PATH = MySql_Path;
        this.DATABASES = nombreBasesDatos;
        this.BackUp_Path = BackUp_Path;
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy HHmmss");

        String command = MYSQL_PATH + "mysqldump.exe";
        String[] databases = DATABASES.split(" ");
        for (int i = 0; i < databases.length; i++) {
            dbList.add(databases[i]);
        }
        // Mostra apresentação
        txtLog.append("Iniciando backups...\n\n");
        // Contador
        int i = 1;
        // Tempo
        long time1, time2, time;
        // Início
        time1 = System.currentTimeMillis();
        for (String dbName : dbList) {
            nombreArchivo = dbName + " " + dateFormat.format(new Date()) + ".sql";
            ProcessBuilder pb = new ProcessBuilder(command,
                    "--user=" + props.getUsr(),
                    "--password=" + props.getPsw(),
                    dbName,
                    "--result-file=" + this.BackUp_Path + "/" + nombreArchivo);
            try {
                txtLog.append("Backup del banco de dados (" + i + "): " + dbName + " ...");
                pb.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }
        // Fim
        time2 = System.currentTimeMillis();
        // Tempo total da operação
        time = time2 - time1;
        // Avisa do sucesso
        txtLog.append("\nBackups realizados correctamente.\n\n");
        txtLog.append("\nTempo total de processamento: " + time + " ms\n");
        txtLog.append("\nRespalde el archivo exportado en " + this.BackUp_Path + "/" + nombreArchivo);
        txtLog.append("\nFinalizando...");
        try {
            // Paralisa por 2 segundos
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        // Termina o aplicativo
        // System.exit(0);
    }

}
