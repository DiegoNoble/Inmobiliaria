package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.CuentaBancoProveedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ICuentaBancoProveedorDAO extends JpaRepository<CuentaBancoProveedor, Long> {

    @Query("select c from CuentaBancoProveedor c where c.nro like %?1% ")
    List<CuentaBancoProveedor> findCuentaBanco(String texto);

    @Modifying
    @Transactional
    @Query("delete from CuentaBancoProveedor i where i.id= ?1")
    void deleteCuentaBanco(Long id);
}
