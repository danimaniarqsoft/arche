package mx.conacyt.arche.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.conacyt.arche.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComponenteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Componente.class);
    }
}
