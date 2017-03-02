package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Comodidad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IComodidadDAO extends JpaRepository<Comodidad, Long> {

    @Query("select c from Comodidad c where c.nombre like %?1% ")
    List<Comodidad> findComodidad(String texto);
}
