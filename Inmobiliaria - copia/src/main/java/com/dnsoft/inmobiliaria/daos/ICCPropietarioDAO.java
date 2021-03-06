package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.CCPropietario;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.Propietario;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICCPropietarioDAO extends JpaRepository<CCPropietario, Long> {

    //List<CCPropietario> findByPropietarioAndMonedaAndFechaGreaterThanEqual(Propietario propietario, Moneda moneda, Date fecha);

    List<CCPropietario> findByPropietarioAndMonedaAndFechaBetween(Propietario propietario, Moneda moneda, Date fechaInicio, Date fechaFin);

    //CCPropietario findByPropietario(Propietario propietario);
    @Query("select c from CCPropietario c where c.id = (select max(id) from CCPropietario ca where ca.moneda = ?1 and c.propietario = ?2)")
    CCPropietario findUltimoMovimiento(Moneda moneda, Propietario propietario);
    
    List<CCPropietario> findByPropietarioAndMonedaOrderByIdAsc(Propietario propietario, Moneda moneda);
}
