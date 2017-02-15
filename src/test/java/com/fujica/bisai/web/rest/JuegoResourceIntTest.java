package com.fujica.bisai.web.rest;

import com.fujica.bisai.BisaiApp;

import com.fujica.bisai.domain.Juego;
import com.fujica.bisai.repository.JuegoRepository;

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
 * Test class for the JuegoResource REST controller.
 *
 * @see JuegoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BisaiApp.class)
public class JuegoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_QR = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_QR = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_QR_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_QR_CONTENT_TYPE = "image/png";

    @Inject
    private JuegoRepository juegoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restJuegoMockMvc;

    private Juego juego;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JuegoResource juegoResource = new JuegoResource();
        ReflectionTestUtils.setField(juegoResource, "juegoRepository", juegoRepository);
        this.restJuegoMockMvc = MockMvcBuilders.standaloneSetup(juegoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Juego createEntity(EntityManager em) {
        Juego juego = new Juego()
                .nombre(DEFAULT_NOMBRE)
                .url(DEFAULT_URL)
                .foto(DEFAULT_FOTO)
                .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
                .qr(DEFAULT_QR)
                .qrContentType(DEFAULT_QR_CONTENT_TYPE);
        return juego;
    }

    @Before
    public void initTest() {
        juego = createEntity(em);
    }

    @Test
    @Transactional
    public void createJuego() throws Exception {
        int databaseSizeBeforeCreate = juegoRepository.findAll().size();

        // Create the Juego

        restJuegoMockMvc.perform(post("/api/juegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(juego)))
            .andExpect(status().isCreated());

        // Validate the Juego in the database
        List<Juego> juegos = juegoRepository.findAll();
        assertThat(juegos).hasSize(databaseSizeBeforeCreate + 1);
        Juego testJuego = juegos.get(juegos.size() - 1);
        assertThat(testJuego.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testJuego.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testJuego.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testJuego.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testJuego.getQr()).isEqualTo(DEFAULT_QR);
        assertThat(testJuego.getQrContentType()).isEqualTo(DEFAULT_QR_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = juegoRepository.findAll().size();
        // set the field null
        juego.setNombre(null);

        // Create the Juego, which fails.

        restJuegoMockMvc.perform(post("/api/juegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(juego)))
            .andExpect(status().isBadRequest());

        List<Juego> juegos = juegoRepository.findAll();
        assertThat(juegos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = juegoRepository.findAll().size();
        // set the field null
        juego.setUrl(null);

        // Create the Juego, which fails.

        restJuegoMockMvc.perform(post("/api/juegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(juego)))
            .andExpect(status().isBadRequest());

        List<Juego> juegos = juegoRepository.findAll();
        assertThat(juegos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJuegos() throws Exception {
        // Initialize the database
        juegoRepository.saveAndFlush(juego);

        // Get all the juegos
        restJuegoMockMvc.perform(get("/api/juegos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(juego.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].qrContentType").value(hasItem(DEFAULT_QR_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].qr").value(hasItem(Base64Utils.encodeToString(DEFAULT_QR))));
    }

    @Test
    @Transactional
    public void getJuego() throws Exception {
        // Initialize the database
        juegoRepository.saveAndFlush(juego);

        // Get the juego
        restJuegoMockMvc.perform(get("/api/juegos/{id}", juego.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(juego.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.qrContentType").value(DEFAULT_QR_CONTENT_TYPE))
            .andExpect(jsonPath("$.qr").value(Base64Utils.encodeToString(DEFAULT_QR)));
    }

    @Test
    @Transactional
    public void getNonExistingJuego() throws Exception {
        // Get the juego
        restJuegoMockMvc.perform(get("/api/juegos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJuego() throws Exception {
        // Initialize the database
        juegoRepository.saveAndFlush(juego);
        int databaseSizeBeforeUpdate = juegoRepository.findAll().size();

        // Update the juego
        Juego updatedJuego = juegoRepository.findOne(juego.getId());
        updatedJuego
                .nombre(UPDATED_NOMBRE)
                .url(UPDATED_URL)
                .foto(UPDATED_FOTO)
                .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
                .qr(UPDATED_QR)
                .qrContentType(UPDATED_QR_CONTENT_TYPE);

        restJuegoMockMvc.perform(put("/api/juegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJuego)))
            .andExpect(status().isOk());

        // Validate the Juego in the database
        List<Juego> juegos = juegoRepository.findAll();
        assertThat(juegos).hasSize(databaseSizeBeforeUpdate);
        Juego testJuego = juegos.get(juegos.size() - 1);
        assertThat(testJuego.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testJuego.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testJuego.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testJuego.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testJuego.getQr()).isEqualTo(UPDATED_QR);
        assertThat(testJuego.getQrContentType()).isEqualTo(UPDATED_QR_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteJuego() throws Exception {
        // Initialize the database
        juegoRepository.saveAndFlush(juego);
        int databaseSizeBeforeDelete = juegoRepository.findAll().size();

        // Get the juego
        restJuegoMockMvc.perform(delete("/api/juegos/{id}", juego.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Juego> juegos = juegoRepository.findAll();
        assertThat(juegos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
