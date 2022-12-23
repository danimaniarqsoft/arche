package mx.conacyt.arche.service;

import mx.conacyt.arche.service.dto.SolucionDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link mx.conacyt.arche.domain.Solucion}.
 */
public interface SolucionService {
    /**
     * Save a solucion.
     *
     * @param solucionDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<SolucionDTO> save(SolucionDTO solucionDTO);

    /**
     * Updates a solucion.
     *
     * @param solucionDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<SolucionDTO> update(SolucionDTO solucionDTO);

    /**
     * Partially updates a solucion.
     *
     * @param solucionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<SolucionDTO> partialUpdate(SolucionDTO solucionDTO);

    /**
     * Get all the solucions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<SolucionDTO> findAll(Pageable pageable);

    /**
     * Returns the number of solucions available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" solucion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<SolucionDTO> findOne(String id);

    /**
     * Delete the "id" solucion.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(String id);
}
