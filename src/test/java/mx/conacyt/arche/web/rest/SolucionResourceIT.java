package mx.conacyt.arche.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import mx.conacyt.arche.IntegrationTest;
import mx.conacyt.arche.domain.Solucion;
import mx.conacyt.arche.repository.SolucionRepository;
import mx.conacyt.arche.service.dto.SolucionDTO;
import mx.conacyt.arche.service.mapper.SolucionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link SolucionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class SolucionResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/solucions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SolucionRepository solucionRepository;

    @Autowired
    private SolucionMapper solucionMapper;

    @Autowired
    private WebTestClient webTestClient;

    private Solucion solucion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Solucion createEntity() {
        Solucion solucion = new Solucion().titulo(DEFAULT_TITULO).descripcion(DEFAULT_DESCRIPCION);
        return solucion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Solucion createUpdatedEntity() {
        Solucion solucion = new Solucion().titulo(UPDATED_TITULO).descripcion(UPDATED_DESCRIPCION);
        return solucion;
    }

    @BeforeEach
    public void initTest() {
        solucionRepository.deleteAll().block();
        solucion = createEntity();
    }

    @Test
    void createSolucion() throws Exception {
        int databaseSizeBeforeCreate = solucionRepository.findAll().collectList().block().size();
        // Create the Solucion
        SolucionDTO solucionDTO = solucionMapper.toDto(solucion);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(solucionDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Solucion in the database
        List<Solucion> solucionList = solucionRepository.findAll().collectList().block();
        assertThat(solucionList).hasSize(databaseSizeBeforeCreate + 1);
        Solucion testSolucion = solucionList.get(solucionList.size() - 1);
        assertThat(testSolucion.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testSolucion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    void createSolucionWithExistingId() throws Exception {
        // Create the Solucion with an existing ID
        solucion.setId("existing_id");
        SolucionDTO solucionDTO = solucionMapper.toDto(solucion);

        int databaseSizeBeforeCreate = solucionRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(solucionDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Solucion in the database
        List<Solucion> solucionList = solucionRepository.findAll().collectList().block();
        assertThat(solucionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSolucions() {
        // Initialize the database
        solucionRepository.save(solucion).block();

        // Get all the solucionList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(solucion.getId()))
            .jsonPath("$.[*].titulo")
            .value(hasItem(DEFAULT_TITULO))
            .jsonPath("$.[*].descripcion")
            .value(hasItem(DEFAULT_DESCRIPCION));
    }

    @Test
    void getSolucion() {
        // Initialize the database
        solucionRepository.save(solucion).block();

        // Get the solucion
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, solucion.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(solucion.getId()))
            .jsonPath("$.titulo")
            .value(is(DEFAULT_TITULO))
            .jsonPath("$.descripcion")
            .value(is(DEFAULT_DESCRIPCION));
    }

    @Test
    void getNonExistingSolucion() {
        // Get the solucion
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingSolucion() throws Exception {
        // Initialize the database
        solucionRepository.save(solucion).block();

        int databaseSizeBeforeUpdate = solucionRepository.findAll().collectList().block().size();

        // Update the solucion
        Solucion updatedSolucion = solucionRepository.findById(solucion.getId()).block();
        updatedSolucion.titulo(UPDATED_TITULO).descripcion(UPDATED_DESCRIPCION);
        SolucionDTO solucionDTO = solucionMapper.toDto(updatedSolucion);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, solucionDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(solucionDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Solucion in the database
        List<Solucion> solucionList = solucionRepository.findAll().collectList().block();
        assertThat(solucionList).hasSize(databaseSizeBeforeUpdate);
        Solucion testSolucion = solucionList.get(solucionList.size() - 1);
        assertThat(testSolucion.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testSolucion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    void putNonExistingSolucion() throws Exception {
        int databaseSizeBeforeUpdate = solucionRepository.findAll().collectList().block().size();
        solucion.setId(UUID.randomUUID().toString());

        // Create the Solucion
        SolucionDTO solucionDTO = solucionMapper.toDto(solucion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, solucionDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(solucionDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Solucion in the database
        List<Solucion> solucionList = solucionRepository.findAll().collectList().block();
        assertThat(solucionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSolucion() throws Exception {
        int databaseSizeBeforeUpdate = solucionRepository.findAll().collectList().block().size();
        solucion.setId(UUID.randomUUID().toString());

        // Create the Solucion
        SolucionDTO solucionDTO = solucionMapper.toDto(solucion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(solucionDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Solucion in the database
        List<Solucion> solucionList = solucionRepository.findAll().collectList().block();
        assertThat(solucionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSolucion() throws Exception {
        int databaseSizeBeforeUpdate = solucionRepository.findAll().collectList().block().size();
        solucion.setId(UUID.randomUUID().toString());

        // Create the Solucion
        SolucionDTO solucionDTO = solucionMapper.toDto(solucion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(solucionDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Solucion in the database
        List<Solucion> solucionList = solucionRepository.findAll().collectList().block();
        assertThat(solucionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSolucionWithPatch() throws Exception {
        // Initialize the database
        solucionRepository.save(solucion).block();

        int databaseSizeBeforeUpdate = solucionRepository.findAll().collectList().block().size();

        // Update the solucion using partial update
        Solucion partialUpdatedSolucion = new Solucion();
        partialUpdatedSolucion.setId(solucion.getId());

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSolucion.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSolucion))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Solucion in the database
        List<Solucion> solucionList = solucionRepository.findAll().collectList().block();
        assertThat(solucionList).hasSize(databaseSizeBeforeUpdate);
        Solucion testSolucion = solucionList.get(solucionList.size() - 1);
        assertThat(testSolucion.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testSolucion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    void fullUpdateSolucionWithPatch() throws Exception {
        // Initialize the database
        solucionRepository.save(solucion).block();

        int databaseSizeBeforeUpdate = solucionRepository.findAll().collectList().block().size();

        // Update the solucion using partial update
        Solucion partialUpdatedSolucion = new Solucion();
        partialUpdatedSolucion.setId(solucion.getId());

        partialUpdatedSolucion.titulo(UPDATED_TITULO).descripcion(UPDATED_DESCRIPCION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSolucion.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSolucion))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Solucion in the database
        List<Solucion> solucionList = solucionRepository.findAll().collectList().block();
        assertThat(solucionList).hasSize(databaseSizeBeforeUpdate);
        Solucion testSolucion = solucionList.get(solucionList.size() - 1);
        assertThat(testSolucion.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testSolucion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    void patchNonExistingSolucion() throws Exception {
        int databaseSizeBeforeUpdate = solucionRepository.findAll().collectList().block().size();
        solucion.setId(UUID.randomUUID().toString());

        // Create the Solucion
        SolucionDTO solucionDTO = solucionMapper.toDto(solucion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, solucionDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(solucionDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Solucion in the database
        List<Solucion> solucionList = solucionRepository.findAll().collectList().block();
        assertThat(solucionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSolucion() throws Exception {
        int databaseSizeBeforeUpdate = solucionRepository.findAll().collectList().block().size();
        solucion.setId(UUID.randomUUID().toString());

        // Create the Solucion
        SolucionDTO solucionDTO = solucionMapper.toDto(solucion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(solucionDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Solucion in the database
        List<Solucion> solucionList = solucionRepository.findAll().collectList().block();
        assertThat(solucionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSolucion() throws Exception {
        int databaseSizeBeforeUpdate = solucionRepository.findAll().collectList().block().size();
        solucion.setId(UUID.randomUUID().toString());

        // Create the Solucion
        SolucionDTO solucionDTO = solucionMapper.toDto(solucion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(solucionDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Solucion in the database
        List<Solucion> solucionList = solucionRepository.findAll().collectList().block();
        assertThat(solucionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSolucion() {
        // Initialize the database
        solucionRepository.save(solucion).block();

        int databaseSizeBeforeDelete = solucionRepository.findAll().collectList().block().size();

        // Delete the solucion
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, solucion.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Solucion> solucionList = solucionRepository.findAll().collectList().block();
        assertThat(solucionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
