package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.GastoInmuebleInmobiliaria;
import com.dnsoft.inmobiliaria.beans.Inmueble;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGastoInmuebleInmobiliariaDAO extends JpaRepository<GastoInmuebleInmobiliaria, Long> {

    List<GastoInmuebleInmobiliaria> findByInmuebleAndFechaBetween(Inmueble inmueble, Date inico, Date fin);

}
