package mx.conacyt.arche.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link mx.conacyt.arche.domain.Solucion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SolucionDTO implements Serializable {

    private String id;

    private String titulo;

    private String descripcion;

    private Set<ComponenteDTO> componentes = new HashSet<>();

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

    public Set<ComponenteDTO> getComponentes() {
        return componentes;
    }

    public void setComponentes(Set<ComponenteDTO> componentes) {
        this.componentes = componentes;
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
                ", componentes=" + getComponentes() +
                "}";
    }
}
