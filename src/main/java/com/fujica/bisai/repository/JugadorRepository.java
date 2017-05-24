package com.fujica.bisai.repository;

import com.fujica.bisai.domain.Jugador;

import com.fujica.bisai.domain.User;
import org.springframework.data.jpa.repository.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Jugador entity.
 */
@SuppressWarnings("unused")
public interface JugadorRepository extends JpaRepository<Jugador,Long> {

    Optional<Jugador> findByUser(User user);


}
