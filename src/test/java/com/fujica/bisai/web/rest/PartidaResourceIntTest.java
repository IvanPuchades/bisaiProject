package com.fujica.bisai.web.rest;

import com.fujica.bisai.BisaiApp;

import com.fujica.bisai.domain.Partida;
import com.fujica.bisai.repository.PartidaRepository;

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
 * Test class for the PartidaResource REST controller.
 *
 * @see PartidaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BisaiApp.class)
public class PartidaResourceIntTest {

    private static final ZonedDateTime DEFAULT_FECHA_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_INICIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_FINAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_FINAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_RESULTADO_EQUIPO_1 = 1;
    private static final Integer UPDATED_RESULTADO_EQUIPO_1 = 2;

    private static final Integer DEFAULT_RESULTADO_EQUIPO_2 = 1;
    private static final Integer UPDATED_RESULTADO_EQUIPO_2 = 2;

    private static final Integer DEFAULT_NUM_RONDA = 0;
    private static final Integer UPDATED_NUM_RONDA = 1;

    private static final Integer DEFAULT_NUM_PARTIDA_RONDA = 0;
    private static final Integer UPDATED_NUM_PARTIDA_RONDA = 1;

    @Inject
    private PartidaRepository partidaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPartidaMockMvc;

    private Partida partida;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PartidaResource partidaResource = new PartidaResource();
        ReflectionTestUtils.setField(partidaResource, "partidaRepository", partidaRepository);
        this.restPartidaMockMvc = MockMvcBuilders.standaloneSetup(partidaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partida createEntity(EntityManager em) {
        Partida partida = new Partida()
                .fechaInicio(DEFAULT_FECHA_INICIO)
                .fechaFinal(DEFAULT_FECHA_FINAL)
                .resultadoEquipo1(DEFAULT_RESULTADO_EQUIPO_1)
                .resultadoEquipo2(DEFAULT_RESULTADO_EQUIPO_2)
                .numRonda(DEFAULT_NUM_RONDA)
                .numPartidaRonda(DEFAULT_NUM_PARTIDA_RONDA);
        return partida;
    }

    @Before
    public void initTest() {
        partida = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartida() throws Exception {
        int databaseSizeBeforeCreate = partidaRepository.findAll().size();

        // Create the Partida

        restPartidaMockMvc.perform(post("/api/partidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partida)))
            .andExpect(status().isCreated());

        // Validate the Partida in the database
        List<Partida> partidas = partidaRepository.findAll();
        assertThat(partidas).hasSize(databaseSizeBeforeCreate + 1);
        Partida testPartida = partidas.get(partidas.size() - 1);
        assertThat(testPartida.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testPartida.getFechaFinal()).isEqualTo(DEFAULT_FECHA_FINAL);
        assertThat(testPartida.getResultadoEquipo1()).isEqualTo(DEFAULT_RESULTADO_EQUIPO_1);
        assertThat(testPartida.getResultadoEquipo2()).isEqualTo(DEFAULT_RESULTADO_EQUIPO_2);
        assertThat(testPartida.getNumRonda()).isEqualTo(DEFAULT_NUM_RONDA);
        assertThat(testPartida.getNumPartidaRonda()).isEqualTo(DEFAULT_NUM_PARTIDA_RONDA);
    }

    @Test
    @Transactional
    public void getAllPartidas() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidas
        restPartidaMockMvc.perform(get("/api/partidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partida.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(sameInstant(DEFAULT_FECHA_INICIO))))
            .andExpect(jsonPath("$.[*].fechaFinal").value(hasItem(sameInstant(DEFAULT_FECHA_FINAL))))
            .andExpect(jsonPath("$.[*].resultadoEquipo1").value(hasItem(DEFAULT_RESULTADO_EQUIPO_1)))
            .andExpect(jsonPath("$.[*].resultadoEquipo2").value(hasItem(DEFAULT_RESULTADO_EQUIPO_2)))
            .andExpect(jsonPath("$.[*].numRonda").value(hasItem(DEFAULT_NUM_RONDA)))
            .andExpect(jsonPath("$.[*].numPartidaRonda").value(hasItem(DEFAULT_NUM_PARTIDA_RONDA)));
    }

    @Test
    @Transactional
    public void getPartida() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get the partida
        restPartidaMockMvc.perform(get("/api/partidas/{id}", partida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partida.getId().intValue()))
            .andExpect(jsonPath("$.fechaInicio").value(sameInstant(DEFAULT_FECHA_INICIO)))
            .andExpect(jsonPath("$.fechaFinal").value(sameInstant(DEFAULT_FECHA_FINAL)))
            .andExpect(jsonPath("$.resultadoEquipo1").value(DEFAULT_RESULTADO_EQUIPO_1))
            .andExpect(jsonPath("$.resultadoEquipo2").value(DEFAULT_RESULTADO_EQUIPO_2))
            .andExpect(jsonPath("$.numRonda").value(DEFAULT_NUM_RONDA))
            .andExpect(jsonPath("$.numPartidaRonda").value(DEFAULT_NUM_PARTIDA_RONDA));
    }

    @Test
    @Transactional
    public void getNonExistingPartida() throws Exception {
        // Get the partida
        restPartidaMockMvc.perform(get("/api/partidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartida() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);
        int databaseSizeBeforeUpdate = partidaRepository.findAll().size();

        // Update the partida
        Partida updatedPartida = partidaRepository.findOne(partida.getId());
        updatedPartida
                .fechaInicio(UPDATED_FECHA_INICIO)
                .fechaFinal(UPDATED_FECHA_FINAL)
                .resultadoEquipo1(UPDATED_RESULTADO_EQUIPO_1)
                .resultadoEquipo2(UPDATED_RESULTADO_EQUIPO_2)
                .numRonda(UPDATED_NUM_RONDA)
                .numPartidaRonda(UPDATED_NUM_PARTIDA_RONDA);

        restPartidaMockMvc.perform(put("/api/partidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartida)))
            .andExpect(status().isOk());

        // Validate the Partida in the database
        List<Partida> partidas = partidaRepository.findAll();
        assertThat(partidas).hasSize(databaseSizeBeforeUpdate);
        Partida testPartida = partidas.get(partidas.size() - 1);
        assertThat(testPartida.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testPartida.getFechaFinal()).isEqualTo(UPDATED_FECHA_FINAL);
        assertThat(testPartida.getResultadoEquipo1()).isEqualTo(UPDATED_RESULTADO_EQUIPO_1);
        assertThat(testPartida.getResultadoEquipo2()).isEqualTo(UPDATED_RESULTADO_EQUIPO_2);
        assertThat(testPartida.getNumRonda()).isEqualTo(UPDATED_NUM_RONDA);
        assertThat(testPartida.getNumPartidaRonda()).isEqualTo(UPDATED_NUM_PARTIDA_RONDA);
    }

    @Test
    @Transactional
    public void deletePartida() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);
        int databaseSizeBeforeDelete = partidaRepository.findAll().size();

        // Get the partida
        restPartidaMockMvc.perform(delete("/api/partidas/{id}", partida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Partida> partidas = partidaRepository.findAll();
        assertThat(partidas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
