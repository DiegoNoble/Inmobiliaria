package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Inmueble;
import com.dnsoft.inmobiliaria.beans.Propietario;
import com.dnsoft.inmobiliaria.beans.PropietarioHasInmueble;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IPropietarioHasInmuebleDAO extends JpaRepository<PropietarioHasInmueble, Long> {

    List<PropietarioHasInmueble> findByInmuebleId(Long idInmueble);

    List<PropietarioHasInmueble> findByPropietario(Propietario propietario);

    @Modifying
    @Transactional
    @Query("delete from PropietarioHasInmueble i where i.id= ?1")
    void deletePropietarioHasInmueble(Long id);

    List<PropietarioHasInmueble> findByInmueble(Inmueble inmueble);
}
