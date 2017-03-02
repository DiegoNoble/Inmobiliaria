package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.CuentaBanco;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ICuentaBancoDAO extends JpaRepository<CuentaBanco, Long> {

    @Query("select c from CuentaBanco c where c.nro like %?1% ")
    List<CuentaBanco> findCuentaBanco(String texto);

    @Modifying
    @Transactional
    @Query("delete from CuentaBanco i where i.id= ?1")
    void deleteCuentaBanco(Long id);
}
