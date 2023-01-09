package mx.conacyt.arche.repository;

import mx.conacyt.arche.domain.Solucion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Spring Data MongoDB reactive repository for the Solucion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolucionRepository extends ReactiveMongoRepository<Solucion, String> {
    Flux<Solucion> findAllBy(Pageable pageable);

    Flux<Solucion> findAllByEstado(String estado, Pageable pageable);
}
