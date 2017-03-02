package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.GastoInmuebleInquilino;
import com.dnsoft.inmobiliaria.beans.Inmueble;
import com.dnsoft.inmobiliaria.beans.Situacion;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IGastoInmuebleInquilinoDAO extends JpaRepository<GastoInmuebleInquilino, Long> {

    @Query("select c from GastoInmuebleInquilino c where c.rubro.nombre like %?1% ")
    List<GastoInmuebleInquilino> findGastoInmuebleInquilino(String texto);
    
    List<GastoInmuebleInquilino>  findByInmuebleAndFechaBetween(Inmueble inmueble, Date inico, Date fin);
    
    List<GastoInmuebleInquilino>  findByInmuebleAndSituacionIn(Inmueble inmueble,List<Situacion> situacion);
    
    //@Query("select c from GastoInmuebleInquilino c where c.inmueble = ?1 and c.situacion = 'PENDIENTE' and c.situacion = 'CON_SALDO'")
    //List<GastoInmuebleInquilino>  findByInmueblePendientes(Inmueble inmueble);
    
    List<GastoInmuebleInquilino>  findByInmueble(Inmueble inmueble);
}
