package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Comodidades;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IComodidadesDAO extends JpaRepository<Comodidades, Long> {

    @Query("select c from Comodidades c where c.nombre like %?1% ")
    List<Comodidades> findComodidades(String texto);
}
