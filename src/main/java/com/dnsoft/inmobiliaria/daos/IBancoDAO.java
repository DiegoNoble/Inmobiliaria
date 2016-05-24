package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Banco;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IBancoDAO extends JpaRepository<Banco, Long> {

    @Query("select c from Banco c where c.nombre like %?1% ")
    List<Banco> findBanco(String texto);
}
