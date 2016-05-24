package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Rubro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IRubroDAO extends JpaRepository<Rubro, Long> {

    @Query("select c from Rubro c where c.nombre like %?1% ")
    List<Rubro> findRubro(String texto);
    Rubro findByNombre(String nombre);

}
