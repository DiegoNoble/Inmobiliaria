package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.GastoInmueblePropietario;
import com.dnsoft.inmobiliaria.beans.Inmueble;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IGastoInmueblePropietarioDAO extends JpaRepository<GastoInmueblePropietario, Long> {

    @Query("select c from GastoInmueblePropietario c where c.rubro.nombre like %?1% ")
    List<GastoInmueblePropietario> findGastoInmueblePropietario(String texto);

    List<GastoInmueblePropietario> findByInmuebleAndFechaBetween(Inmueble inmueble, Date inico, Date fin);
}
