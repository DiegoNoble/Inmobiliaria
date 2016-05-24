package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Inquilino;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IInquilinoDAO extends JpaRepository<Inquilino, Long> {

    @Query("select new Inquilino(i.id, i.nombre, i.documento, i.activo) from Inquilino i where i.nombre like %?1% or i.documento like %?1% ")
    List<Inquilino> findInquilino(String texto);

    @Query("from Inquilino i join fetch i.tipoDocumento left join fetch i.listCuentas where i.id = ?1")
    Inquilino findByInquilinoEager(Long inquilinoId);

    @Query("Select new Inquilino(i.id, i.nombre, i.documento,i.activo, i.direccion, i.tel, i.cel) from Inquilino i order by i.id desc")
    List<Inquilino> findAllNombreDocumento();
}
//
