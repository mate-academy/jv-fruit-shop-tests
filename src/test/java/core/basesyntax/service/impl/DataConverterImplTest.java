package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataConverter;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final int TEST_QUANTITY = 1;

    @Test
    void convertToTransaction_validInputData_ok() {
        List<String> inputReport = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,1",
                "b,apple,1"
        );
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(
                        Operation.BALANCE,
                        BANANA,
                        TEST_QUANTITY),
                new FruitTransaction(
                        Operation.BALANCE,
                        APPLE,
                        TEST_QUANTITY));
        DataConverter converter = new DataConverterImpl();
        assertEquals(expected, converter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_wrongNumberOfData_notOk() {
        List<String> inputReport = Arrays.asList(
                "type,fruit,quantity",
                "b,banana1",
                "bapple,1"
        );
        DataConverter converter = new DataConverterImpl();
        assertThrows(RuntimeException.class, () ->
                converter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_wrongQuantityData_notOk() {
        List<String> inputReport = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,1",
                "b,apple,o"
        );
        DataConverter converter = new DataConverterImpl();
        assertThrows(RuntimeException.class, () ->
                converter.convertToTransaction(inputReport));
    }
}
