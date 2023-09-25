package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.DataConverterServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DataConverterServiceImplTest {
    private final DataConverterService dataConverterService = new DataConverterServiceImpl();

    @Test
    void convertData_invalidOperation_notOk() {
        List<String> data = List.of("b,banana,10", "b,apple,10", "q,banana,20");
        assertThrows(RuntimeException.class,
                () -> dataConverterService.convertText(data));
    }

    @Test
    void convertData_notNumericQuantity_notOk() {
        List<String> data = List.of("b,banana,10", "b,apple,10", "s,banana,2a0");
        assertThrows(RuntimeException.class,
                () -> dataConverterService.convertText(data));
    }
}
