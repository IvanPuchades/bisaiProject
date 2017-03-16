package com.fujica.bisai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fujica.bisai.domain.Equipo;
import com.fujica.bisai.domain.Partida;
import com.fujica.bisai.domain.Torneo;

import com.fujica.bisai.repository.EquipoRepository;
import com.fujica.bisai.repository.PartidaRepository;
import com.fujica.bisai.repository.TorneoRepository;
import com.fujica.bisai.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managing Torneo.
 */
@RestController
@RequestMapping("/api")
public class TorneoResource {

    private final Logger log = LoggerFactory.getLogger(TorneoResource.class);

    @Inject
    private TorneoRepository torneoRepository;

    @Inject
    private EquipoRepository equipoRepository;

    @Inject
    private PartidaRepository partidaRepository;





    /**
     * POST  /torneos : Create a new torneo.
     *
     * @param torneo the torneo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new torneo, or with status 400 (Bad Request) if the torneo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/torneos")
    @Timed
    public ResponseEntity<Torneo> createTorneo(@Valid @RequestBody Torneo torneo) throws URISyntaxException {
        log.debug("REST request to save Torneo : {}", torneo);
        if (torneo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("torneo", "idexists", "A new torneo cannot already have an ID")).body(null);
        }
        Torneo result = torneoRepository.save(torneo);

        // control de errores para que cuando se crea un torneo no tenga partidas

        if(torneo.getEquipos().size()>0){

            log.debug("REST request to save Torneo : Warning el torneo ya tiene equipos asociados", torneo);

         return ResponseEntity.
             badRequest().
             headers(HeaderUtil.createFailureAlert("torneo", "equiposExist", "A new torneo cannot already have an asociated teams")).body(null);


        }
        return ResponseEntity.created(new URI("/api/torneos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("torneo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /torneos : Updates an existing torneo.
     *
     * @param torneo the torneo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated torneo,
     * or with status 400 (Bad Request) if the torneo is not valid,
     * or with status 500 (Internal Server Error) if the torneo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/torneos")
    @Timed
    public ResponseEntity<Torneo> updateTorneo(@Valid @RequestBody Torneo torneo) throws URISyntaxException {
        log.debug("REST request to update Torneo : {}", torneo);
        if (torneo.getId() == null) {
            return createTorneo(torneo);
        }
        Torneo result = torneoRepository.save(torneo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("torneo", torneo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /torneos : get all the torneos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of torneos in body
     */
    @GetMapping("/torneos")
    @Timed
    public List<Torneo> getAllTorneos() {
        log.debug("REST request to get all Torneos");
        List<Torneo> torneos = torneoRepository.findAllWithEagerRelationships();
        return torneos;
    }

    /**
     * GET  /torneos/:id : get the "id" torneo.
     *
     * @param id the id of the torneo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the torneo, or with status 404 (Not Found)
     */
    @GetMapping("/torneos/{id}")
    @Timed
    public ResponseEntity<Torneo> getTorneo(@PathVariable Long id) {
        log.debug("REST request to get Torneo : {}", id);
        Torneo torneo = torneoRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(torneo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
//
    @PutMapping("/torneos/{id}/equipo/{idEquipo}")
    @Timed
    @Transactional
    public ResponseEntity<Torneo> getTorneo(@PathVariable Long id, @PathVariable Long idEquipo) {
        log.debug("REST request to get Torneo : {}", id);
        Torneo torneo = torneoRepository.findOneWithEagerRelationships(id);

        // control de errores

        if(torneo == null){

            return ResponseEntity.
                badRequest().
                headers(HeaderUtil.
                    createFailureAlert("torneo", "torneoNotExist", "El torneo no existe ")).
                body(null);


        }

        if(torneo.getEquipos().size()==torneo.getNumeroParticipantes()){

            return ResponseEntity.
                badRequest().
                headers(HeaderUtil.
                    createFailureAlert("torneo", "equiposMax", "El  " + torneo.getNombre()+ " ya tiene el maximo de equipos")).
                body(null);

        }


        Equipo equipo = equipoRepository.findOne(idEquipo);

        if(equipo == null){

            return ResponseEntity.
                badRequest().
                headers(HeaderUtil.
                    createFailureAlert("equipo", "equipoNotExist", "El equipo no existe ")).
                body(null);


        }


        torneo.addEquipo(equipo);

        torneoRepository.save(torneo);


        return new ResponseEntity<>(torneo, HttpStatus.OK);
    }


    @PostMapping("/torneos/{id}/partidas")
    @Timed
    @Transactional

    //Metodo para generar Partidas
    public ResponseEntity<Torneo> generarPartidas(@PathVariable Long id) {
        log.debug("REST request to generate games : {}", id);
        Torneo torneo = torneoRepository.findOneWithEagerRelationships(id);

        // Controlamos que no se genere el torneo antes de cumplir con todos los equipos

        if(torneo.getNumeroParticipantes()< torneo.getEquipos().size()){

            return ResponseEntity.
                badRequest().
                headers(HeaderUtil.
                    createFailureAlert("torneo", "equiposInsuficientes", "No hay suficientes equipos inscritos ")).
                body(null);


        }



        List<Equipo> equipos = new ArrayList<>(torneo.getEquipos());

        // ordenamos aleatoriamente la lista de quipos

        Collections.shuffle(equipos);

        // Creamos la cola

        Queue<Partida> partidaQueue = new LinkedList<>();

        int numPartidaEnRonda = 0;

        for (int i = 0; i< equipos.size() ; i++) {
            Partida partida = new Partida();
            partida.setNumRonda(0);
            partida.setNumPartidaRonda(numPartidaEnRonda++);

            Equipo equipo1 = equipos.get(i);

            Equipo equipo2 = equipos.get(++i);


            partida.setEquipo1(equipo1);
            partida.setEquipo2(equipo2);

            partidaQueue.add(partida);

            partidaRepository.save(partida);

        }

        while(true){

            Partida partida1 = partidaQueue.poll();
            Partida partida2 = partidaQueue.poll();

            Partida siguientePartida = new Partida();

            siguientePartida.setNumRonda(partida1.getNumRonda()+1);
            // TODO GENERAR CORRECTAMENTE EL NUMERO DE PARTIDA EN RONDA
            partida1.setSiguientePartida(siguientePartida);
            partida2.setSiguientePartida(siguientePartida);

            partidaRepository.save(siguientePartida);


            if(partidaQueue.isEmpty()){

                log.debug("REST request to generate games : Ya hemos generado la final", id);

                break;

            }

            partidaQueue.add(siguientePartida);




        }

            return new ResponseEntity<>(torneo, HttpStatus.OK);



    }

        /**
         * DELETE  /torneos/:id : delete the "id" torneo.
         *
         * @param id the id of the torneo to delete
         * @return the ResponseEntity with status 200 (OK)
         */
    @DeleteMapping("/torneos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTorneo(@PathVariable Long id) {
        log.debug("REST request to delete Torneo : {}", id);
        torneoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("torneo", id.toString())).build();
    }

}
