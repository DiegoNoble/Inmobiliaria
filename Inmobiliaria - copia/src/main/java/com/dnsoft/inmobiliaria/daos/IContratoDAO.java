package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.Inmueble;
import com.dnsoft.inmobiliaria.beans.Inquilino;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IContratoDAO extends JpaRepository<Contrato, Long> {

    @Query("select i from Contrato i where i.inquilino.nombre like %?1% or i.inquilino.apellidos like %?1% or i.inquilino.documento like %?1% or i.inmueble.padron like %?1% or i.inmueble.calle.nombre like %?1% or i.inmueble.barrio.nombre like %?1% or i.inmueble.ciudad.nombre like %?1% or i.inmueble.nro like %?1%")
    List<Contrato> findByInquilinoOrInmueble(String texto);
  
    List<Contrato> findByInquilino(Inquilino inquilino);
    
    Contrato findByInmuebleAndActivo(Inmueble inmueble, Boolean activo);
}
