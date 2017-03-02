package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Caja;
import com.dnsoft.inmobiliaria.beans.Moneda;
import com.dnsoft.inmobiliaria.beans.PagoRecibo;
import com.dnsoft.inmobiliaria.beans.TipoDeCaja;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICajaDAO extends JpaRepository<Caja, Long> {

    List<Caja> findByMonedaAndTipoDeCajaAndFechaBetweenOrderByFechaDesc(Moneda moneda, TipoDeCaja tipoDeCaja,Date fechaInicio, Date fechaFin);

    @Query("select c from Caja c where c.fecha >= ?1 and c.moneda = ?2 and c.tipoDeCaja= ?3 order by fecha desc")
    List<Caja> findByFechaAfterOrFechaEqualAndMonedaAndTipoDeCajaOrderByFechaDesc(Date fecha, Moneda moneda, TipoDeCaja tipoDeCaja);

    @Query("select c from Caja c where c.id = (select max(id) from Caja ca where ca.moneda = ?1 and ca.tipoDeCaja = ?2)")
    Caja findUltimoMovimiento(Moneda moneda, TipoDeCaja tipoDeCaja);
    
    
    //@Query("select c from Caja c where c.id = (select max(id) from Caja ca where ca.moneda = ?1 and ca.tipoDeCaja = ?2)")
    //Caja findUltimoMovimiento(Moneda moneda, TipoDeCaja tipoDeCaja);

    @Query("select c from Caja c where c.id = (select max(id) from Caja ca where ca.moneda = ?1 and ca.fecha < ?2 and ca.tipoDeCaja = ?3)")
    Caja findSaldoDiaAnterior(Moneda moneda, Date fecha, TipoDeCaja tipoDeCaja);
    
    Caja findByPagoRecibo(PagoRecibo pagoRecibo);

}
