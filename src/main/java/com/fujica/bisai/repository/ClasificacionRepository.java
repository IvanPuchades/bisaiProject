package com.fujica.bisai.repository;

import com.fujica.bisai.domain.Clasificacion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Clasificacion entity.
 */
@SuppressWarnings("unused")
public interface ClasificacionRepository extends JpaRepository<Clasificacion,Long> {

}
