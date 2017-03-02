/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnsoft.inmobiliaria.utils;

import com.dnsoft.inmobiliaria.beans.Recibos;
import com.dnsoft.inmobiliaria.daos.IRecibosDAO;
import java.util.Date;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Diego Noble
 */
public class PagaRecibo {

    Recibos recibo;
    ApplicationContext applicationContext;
    IRecibosDAO recibosDAO;
    

    public PagaRecibo(Recibos recibo) {
        this.recibo = recibo;
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        recibosDAO = applicationContext.getBean(IRecibosDAO.class);
    }

    public void pagarRecibo() {
        recibo.setFechaPago(new Date());
        recibo.setPago(Boolean.TRUE);
        recibosDAO.save(recibo);

    }
}
