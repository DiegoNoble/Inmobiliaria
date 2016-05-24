package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.beans.PagoRecibo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPagoReciboDAO extends JpaRepository<PagoRecibo, Long> {

    List<PagoRecibo> findByRecibo(Recibo recibo);

    @Query("from PagoRecibo p join fetch p.recibo where p.id = ?1")
    PagoRecibo findFetch(Long idPago);

}
