package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.TipoDocumento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ITipoDocumentoDAO extends JpaRepository<TipoDocumento, Long> {

    @Query("select c from TipoDocumento c where c.nombre like %?1% ")
    List<TipoDocumento> findBanco(String texto);
}
