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

    List<Recibo> findByContrato(Contrato contrato);

    @Modifying
    @Transactional
    @Query("delete from Recibo i where i.id= ?1")
    void deleteRecibo(Long id);
    
    List<Recibo>  findByContratoAndSituacionIn(Contrato contrato,List<Situacion> situacion);
    
    //@Query("select c from Alquiler c where c.contrato = ?1 and c.situacion not in(PAGO)")
    //List<Alquiler>  findByContratoAndSitucionNotIn(Contrato contrato);
    
    
}
