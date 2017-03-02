package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Barrio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IBarriosDAO extends JpaRepository<Barrio, Long> {

    @Query("select c from Barrio c where c.nombre like %?1% ")
    List<Barrio> findBarrios(String texto);
   
}
