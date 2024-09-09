package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validData_ok() {
        List<String> data = List.of("b,banana,20", "s,apple,100");
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(data);
        assertEquals(2, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("banana", transactions.get(0).getFruit());
        assertEquals(20, transactions.get(0).getQuantity());
    }

    @Test
    void convertToTransaction_invalidDataFormat_notOk() {
        List<String> data = List.of("b,banana");
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(data));
    }

    @Test
    void convertToTransaction_invalidQuantity_notOk() {
        List<String> data = List.of("b,banana,abc");
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(data));
    }
}
