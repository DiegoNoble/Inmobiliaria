package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.GarantiaAlquiler;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IGarantiaAlquilerDAO extends JpaRepository<GarantiaAlquiler, Long> {

    @Query("select c from GarantiaAlquiler c where c.nombre like %?1% ")
    List<GarantiaAlquiler> findGarantiaAlquiler(String texto);
}
