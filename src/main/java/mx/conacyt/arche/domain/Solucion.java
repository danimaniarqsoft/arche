package mx.conacyt.arche.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import mx.conacyt.arche.domain.enumeration.EstadoSolucion;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Solucion.
 */
@Document(collection = "soluciones")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Solucion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("titulo")
    private String titulo;

    @Field("descripcion")
    private String descripcion;

    @Field("estado")
    private EstadoSolucion estado;

    @Field("componentes")
    @JsonIgnoreProperties(value = { "solucion" }, allowSetters = true)
    private Set<Componente> componentes = new HashSet<>();

    @Field("tags")
    private List<String> tags = new ArrayList<>();

    @Field("mensaje")
    private Mensaje mensaje = new Mensaje();

    @Field("calendario")
    private Calendario calendario = new Calendario();

    @Field("properties")
    private Map<String, Object> properties = new HashMap<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Solucion id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Solucion titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Solucion descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoSolucion getEstado() {
        return this.estado;
    }

    public Solucion estado(EstadoSolucion estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoSolucion estado) {
        this.estado = estado;
    }

    public Set<Componente> getComponentes() {
        return this.componentes;
    }

    public void setComponentes(Set<Componente> componentes) {
        this.componentes = componentes;
    }

    public Solucion componentes(Set<Componente> componentes) {
        this.setComponentes(componentes);
        return this;
    }

    public Solucion addComponentes(Componente componente) {
        this.componentes.add(componente);
        return this;
    }

    public Solucion removeComponentes(Componente componente) {
        this.componentes.remove(componente);
        return this;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public Mensaje getMensaje() {
        return this.mensaje;
    }

    public Solucion estado(Mensaje mensaje) {
        this.setMensaje(mensaje);
        return this;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Solucion calendario(Calendario calendario) {
        this.setCalendario(calendario);
        return this;
    }

    public Calendario getCalendario() {
        return calendario;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }

    public void setProperty(String name, Object value) {
        this.properties.put(name, value);
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Solucion)) {
            return false;
        }
        return id != null && id.equals(((Solucion) o).id);
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
        return "Solucion{" +
                "id=" + getId() +
                ", titulo='" + getTitulo() + "'" +
                ", descripcion='" + getDescripcion() + "'" +
                ", estado='" + getEstado() + "'" +
                ", tags='" + getTags() + "'" +
                "}";
    }
}
