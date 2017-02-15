package com.fujica.bisai.repository;

import com.fujica.bisai.domain.Partida;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Partida entity.
 */
@SuppressWarnings("unused")
public interface PartidaRepository extends JpaRepository<Partida,Long> {

}
