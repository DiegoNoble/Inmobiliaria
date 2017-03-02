package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Ciudad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICiudadesDAO extends JpaRepository<Ciudad, Long> {

    @Query("select c from Ciudad c where c.nombre like %?1% ")
    List<Ciudad> findCiudades(String texto);
}
