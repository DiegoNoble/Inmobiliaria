package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.TipoInmueble;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ITipoInmuebleDAO extends JpaRepository<TipoInmueble, Long> {

    @Query("select c from TipoInmueble c where c.nombre like %?1% ")
    List<TipoInmueble> findTipoInmueble(String texto);
}
