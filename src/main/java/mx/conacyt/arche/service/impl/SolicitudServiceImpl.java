package mx.conacyt.arche.service.impl;

import mx.conacyt.arche.domain.Solicitud;
import mx.conacyt.arche.repository.SolicitudRepository;
import mx.conacyt.arche.service.SolicitudService;
import mx.conacyt.arche.service.dto.SolicitudDTO;
import mx.conacyt.arche.service.mapper.SolicitudMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Solicitud}.
 */
@Service
public class SolicitudServiceImpl implements SolicitudService {

    private final Logger log = LoggerFactory.getLogger(SolicitudServiceImpl.class);

    private final SolicitudRepository solicitudRepository;

    private final SolicitudMapper solicitudMapper;

    public SolicitudServiceImpl(SolicitudRepository solicitudRepository, SolicitudMapper solicitudMapper) {
        this.solicitudRepository = solicitudRepository;
        this.solicitudMapper = solicitudMapper;
    }

    @Override
    public Mono<SolicitudDTO> save(SolicitudDTO solicitudDTO) {
        log.debug("Request to save Solicitud : {}", solicitudDTO);
        return solicitudRepository.save(solicitudMapper.toEntity(solicitudDTO)).map(solicitudMapper::toDto);
    }

    @Override
    public Mono<SolicitudDTO> update(SolicitudDTO solicitudDTO) {
        log.debug("Request to update Solicitud : {}", solicitudDTO);
        return solicitudRepository.save(solicitudMapper.toEntity(solicitudDTO)).map(solicitudMapper::toDto);
    }

    @Override
    public Mono<SolicitudDTO> partialUpdate(SolicitudDTO solicitudDTO) {
        log.debug("Request to partially update Solicitud : {}", solicitudDTO);

        return solicitudRepository
            .findById(solicitudDTO.getId())
            .map(existingSolicitud -> {
                solicitudMapper.partialUpdate(existingSolicitud, solicitudDTO);

                return existingSolicitud;
            })
            .flatMap(solicitudRepository::save)
            .map(solicitudMapper::toDto);
    }

    @Override
    public Flux<SolicitudDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Solicituds");
        return solicitudRepository.findAllBy(pageable).map(solicitudMapper::toDto);
    }

    public Mono<Long> countAll() {
        return solicitudRepository.count();
    }

    @Override
    public Mono<SolicitudDTO> findOne(String id) {
        log.debug("Request to get Solicitud : {}", id);
        return solicitudRepository.findById(id).map(solicitudMapper::toDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Solicitud : {}", id);
        return solicitudRepository.deleteById(id);
    }
}
