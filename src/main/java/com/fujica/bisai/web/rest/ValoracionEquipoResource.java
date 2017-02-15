package com.fujica.bisai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fujica.bisai.domain.ValoracionEquipo;

import com.fujica.bisai.repository.ValoracionEquipoRepository;
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
 * REST controller for managing ValoracionEquipo.
 */
@RestController
@RequestMapping("/api")
public class ValoracionEquipoResource {

    private final Logger log = LoggerFactory.getLogger(ValoracionEquipoResource.class);
        
    @Inject
    private ValoracionEquipoRepository valoracionEquipoRepository;

    /**
     * POST  /valoracion-equipos : Create a new valoracionEquipo.
     *
     * @param valoracionEquipo the valoracionEquipo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new valoracionEquipo, or with status 400 (Bad Request) if the valoracionEquipo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/valoracion-equipos")
    @Timed
    public ResponseEntity<ValoracionEquipo> createValoracionEquipo(@RequestBody ValoracionEquipo valoracionEquipo) throws URISyntaxException {
        log.debug("REST request to save ValoracionEquipo : {}", valoracionEquipo);
        if (valoracionEquipo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("valoracionEquipo", "idexists", "A new valoracionEquipo cannot already have an ID")).body(null);
        }
        ValoracionEquipo result = valoracionEquipoRepository.save(valoracionEquipo);
        return ResponseEntity.created(new URI("/api/valoracion-equipos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("valoracionEquipo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /valoracion-equipos : Updates an existing valoracionEquipo.
     *
     * @param valoracionEquipo the valoracionEquipo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated valoracionEquipo,
     * or with status 400 (Bad Request) if the valoracionEquipo is not valid,
     * or with status 500 (Internal Server Error) if the valoracionEquipo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/valoracion-equipos")
    @Timed
    public ResponseEntity<ValoracionEquipo> updateValoracionEquipo(@RequestBody ValoracionEquipo valoracionEquipo) throws URISyntaxException {
        log.debug("REST request to update ValoracionEquipo : {}", valoracionEquipo);
        if (valoracionEquipo.getId() == null) {
            return createValoracionEquipo(valoracionEquipo);
        }
        ValoracionEquipo result = valoracionEquipoRepository.save(valoracionEquipo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("valoracionEquipo", valoracionEquipo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /valoracion-equipos : get all the valoracionEquipos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of valoracionEquipos in body
     */
    @GetMapping("/valoracion-equipos")
    @Timed
    public List<ValoracionEquipo> getAllValoracionEquipos() {
        log.debug("REST request to get all ValoracionEquipos");
        List<ValoracionEquipo> valoracionEquipos = valoracionEquipoRepository.findAll();
        return valoracionEquipos;
    }

    /**
     * GET  /valoracion-equipos/:id : get the "id" valoracionEquipo.
     *
     * @param id the id of the valoracionEquipo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the valoracionEquipo, or with status 404 (Not Found)
     */
    @GetMapping("/valoracion-equipos/{id}")
    @Timed
    public ResponseEntity<ValoracionEquipo> getValoracionEquipo(@PathVariable Long id) {
        log.debug("REST request to get ValoracionEquipo : {}", id);
        ValoracionEquipo valoracionEquipo = valoracionEquipoRepository.findOne(id);
        return Optional.ofNullable(valoracionEquipo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /valoracion-equipos/:id : delete the "id" valoracionEquipo.
     *
     * @param id the id of the valoracionEquipo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/valoracion-equipos/{id}")
    @Timed
    public ResponseEntity<Void> deleteValoracionEquipo(@PathVariable Long id) {
        log.debug("REST request to delete ValoracionEquipo : {}", id);
        valoracionEquipoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("valoracionEquipo", id.toString())).build();
    }

}
