package core.basesyntax;

import core.basesyntax.impl.DataConverterImpl;
import core.basesyntax.service.DataConverter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
\
import static org.junit.jupiter.api.Assertions.assertEquals;

class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_ValidData_ReturnsCorrectTransactions() {
        List<String> csvData = List.of(
                "operation,fruit,quantity",
                "b,banana,20",
                "s,apple,10"
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(csvData);

        assertEquals(2, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("banana", transactions.get(0).getFruit());
        assertEquals(20, transactions.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY, transactions.get(1).getOperation());
        assertEquals("apple", transactions.get(1).getFruit());
        assertEquals(10, transactions.get(1).getQuantity());
    }

    @Test
    void convertToTransaction_EmptyLines_IgnoresThem() {
        List<String> csvData = List.of(
                "operation,fruit,quantity",
                " ",
                "b,banana,20",
                "",
                "s,apple,10"
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(csvData);
        assertEquals(2, transactions.size());
    }

    @Test
    void convertToTransaction_InvalidFormat_ThrowsException() {
        List<String> csvData = List.of(
                "operation,fruit,quantity",
                "b,banana",
                "s,apple,10"
        );

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(csvData)
        );

        assertTrue(exception.getMessage().contains("Invalid CSV format"));
    }

    @Test
    void convertToTransaction_InvalidNumberFormat_ThrowsException() {
        List<String> csvData = List.of(
                "operation,fruit,quantity",
                "b,banana,abc",
                "s,apple,10"
        );

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(csvData)
        );

        assertTrue(exception.getMessage().contains("Invalid number at line"));
    }
}
