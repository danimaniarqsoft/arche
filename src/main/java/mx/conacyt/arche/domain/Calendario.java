package mx.conacyt.arche.domain;

import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.mongodb.core.mapping.Field;

public class Calendario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("fechaInicio")
    private Instant fechaInicio;

    @Field("fechaFinSolicitud")
    private Instant fechaFinSolicitud;

    @Field("fechaFinRevision")
    private Instant fechaFinRevision;

    public Instant getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Instant getFechaFinSolicitud() {
        return fechaFinSolicitud;
    }

    public void setFechaFinSolicitud(Instant fechaFinSolicitud) {
        this.fechaFinSolicitud = fechaFinSolicitud;
    }

    public Instant getFechaFinRevision() {
        return fechaFinRevision;
    }

    public void setFechaFinRevision(Instant fechaFinRevision) {
        this.fechaFinRevision = fechaFinRevision;
    }

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
        return "Mensaje{" +
                ", fechaInicio=" + getFechaInicio() +
                ", fechaFinSolicitud='" + getFechaFinSolicitud() + "'" +
                ", fechaFinRevision='" + getFechaFinRevision() + "'" +
                "}";
    }
}
