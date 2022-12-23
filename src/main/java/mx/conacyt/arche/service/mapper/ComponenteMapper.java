package mx.conacyt.arche.service.mapper;

import mx.conacyt.arche.domain.Componente;
import mx.conacyt.arche.domain.Solucion;
import mx.conacyt.arche.service.dto.ComponenteDTO;
import mx.conacyt.arche.service.dto.SolucionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Componente} and its DTO {@link ComponenteDTO}.
 */
@Mapper(componentModel = "spring")
public interface ComponenteMapper extends EntityMapper<ComponenteDTO, Componente> {}
