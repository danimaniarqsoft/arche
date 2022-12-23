package mx.conacyt.arche.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mx.conacyt.arche.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SolucionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolucionDTO.class);
        SolucionDTO solucionDTO1 = new SolucionDTO();
        solucionDTO1.setId("id1");
        SolucionDTO solucionDTO2 = new SolucionDTO();
        assertThat(solucionDTO1).isNotEqualTo(solucionDTO2);
        solucionDTO2.setId(solucionDTO1.getId());
        assertThat(solucionDTO1).isEqualTo(solucionDTO2);
        solucionDTO2.setId("id2");
        assertThat(solucionDTO1).isNotEqualTo(solucionDTO2);
        solucionDTO1.setId(null);
        assertThat(solucionDTO1).isNotEqualTo(solucionDTO2);
    }
}
