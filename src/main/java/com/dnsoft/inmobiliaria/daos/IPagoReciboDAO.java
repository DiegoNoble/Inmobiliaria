package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Iva;
import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.beans.PagoRecibo;
import com.dnsoft.inmobiliaria.beans.TipoContrato;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPagoReciboDAO extends JpaRepository<PagoRecibo, Long> {

    List<PagoRecibo> findByRecibo(Recibo recibo);

    @Query("from PagoRecibo p join fetch p.recibo where p.id = ?1")
    PagoRecibo findFetch(Long idPago);

    @Query("from PagoRecibo p join fetch p.recibo as R where MONTH(p.fecha) = ?1 and YEAR(p.fecha) = ?2 and R.contrato.tipoContrato = ?3 and R.contrato.iva = ?4")
    List<PagoRecibo> findPagosParaFacturacion(Integer mes, Integer ano, TipoContrato tipoContrato, Iva iva);
    
}
