package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Contrato;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IContratoDAO extends JpaRepository<Contrato, Long> {

    @Query("select i from Contrato i where i.inquilino.nombre like %?1% or i.inquilino.apellidos like %?1% or i.inquilino.documento like %?1%")
    List<Contrato> findByInquilino(String texto);
  
    
}
