package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.DataConverter;
import core.basesyntax.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    @Test
    void convert_validData_returnsTransactions() {
        DataConverter converter = new DataConverterImpl();
        List<String> data = List.of("b,apple,50", "s,banana,30");
        List<FruitTransaction> result = converter.convertToTransaction(data);
        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("apple", result.get(0).getFruit());
        assertEquals(50, result.get(0).getQuantity());
    }

}
