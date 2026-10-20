package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private final DataConverter dataConverter = new DataConverterImpl();

    @Test
    void convertToTransaction_emptyList_ok() {
        List<String> input = List.of("type,fruit,quantity");
        List<FruitTransaction> result = dataConverter.convertToTransaction(input);
        assertEquals(0, result.size());
    }

    @Test
    void convertToTransaction_validData_ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,100",
                "s,apple,50"
        );

        List<FruitTransaction> result = dataConverter.convertToTransaction(input);

        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(100, result.get(0).getQuantity());
    }

    @Test
    void convertToTransaction_allOperations_ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,100",
                "s,banana,50",
                "p,banana,20",
                "r,banana,10"
        );

        List<FruitTransaction> result = dataConverter.convertToTransaction(input);

        assertEquals(4, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals(FruitTransaction.Operation.SUPPLY, result.get(1).getOperation());
        assertEquals(FruitTransaction.Operation.PURCHASE, result.get(2).getOperation());
        assertEquals(FruitTransaction.Operation.RETURN, result.get(3).getOperation());
    }
}

