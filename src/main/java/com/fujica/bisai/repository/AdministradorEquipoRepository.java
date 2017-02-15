package com.fujica.bisai.repository;

import com.fujica.bisai.domain.AdministradorEquipo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AdministradorEquipo entity.
 */
@SuppressWarnings("unused")
public interface AdministradorEquipoRepository extends JpaRepository<AdministradorEquipo,Long> {

}
