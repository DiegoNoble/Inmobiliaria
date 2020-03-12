package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.CCPropietario;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.PagoPropietario;
import com.dnsoft.inmobiliaria.beans.Propietario;
import java.util.Date;
import java.util.List;
import javax.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICCPropietarioDAO extends JpaRepository<CCPropietario, Long> {

//    @Query("select cc, pr.tipoPago from CCPropietario cc, PagoPropietario pp, PagoRecibo pr where cc.pagoPropietario= pp.id "
            //+ "and pp.pagoRecibo = pr.id and cc.propietario=?1 and cc.moneda=?2 and cc.fecha >=?3 and cc.fecha<=?4")

    @Query("select cc.fecha, cc.descipcion, pr.tipoPago, cc.debito, cc.credito, cc.saldo from CCPropietario cc "
            + "left join PagoPropietario pp on cc.pagoPropietario= pp.id "
            + "left join PagoRecibo pr on  pp.pagoRecibo = pr.id "
            + "where cc.propietario=?1 and cc.moneda=?2 and cc.fecha >=?3 and cc.fecha<=?4")
    List<Tuple> findByPropietarioAndMonedaAndFechaBetween(Propietario propietario, Moneda moneda, Date fechaInicio, Date fechaFin);

    //CCPropietario findByPropietario(Propietario propietario);
    /*@Query("select c from CCPropietario c join fetch c.propietario where c.id = (select max(id) from CCPropietario ca where ca.moneda = ?1 and ca.propietario = ?2)")
    CCPropietario findUltimoMovimientoEager(Moneda moneda, Propietario propietario);*/

    @Query("select c from CCPropietario c where c.id = (select max(id) from CCPropietario ca where ca.moneda=?1 and ca.propietario=?2)")
    CCPropietario findUltimoMovimiento(Moneda moneda, Propietario propietario);
    
    @Query("Select c from CCPropietario c where c.propietario=?1 and c.moneda =?2 order by c.id Asc ")
    List<CCPropietario> findByPropietarioAndMonedaOrderByIdAsc(Propietario propietario, Moneda moneda);
    
    List<CCPropietario> findByPagoPropietario(PagoPropietario pagoPropietario);

}
