package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.GastoInmuebleInquilino;
import com.dnsoft.inmobiliaria.beans.GastoInmueblePropietario;
import com.dnsoft.inmobiliaria.beans.PagoGastoInmueble;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPagoGastoInmuebleDAO extends JpaRepository<PagoGastoInmueble, Long> {

    List<PagoGastoInmueble> findByGastoInmueblePropietario(GastoInmueblePropietario gastoInmueblePropietario);
    
    List<PagoGastoInmueble> findByGastoInmuebleInquilino(GastoInmuebleInquilino gastoInmuebleInquilino);
}
