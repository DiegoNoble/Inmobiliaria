package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Inmueble;
import com.dnsoft.inmobiliaria.beans.StatusInmueble;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IInmuebleDAO extends JpaRepository<Inmueble, Long> {

    List<Inmueble> findAllOrderBy();
    
    
    @Query("select i from Inmueble i where i.padron like %?1% or i.calle.nombre like %?1% "
            + "or i.barrio.nombre like %?1% or i.ciudad.nombre like %?1% or i.nro like %?1% "
            + "or i.fraccionamiento like %?1% or i.manzana like %?1% or i.solar like %?1% order by i.id desc")
    List<Inmueble> findInmuebles(String texto);

    List<Inmueble> findByStatusInmueble(StatusInmueble statusInmueble);

    
    @Query("from Inmueble i join fetch i.calle join fetch i.barrio join fetch i.tipoinmueble join fetch i.ciudad where i.id = ?1")
    Inmueble findByInmuebleEager(Long inmuebleId);
}
