package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.DataConverter;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private final DataConverter dataConverter = new DataConverterImpl();

    @Test
    void noData_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransaction(List.of());
        });
    }

    @Test
    void lineIsLonger_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransaction(List.of("b,apple,123,r", "s,banana,10,p"));
        });
    }
}
