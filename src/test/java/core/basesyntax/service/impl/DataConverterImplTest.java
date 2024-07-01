package core.basesyntax.service.impl;

import static core.basesyntax.constants.Constants.APPLE;
import static core.basesyntax.constants.Constants.BANANA;
import static core.basesyntax.constants.Constants.NORMAL_QUANTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataConverter;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private final DataConverter converter = new DataConverterImpl();

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
                        NORMAL_QUANTITY),
                new FruitTransaction(
                        Operation.BALANCE,
                        APPLE,
                        NORMAL_QUANTITY));
        assertEquals(expected, converter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_wrongNumberOfData_notOk() {
        List<String> inputReport = Arrays.asList(
                "type,fruit,quantity",
                "b,banana1",
                "bapple,1"
        );
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
        assertThrows(RuntimeException.class, () ->
                converter.convertToTransaction(inputReport));
    }
}
