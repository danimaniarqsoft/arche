package mx.conacyt.arche.service.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A DTO for the {@link mx.conacyt.arche.domain.Solicitud} entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SolicitudDTO implements Serializable {

    private String id;

    private String nombre;

    private String usuario;

    @JsonIgnore
    private transient Map<String, Object> properties = new HashMap<>();

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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public SolicitudDTO usuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return this.properties;
    }

    @JsonAnySetter
    public void setProperty(String name, Object value) {
        this.properties.put(name, value);
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
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : this.getProperties().entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue().toString());
        }
        return "SolicitudDTO{" +
                "id='" + getId() + "'" +
                ", nombre='" + getNombre() + "'" +
                ", usuario='" + getUsuario() + "'" +
                "," + sb.toString() +
                "}";
    }
}
