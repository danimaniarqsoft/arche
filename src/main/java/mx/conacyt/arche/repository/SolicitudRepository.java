package mx.conacyt.arche.repository;

import mx.conacyt.arche.domain.Solicitud;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Spring Data MongoDB reactive repository for the Solicitud entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolicitudRepository extends ReactiveMongoRepository<Solicitud, String> {
    Flux<Solicitud> findAllBy(Pageable pageable);

    Flux<Solicitud> findAllByUsuario(String usuario, Pageable pageable);
}
