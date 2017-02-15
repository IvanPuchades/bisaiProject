package com.fujica.bisai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fujica.bisai.domain.Porra;

import com.fujica.bisai.repository.PorraRepository;
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
 * REST controller for managing Porra.
 */
@RestController
@RequestMapping("/api")
public class PorraResource {

    private final Logger log = LoggerFactory.getLogger(PorraResource.class);
        
    @Inject
    private PorraRepository porraRepository;

    /**
     * POST  /porras : Create a new porra.
     *
     * @param porra the porra to create
     * @return the ResponseEntity with status 201 (Created) and with body the new porra, or with status 400 (Bad Request) if the porra has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/porras")
    @Timed
    public ResponseEntity<Porra> createPorra(@RequestBody Porra porra) throws URISyntaxException {
        log.debug("REST request to save Porra : {}", porra);
        if (porra.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("porra", "idexists", "A new porra cannot already have an ID")).body(null);
        }
        Porra result = porraRepository.save(porra);
        return ResponseEntity.created(new URI("/api/porras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("porra", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /porras : Updates an existing porra.
     *
     * @param porra the porra to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated porra,
     * or with status 400 (Bad Request) if the porra is not valid,
     * or with status 500 (Internal Server Error) if the porra couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/porras")
    @Timed
    public ResponseEntity<Porra> updatePorra(@RequestBody Porra porra) throws URISyntaxException {
        log.debug("REST request to update Porra : {}", porra);
        if (porra.getId() == null) {
            return createPorra(porra);
        }
        Porra result = porraRepository.save(porra);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("porra", porra.getId().toString()))
            .body(result);
    }

    /**
     * GET  /porras : get all the porras.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of porras in body
     */
    @GetMapping("/porras")
    @Timed
    public List<Porra> getAllPorras() {
        log.debug("REST request to get all Porras");
        List<Porra> porras = porraRepository.findAll();
        return porras;
    }

    /**
     * GET  /porras/:id : get the "id" porra.
     *
     * @param id the id of the porra to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the porra, or with status 404 (Not Found)
     */
    @GetMapping("/porras/{id}")
    @Timed
    public ResponseEntity<Porra> getPorra(@PathVariable Long id) {
        log.debug("REST request to get Porra : {}", id);
        Porra porra = porraRepository.findOne(id);
        return Optional.ofNullable(porra)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /porras/:id : delete the "id" porra.
     *
     * @param id the id of the porra to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/porras/{id}")
    @Timed
    public ResponseEntity<Void> deletePorra(@PathVariable Long id) {
        log.debug("REST request to delete Porra : {}", id);
        porraRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("porra", id.toString())).build();
    }

}
