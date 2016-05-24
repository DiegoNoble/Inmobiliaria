package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.PagoRecibo;
import com.dnsoft.inmobiliaria.beans.Recibo;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPagoDAO extends JpaRepository<PagoRecibo, Long> {

    @Query("select sum(pr.mora) from PagoRecibo pr where pr.recibo = ?1")
    BigDecimal findSumMora(Recibo recibo);

}
