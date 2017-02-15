package com.fujica.bisai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fujica.bisai.domain.AdministradorEquipo;

import com.fujica.bisai.repository.AdministradorEquipoRepository;
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
 * REST controller for managing AdministradorEquipo.
 */
@RestController
@RequestMapping("/api")
public class AdministradorEquipoResource {

    private final Logger log = LoggerFactory.getLogger(AdministradorEquipoResource.class);
        
    @Inject
    private AdministradorEquipoRepository administradorEquipoRepository;

    /**
     * POST  /administrador-equipos : Create a new administradorEquipo.
     *
     * @param administradorEquipo the administradorEquipo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new administradorEquipo, or with status 400 (Bad Request) if the administradorEquipo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/administrador-equipos")
    @Timed
    public ResponseEntity<AdministradorEquipo> createAdministradorEquipo(@RequestBody AdministradorEquipo administradorEquipo) throws URISyntaxException {
        log.debug("REST request to save AdministradorEquipo : {}", administradorEquipo);
        if (administradorEquipo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("administradorEquipo", "idexists", "A new administradorEquipo cannot already have an ID")).body(null);
        }
        AdministradorEquipo result = administradorEquipoRepository.save(administradorEquipo);
        return ResponseEntity.created(new URI("/api/administrador-equipos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("administradorEquipo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /administrador-equipos : Updates an existing administradorEquipo.
     *
     * @param administradorEquipo the administradorEquipo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated administradorEquipo,
     * or with status 400 (Bad Request) if the administradorEquipo is not valid,
     * or with status 500 (Internal Server Error) if the administradorEquipo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/administrador-equipos")
    @Timed
    public ResponseEntity<AdministradorEquipo> updateAdministradorEquipo(@RequestBody AdministradorEquipo administradorEquipo) throws URISyntaxException {
        log.debug("REST request to update AdministradorEquipo : {}", administradorEquipo);
        if (administradorEquipo.getId() == null) {
            return createAdministradorEquipo(administradorEquipo);
        }
        AdministradorEquipo result = administradorEquipoRepository.save(administradorEquipo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("administradorEquipo", administradorEquipo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /administrador-equipos : get all the administradorEquipos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of administradorEquipos in body
     */
    @GetMapping("/administrador-equipos")
    @Timed
    public List<AdministradorEquipo> getAllAdministradorEquipos() {
        log.debug("REST request to get all AdministradorEquipos");
        List<AdministradorEquipo> administradorEquipos = administradorEquipoRepository.findAll();
        return administradorEquipos;
    }

    /**
     * GET  /administrador-equipos/:id : get the "id" administradorEquipo.
     *
     * @param id the id of the administradorEquipo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the administradorEquipo, or with status 404 (Not Found)
     */
    @GetMapping("/administrador-equipos/{id}")
    @Timed
    public ResponseEntity<AdministradorEquipo> getAdministradorEquipo(@PathVariable Long id) {
        log.debug("REST request to get AdministradorEquipo : {}", id);
        AdministradorEquipo administradorEquipo = administradorEquipoRepository.findOne(id);
        return Optional.ofNullable(administradorEquipo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /administrador-equipos/:id : delete the "id" administradorEquipo.
     *
     * @param id the id of the administradorEquipo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/administrador-equipos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdministradorEquipo(@PathVariable Long id) {
        log.debug("REST request to delete AdministradorEquipo : {}", id);
        administradorEquipoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("administradorEquipo", id.toString())).build();
    }

}
