package com.fujica.bisai.web.rest;

import com.fujica.bisai.BisaiApp;

import com.fujica.bisai.domain.Clasificacion;
import com.fujica.bisai.repository.ClasificacionRepository;

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
import org.springframework.util.Base64Utils;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClasificacionResource REST controller.
 *
 * @see ClasificacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BisaiApp.class)
public class ClasificacionResourceIntTest {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_RESULTADO = 1;
    private static final Integer UPDATED_RESULTADO = 2;

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_RANKING = 1;
    private static final Integer UPDATED_RANKING = 2;

    @Inject
    private ClasificacionRepository clasificacionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restClasificacionMockMvc;

    private Clasificacion clasificacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClasificacionResource clasificacionResource = new ClasificacionResource();
        ReflectionTestUtils.setField(clasificacionResource, "clasificacionRepository", clasificacionRepository);
        this.restClasificacionMockMvc = MockMvcBuilders.standaloneSetup(clasificacionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clasificacion createEntity(EntityManager em) {
        Clasificacion clasificacion = new Clasificacion()
                .url(DEFAULT_URL)
                .resultado(DEFAULT_RESULTADO)
                .foto(DEFAULT_FOTO)
                .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
                .ranking(DEFAULT_RANKING);
        return clasificacion;
    }

    @Before
    public void initTest() {
        clasificacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createClasificacion() throws Exception {
        int databaseSizeBeforeCreate = clasificacionRepository.findAll().size();

        // Create the Clasificacion

        restClasificacionMockMvc.perform(post("/api/clasificacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clasificacion)))
            .andExpect(status().isCreated());

        // Validate the Clasificacion in the database
        List<Clasificacion> clasificacions = clasificacionRepository.findAll();
        assertThat(clasificacions).hasSize(databaseSizeBeforeCreate + 1);
        Clasificacion testClasificacion = clasificacions.get(clasificacions.size() - 1);
        assertThat(testClasificacion.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testClasificacion.getResultado()).isEqualTo(DEFAULT_RESULTADO);
        assertThat(testClasificacion.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testClasificacion.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testClasificacion.getRanking()).isEqualTo(DEFAULT_RANKING);
    }

    @Test
    @Transactional
    public void checkResultadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clasificacionRepository.findAll().size();
        // set the field null
        clasificacion.setResultado(null);

        // Create the Clasificacion, which fails.

        restClasificacionMockMvc.perform(post("/api/clasificacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clasificacion)))
            .andExpect(status().isBadRequest());

        List<Clasificacion> clasificacions = clasificacionRepository.findAll();
        assertThat(clasificacions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClasificacions() throws Exception {
        // Initialize the database
        clasificacionRepository.saveAndFlush(clasificacion);

        // Get all the clasificacions
        restClasificacionMockMvc.perform(get("/api/clasificacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clasificacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].resultado").value(hasItem(DEFAULT_RESULTADO)))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].ranking").value(hasItem(DEFAULT_RANKING)));
    }

    @Test
    @Transactional
    public void getClasificacion() throws Exception {
        // Initialize the database
        clasificacionRepository.saveAndFlush(clasificacion);

        // Get the clasificacion
        restClasificacionMockMvc.perform(get("/api/clasificacions/{id}", clasificacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clasificacion.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.resultado").value(DEFAULT_RESULTADO))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.ranking").value(DEFAULT_RANKING));
    }

    @Test
    @Transactional
    public void getNonExistingClasificacion() throws Exception {
        // Get the clasificacion
        restClasificacionMockMvc.perform(get("/api/clasificacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClasificacion() throws Exception {
        // Initialize the database
        clasificacionRepository.saveAndFlush(clasificacion);
        int databaseSizeBeforeUpdate = clasificacionRepository.findAll().size();

        // Update the clasificacion
        Clasificacion updatedClasificacion = clasificacionRepository.findOne(clasificacion.getId());
        updatedClasificacion
                .url(UPDATED_URL)
                .resultado(UPDATED_RESULTADO)
                .foto(UPDATED_FOTO)
                .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
                .ranking(UPDATED_RANKING);

        restClasificacionMockMvc.perform(put("/api/clasificacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClasificacion)))
            .andExpect(status().isOk());

        // Validate the Clasificacion in the database
        List<Clasificacion> clasificacions = clasificacionRepository.findAll();
        assertThat(clasificacions).hasSize(databaseSizeBeforeUpdate);
        Clasificacion testClasificacion = clasificacions.get(clasificacions.size() - 1);
        assertThat(testClasificacion.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testClasificacion.getResultado()).isEqualTo(UPDATED_RESULTADO);
        assertThat(testClasificacion.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testClasificacion.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testClasificacion.getRanking()).isEqualTo(UPDATED_RANKING);
    }

    @Test
    @Transactional
    public void deleteClasificacion() throws Exception {
        // Initialize the database
        clasificacionRepository.saveAndFlush(clasificacion);
        int databaseSizeBeforeDelete = clasificacionRepository.findAll().size();

        // Get the clasificacion
        restClasificacionMockMvc.perform(delete("/api/clasificacions/{id}", clasificacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Clasificacion> clasificacions = clasificacionRepository.findAll();
        assertThat(clasificacions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
