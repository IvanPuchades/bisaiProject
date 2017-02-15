package com.fujica.bisai.repository;

import com.fujica.bisai.domain.Torneo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Torneo entity.
 */
@SuppressWarnings("unused")
public interface TorneoRepository extends JpaRepository<Torneo,Long> {

    @Query("select distinct torneo from Torneo torneo left join fetch torneo.administradors left join fetch torneo.locales left join fetch torneo.equipos")
    List<Torneo> findAllWithEagerRelationships();

    @Query("select torneo from Torneo torneo left join fetch torneo.administradors left join fetch torneo.locales left join fetch torneo.equipos where torneo.id =:id")
    Torneo findOneWithEagerRelationships(@Param("id") Long id);

}
