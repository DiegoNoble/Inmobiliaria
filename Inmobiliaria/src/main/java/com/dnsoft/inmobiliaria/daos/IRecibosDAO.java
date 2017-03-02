package com.dnsoft.inmobiliaria.daos;

import com.dnsoft.inmobiliaria.beans.Contrato;
import com.dnsoft.inmobiliaria.beans.Recibos;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecibosDAO extends JpaRepository<Recibos, Long> {

    List<Recibos> findByContrato(Contrato contrto);

}
