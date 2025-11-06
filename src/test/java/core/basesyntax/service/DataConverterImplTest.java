package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private final DataConverterImpl converter = new DataConverterImpl();

    @Test
    void convertToTransaction_ok() {
        List<String> lines = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,10"
        );
        List<FruitTransaction> result = converter.convertToTransaction(lines);
        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(20, result.get(0).getQuantity());
    }

    @Test
    void convertToTransaction_wrongFormat_throws() {
        List<String> lines = Arrays.asList("type,fruit,quantity", "b,banana");

        assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(lines));
    }

    @Test
    void convertToTransaction_negativeQuantity_throws() {
        List<String> lines = Arrays.asList("type,fruit,quantity", "b,banana,-5");

        assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(lines));
    }
}
