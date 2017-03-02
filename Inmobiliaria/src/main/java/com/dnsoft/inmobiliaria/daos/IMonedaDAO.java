package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Monedas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMonedaDAO extends JpaRepository<Monedas, Long>{

    public Monedas findByNombre(String nombre);
}
