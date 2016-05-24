package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.PagoRecibo;
import com.dnsoft.inmobiliaria.beans.PagoPropietario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPagoPropietarioDAO extends JpaRepository<PagoPropietario, Long> {

    @Query("from PagoPropietario i join fetch i.propietario where i.pagoRecibo = ?1")
    List<PagoPropietario> findByPagoAlquiler(PagoRecibo pagoAlquiler);
}
