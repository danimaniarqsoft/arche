package mx.conacyt.arche.service.mapper;

import mx.conacyt.arche.domain.Mensaje;
import mx.conacyt.arche.service.dto.MensajeDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Mensaje} and its DTO {@link MensajeDTO}.
 */
@Mapper(componentModel = "spring")
public interface MensajeMapper extends EntityMapper<MensajeDTO, Mensaje> {}
