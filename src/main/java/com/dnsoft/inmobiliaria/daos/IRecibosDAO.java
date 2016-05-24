package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.Recibo;
import com.dnsoft.inmobiliaria.beans.Situacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IRecibosDAO extends JpaRepository<Recibo, Long> {

    List<Recibo> findByContratoOrderByFechaVencimientoAsc(Contrato contrato);

    @Modifying
    @Transactional
    @Query("delete from Recibo i where i.id= ?1")
    void deleteRecibo(Long id);

    List<Recibo> findByContratoAndSituacionInOrderByFechaVencimientoAsc(Contrato contrato, List<Situacion> situacion);

    List<Recibo> findByContratoOrderByNroReciboDesc(Contrato contrato);

    Recibo findByContratoAndNroRecibo(Contrato contrato, Integer nroRecibo);

    List<Recibo> findByContratoAndSituacion(Contrato contrato, Situacion situacion);

    //@Query("Select r from Recibos where r.situacion = 'PENDIENTE' and nroRecibo < ?1")
    @Query("SELECT CASE WHEN COUNT(r) = 0 THEN false ELSE true END FROM Recibo r WHERE r.situacion = 'PENDIENTE' and r.contrato = ?1 and r.nroRecibo < ?2")
    Boolean findRecibosPendientesAnteriores(Contrato contrato, Integer nroRecibo);

    @Query("SELECT CASE WHEN COUNT(r) = 0 THEN false ELSE true END FROM Recibo r WHERE r.situacion = 'PAGO' and r.contrato = ?1 and r.nroRecibo = ?2")
    Boolean findPorNroReciboContrato(Contrato contrato, Integer nroRecibo);
    
}
