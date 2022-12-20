package mx.conacyt.arche.service.mapper;

import mx.conacyt.arche.domain.Solicitud;
import mx.conacyt.arche.service.dto.SolicitudDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Solicitud} and its DTO {@link SolicitudDTO}.
 */
@Mapper(componentModel = "spring")
public interface SolicitudMapper extends EntityMapper<SolicitudDTO, Solicitud> {}
