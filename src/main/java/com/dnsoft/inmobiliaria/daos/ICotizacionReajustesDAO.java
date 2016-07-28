package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.CotizacionReajustes;
import com.dnsoft.inmobiliaria.beans.TipoReajuste;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICotizacionReajustesDAO extends JpaRepository<CotizacionReajustes, Long> {

    List<CotizacionReajustes> findByTipoReajusteOrderByPeriodoDesc(TipoReajuste tipoReajuste);

}
