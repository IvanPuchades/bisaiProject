package com.fujica.bisai.repository;

import com.fujica.bisai.domain.Juego;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Juego entity.
 */
@SuppressWarnings("unused")
public interface JuegoRepository extends JpaRepository<Juego,Long> {

}
