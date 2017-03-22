package com.fujica.bisai.web.rest;

import com.fujica.bisai.BisaiApp;

import com.fujica.bisai.domain.Torneo;
import com.fujica.bisai.repository.TorneoRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.fujica.bisai.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TorneoResource REST controller.
 *
 * @see TorneoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BisaiApp.class)
public class TorneoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_PARTICIPANTES = 1;
    private static final Integer UPDATED_NUMERO_PARTICIPANTES = 2;

    private static final ZonedDateTime DEFAULT_FECHA_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_INICIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_CANCELADO = false;
    private static final Boolean UPDATED_CANCELADO = true;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Inject
    private TorneoRepository torneoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTorneoMockMvc;

    private Torneo torneo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TorneoResource torneoResource = new TorneoResource();
        ReflectionTestUtils.setField(torneoResource, "torneoRepository", torneoRepository);
        this.restTorneoMockMvc = MockMvcBuilders.standaloneSetup(torneoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Torneo createEntity(EntityManager em) {
        Torneo torneo = new Torneo()
                .nombre(DEFAULT_NOMBRE)
                .numeroParticipantes(DEFAULT_NUMERO_PARTICIPANTES)
                .fechaInicio(DEFAULT_FECHA_INICIO)
                .cancelado(DEFAULT_CANCELADO)
                .descripcion(DEFAULT_DESCRIPCION);
        return torneo;
    }

    @Before
    public void initTest() {
        torneo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTorneo() throws Exception {
        int databaseSizeBeforeCreate = torneoRepository.findAll().size();

        // Create the Torneo

        restTorneoMockMvc.perform(post("/api/torneos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(torneo)))
            .andExpect(status().isCreated());

        // Validate the Torneo in the database
        List<Torneo> torneos = torneoRepository.findAll();
        assertThat(torneos).hasSize(databaseSizeBeforeCreate + 1);
        Torneo testTorneo = torneos.get(torneos.size() - 1);
        assertThat(testTorneo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTorneo.getNumeroParticipantes()).isEqualTo(DEFAULT_NUMERO_PARTICIPANTES);
        assertThat(testTorneo.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testTorneo.isCancelado()).isEqualTo(DEFAULT_CANCELADO);
        assertThat(testTorneo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = torneoRepository.findAll().size();
        // set the field null
        torneo.setNombre(null);

        // Create the Torneo, which fails.

        restTorneoMockMvc.perform(post("/api/torneos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(torneo)))
            .andExpect(status().isBadRequest());

        List<Torneo> torneos = torneoRepository.findAll();
        assertThat(torneos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroParticipantesIsRequired() throws Exception {
        int databaseSizeBeforeTest = torneoRepository.findAll().size();
        // set the field null
        torneo.setNumeroParticipantes(null);

        // Create the Torneo, which fails.

        restTorneoMockMvc.perform(post("/api/torneos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(torneo)))
            .andExpect(status().isBadRequest());

        List<Torneo> torneos = torneoRepository.findAll();
        assertThat(torneos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTorneos() throws Exception {
        // Initialize the database
        torneoRepository.saveAndFlush(torneo);

        // Get all the torneos
        restTorneoMockMvc.perform(get("/api/torneos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(torneo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].numeroParticipantes").value(hasItem(DEFAULT_NUMERO_PARTICIPANTES)))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(sameInstant(DEFAULT_FECHA_INICIO))))
            .andExpect(jsonPath("$.[*].cancelado").value(hasItem(DEFAULT_CANCELADO.booleanValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getTorneo() throws Exception {
        // Initialize the database
        torneoRepository.saveAndFlush(torneo);

        // Get the torneo
        restTorneoMockMvc.perform(get("/api/torneos/{id}", torneo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(torneo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.numeroParticipantes").value(DEFAULT_NUMERO_PARTICIPANTES))
            .andExpect(jsonPath("$.fechaInicio").value(sameInstant(DEFAULT_FECHA_INICIO)))
            .andExpect(jsonPath("$.cancelado").value(DEFAULT_CANCELADO.booleanValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTorneo() throws Exception {
        // Get the torneo
        restTorneoMockMvc.perform(get("/api/torneos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTorneo() throws Exception {
        // Initialize the database
        torneoRepository.saveAndFlush(torneo);
        int databaseSizeBeforeUpdate = torneoRepository.findAll().size();

        // Update the torneo
        Torneo updatedTorneo = torneoRepository.findOne(torneo.getId());
        updatedTorneo
                .nombre(UPDATED_NOMBRE)
                .numeroParticipantes(UPDATED_NUMERO_PARTICIPANTES)
                .fechaInicio(UPDATED_FECHA_INICIO)
                .cancelado(UPDATED_CANCELADO)
                .descripcion(UPDATED_DESCRIPCION);

        restTorneoMockMvc.perform(put("/api/torneos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTorneo)))
            .andExpect(status().isOk());

        // Validate the Torneo in the database
        List<Torneo> torneos = torneoRepository.findAll();
        assertThat(torneos).hasSize(databaseSizeBeforeUpdate);
        Torneo testTorneo = torneos.get(torneos.size() - 1);
        assertThat(testTorneo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTorneo.getNumeroParticipantes()).isEqualTo(UPDATED_NUMERO_PARTICIPANTES);
        assertThat(testTorneo.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testTorneo.isCancelado()).isEqualTo(UPDATED_CANCELADO);
        assertThat(testTorneo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void deleteTorneo() throws Exception {
        // Initialize the database
        torneoRepository.saveAndFlush(torneo);
        int databaseSizeBeforeDelete = torneoRepository.findAll().size();

        // Get the torneo
        restTorneoMockMvc.perform(delete("/api/torneos/{id}", torneo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Torneo> torneos = torneoRepository.findAll();
        assertThat(torneos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
