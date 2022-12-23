package mx.conacyt.arche.service.impl;

import mx.conacyt.arche.domain.Solucion;
import mx.conacyt.arche.repository.SolucionRepository;
import mx.conacyt.arche.service.SolucionService;
import mx.conacyt.arche.service.dto.SolucionDTO;
import mx.conacyt.arche.service.mapper.SolucionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Solucion}.
 */
@Service
public class SolucionServiceImpl implements SolucionService {

    private final Logger log = LoggerFactory.getLogger(SolucionServiceImpl.class);

    private final SolucionRepository solucionRepository;

    private final SolucionMapper solucionMapper;

    public SolucionServiceImpl(SolucionRepository solucionRepository, SolucionMapper solucionMapper) {
        this.solucionRepository = solucionRepository;
        this.solucionMapper = solucionMapper;
    }

    @Override
    public Mono<SolucionDTO> save(SolucionDTO solucionDTO) {
        log.debug("Request to save Solucion : {}", solucionDTO);
        return solucionRepository.save(solucionMapper.toEntity(solucionDTO)).map(solucionMapper::toDto);
    }

    @Override
    public Mono<SolucionDTO> update(SolucionDTO solucionDTO) {
        log.debug("Request to update Solucion : {}", solucionDTO);
        return solucionRepository.save(solucionMapper.toEntity(solucionDTO)).map(solucionMapper::toDto);
    }

    @Override
    public Mono<SolucionDTO> partialUpdate(SolucionDTO solucionDTO) {
        log.debug("Request to partially update Solucion : {}", solucionDTO);

        return solucionRepository
            .findById(solucionDTO.getId())
            .map(existingSolucion -> {
                solucionMapper.partialUpdate(existingSolucion, solucionDTO);

                return existingSolucion;
            })
            .flatMap(solucionRepository::save)
            .map(solucionMapper::toDto);
    }

    @Override
    public Flux<SolucionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Solucions");
        return solucionRepository.findAllBy(pageable).map(solucionMapper::toDto);
    }

    public Mono<Long> countAll() {
        return solucionRepository.count();
    }

    @Override
    public Mono<SolucionDTO> findOne(String id) {
        log.debug("Request to get Solucion : {}", id);
        return solucionRepository.findById(id).map(solucionMapper::toDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Solucion : {}", id);
        return solucionRepository.deleteById(id);
    }
}
