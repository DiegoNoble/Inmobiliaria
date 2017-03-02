package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.CuentaBancoPropietario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ICuentaBancoPropietarioDAO extends JpaRepository<CuentaBancoPropietario, Long> {

    @Query("select c from CuentaBancoPropietario c where c.nro like %?1% ")
    List<CuentaBancoPropietario> findCuentaBanco(String texto);

    @Modifying
    @Transactional
    @Query("delete from CuentaBancoPropietario i where i.id= ?1")
    void deleteCuentaBanco(Long id);
}
