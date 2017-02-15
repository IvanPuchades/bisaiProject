package com.fujica.bisai.repository;

import com.fujica.bisai.domain.Porra;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Porra entity.
 */
@SuppressWarnings("unused")
public interface PorraRepository extends JpaRepository<Porra,Long> {

}
