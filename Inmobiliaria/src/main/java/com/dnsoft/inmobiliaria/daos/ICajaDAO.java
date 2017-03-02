package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Caja;
import com.dnsoft.inmobiliaria.beans.Monedas;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICajaDAO extends JpaRepository<Caja, Long> {

    @Query("select c from Caja c where c.fecha >= ?1 and c.moneda = ?2 order by fecha desc")
    List<Caja> findByFechaAfterOrFechaEqualAndMonedaOrderByFechaDesc(Date fecha, Monedas moneda);
    
    @Query("select c from Caja c where c.id = (select max(id) from Caja ca where ca.moneda.id = ?1)")
    Caja findUltimoMovimiento(Long monedaId);
}
