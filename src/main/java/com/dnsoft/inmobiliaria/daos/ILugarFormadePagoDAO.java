package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.LugarFormadePago;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ILugarFormadePagoDAO extends JpaRepository<LugarFormadePago, Long> {

    @Query("select c from LugarFormadePago c where c.nombre like %?1% ")
    List<LugarFormadePago> findLugarFormadePago(String texto);
}
