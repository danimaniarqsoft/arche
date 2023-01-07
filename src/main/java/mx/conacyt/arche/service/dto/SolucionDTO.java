package mx.conacyt.arche.service.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import mx.conacyt.arche.domain.enumeration.EstadoSolucion;

/**
 * A DTO for the {@link mx.conacyt.arche.domain.Solucion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SolucionDTO implements Serializable {

    private String id;

    private String titulo;

    private String descripcion;

    private EstadoSolucion estado;

    private MensajeDTO mensaje = new MensajeDTO();

    private CalendarioDTO calendario = new CalendarioDTO();

    private Set<ComponenteDTO> componentes = new HashSet<>();

    private List<String> tags = new ArrayList<>();

    @JsonIgnore
    private transient Map<String, Object> properties = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoSolucion getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolucion estado) {
        this.estado = estado;
    }

    public Set<ComponenteDTO> getComponentes() {
        return componentes;
    }

    public void setComponentes(Set<ComponenteDTO> componentes) {
        this.componentes = componentes;
    }

    public MensajeDTO getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeDTO mensaje) {
        this.mensaje = mensaje;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public CalendarioDTO getCalendario() {
        return calendario;
    }

    public void setCalendario(CalendarioDTO calendario) {
        this.calendario = calendario;
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
        if (!(o instanceof SolucionDTO)) {
            return false;
        }

        SolucionDTO solucionDTO = (SolucionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, solucionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SolucionDTO{" +
                "id='" + getId() + "'" +
                ", titulo='" + getTitulo() + "'" +
                ", descripcion='" + getDescripcion() + "'" +
                ", estado='" + getEstado() + "'" +
                ", componentes=" + getComponentes() +
                ", mensaje=" + getMensaje() +
                ", tags=" + getTags() +
                "}";
    }
}
