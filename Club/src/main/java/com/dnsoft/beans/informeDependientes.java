package com.dnsoft.beans;

import com.club.control.acceso.conexion;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class informeDependientes {

    conexion conexionInforme = new conexion();
   

    public informeDependientes(){
        
        
        try {
            conexionInforme.conecta();
            conexionInforme.ejecutaSQL("SELECT tbdependiente.`ID` AS tbdependiente_ID, tbdependiente.`NOMBRE` AS tbdependiente_NOMBRE, "
                    + "tbdependiente.`CI` AS tbdependiente_CI, tbdependiente.`FECHANACIMIENTO` AS tbdependiente_FECHANACIMIENTO, "
                    + "tbdependiente.`SITUACION` AS tbdependiente_SITUACION, tbdependiente.`ID_SOCIO` AS tbdependiente_ID_SOCIO, "
                    + "tbsocio.`ID` AS tbsocio_ID, tbsocio.`NOMBRE` AS tbsocio_NOMBRE FROM "
                    + "`tbsocio` tbsocio INNER JOIN `tbdependiente` tbdependiente ON tbsocio.`ID` = tbdependiente.`ID_SOCIO`");

            
            JRResultSetDataSource jrRS = new JRResultSetDataSource(conexionInforme.resultSet);
             InputStream resource = getClass().getResourceAsStream("/com/club/control/dependientes/informeDependientes.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, new HashMap(), jrRS);
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException ex) {
            Logger.getLogger(informeDependientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main (String args[]){

        new informeDependientes();
    }

}
