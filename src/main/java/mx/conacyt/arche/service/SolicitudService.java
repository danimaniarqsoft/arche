package mx.conacyt.arche.service;

import mx.conacyt.arche.service.dto.SolicitudDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link mx.conacyt.arche.domain.Solicitud}.
 */
public interface SolicitudService {
    /**
     * Save a solicitud.
     *
     * @param solicitudDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<SolicitudDTO> save(SolicitudDTO solicitudDTO);

    /**
     * Updates a solicitud.
     *
     * @param solicitudDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<SolicitudDTO> update(SolicitudDTO solicitudDTO);

    /**
     * Partially updates a solicitud.
     *
     * @param solicitudDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<SolicitudDTO> partialUpdate(SolicitudDTO solicitudDTO);

    /**
     * Get all the solicituds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<SolicitudDTO> findAll(Pageable pageable);

    /**
     * Returns the number of solicituds available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" solicitud.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<SolicitudDTO> findOne(String id);

    /**
     * Delete the "id" solicitud.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(String id);
}
