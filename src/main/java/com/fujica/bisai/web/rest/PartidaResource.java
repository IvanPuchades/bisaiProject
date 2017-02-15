package com.fujica.bisai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fujica.bisai.domain.Partida;

import com.fujica.bisai.repository.PartidaRepository;
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
 * REST controller for managing Partida.
 */
@RestController
@RequestMapping("/api")
public class PartidaResource {

    private final Logger log = LoggerFactory.getLogger(PartidaResource.class);
        
    @Inject
    private PartidaRepository partidaRepository;

    /**
     * POST  /partidas : Create a new partida.
     *
     * @param partida the partida to create
     * @return the ResponseEntity with status 201 (Created) and with body the new partida, or with status 400 (Bad Request) if the partida has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/partidas")
    @Timed
    public ResponseEntity<Partida> createPartida(@RequestBody Partida partida) throws URISyntaxException {
        log.debug("REST request to save Partida : {}", partida);
        if (partida.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("partida", "idexists", "A new partida cannot already have an ID")).body(null);
        }
        Partida result = partidaRepository.save(partida);
        return ResponseEntity.created(new URI("/api/partidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("partida", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /partidas : Updates an existing partida.
     *
     * @param partida the partida to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated partida,
     * or with status 400 (Bad Request) if the partida is not valid,
     * or with status 500 (Internal Server Error) if the partida couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/partidas")
    @Timed
    public ResponseEntity<Partida> updatePartida(@RequestBody Partida partida) throws URISyntaxException {
        log.debug("REST request to update Partida : {}", partida);
        if (partida.getId() == null) {
            return createPartida(partida);
        }
        Partida result = partidaRepository.save(partida);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("partida", partida.getId().toString()))
            .body(result);
    }

    /**
     * GET  /partidas : get all the partidas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of partidas in body
     */
    @GetMapping("/partidas")
    @Timed
    public List<Partida> getAllPartidas() {
        log.debug("REST request to get all Partidas");
        List<Partida> partidas = partidaRepository.findAll();
        return partidas;
    }

    /**
     * GET  /partidas/:id : get the "id" partida.
     *
     * @param id the id of the partida to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the partida, or with status 404 (Not Found)
     */
    @GetMapping("/partidas/{id}")
    @Timed
    public ResponseEntity<Partida> getPartida(@PathVariable Long id) {
        log.debug("REST request to get Partida : {}", id);
        Partida partida = partidaRepository.findOne(id);
        return Optional.ofNullable(partida)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /partidas/:id : delete the "id" partida.
     *
     * @param id the id of the partida to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/partidas/{id}")
    @Timed
    public ResponseEntity<Void> deletePartida(@PathVariable Long id) {
        log.debug("REST request to delete Partida : {}", id);
        partidaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("partida", id.toString())).build();
    }

}
