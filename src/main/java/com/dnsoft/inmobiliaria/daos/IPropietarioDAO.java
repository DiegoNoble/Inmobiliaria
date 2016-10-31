package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Propietario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPropietarioDAO extends JpaRepository<Propietario, Long> {

    @Query("select new Propietario(p.id,p.codReferencia,p.nombre, p.documento, p.activo, p.direccion, p.tel, p.cel) from Propietario "
            + "p where p.nombre like %?1% or p.documento like %?1% or p.codReferencia like %?1% ")
    List<Propietario> findPropietario(String texto);

    
    @Query("from Propietario p join fetch p.tipoDocumento where p.id = ?1")
    Propietario findByPropietarioEager(Long propietarioId);

    @Query("Select new Propietario(p.id,p.codReferencia,p.nombre, p.documento, p.activo, p.direccion, p.tel, p.cel) from Propietario p")
    List<Propietario> findAllNombreDocumento();
    
    
}
