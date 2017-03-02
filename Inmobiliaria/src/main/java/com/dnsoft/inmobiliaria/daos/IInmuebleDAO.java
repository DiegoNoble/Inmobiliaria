package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Inmueble;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IInmuebleDAO extends JpaRepository<Inmueble, Long> {

    @Query("select i from Inmueble i where i.padron like %?1% or i.valorReferencia like %?1%")
    List<Inmueble> findPropietarios(String texto);

    
}
