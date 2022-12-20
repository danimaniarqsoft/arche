package mx.conacyt.arche.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.conacyt.arche.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SolicitudTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Solicitud.class);
        Solicitud solicitud1 = new Solicitud();
        solicitud1.setId("id1");
        Solicitud solicitud2 = new Solicitud();
        solicitud2.setId(solicitud1.getId());
        assertThat(solicitud1).isEqualTo(solicitud2);
        solicitud2.setId("id2");
        assertThat(solicitud1).isNotEqualTo(solicitud2);
        solicitud1.setId(null);
        assertThat(solicitud1).isNotEqualTo(solicitud2);
    }
}
