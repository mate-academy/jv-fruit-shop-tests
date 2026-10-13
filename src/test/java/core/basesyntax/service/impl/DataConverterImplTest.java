package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validLines_ok() {
        List<String> data = List.of("type,fruit,quantity", "b,banana,20", "s,apple,10");
        List<FruitTransaction> fruitTransactions = dataConverter.convertToTransaction(data);
        assertEquals(2, fruitTransactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, fruitTransactions.get(0).getOperation());
        assertEquals("banana", fruitTransactions.get(0).getFruit());
        assertEquals(20, fruitTransactions.get(0).getQuantity());
    }

    @Test
    void convertToTransaction_onlyHeader_ok() {
        List<String> data = List.of("type,fruit,quantity");
        List<FruitTransaction> fruitTransactions = dataConverter.convertToTransaction(data);
        assertTrue(fruitTransactions.isEmpty());
    }

    @Test
    void convertToTransaction_invalidColumnsCount_notOk() {
        List<String> data = List.of("type,fruit,quantity", "b,banana");
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(data));
    }

    @Test
    void convertToTransaction_quantityNotNumber_notOk() {
        List<String> data = List.of("type,fruit,quantity", "b,banana,abs");
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(data));
    }

    @Test
    void convertToTransaction_negativeQuantity_notOk() {
        List<String> data = List.of("type,fruit,quantity", "b,banana,-5");
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(data));
    }

    @Test
    void convertToTransaction_unknownOperation_notOk() {
        List<String> data = List.of("type,fruit,quantity", "x,banana,5");
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(data));
    }
}
