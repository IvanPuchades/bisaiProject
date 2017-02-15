package com.fujica.bisai.web.rest;

import com.fujica.bisai.BisaiApp;

import com.fujica.bisai.domain.ValoracionEquipo;
import com.fujica.bisai.repository.ValoracionEquipoRepository;

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
 * Test class for the ValoracionEquipoResource REST controller.
 *
 * @see ValoracionEquipoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BisaiApp.class)
public class ValoracionEquipoResourceIntTest {

    private static final ZonedDateTime DEFAULT_HORA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_ME_GUSTA = false;
    private static final Boolean UPDATED_ME_GUSTA = true;

    @Inject
    private ValoracionEquipoRepository valoracionEquipoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restValoracionEquipoMockMvc;

    private ValoracionEquipo valoracionEquipo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ValoracionEquipoResource valoracionEquipoResource = new ValoracionEquipoResource();
        ReflectionTestUtils.setField(valoracionEquipoResource, "valoracionEquipoRepository", valoracionEquipoRepository);
        this.restValoracionEquipoMockMvc = MockMvcBuilders.standaloneSetup(valoracionEquipoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ValoracionEquipo createEntity(EntityManager em) {
        ValoracionEquipo valoracionEquipo = new ValoracionEquipo()
                .hora(DEFAULT_HORA)
                .meGusta(DEFAULT_ME_GUSTA);
        return valoracionEquipo;
    }

    @Before
    public void initTest() {
        valoracionEquipo = createEntity(em);
    }

    @Test
    @Transactional
    public void createValoracionEquipo() throws Exception {
        int databaseSizeBeforeCreate = valoracionEquipoRepository.findAll().size();

        // Create the ValoracionEquipo

        restValoracionEquipoMockMvc.perform(post("/api/valoracion-equipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoracionEquipo)))
            .andExpect(status().isCreated());

        // Validate the ValoracionEquipo in the database
        List<ValoracionEquipo> valoracionEquipos = valoracionEquipoRepository.findAll();
        assertThat(valoracionEquipos).hasSize(databaseSizeBeforeCreate + 1);
        ValoracionEquipo testValoracionEquipo = valoracionEquipos.get(valoracionEquipos.size() - 1);
        assertThat(testValoracionEquipo.getHora()).isEqualTo(DEFAULT_HORA);
        assertThat(testValoracionEquipo.isMeGusta()).isEqualTo(DEFAULT_ME_GUSTA);
    }

    @Test
    @Transactional
    public void getAllValoracionEquipos() throws Exception {
        // Initialize the database
        valoracionEquipoRepository.saveAndFlush(valoracionEquipo);

        // Get all the valoracionEquipos
        restValoracionEquipoMockMvc.perform(get("/api/valoracion-equipos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valoracionEquipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].hora").value(hasItem(sameInstant(DEFAULT_HORA))))
            .andExpect(jsonPath("$.[*].meGusta").value(hasItem(DEFAULT_ME_GUSTA.booleanValue())));
    }

    @Test
    @Transactional
    public void getValoracionEquipo() throws Exception {
        // Initialize the database
        valoracionEquipoRepository.saveAndFlush(valoracionEquipo);

        // Get the valoracionEquipo
        restValoracionEquipoMockMvc.perform(get("/api/valoracion-equipos/{id}", valoracionEquipo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(valoracionEquipo.getId().intValue()))
            .andExpect(jsonPath("$.hora").value(sameInstant(DEFAULT_HORA)))
            .andExpect(jsonPath("$.meGusta").value(DEFAULT_ME_GUSTA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingValoracionEquipo() throws Exception {
        // Get the valoracionEquipo
        restValoracionEquipoMockMvc.perform(get("/api/valoracion-equipos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValoracionEquipo() throws Exception {
        // Initialize the database
        valoracionEquipoRepository.saveAndFlush(valoracionEquipo);
        int databaseSizeBeforeUpdate = valoracionEquipoRepository.findAll().size();

        // Update the valoracionEquipo
        ValoracionEquipo updatedValoracionEquipo = valoracionEquipoRepository.findOne(valoracionEquipo.getId());
        updatedValoracionEquipo
                .hora(UPDATED_HORA)
                .meGusta(UPDATED_ME_GUSTA);

        restValoracionEquipoMockMvc.perform(put("/api/valoracion-equipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedValoracionEquipo)))
            .andExpect(status().isOk());

        // Validate the ValoracionEquipo in the database
        List<ValoracionEquipo> valoracionEquipos = valoracionEquipoRepository.findAll();
        assertThat(valoracionEquipos).hasSize(databaseSizeBeforeUpdate);
        ValoracionEquipo testValoracionEquipo = valoracionEquipos.get(valoracionEquipos.size() - 1);
        assertThat(testValoracionEquipo.getHora()).isEqualTo(UPDATED_HORA);
        assertThat(testValoracionEquipo.isMeGusta()).isEqualTo(UPDATED_ME_GUSTA);
    }

    @Test
    @Transactional
    public void deleteValoracionEquipo() throws Exception {
        // Initialize the database
        valoracionEquipoRepository.saveAndFlush(valoracionEquipo);
        int databaseSizeBeforeDelete = valoracionEquipoRepository.findAll().size();

        // Get the valoracionEquipo
        restValoracionEquipoMockMvc.perform(delete("/api/valoracion-equipos/{id}", valoracionEquipo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ValoracionEquipo> valoracionEquipos = valoracionEquipoRepository.findAll();
        assertThat(valoracionEquipos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
