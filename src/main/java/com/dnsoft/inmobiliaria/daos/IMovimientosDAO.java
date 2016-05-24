package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Caja;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMovimientosDAO extends JpaRepository<Caja, Long>{

    
}
