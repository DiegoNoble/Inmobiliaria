package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Cotizaciones;
import com.dnsoft.inmobiliaria.beans.Monedas;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICotizacionDAO extends JpaRepository<Cotizaciones, Long>{

    List<Cotizaciones> findByMonedas(Monedas moneda);
    
    @Query("select c from Cotizaciones c where c.id = (select max(id) from Cotizaciones co where co.monedas = ?1)")
    Cotizaciones findLastCotizacion(Monedas moneda);
}
