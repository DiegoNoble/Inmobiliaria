package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.DestinoAlquiler;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IDestinoAlquilerDAO extends JpaRepository<DestinoAlquiler, Long> {

    @Query("select c from DestinoAlquiler c where c.nombre like %?1% ")
    List<DestinoAlquiler> findDestinoAlquiler(String texto);
}
