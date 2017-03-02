package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.CuentaBancoInquilino;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ICuentaBancoInquilinoDAO extends JpaRepository<CuentaBancoInquilino, Long> {

    @Query("select c from CuentaBancoInquilino c where c.nro like %?1% ")
    List<CuentaBancoInquilino> findCuentaBanco(String texto);

    @Modifying
    @Transactional
    @Query("delete from CuentaBancoInquilino i where i.id= ?1")
    void deleteCuentaBanco(Long id);
}
