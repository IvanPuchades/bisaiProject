package com.fujica.bisai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fujica.bisai.domain.Equipo;

import com.fujica.bisai.domain.Jugador;
import com.fujica.bisai.domain.util.JSR310DateConverters;
import com.fujica.bisai.repository.EquipoRepository;
import com.fujica.bisai.repository.JugadorRepository;
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
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Equipo.
 */
@RestController
@RequestMapping("/api")
public class EquipoResource {

    private final Logger log = LoggerFactory.getLogger(EquipoResource.class);

    @Inject
    private EquipoRepository equipoRepository;
    @Inject
    private JugadorRepository jugadorRepository;

    /**
     * POST  /equipos : Create a new equipo.
     *
     * @param equipo the equipo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new equipo, or with status 400 (Bad Request) if the equipo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/equipos")
    @Timed
    public ResponseEntity<Equipo> createEquipo(@RequestBody Equipo equipo) throws URISyntaxException {
        log.debug("REST request to save Equipo : {}", equipo);
        if (equipo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("equipo", "idexists", "A new equipo cannot already have an ID")).body(null);
        }
        ZonedDateTime now = ZonedDateTime.now();
        equipo.setFechaCreacion(now.toLocalDate());
        Equipo result = equipoRepository.save(equipo);



        return ResponseEntity.created(new URI("/api/equipos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("equipo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /equipos : Updates an existing equipo.
     *
     * @param equipo the equipo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated equipo,
     * or with status 400 (Bad Request) if the equipo is not valid,
     * or with status 500 (Internal Server Error) if the equipo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/equipos")
    @Timed
    public ResponseEntity<Equipo> updateEquipo(@Valid @RequestBody Equipo equipo) throws URISyntaxException {
        log.debug("REST request to update Equipo : {}", equipo);
        if (equipo.getId() == null) {
            return createEquipo(equipo);
        }
        Equipo result = equipoRepository.save(equipo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("equipo", equipo.getId().toString()))
            .body(result);
    }

    @PutMapping("/equipos/{idEquipo}/jugador/{idJugador}")
    @Timed
    public ResponseEntity<Equipo> updateJugadorInEquipo(@PathVariable Long idEquipo,
                                                        @PathVariable Long idJugador,
                                                        @RequestHeader String equipoPassword)
        throws URISyntaxException {

        Jugador jugador = jugadorRepository.findOne(idJugador);
        if (jugador == null){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("jugador", "idJugador", "ID jugador not exist.")).body(null);
        }
        Equipo equipo = equipoRepository.findOneWithEagerRelationships(idEquipo);

        if(equipo == null){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("equipo", "idexists", "A equipo do not exist")).body(null);
        }

        if(jugador == null){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("equipo", "idexists", "A new jugador cannot be null")).body(null);
        }

        if(!equipoPassword.equals( equipo.getPassword())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("equipo", "idexists", "the passwprd did not match")).body(null);

        }

        equipo.getJugadors().add(jugador);

        Equipo result = equipoRepository.save(equipo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("equipo", equipo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /equipos : get all the equipos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of equipos in body
     */
    @GetMapping("/equipos")
    @Timed
    public List<Equipo> getAllEquipos() {
        log.debug("REST request to get all Equipos");
        List<Equipo> equipos = equipoRepository.findAllWithEagerRelationships();
        return equipos;
    }

    @GetMapping("/equipos/filtro/{palabra}")
    @Timed
    public List<Equipo> getAllTorneosBuscar(@PathVariable String palabra) {
        log.debug("REST request to get all Torneos / filtro");
        List<Equipo> equipos = equipoRepository.findAllWithEagerRelationships();
        List<Equipo> EquiposFiltrados = new ArrayList<>();
        if(palabra.equals("")){
            EquiposFiltrados = equipos;
        }else{
            for(Equipo e : equipos){
                if(e.getNombre().toLowerCase().contains(palabra.toLowerCase())){
                    EquiposFiltrados.add(e);
                }
            }}
        return EquiposFiltrados;
    }

    /**
     * GET  /equipos/:id : get the "id" equipo.
     *
     * @param id the id of the equipo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the equipo, or with status 404 (Not Found)
     */
    @GetMapping("/equipos/{id}")
    @Timed
    public ResponseEntity<Equipo> getEquipo(@PathVariable Long id) {
        log.debug("REST request to get Equipo : {}", id);
        Equipo equipo = equipoRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(equipo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/equiposDeUnJugador/{id}")
    @Timed
    public List<Equipo> getEquiposDeUnJugador(@PathVariable Long id) {
        log.debug("REST request to get Equipos from Jugador : {}", id);
        List<Equipo> equipos = equipoRepository.findAllWithEagerRelationships();
        List<Equipo> listaEquipoDeUnJugador = new ArrayList<>();

        for (Equipo e : equipos){

           for (Jugador j: e.getJugadors()){

               if(j.getId() == id){
                   listaEquipoDeUnJugador.add(e);
               }

            }


        }

        return listaEquipoDeUnJugador;
    }
    /**
     * DELETE  /equipos/:id : delete the "id" equipo.
     *
     * @param id the id of the equipo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/equipos/{id}")
    @Timed
    public ResponseEntity<Void> deleteEquipo(@PathVariable Long id) {
        log.debug("REST request to delete Equipo : {}", id);
        equipoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("equipo", id.toString())).build();
    }

}
