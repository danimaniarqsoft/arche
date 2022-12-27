package mx.conacyt.arche.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link mx.conacyt.arche.domain.Componente} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ComponenteDTO implements Serializable {

    private Integer orden;

    private String titulo;

    private String descripcion;

    private String formId;

    private String icon;

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
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

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return this.icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComponenteDTO)) {
            return false;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComponenteDTO{" +
                "orden=" + getOrden() +
                ", titulo='" + getTitulo() + "'" +
                ", descripcion='" + getDescripcion() + "'" +
                ", formId='" + getFormId() + "'" +
                "}";
    }
}
