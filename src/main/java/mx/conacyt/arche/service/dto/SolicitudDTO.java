package mx.conacyt.arche.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mx.conacyt.arche.domain.Solicitud} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SolicitudDTO implements Serializable {

    private String id;

    private String nombre;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SolicitudDTO)) {
            return false;
        }

        SolicitudDTO solicitudDTO = (SolicitudDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, solicitudDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SolicitudDTO{" +
            "id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
