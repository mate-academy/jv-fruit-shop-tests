package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private final DataConverterImpl dataConverter = new DataConverterImpl();

    @Test
    void convertToTransaction_validData_ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,100"
        );
        List<FruitTransaction> result = dataConverter.convertToTransaction(input);

        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(20, result.get(0).getQuantity());
    }

    @Test
    void convertToTransaction_emptyList_ok() {
        List<FruitTransaction> result = dataConverter.convertToTransaction(List.of("type,fruit,"
                + "quantity"));
        assertEquals(0, result.size());
    }
}
