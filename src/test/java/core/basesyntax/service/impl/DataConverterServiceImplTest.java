package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverterService;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DataConverterServiceImplTest {
    private final DataConverterService dataConverterService = new DataConverterServiceImpl();

    @Test
    public void testConvertToTransaction_withValidData_ok() {
        List<String> inputData = List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "p,banana,50"
        );

        List<FruitTransaction> transactions = dataConverterService.convertToTransaction(inputData);

        assertEquals(2, transactions.size(), "Should convert two transactions");

        FruitTransaction firstTransaction = transactions.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, firstTransaction.getOperation(),
                "First transaction should be BALANCE");
        assertEquals("apple", firstTransaction.getFruit(),
                "First transaction fruit should be apple");
        assertEquals(100, firstTransaction.getQuantity(),
                "First transaction quantity should be 100");

        FruitTransaction secondTransaction = transactions.get(1);
        assertEquals(FruitTransaction.Operation.PURCHASE, secondTransaction.getOperation(),
                "Second transaction should be PURCHASE");
        assertEquals("banana", secondTransaction.getFruit(),
                "Second transaction fruit should be banana");
        assertEquals(50, secondTransaction.getQuantity(),
                "Second transaction quantity should be 50");
    }

    @Test
    public void testConvertToTransaction_withInvalidRowFormat_notOk() {
        List<String> inputData = List.of(
                "type,fruit,quantity",
                "balance,apple"
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> dataConverterService.convertToTransaction(inputData),
                "Expected exception for invalid row format"
        );

        assertTrue(exception.getMessage().contains("Invalid data format"),
                "Exception message should indicate invalid data format");
    }

    @Test
    public void testConvertToTransaction_withInvalidOperation_notOk() {
        List<String> inputData = List.of(
                "type,fruit,quantity",
                "unknown,apple,100"
        );

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dataConverterService.convertToTransaction(inputData),
                "Expected exception for invalid operation"
        );

        assertTrue(exception.getMessage().contains("Error parsing line"),
                "Exception message should indicate error parsing line");
    }

    @Test
    public void testConvertToTransaction_withInvalidQuantity_notOk() {
        List<String> inputData = List.of(
                "type,fruit,quantity",
                "balance,apple,abc"
        );

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dataConverterService.convertToTransaction(inputData),
                "Expected exception for invalid quantity"
        );

        assertTrue(exception.getMessage().contains("Error parsing line"),
                "Exception message should indicate error parsing line");
    }
}
