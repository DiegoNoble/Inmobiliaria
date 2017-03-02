package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.TipoReajuste;
import com.dnsoft.inmobiliaria.beans.TipoReajusteAlquilerEnum;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITipoReajusteDAO extends JpaRepository<TipoReajuste, Long> {
    
    List<TipoReajuste> findByTipoReajusteAlquilerEnum(TipoReajusteAlquilerEnum tipoReajusteAlquilerEnum);

}
