package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.PagoRecibo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPagoDAO extends JpaRepository<PagoRecibo, Long> {

    //List<Pago> findByContrato(Contrato contrato);

}
