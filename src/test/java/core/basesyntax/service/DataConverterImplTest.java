package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private final DataConverter dataConverter = new DataConverterImpl();

    @Test
    void convertToTransaction_validData_ok() {
        List<String> input = List.of("b,apple,88", "p,banana,23");

        List<FruitTransaction> result = dataConverter.convertToTransaction(input);

        assertEquals(2, result.size());

        FruitTransaction first = result.get(0);
        assertEquals(Operation.BALANCE, first.getOperation());
        assertEquals("apple", first.getFruit());
        assertEquals(88, first.getQuantity());

        FruitTransaction second = result.get(1);
        assertEquals(Operation.PURCHASE, second.getOperation());
        assertEquals("banana", second.getFruit());
        assertEquals(23, second.getQuantity());
    }

    @Test
    void convertToTransaction_invalidOperation_throwsException() {
        List<String> input = List.of("x,apple,88");

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(input));

        System.out.println("EXCEPTION MESSAGE: " + exception.getMessage());

        assertTrue(exception.getMessage().contains("Invalid operation"));
    }
}
