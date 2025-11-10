package core.basesyntax.serviceimpltest;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.serviceimpl.DataConverterImpl;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private final DataConverterImpl dataConverter = new DataConverterImpl();

    @Test
    void convertToTransaction_validInput_shouldReturnCorrectTransactions() {
        DataConverterImpl dataConverter1 = new DataConverterImpl();
        List<String> inputReport = List.of(
                "type,fruit,quantity",
                "b,apple,10",
                "s,banana,5"
        );
        List<FruitTransaction> transactions = dataConverter1.convertToTransaction(inputReport);
        Assertions.assertEquals(2, transactions.size());

        FruitTransaction first = transactions.get(0);
        Assertions.assertEquals(Operation.BALANCE, first.getOperation());
        Assertions.assertEquals("apple", first.getFruit());
        Assertions.assertEquals(10, first.getQuantity());

        FruitTransaction second = transactions.get(1);
        Assertions.assertEquals(Operation.SUPPLY, second.getOperation());
        Assertions.assertEquals("banana", second.getFruit());
        Assertions.assertEquals(5, second.getQuantity());
    }

    @Test
    void convertToTransaction_invalidFormat_shouldThrowRuntimeException() {
        DataConverterImpl dataConverter = new DataConverterImpl();
        List<String> inputReport = List.of(
                "type,fruit,quantity",
                "b,apple"
        );
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(inputReport)
        );
        Assertions.assertTrue(exception.getMessage().contains("Invalid string format"));
    }

}

