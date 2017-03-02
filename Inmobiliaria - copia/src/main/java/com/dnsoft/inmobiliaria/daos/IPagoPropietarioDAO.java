package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.PagoRecibo;
import com.dnsoft.inmobiliaria.beans.PagoPropietario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPagoPropietarioDAO extends JpaRepository<PagoPropietario, Long> {

    List<PagoPropietario> findByPagoAlquiler(PagoRecibo pagoAlquiler);
}
