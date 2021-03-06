package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Propietario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPropietarioDAO extends JpaRepository<Propietario, Long> {

    @Query("select i from Propietario i where i.nombre like %?1% or i.apellidos like %?1% or i.documento like %?1%")
    List<Propietario> findPropietario(String texto);

    @Query("from Propietario p join fetch p.calle join fetch p.barrio join fetch p.tipoDocumento join fetch p.ciudad where p.id = ?1")
    Propietario findByPropietario(Long propietarioId);

}
