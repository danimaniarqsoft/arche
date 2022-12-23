package mx.conacyt.arche.domain;

import java.io.Serializable;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Componente.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Componente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("orden")
    private Integer orden;

    @Field("titulo")
    private String titulo;

    @Field("descripcion")
    private String descripcion;

    @Field("form_id")
    private String formId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getOrden() {
        return this.orden;
    }

    public Componente orden(Integer orden) {
        this.setOrden(orden);
        return this;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Componente titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Componente descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFormId() {
        return this.formId;
    }

    public Componente formId(String formId) {
        this.setFormId(formId);
        return this;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Componente)) {
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Componente{" +
                ", orden=" + getOrden() +
                ", titulo='" + getTitulo() + "'" +
                ", descripcion='" + getDescripcion() + "'" +
                ", formId='" + getFormId() + "'" +
                "}";
    }
}
