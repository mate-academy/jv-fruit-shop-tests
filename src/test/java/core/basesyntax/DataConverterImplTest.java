package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.DataConverterImpl;
import core.basesyntax.strategy.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private final DataConverterImpl dataConverter = new DataConverterImpl();

    @Test
    void convertToTransaction_validData_ok() {
        List<String> rawData = List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "b,banana,50",
                "p,banana,50"
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(rawData);

        assertNotNull(transactions);
        assertEquals(3, transactions.size());

        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(100, transactions.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(1).getOperation());
        assertEquals("banana", transactions.get(1).getFruit());
        assertEquals(50, transactions.get(1).getQuantity());

        assertEquals(FruitTransaction.Operation.PURCHASE, transactions.get(2).getOperation());
        assertEquals("banana", transactions.get(2).getFruit());
        assertEquals(50, transactions.get(2).getQuantity());
    }

    @Test
    void convertToTransaction_invalidBalance_throwsException() {
        List<String> rawData = List.of(
                "type,fruit,quantity",
                "BALANCE,apple,100",
                "PURCHASE,banana,50"
        );

        assertThrows(IllegalArgumentException.class, ()
                -> dataConverter.convertToTransaction(rawData));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
                -> dataConverter.convertToTransaction(rawData));

        String expectedMessage = "Error processing data at line ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage),
                "Expected exception message to contain: " + expectedMessage);
    }

    @Test
    void convertToTransaction_emptyData_returnsEmptyList() {
        List<String> rawData = List.of();
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(rawData);
        assertNotNull(transactions);
        assertTrue(transactions.isEmpty());
    }

    @Test
    void convertToTransaction_malformedData_throwsException() {
        List<String> rawData = List.of(
                "type,fruit,quantity",
                "b,apple",
                "p,,50"
        );

        assertThrows(IllegalArgumentException.class, ()
                -> dataConverter.convertToTransaction(rawData));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
                -> dataConverter.convertToTransaction(rawData));

        String expectedMessage = "Error processing data at line ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage),
                "Expected exception message to contain: " + expectedMessage);
    }

    @Test
    void convertToTransaction_invalidOperationCode_throwsException() {
        List<String> rawData = List.of(
                "type,fruit,quantity",
                "i,apple,100"
        );

        assertThrows(IllegalArgumentException.class, () -> dataConverter
                .convertToTransaction(rawData));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()
                -> dataConverter.convertToTransaction(rawData));

        String expectedMessage = "Error processing data at line ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage),
                "Expected exception message to contain: " + expectedMessage);
    }

    @Test
    void convertToTransaction_headerRowIgnored_ok() {
        List<String> rawData = List.of(
                "type,fruit,quantity",
                "s,apple,30"
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(rawData);

        assertEquals(1, transactions.size());
        FruitTransaction transaction = transactions.get(0);
        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
        assertEquals("apple", transaction.getFruit());
        assertEquals(30, transaction.getQuantity());
    }

    @Test
    void convertToTransaction_nonNumericQuantity_throwsException() {
        List<String> rawData = List.of(
                "type,fruit,quantity",
                "b,apple,abc"
        );

        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(rawData));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(rawData));

        String expectedMessage = "Invalid number format at line ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage),
                "Expected exception message to contain: " + expectedMessage);
    }
}

