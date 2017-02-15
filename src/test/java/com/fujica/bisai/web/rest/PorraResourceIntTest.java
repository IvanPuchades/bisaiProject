package com.fujica.bisai.web.rest;

import com.fujica.bisai.BisaiApp;

import com.fujica.bisai.domain.Porra;
import com.fujica.bisai.repository.PorraRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PorraResource REST controller.
 *
 * @see PorraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BisaiApp.class)
public class PorraResourceIntTest {

    private static final Double DEFAULT_CANTIDAD = 1D;
    private static final Double UPDATED_CANTIDAD = 2D;

    private static final Integer DEFAULT_ELECCION = 1;
    private static final Integer UPDATED_ELECCION = 2;

    @Inject
    private PorraRepository porraRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPorraMockMvc;

    private Porra porra;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PorraResource porraResource = new PorraResource();
        ReflectionTestUtils.setField(porraResource, "porraRepository", porraRepository);
        this.restPorraMockMvc = MockMvcBuilders.standaloneSetup(porraResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Porra createEntity(EntityManager em) {
        Porra porra = new Porra()
                .cantidad(DEFAULT_CANTIDAD)
                .eleccion(DEFAULT_ELECCION);
        return porra;
    }

    @Before
    public void initTest() {
        porra = createEntity(em);
    }

    @Test
    @Transactional
    public void createPorra() throws Exception {
        int databaseSizeBeforeCreate = porraRepository.findAll().size();

        // Create the Porra

        restPorraMockMvc.perform(post("/api/porras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(porra)))
            .andExpect(status().isCreated());

        // Validate the Porra in the database
        List<Porra> porras = porraRepository.findAll();
        assertThat(porras).hasSize(databaseSizeBeforeCreate + 1);
        Porra testPorra = porras.get(porras.size() - 1);
        assertThat(testPorra.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testPorra.getEleccion()).isEqualTo(DEFAULT_ELECCION);
    }

    @Test
    @Transactional
    public void getAllPorras() throws Exception {
        // Initialize the database
        porraRepository.saveAndFlush(porra);

        // Get all the porras
        restPorraMockMvc.perform(get("/api/porras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(porra.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.doubleValue())))
            .andExpect(jsonPath("$.[*].eleccion").value(hasItem(DEFAULT_ELECCION)));
    }

    @Test
    @Transactional
    public void getPorra() throws Exception {
        // Initialize the database
        porraRepository.saveAndFlush(porra);

        // Get the porra
        restPorraMockMvc.perform(get("/api/porras/{id}", porra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(porra.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.doubleValue()))
            .andExpect(jsonPath("$.eleccion").value(DEFAULT_ELECCION));
    }

    @Test
    @Transactional
    public void getNonExistingPorra() throws Exception {
        // Get the porra
        restPorraMockMvc.perform(get("/api/porras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePorra() throws Exception {
        // Initialize the database
        porraRepository.saveAndFlush(porra);
        int databaseSizeBeforeUpdate = porraRepository.findAll().size();

        // Update the porra
        Porra updatedPorra = porraRepository.findOne(porra.getId());
        updatedPorra
                .cantidad(UPDATED_CANTIDAD)
                .eleccion(UPDATED_ELECCION);

        restPorraMockMvc.perform(put("/api/porras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPorra)))
            .andExpect(status().isOk());

        // Validate the Porra in the database
        List<Porra> porras = porraRepository.findAll();
        assertThat(porras).hasSize(databaseSizeBeforeUpdate);
        Porra testPorra = porras.get(porras.size() - 1);
        assertThat(testPorra.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testPorra.getEleccion()).isEqualTo(UPDATED_ELECCION);
    }

    @Test
    @Transactional
    public void deletePorra() throws Exception {
        // Initialize the database
        porraRepository.saveAndFlush(porra);
        int databaseSizeBeforeDelete = porraRepository.findAll().size();

        // Get the porra
        restPorraMockMvc.perform(delete("/api/porras/{id}", porra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Porra> porras = porraRepository.findAll();
        assertThat(porras).hasSize(databaseSizeBeforeDelete - 1);
    }
}
