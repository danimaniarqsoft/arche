package mx.conacyt.arche.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import mx.conacyt.arche.domain.enumeration.EstadoSolicitud;
import mx.conacyt.arche.repository.SolicitudRepository;
import mx.conacyt.arche.service.SolicitudService;
import mx.conacyt.arche.service.dto.SolicitudDTO;
import mx.conacyt.arche.web.rest.errors.BadRequestAlertException;
import mx.conacyt.arche.web.rest.errors.ErrorSolicitud;
import mx.conacyt.arche.web.rest.errors.InvalidSolicitudException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link mx.conacyt.arche.domain.Solicitud}.
 */
@RestController
@RequestMapping("/api")
public class SolicitudResource {

    private final Logger log = LoggerFactory.getLogger(SolicitudResource.class);

    private static final String ENTITY_NAME = "solicitud";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SolicitudService solicitudService;

    private final SolicitudRepository solicitudRepository;

    public SolicitudResource(SolicitudService solicitudService, SolicitudRepository solicitudRepository) {
        this.solicitudService = solicitudService;
        this.solicitudRepository = solicitudRepository;
    }

    /**
     * {@code POST  /solicituds} : Create a new solicitud.
     *
     * @param solicitudDTO the solicitudDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new solicitudDTO, or with status {@code 400 (Bad Request)}
     *         if the solicitud has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/solicitudes")
    public Mono<ResponseEntity<SolicitudDTO>> createSolicitud(@RequestBody SolicitudDTO solicitudDTO) throws URISyntaxException {
        log.debug("REST request to save Solicitud : {}", solicitudDTO);
        if (solicitudDTO.getId() != null) {
            throw new BadRequestAlertException("A new solicitud cannot already have an ID", ENTITY_NAME, "idexists");
        }
        solicitudDTO.setEstado(EstadoSolicitud.EN_CAPTURA);
        return solicitudService
            .save(solicitudDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/solicitudes/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /solicitudes/:id} : Updates an existing solicitud.
     *
     * @param id           the id of the solicitudDTO to save.
     * @param solicitudDTO the solicitudDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated solicitudDTO,
     *         or with status {@code 400 (Bad Request)} if the solicitudDTO is not
     *         valid,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         solicitudDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/solicitudes/{id}")
    public Mono<ResponseEntity<SolicitudDTO>> updateSolicitud(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SolicitudDTO solicitudDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Solicitud : {}, {}", id, solicitudDTO);
        if (solicitudDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, solicitudDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return solicitudRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return solicitudService
                    .update(solicitudDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /solicituds/:id} : Partial updates given fields of an existing
     * solicitud, field will ignore if it is null
     *
     * @param id           the id of the solicitudDTO to save.
     * @param solicitudDTO the solicitudDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated solicitudDTO,
     *         or with status {@code 400 (Bad Request)} if the solicitudDTO is not
     *         valid,
     *         or with status {@code 404 (Not Found)} if the solicitudDTO is not
     *         found,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         solicitudDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/solicitudes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<SolicitudDTO>> partialUpdateSolicitud(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SolicitudDTO solicitudDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Solicitud partially : {}, {}", id, solicitudDTO);
        if (solicitudDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, solicitudDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return solicitudRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<SolicitudDTO> result = solicitudService.partialUpdate(solicitudDTO);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /solicitudes} : get all the solicituds.
     *
     * @param pageable the pagination information.
     * @param request  a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of solicituds in body.
     */
    @GetMapping("/solicitudes")
    public Mono<ResponseEntity<List<SolicitudDTO>>> getAllSolicituds(
        @RequestParam(required = false) String solucionId,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Solicituds");
        return solicitudService
            .countAll()
            .zipWith(solicitudService.findAll(solucionId, pageable).collectList())
            .map(countWithEntities ->
                ResponseEntity
                    .ok()
                    .headers(
                        PaginationUtil.generatePaginationHttpHeaders(
                            UriComponentsBuilder.fromHttpRequest(request),
                            new PageImpl<>(countWithEntities.getT2(), pageable, countWithEntities.getT1())
                        )
                    )
                    .body(countWithEntities.getT2())
            );
    }

    /**
     * {@code GET  /solicituds/:id} : get the "id" solicitud.
     *
     * @param id the id of the solicitudDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the solicitudDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/solicitudes/{id}")
    public Mono<ResponseEntity<SolicitudDTO>> getSolicitud(@PathVariable String id) {
        log.debug("REST request to get Solicitud : {}", id);
        Mono<SolicitudDTO> solicitudDTO = solicitudService.findOne(id);
        return ResponseUtil.wrapOrNotFound(solicitudDTO);
    }

    @GetMapping("/solicitudes/solucion/{id}")
    public Mono<ResponseEntity<SolicitudDTO>> getSolicitudBySolucionId(@PathVariable String id) {
        log.debug("REST request to get Solicitud : {}", id);
        Mono<SolicitudDTO> solicitudDTO = solicitudService.findBySolucionId(id);
        return ResponseUtil.wrapOrNotFound(solicitudDTO);
    }

    /**
     * {@code DELETE  /solicituds/:id} : delete the "id" solicitud.
     *
     * @param id the id of the solicitudDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/solicitudes/{id}")
    public Mono<ResponseEntity<Void>> deleteSolicitud(@PathVariable String id) {
        log.debug("REST request to delete Solicitud : {}", id);
        return solicitudService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }

    @PutMapping("/solicitudes/enviar/{id}")
    public Mono<ResponseEntity<SolicitudDTO>> sendSolicitud(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SolicitudDTO solicitudDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Solicitud : {}, {}", id, solicitudDTO);
        if (solicitudDTO.getId() == null) {
            List<ErrorSolicitud> errores = new ArrayList<>();
            ErrorSolicitud error = new ErrorSolicitud("becas_id", "La beca ya existe");
            errores.add(error);
            throw new InvalidSolicitudException("Invalid id", "invalidSolicitud", errores);
        }
        solicitudDTO.setEstado(EstadoSolicitud.ENVIADA);
        return solicitudRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }
                return solicitudService
                    .update(solicitudDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId()))
                            .body(result)
                    );
            });
    }
}
