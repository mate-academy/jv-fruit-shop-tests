package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterTest {
    private static final String BANANA = "banana";
    private static final String BALANCE_RECORD = "b,banana,20";
    private static final String SUPPLY_RECORD = "s,apple,15";

    private DataConverterImpl converter;

    @BeforeEach
    void setUp() {
        converter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validBalanceRecord_shouldReturnTransaction() {
        List<String> input = List.of(BALANCE_RECORD);

        List<FruitTransaction> result = converter.convertToTransaction(input);

        assertEquals(1, result.size());

        FruitTransaction transaction = result.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        assertEquals(BANANA, transaction.getFruit());
        assertEquals(20, transaction.getQuantity());
    }

    @Test
    void convertToTransaction_validRecords_shouldReturnAllTransactions() {
        List<String> input = List.of(BALANCE_RECORD, SUPPLY_RECORD);

        List<FruitTransaction> result = converter.convertToTransaction(input);

        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.SUPPLY, result.get(1).getOperation());
        assertEquals("apple", result.get(1).getFruit());
        assertEquals(15, result.get(1).getQuantity());
    }
}
