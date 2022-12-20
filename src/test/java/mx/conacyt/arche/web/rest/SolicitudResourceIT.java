package mx.conacyt.arche.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import mx.conacyt.arche.IntegrationTest;
import mx.conacyt.arche.domain.Solicitud;
import mx.conacyt.arche.repository.SolicitudRepository;
import mx.conacyt.arche.service.dto.SolicitudDTO;
import mx.conacyt.arche.service.mapper.SolicitudMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link SolicitudResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class SolicitudResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/solicituds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private SolicitudMapper solicitudMapper;

    @Autowired
    private WebTestClient webTestClient;

    private Solicitud solicitud;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Solicitud createEntity() {
        Solicitud solicitud = new Solicitud().nombre(DEFAULT_NOMBRE);
        return solicitud;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Solicitud createUpdatedEntity() {
        Solicitud solicitud = new Solicitud().nombre(UPDATED_NOMBRE);
        return solicitud;
    }

    @BeforeEach
    public void initTest() {
        solicitudRepository.deleteAll().block();
        solicitud = createEntity();
    }

    @Test
    void createSolicitud() throws Exception {
        int databaseSizeBeforeCreate = solicitudRepository.findAll().collectList().block().size();
        // Create the Solicitud
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(solicitudDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll().collectList().block();
        assertThat(solicitudList).hasSize(databaseSizeBeforeCreate + 1);
        Solicitud testSolicitud = solicitudList.get(solicitudList.size() - 1);
        assertThat(testSolicitud.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    void createSolicitudWithExistingId() throws Exception {
        // Create the Solicitud with an existing ID
        solicitud.setId("existing_id");
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);

        int databaseSizeBeforeCreate = solicitudRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(solicitudDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll().collectList().block();
        assertThat(solicitudList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSolicituds() {
        // Initialize the database
        solicitudRepository.save(solicitud).block();

        // Get all the solicitudList
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
            .value(hasItem(solicitud.getId()))
            .jsonPath("$.[*].nombre")
            .value(hasItem(DEFAULT_NOMBRE));
    }

    @Test
    void getSolicitud() {
        // Initialize the database
        solicitudRepository.save(solicitud).block();

        // Get the solicitud
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, solicitud.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(solicitud.getId()))
            .jsonPath("$.nombre")
            .value(is(DEFAULT_NOMBRE));
    }

    @Test
    void getNonExistingSolicitud() {
        // Get the solicitud
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingSolicitud() throws Exception {
        // Initialize the database
        solicitudRepository.save(solicitud).block();

        int databaseSizeBeforeUpdate = solicitudRepository.findAll().collectList().block().size();

        // Update the solicitud
        Solicitud updatedSolicitud = solicitudRepository.findById(solicitud.getId()).block();
        updatedSolicitud.nombre(UPDATED_NOMBRE);
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(updatedSolicitud);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, solicitudDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(solicitudDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll().collectList().block();
        assertThat(solicitudList).hasSize(databaseSizeBeforeUpdate);
        Solicitud testSolicitud = solicitudList.get(solicitudList.size() - 1);
        assertThat(testSolicitud.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    void putNonExistingSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = solicitudRepository.findAll().collectList().block().size();
        solicitud.setId(UUID.randomUUID().toString());

        // Create the Solicitud
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, solicitudDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(solicitudDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll().collectList().block();
        assertThat(solicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = solicitudRepository.findAll().collectList().block().size();
        solicitud.setId(UUID.randomUUID().toString());

        // Create the Solicitud
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(solicitudDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll().collectList().block();
        assertThat(solicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = solicitudRepository.findAll().collectList().block().size();
        solicitud.setId(UUID.randomUUID().toString());

        // Create the Solicitud
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(solicitudDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll().collectList().block();
        assertThat(solicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSolicitudWithPatch() throws Exception {
        // Initialize the database
        solicitudRepository.save(solicitud).block();

        int databaseSizeBeforeUpdate = solicitudRepository.findAll().collectList().block().size();

        // Update the solicitud using partial update
        Solicitud partialUpdatedSolicitud = new Solicitud();
        partialUpdatedSolicitud.setId(solicitud.getId());

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSolicitud.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSolicitud))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll().collectList().block();
        assertThat(solicitudList).hasSize(databaseSizeBeforeUpdate);
        Solicitud testSolicitud = solicitudList.get(solicitudList.size() - 1);
        assertThat(testSolicitud.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    void fullUpdateSolicitudWithPatch() throws Exception {
        // Initialize the database
        solicitudRepository.save(solicitud).block();

        int databaseSizeBeforeUpdate = solicitudRepository.findAll().collectList().block().size();

        // Update the solicitud using partial update
        Solicitud partialUpdatedSolicitud = new Solicitud();
        partialUpdatedSolicitud.setId(solicitud.getId());

        partialUpdatedSolicitud.nombre(UPDATED_NOMBRE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSolicitud.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSolicitud))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll().collectList().block();
        assertThat(solicitudList).hasSize(databaseSizeBeforeUpdate);
        Solicitud testSolicitud = solicitudList.get(solicitudList.size() - 1);
        assertThat(testSolicitud.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    void patchNonExistingSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = solicitudRepository.findAll().collectList().block().size();
        solicitud.setId(UUID.randomUUID().toString());

        // Create the Solicitud
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, solicitudDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(solicitudDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll().collectList().block();
        assertThat(solicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = solicitudRepository.findAll().collectList().block().size();
        solicitud.setId(UUID.randomUUID().toString());

        // Create the Solicitud
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(solicitudDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll().collectList().block();
        assertThat(solicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = solicitudRepository.findAll().collectList().block().size();
        solicitud.setId(UUID.randomUUID().toString());

        // Create the Solicitud
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(solicitudDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll().collectList().block();
        assertThat(solicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSolicitud() {
        // Initialize the database
        solicitudRepository.save(solicitud).block();

        int databaseSizeBeforeDelete = solicitudRepository.findAll().collectList().block().size();

        // Delete the solicitud
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, solicitud.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Solicitud> solicitudList = solicitudRepository.findAll().collectList().block();
        assertThat(solicitudList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
