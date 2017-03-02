package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Proveedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IProveedorDAO extends JpaRepository<Proveedor, Long>{

    @Query("select i from Proveedor i where i.nombre like %?1% or i.apellidos like %?1% or i.documento like %?1%")
    List<Proveedor> findProveedor(String texto);
}
//