package core.basesyntax.service.impl;

import core.basesyntax.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {

    private final DataConverterImpl dataConverter = new DataConverterImpl();

    @Test
    void convertToTransaction_validData_ok() {
        List<String> inputData = Arrays.asList("type,fruit,quantity", "b,banana,20", "s,apple,50");
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputData);

        Assertions.assertEquals(2, transactions.size(),
                "Wrong transaction list size.");
        Assertions.assertEquals("banana", transactions.get(0).getFruit(),
                "Wrong first transaction fruit.");
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE,
                transactions.get(0).getOperation());
    }

    @Test
    void convertToTransaction_invalidQuantity_exceptionThrown() {
        List<String> inputData = Arrays.asList("b,banana,abc");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(inputData);
        }, "Expected IllegalArgumentException for invalid quantity.");
    }
}
