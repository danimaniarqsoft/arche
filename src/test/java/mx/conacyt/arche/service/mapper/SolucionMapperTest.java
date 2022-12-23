package mx.conacyt.arche.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolucionMapperTest {

    private SolucionMapper solucionMapper;

    @BeforeEach
    public void setUp() {
        solucionMapper = new SolucionMapperImpl();
    }
}
