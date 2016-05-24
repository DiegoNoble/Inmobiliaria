package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Cotizacion;
import com.dnsoft.inmobiliaria.beans.Moneda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICotizacionDAO extends JpaRepository<Cotizacion, Long>{

    List<Cotizacion> findByMonedaOrderByFechaDesc(Moneda moneda);
    
    @Query("select c from Cotizacion c where c.id = (select max(id) from Cotizacion co where co.moneda = ?1)")
    Cotizacion findLastCotizacion(Moneda moneda);
}
