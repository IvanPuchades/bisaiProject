package com.fujica.bisai.repository;

import com.fujica.bisai.domain.ValoracionJugador;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ValoracionJugador entity.
 */
@SuppressWarnings("unused")
public interface ValoracionJugadorRepository extends JpaRepository<ValoracionJugador,Long> {

}
