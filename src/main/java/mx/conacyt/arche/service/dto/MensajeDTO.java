package mx.conacyt.arche.service.dto;

import java.io.Serializable;
import mx.conacyt.arche.domain.Componente;

public class MensajeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bienvenida;

    private String terminos;

    public String getBienvenida() {
        return bienvenida;
    }

    public void setBienvenida(String bienvenida) {
        this.bienvenida = bienvenida;
    }

    public String getTerminos() {
        return terminos;
    }

    public void setTerminos(String terminos) {
        this.terminos = terminos;
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
                ", bienvenida=" + getBienvenida() +
                ", terminos='" + getTerminos() + "'" +
                "}";
    }
}
