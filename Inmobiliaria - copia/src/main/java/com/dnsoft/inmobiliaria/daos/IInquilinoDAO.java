package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Inquilino;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IInquilinoDAO extends JpaRepository<Inquilino, Long>{

    @Query("select i from Inquilino i where i.nombre like %?1% or i.apellidos like %?1% or i.documento like %?1%")
    List<Inquilino> findInquilino(String texto);
}
//