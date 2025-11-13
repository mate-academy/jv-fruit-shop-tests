package core.basesyntax.serviceimpltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.serviceimpl.DataConverterImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private final DataConverterImpl dataConverter = new DataConverterImpl();

    @Test
    void convertToTransaction_validInput_shouldReturnCorrectTransactions() {
        List<String> inputReport = List.of(
                "type,fruit,quantity",
                "b,apple,10",
                "s,banana,5"
        );
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);
        assertEquals(2, transactions.size());
        FruitTransaction first = transactions.get(0);
        assertEquals(Operation.BALANCE, first.getOperation());
        assertEquals("apple", first.getFruit());
        assertEquals(10, first.getQuantity());
        FruitTransaction second = transactions.get(1);
        assertEquals(Operation.SUPPLY, second.getOperation());
        assertEquals("banana", second.getFruit());
        assertEquals(5, second.getQuantity());
    }

    @Test
    void convertToTransaction_invalidFormat_shouldThrowRuntimeException() {
        List<String> inputReport = List.of(
                "type,fruit,quantity",
                "b,apple"
        );
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(inputReport)
        );
        assertTrue(exception.getMessage().contains("Invalid string format"));
    }
}
