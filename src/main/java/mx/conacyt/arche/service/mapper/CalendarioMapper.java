package mx.conacyt.arche.service.mapper;

import mx.conacyt.arche.domain.Calendario;
import mx.conacyt.arche.service.dto.CalendarioDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Calendario} and its DTO {@link CalendarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface CalendarioMapper extends EntityMapper<CalendarioDTO, Calendario> {}
