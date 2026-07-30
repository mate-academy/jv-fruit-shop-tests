package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverterImpl dataConverter = new DataConverterImpl();

    @Test
    void convertToTransaction_validList_ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "s,banana,50"
        );
        List<FruitTransaction> actual = dataConverter.convertToTransaction(input);
        Assertions.assertNotNull(actual, "List should be not null");
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE, actual.get(0).getOperation());
        Assertions.assertEquals("apple", actual.get(0).getFruit());
        Assertions.assertEquals(100, actual.get(0).getQuantity());

        Assertions.assertEquals(FruitTransaction.Operation.SUPPLY, actual.get(1).getOperation());
        Assertions.assertEquals("banana", actual.get(1).getFruit());
        Assertions.assertEquals(50, actual.get(1).getQuantity());
    }

    @Test
    void convertToTransaction_invalidListOperation_notOk() {
        List<String> invalidInput = List.of(
                "type,fruit,quantity",
                "v,apple,100"
        );
        Assertions.assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransaction(invalidInput);
        },"List operation should be correct");
    }
}
