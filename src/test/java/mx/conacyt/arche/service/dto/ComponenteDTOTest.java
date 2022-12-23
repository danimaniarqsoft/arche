package mx.conacyt.arche.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mx.conacyt.arche.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComponenteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComponenteDTO.class);
    }
}
