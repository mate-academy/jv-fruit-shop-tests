package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverterService;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private final DataConverterService dataConverter = new DataConverterImpl();

    @Test
    void convertToTransaction_validData_ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "balance,apple,100",
                "supply,banana,50"
        );
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(input);
        assertEquals(2, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(100, transactions.get(0).getQuantity());
    }

    @Test
    void convertToTransaction_invalidFormat_exceptionThrown() {
        List<String> input = List.of("invalid,format");
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(input));
    }
}
