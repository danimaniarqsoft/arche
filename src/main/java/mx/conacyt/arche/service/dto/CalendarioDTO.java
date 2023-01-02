package mx.conacyt.arche.service.dto;

import java.io.Serializable;
import java.time.Instant;
import mx.conacyt.arche.domain.Componente;

public class CalendarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Instant fechaInicio;

    private Instant fechaFinSolicitud;

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

    // prettier-ignore
    @Override
    public String toString() {
        return "Calendario{" +
                ", fechaInicio=" + getFechaInicio() +
                ", fechaFinSolicitud='" + getFechaFinSolicitud() + "'" +
                ", fechaFinRevision='" + getFechaFinRevision() + "'" +
                "}";
    }
}
