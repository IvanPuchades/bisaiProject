package com.fujica.bisai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fujica.bisai.domain.ValoracionJugador;

import com.fujica.bisai.repository.ValoracionJugadorRepository;
import com.fujica.bisai.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ValoracionJugador.
 */
@RestController
@RequestMapping("/api")
public class ValoracionJugadorResource {

    private final Logger log = LoggerFactory.getLogger(ValoracionJugadorResource.class);
        
    @Inject
    private ValoracionJugadorRepository valoracionJugadorRepository;

    /**
     * POST  /valoracion-jugadors : Create a new valoracionJugador.
     *
     * @param valoracionJugador the valoracionJugador to create
     * @return the ResponseEntity with status 201 (Created) and with body the new valoracionJugador, or with status 400 (Bad Request) if the valoracionJugador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/valoracion-jugadors")
    @Timed
    public ResponseEntity<ValoracionJugador> createValoracionJugador(@RequestBody ValoracionJugador valoracionJugador) throws URISyntaxException {
        log.debug("REST request to save ValoracionJugador : {}", valoracionJugador);
        if (valoracionJugador.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("valoracionJugador", "idexists", "A new valoracionJugador cannot already have an ID")).body(null);
        }
        ValoracionJugador result = valoracionJugadorRepository.save(valoracionJugador);
        return ResponseEntity.created(new URI("/api/valoracion-jugadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("valoracionJugador", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /valoracion-jugadors : Updates an existing valoracionJugador.
     *
     * @param valoracionJugador the valoracionJugador to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated valoracionJugador,
     * or with status 400 (Bad Request) if the valoracionJugador is not valid,
     * or with status 500 (Internal Server Error) if the valoracionJugador couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/valoracion-jugadors")
    @Timed
    public ResponseEntity<ValoracionJugador> updateValoracionJugador(@RequestBody ValoracionJugador valoracionJugador) throws URISyntaxException {
        log.debug("REST request to update ValoracionJugador : {}", valoracionJugador);
        if (valoracionJugador.getId() == null) {
            return createValoracionJugador(valoracionJugador);
        }
        ValoracionJugador result = valoracionJugadorRepository.save(valoracionJugador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("valoracionJugador", valoracionJugador.getId().toString()))
            .body(result);
    }

    /**
     * GET  /valoracion-jugadors : get all the valoracionJugadors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of valoracionJugadors in body
     */
    @GetMapping("/valoracion-jugadors")
    @Timed
    public List<ValoracionJugador> getAllValoracionJugadors() {
        log.debug("REST request to get all ValoracionJugadors");
        List<ValoracionJugador> valoracionJugadors = valoracionJugadorRepository.findAll();
        return valoracionJugadors;
    }

    /**
     * GET  /valoracion-jugadors/:id : get the "id" valoracionJugador.
     *
     * @param id the id of the valoracionJugador to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the valoracionJugador, or with status 404 (Not Found)
     */
    @GetMapping("/valoracion-jugadors/{id}")
    @Timed
    public ResponseEntity<ValoracionJugador> getValoracionJugador(@PathVariable Long id) {
        log.debug("REST request to get ValoracionJugador : {}", id);
        ValoracionJugador valoracionJugador = valoracionJugadorRepository.findOne(id);
        return Optional.ofNullable(valoracionJugador)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /valoracion-jugadors/:id : delete the "id" valoracionJugador.
     *
     * @param id the id of the valoracionJugador to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/valoracion-jugadors/{id}")
    @Timed
    public ResponseEntity<Void> deleteValoracionJugador(@PathVariable Long id) {
        log.debug("REST request to delete ValoracionJugador : {}", id);
        valoracionJugadorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("valoracionJugador", id.toString())).build();
    }

}
