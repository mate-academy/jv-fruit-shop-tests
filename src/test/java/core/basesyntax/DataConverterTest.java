package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.serviceimpl.DataConverterImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validData_success() {
        List<String> rawData = List.of("type,fruit,quantity", "b,banana,20", "p,apple,30");
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(rawData);
        assertEquals(2, transactions.size());

        assertEquals("banana", transactions.get(0).getFruit());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals(20, transactions.get(0).getQuantity());

        assertEquals("apple", transactions.get(1).getFruit());
        assertEquals(FruitTransaction.Operation.PURCHASE, transactions.get(1).getOperation());
        assertEquals(30, transactions.get(1).getQuantity());
    }

    @Test
    void convertToTransaction_invalidData_throwsException() {
        List<String> rawData = List.of("type,fruit,quantity", "x,grape,15");
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(rawData));
        assertEquals("No enum constant for code: x", thrown.getMessage());
    }

    @Test
    void convertToTransaction_negativeQuantity_throwsException() {
        List<String> rawData = List.of("type,fruit,quantity", "b,banana,-10");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(rawData));
        assertEquals("Quantity can't be negative: -10", thrown.getMessage());
    }
}
