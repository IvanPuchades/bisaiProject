package com.fujica.bisai.web.rest;

import com.fujica.bisai.BisaiApp;

import com.fujica.bisai.domain.AdministradorEquipo;
import com.fujica.bisai.repository.AdministradorEquipoRepository;

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

import com.fujica.bisai.domain.enumeration.Permiso;
/**
 * Test class for the AdministradorEquipoResource REST controller.
 *
 * @see AdministradorEquipoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BisaiApp.class)
public class AdministradorEquipoResourceIntTest {

    private static final ZonedDateTime DEFAULT_HORA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Permiso DEFAULT_PERMISO = Permiso.INVITAR;
    private static final Permiso UPDATED_PERMISO = Permiso.MODIFICAR;

    @Inject
    private AdministradorEquipoRepository administradorEquipoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAdministradorEquipoMockMvc;

    private AdministradorEquipo administradorEquipo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AdministradorEquipoResource administradorEquipoResource = new AdministradorEquipoResource();
        ReflectionTestUtils.setField(administradorEquipoResource, "administradorEquipoRepository", administradorEquipoRepository);
        this.restAdministradorEquipoMockMvc = MockMvcBuilders.standaloneSetup(administradorEquipoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdministradorEquipo createEntity(EntityManager em) {
        AdministradorEquipo administradorEquipo = new AdministradorEquipo()
                .hora(DEFAULT_HORA)
                .permiso(DEFAULT_PERMISO);
        return administradorEquipo;
    }

    @Before
    public void initTest() {
        administradorEquipo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdministradorEquipo() throws Exception {
        int databaseSizeBeforeCreate = administradorEquipoRepository.findAll().size();

        // Create the AdministradorEquipo

        restAdministradorEquipoMockMvc.perform(post("/api/administrador-equipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(administradorEquipo)))
            .andExpect(status().isCreated());

        // Validate the AdministradorEquipo in the database
        List<AdministradorEquipo> administradorEquipos = administradorEquipoRepository.findAll();
        assertThat(administradorEquipos).hasSize(databaseSizeBeforeCreate + 1);
        AdministradorEquipo testAdministradorEquipo = administradorEquipos.get(administradorEquipos.size() - 1);
        assertThat(testAdministradorEquipo.getHora()).isEqualTo(DEFAULT_HORA);
        assertThat(testAdministradorEquipo.getPermiso()).isEqualTo(DEFAULT_PERMISO);
    }

    @Test
    @Transactional
    public void getAllAdministradorEquipos() throws Exception {
        // Initialize the database
        administradorEquipoRepository.saveAndFlush(administradorEquipo);

        // Get all the administradorEquipos
        restAdministradorEquipoMockMvc.perform(get("/api/administrador-equipos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(administradorEquipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].hora").value(hasItem(sameInstant(DEFAULT_HORA))))
            .andExpect(jsonPath("$.[*].permiso").value(hasItem(DEFAULT_PERMISO.toString())));
    }

    @Test
    @Transactional
    public void getAdministradorEquipo() throws Exception {
        // Initialize the database
        administradorEquipoRepository.saveAndFlush(administradorEquipo);

        // Get the administradorEquipo
        restAdministradorEquipoMockMvc.perform(get("/api/administrador-equipos/{id}", administradorEquipo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(administradorEquipo.getId().intValue()))
            .andExpect(jsonPath("$.hora").value(sameInstant(DEFAULT_HORA)))
            .andExpect(jsonPath("$.permiso").value(DEFAULT_PERMISO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAdministradorEquipo() throws Exception {
        // Get the administradorEquipo
        restAdministradorEquipoMockMvc.perform(get("/api/administrador-equipos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdministradorEquipo() throws Exception {
        // Initialize the database
        administradorEquipoRepository.saveAndFlush(administradorEquipo);
        int databaseSizeBeforeUpdate = administradorEquipoRepository.findAll().size();

        // Update the administradorEquipo
        AdministradorEquipo updatedAdministradorEquipo = administradorEquipoRepository.findOne(administradorEquipo.getId());
        updatedAdministradorEquipo
                .hora(UPDATED_HORA)
                .permiso(UPDATED_PERMISO);

        restAdministradorEquipoMockMvc.perform(put("/api/administrador-equipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAdministradorEquipo)))
            .andExpect(status().isOk());

        // Validate the AdministradorEquipo in the database
        List<AdministradorEquipo> administradorEquipos = administradorEquipoRepository.findAll();
        assertThat(administradorEquipos).hasSize(databaseSizeBeforeUpdate);
        AdministradorEquipo testAdministradorEquipo = administradorEquipos.get(administradorEquipos.size() - 1);
        assertThat(testAdministradorEquipo.getHora()).isEqualTo(UPDATED_HORA);
        assertThat(testAdministradorEquipo.getPermiso()).isEqualTo(UPDATED_PERMISO);
    }

    @Test
    @Transactional
    public void deleteAdministradorEquipo() throws Exception {
        // Initialize the database
        administradorEquipoRepository.saveAndFlush(administradorEquipo);
        int databaseSizeBeforeDelete = administradorEquipoRepository.findAll().size();

        // Get the administradorEquipo
        restAdministradorEquipoMockMvc.perform(delete("/api/administrador-equipos/{id}", administradorEquipo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AdministradorEquipo> administradorEquipos = administradorEquipoRepository.findAll();
        assertThat(administradorEquipos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
