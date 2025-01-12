package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.DataConverterService;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private final DataConverterService dataConverter = new DataConverterImpl();

    @Test
    void convertToTransaction_invalidFormat_exceptionThrown() {
        List<String> input = List.of("invalid,format");
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(input));
    }
}
