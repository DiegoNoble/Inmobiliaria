package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Inmueble;
import com.dnsoft.inmobiliaria.beans.StatusInmueble;
import com.dnsoft.inmobiliaria.beans.TipoContrato;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IInmuebleDAO extends JpaRepository<Inmueble, Long> {

    @Query("select i from Inmueble i where i.padron like %?1% or i.valorReferencia like %?1% or i.calle.nombre like %?1% or i.barrio.nombre like %?1% or i.ciudad.nombre like %?1% or i.nro like %?1%")
    List<Inmueble> findInmuebles(String texto);

    List<Inmueble> findByTipoContratoAndStatusInmueble(TipoContrato tipoContrato, StatusInmueble statusInmueble);
    
}
