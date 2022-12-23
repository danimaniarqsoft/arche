package mx.conacyt.arche.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.conacyt.arche.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SolucionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Solucion.class);
        Solucion solucion1 = new Solucion();
        solucion1.setId("id1");
        Solucion solucion2 = new Solucion();
        solucion2.setId(solucion1.getId());
        assertThat(solucion1).isEqualTo(solucion2);
        solucion2.setId("id2");
        assertThat(solucion1).isNotEqualTo(solucion2);
        solucion1.setId(null);
        assertThat(solucion1).isNotEqualTo(solucion2);
    }
}
