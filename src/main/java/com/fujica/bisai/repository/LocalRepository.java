package com.fujica.bisai.repository;

import com.fujica.bisai.domain.Local;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Local entity.
 */
@SuppressWarnings("unused")
public interface LocalRepository extends JpaRepository<Local,Long> {

}
