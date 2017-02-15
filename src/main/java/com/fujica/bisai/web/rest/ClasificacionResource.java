package com.fujica.bisai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fujica.bisai.domain.Clasificacion;

import com.fujica.bisai.repository.ClasificacionRepository;
import com.fujica.bisai.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Clasificacion.
 */
@RestController
@RequestMapping("/api")
public class ClasificacionResource {

    private final Logger log = LoggerFactory.getLogger(ClasificacionResource.class);
        
    @Inject
    private ClasificacionRepository clasificacionRepository;

    /**
     * POST  /clasificacions : Create a new clasificacion.
     *
     * @param clasificacion the clasificacion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clasificacion, or with status 400 (Bad Request) if the clasificacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clasificacions")
    @Timed
    public ResponseEntity<Clasificacion> createClasificacion(@Valid @RequestBody Clasificacion clasificacion) throws URISyntaxException {
        log.debug("REST request to save Clasificacion : {}", clasificacion);
        if (clasificacion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("clasificacion", "idexists", "A new clasificacion cannot already have an ID")).body(null);
        }
        Clasificacion result = clasificacionRepository.save(clasificacion);
        return ResponseEntity.created(new URI("/api/clasificacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("clasificacion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clasificacions : Updates an existing clasificacion.
     *
     * @param clasificacion the clasificacion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clasificacion,
     * or with status 400 (Bad Request) if the clasificacion is not valid,
     * or with status 500 (Internal Server Error) if the clasificacion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clasificacions")
    @Timed
    public ResponseEntity<Clasificacion> updateClasificacion(@Valid @RequestBody Clasificacion clasificacion) throws URISyntaxException {
        log.debug("REST request to update Clasificacion : {}", clasificacion);
        if (clasificacion.getId() == null) {
            return createClasificacion(clasificacion);
        }
        Clasificacion result = clasificacionRepository.save(clasificacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("clasificacion", clasificacion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clasificacions : get all the clasificacions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of clasificacions in body
     */
    @GetMapping("/clasificacions")
    @Timed
    public List<Clasificacion> getAllClasificacions() {
        log.debug("REST request to get all Clasificacions");
        List<Clasificacion> clasificacions = clasificacionRepository.findAll();
        return clasificacions;
    }

    /**
     * GET  /clasificacions/:id : get the "id" clasificacion.
     *
     * @param id the id of the clasificacion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clasificacion, or with status 404 (Not Found)
     */
    @GetMapping("/clasificacions/{id}")
    @Timed
    public ResponseEntity<Clasificacion> getClasificacion(@PathVariable Long id) {
        log.debug("REST request to get Clasificacion : {}", id);
        Clasificacion clasificacion = clasificacionRepository.findOne(id);
        return Optional.ofNullable(clasificacion)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /clasificacions/:id : delete the "id" clasificacion.
     *
     * @param id the id of the clasificacion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clasificacions/{id}")
    @Timed
    public ResponseEntity<Void> deleteClasificacion(@PathVariable Long id) {
        log.debug("REST request to delete Clasificacion : {}", id);
        clasificacionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("clasificacion", id.toString())).build();
    }

}
