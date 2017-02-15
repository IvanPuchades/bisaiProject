package com.fujica.bisai.web.rest;

import com.fujica.bisai.BisaiApp;

import com.fujica.bisai.domain.ValoracionJugador;
import com.fujica.bisai.repository.ValoracionJugadorRepository;

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
 * Test class for the ValoracionJugadorResource REST controller.
 *
 * @see ValoracionJugadorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BisaiApp.class)
public class ValoracionJugadorResourceIntTest {

    private static final ZonedDateTime DEFAULT_HORA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_ME_GUSTA = false;
    private static final Boolean UPDATED_ME_GUSTA = true;

    @Inject
    private ValoracionJugadorRepository valoracionJugadorRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restValoracionJugadorMockMvc;

    private ValoracionJugador valoracionJugador;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ValoracionJugadorResource valoracionJugadorResource = new ValoracionJugadorResource();
        ReflectionTestUtils.setField(valoracionJugadorResource, "valoracionJugadorRepository", valoracionJugadorRepository);
        this.restValoracionJugadorMockMvc = MockMvcBuilders.standaloneSetup(valoracionJugadorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ValoracionJugador createEntity(EntityManager em) {
        ValoracionJugador valoracionJugador = new ValoracionJugador()
                .hora(DEFAULT_HORA)
                .meGusta(DEFAULT_ME_GUSTA);
        return valoracionJugador;
    }

    @Before
    public void initTest() {
        valoracionJugador = createEntity(em);
    }

    @Test
    @Transactional
    public void createValoracionJugador() throws Exception {
        int databaseSizeBeforeCreate = valoracionJugadorRepository.findAll().size();

        // Create the ValoracionJugador

        restValoracionJugadorMockMvc.perform(post("/api/valoracion-jugadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoracionJugador)))
            .andExpect(status().isCreated());

        // Validate the ValoracionJugador in the database
        List<ValoracionJugador> valoracionJugadors = valoracionJugadorRepository.findAll();
        assertThat(valoracionJugadors).hasSize(databaseSizeBeforeCreate + 1);
        ValoracionJugador testValoracionJugador = valoracionJugadors.get(valoracionJugadors.size() - 1);
        assertThat(testValoracionJugador.getHora()).isEqualTo(DEFAULT_HORA);
        assertThat(testValoracionJugador.isMeGusta()).isEqualTo(DEFAULT_ME_GUSTA);
    }

    @Test
    @Transactional
    public void getAllValoracionJugadors() throws Exception {
        // Initialize the database
        valoracionJugadorRepository.saveAndFlush(valoracionJugador);

        // Get all the valoracionJugadors
        restValoracionJugadorMockMvc.perform(get("/api/valoracion-jugadors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valoracionJugador.getId().intValue())))
            .andExpect(jsonPath("$.[*].hora").value(hasItem(sameInstant(DEFAULT_HORA))))
            .andExpect(jsonPath("$.[*].meGusta").value(hasItem(DEFAULT_ME_GUSTA.booleanValue())));
    }

    @Test
    @Transactional
    public void getValoracionJugador() throws Exception {
        // Initialize the database
        valoracionJugadorRepository.saveAndFlush(valoracionJugador);

        // Get the valoracionJugador
        restValoracionJugadorMockMvc.perform(get("/api/valoracion-jugadors/{id}", valoracionJugador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(valoracionJugador.getId().intValue()))
            .andExpect(jsonPath("$.hora").value(sameInstant(DEFAULT_HORA)))
            .andExpect(jsonPath("$.meGusta").value(DEFAULT_ME_GUSTA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingValoracionJugador() throws Exception {
        // Get the valoracionJugador
        restValoracionJugadorMockMvc.perform(get("/api/valoracion-jugadors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValoracionJugador() throws Exception {
        // Initialize the database
        valoracionJugadorRepository.saveAndFlush(valoracionJugador);
        int databaseSizeBeforeUpdate = valoracionJugadorRepository.findAll().size();

        // Update the valoracionJugador
        ValoracionJugador updatedValoracionJugador = valoracionJugadorRepository.findOne(valoracionJugador.getId());
        updatedValoracionJugador
                .hora(UPDATED_HORA)
                .meGusta(UPDATED_ME_GUSTA);

        restValoracionJugadorMockMvc.perform(put("/api/valoracion-jugadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedValoracionJugador)))
            .andExpect(status().isOk());

        // Validate the ValoracionJugador in the database
        List<ValoracionJugador> valoracionJugadors = valoracionJugadorRepository.findAll();
        assertThat(valoracionJugadors).hasSize(databaseSizeBeforeUpdate);
        ValoracionJugador testValoracionJugador = valoracionJugadors.get(valoracionJugadors.size() - 1);
        assertThat(testValoracionJugador.getHora()).isEqualTo(UPDATED_HORA);
        assertThat(testValoracionJugador.isMeGusta()).isEqualTo(UPDATED_ME_GUSTA);
    }

    @Test
    @Transactional
    public void deleteValoracionJugador() throws Exception {
        // Initialize the database
        valoracionJugadorRepository.saveAndFlush(valoracionJugador);
        int databaseSizeBeforeDelete = valoracionJugadorRepository.findAll().size();

        // Get the valoracionJugador
        restValoracionJugadorMockMvc.perform(delete("/api/valoracion-jugadors/{id}", valoracionJugador.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ValoracionJugador> valoracionJugadors = valoracionJugadorRepository.findAll();
        assertThat(valoracionJugadors).hasSize(databaseSizeBeforeDelete - 1);
    }
}
