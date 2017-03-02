package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.InmuebleHasComodidades;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IInmuebleHasComodidadesDAO extends JpaRepository<InmuebleHasComodidades, Long> {

    List<InmuebleHasComodidades> findByInmuebleId(Long idInmueble);

    @Modifying
    @Transactional
    @Query("delete from InmuebleHasComodidades i where i.id= ?1")
    void deleteComodidad(Long id);
}
