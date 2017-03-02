package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.beans.PagoRecibo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPagoReciboDAO extends JpaRepository<PagoRecibo, Long> {

    List<PagoRecibo> findByRecibo(Recibo recibo);
}
