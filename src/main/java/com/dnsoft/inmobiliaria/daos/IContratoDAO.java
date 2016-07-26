package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.Inmueble;
import com.dnsoft.inmobiliaria.beans.Inquilino;
import com.dnsoft.inmobiliaria.beans.TipoReajuste;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IContratoDAO extends JpaRepository<Contrato, Long> {

    /*Query("select i from Contrato i where i.activo = ?1 and i.inquilino.nombre like %?2% or i.inquilino.apellidos like %?2% or i.inquilino.documento like %?2% "
     + "or i.inmueble.padron like %?2% "
     + "or i.inmueble.calle.nombre like %?2% or i.inmueble.barrio.nombre like %?2% or i.inmueble.ciudad.nombre like %?2% "
     + "or i.inmueble.nro like %?2% or i.inmueble.fraccionamiento like %?2% or i.inmueble.manzana like %?2% or i.inmueble.solar like %?2% order by i.id")
     List<Contrato> findByInquilinoOrInmueble(Boolean activo, String texto );
     */
    @Query("select i from Contrato i where i.activo = ?1")
    List<Contrato> findByInquilinoOrInmuebl(Boolean activo);

    List<Contrato> findByInquilino(Inquilino inquilino);

    Contrato findByInmuebleAndActivo(Inmueble inmueble, Boolean activo);

    @Query("Select new Contrato(c.id,c.descripcionInquilino, c.descripcionInmueble, c.tipoContrato, c.valorCuota, c.valorAlquiler, c.moneda, c.activo) from Contrato c "
            + "where c.descripcionInquilino like %?1% "
            + "or c.descripcionInmueble like %?1% order by c.id desc")
    List<Contrato> findAllRapido(String busqueda);

    /*@Query("Select new Contrato(c.id, c.activo,  c.valorAlquiler, c.valorCuota, c.inquilino.nombre, c.inmueble.fraccionamiento, c.moneda, c.tipoContrato) from Contrato c "
     + "JOIN c.inmueble i"
     + "JOIN "
     + "where c.activo = ?1 and c.inquilino.nombre like %?2% or c.inquilino.documento like %?2% "
     + "or c.inmueble.padron like %?2% "
     + "or c.inmueble.calle.nombre like %?2% or c.inmueble.barrio.nombre like %?2% or c.inmueble.ciudad.nombre like %?2% "
     + "or c.inmueble.nro like %?2% or c.inmueble.fraccionamiento like %?2% or c.inmueble.manzana like %?2% or c.inmueble.solar like %?2% order by c.id")
     List<Contrato> findMasRapido(Boolean activos, String busqueda);
     */
    @Query("from Contrato c where c.id = ?1")
    Contrato findByContrato(Long contratoId);

    @Query("from Contrato c join fetch c.destinoAlquiler join fetch c.garantiaAlquiler join fetch c.tipoReajuste where c.id = ?1")
    Contrato findContratoAlquiler(Long contratoId);

    @Query("from Contrato c where c.id like ?1")
    List<Contrato> findByContratoLike(Long contratoId);

    @Query("from Contrato c where c.tipoReajuste = ?1 and YEAR(c.fechaReajuste) = ?2 and MONTH(c.fechaReajuste) = ?3")
    List<Contrato> findByTipoReauste(TipoReajuste tipoReajuste, Integer year, Integer month);

}
