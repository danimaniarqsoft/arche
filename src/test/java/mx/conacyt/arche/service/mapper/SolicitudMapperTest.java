package mx.conacyt.arche.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolicitudMapperTest {

    private SolicitudMapper solicitudMapper;

    @BeforeEach
    public void setUp() {
        solicitudMapper = new SolicitudMapperImpl();
    }
}
