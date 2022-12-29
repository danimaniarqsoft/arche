package mx.conacyt.arche.service.mapper;

import mx.conacyt.arche.domain.Solucion;
import mx.conacyt.arche.service.dto.SolucionDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Solucion} and its DTO {@link SolucionDTO}.
 */
@Mapper(componentModel = "spring", uses = ComponenteMapper.class)
public interface SolucionMapper extends EntityMapper<SolucionDTO, Solucion> {}
