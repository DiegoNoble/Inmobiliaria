package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.PropietarioHasInmueble;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPropietarioHasInmuebleDAO extends JpaRepository<PropietarioHasInmueble, Long>{

    List<PropietarioHasInmueble> findByInmuebleId(Long idInmueble);
    
}
