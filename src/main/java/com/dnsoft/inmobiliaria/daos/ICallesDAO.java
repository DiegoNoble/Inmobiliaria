package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Calle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICallesDAO extends JpaRepository<Calle, Long> {

    @Query("select c from Calle c where c.nombre like %?1% ")
    List<Calle> findCalles(String texto);
}
