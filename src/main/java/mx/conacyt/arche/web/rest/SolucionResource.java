package mx.conacyt.arche.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mx.conacyt.arche.repository.SolucionRepository;
import mx.conacyt.arche.service.SolucionService;
import mx.conacyt.arche.service.dto.SolucionDTO;
import mx.conacyt.arche.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link mx.conacyt.arche.domain.Solucion}.
 */
@RestController
@RequestMapping("/api")
public class SolucionResource {

    private final Logger log = LoggerFactory.getLogger(SolucionResource.class);

    private static final String ENTITY_NAME = "solucion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SolucionService solucionService;

    private final SolucionRepository solucionRepository;

    public SolucionResource(SolucionService solucionService, SolucionRepository solucionRepository) {
        this.solucionService = solucionService;
        this.solucionRepository = solucionRepository;
    }

    /**
     * {@code POST  /solucions} : Create a new solucion.
     *
     * @param solucionDTO the solucionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new solucionDTO, or with status {@code 400 (Bad Request)} if the solucion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/solucions")
    public Mono<ResponseEntity<SolucionDTO>> createSolucion(@RequestBody SolucionDTO solucionDTO) throws URISyntaxException {
        log.debug("REST request to save Solucion : {}", solucionDTO);
        if (solucionDTO.getId() != null) {
            throw new BadRequestAlertException("A new solucion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return solucionService
            .save(solucionDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/solucions/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /solucions/:id} : Updates an existing solucion.
     *
     * @param id the id of the solucionDTO to save.
     * @param solucionDTO the solucionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solucionDTO,
     * or with status {@code 400 (Bad Request)} if the solucionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the solucionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/solucions/{id}")
    public Mono<ResponseEntity<SolucionDTO>> updateSolucion(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SolucionDTO solucionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Solucion : {}, {}", id, solucionDTO);
        if (solucionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, solucionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return solucionRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return solucionService
                    .update(solucionDTO)
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
     * {@code PATCH  /solucions/:id} : Partial updates given fields of an existing solucion, field will ignore if it is null
     *
     * @param id the id of the solucionDTO to save.
     * @param solucionDTO the solucionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solucionDTO,
     * or with status {@code 400 (Bad Request)} if the solucionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the solucionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the solucionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/solucions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<SolucionDTO>> partialUpdateSolucion(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SolucionDTO solucionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Solucion partially : {}, {}", id, solucionDTO);
        if (solucionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, solucionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return solucionRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<SolucionDTO> result = solucionService.partialUpdate(solucionDTO);

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
     * {@code GET  /solucions} : get all the solucions.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of solucions in body.
     */
    @GetMapping("/solucions")
    public Mono<ResponseEntity<List<SolucionDTO>>> getAllSolucions(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Solucions");
        return solucionService
            .countAll()
            .zipWith(solucionService.findAll(pageable).collectList())
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
     * {@code GET  /solucions/:id} : get the "id" solucion.
     *
     * @param id the id of the solucionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the solucionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/solucions/{id}")
    public Mono<ResponseEntity<SolucionDTO>> getSolucion(@PathVariable String id) {
        log.debug("REST request to get Solucion : {}", id);
        Mono<SolucionDTO> solucionDTO = solucionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(solucionDTO);
    }

    /**
     * {@code DELETE  /solucions/:id} : delete the "id" solucion.
     *
     * @param id the id of the solucionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/solucions/{id}")
    public Mono<ResponseEntity<Void>> deleteSolucion(@PathVariable String id) {
        log.debug("REST request to delete Solucion : {}", id);
        return solucionService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
