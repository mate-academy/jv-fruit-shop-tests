package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private final DataConverterImpl converter = new DataConverterImpl();

    @Test
    void convertToTransaction_validInput_ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "p,apple,5");

        List<FruitTransaction> result = converter.convertToTransaction(input);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE,
                result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(20, result.get(0).getQuantity());
    }

    @Test
    void convertToTransaction_inputWithSpaces_ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                " s , apple , 10");

        List<FruitTransaction> result = converter.convertToTransaction(input);

        assertEquals("apple", result.get(0).getFruit());
        assertEquals(10, result.get(0).getQuantity());
    }

    @Test
    void convertToTransaction_invalidFormat_notOk() {
        List<String> input = List.of("type,fruit,quantity", "b,banana");

        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(input));
    }
}
