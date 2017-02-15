package com.fujica.bisai.repository;

import com.fujica.bisai.domain.ValoracionEquipo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ValoracionEquipo entity.
 */
@SuppressWarnings("unused")
public interface ValoracionEquipoRepository extends JpaRepository<ValoracionEquipo,Long> {

}
