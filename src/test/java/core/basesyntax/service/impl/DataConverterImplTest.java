package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_shouldConvertCorrectly() {
        List<String> input = Arrays.asList(
                "b,apple,100",
                "s,banana,50",
                "p,apple,20",
                "r,banana,10"
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(input);

        assertEquals(4, transactions.size(), "The number of transactions should be 4");

        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0)
                .getOperation(), "The first operation should be BALANCE");
        assertEquals("apple", transactions.get(0)
                .getFruit(), "The first fruit should be apple");
        assertEquals(100, transactions.get(0)
                .getQuantity(), "The first quantity should be 100");

        assertEquals(FruitTransaction.Operation.SUPPLY, transactions.get(1)
                .getOperation(), "The second operation should be SUPPLY");
        assertEquals("banana", transactions.get(1)
                .getFruit(), "The second fruit should be banana");
        assertEquals(50, transactions.get(1)
                .getQuantity(), "The second quantity should be 50");

        assertEquals(FruitTransaction.Operation.PURCHASE, transactions.get(2)
                .getOperation(), "The third operation should be PURCHASE");
        assertEquals("apple", transactions.get(2)
                .getFruit(), "The third fruit should be apple");
        assertEquals(20, transactions.get(2)
                .getQuantity(), "The third quantity should be 20");

        assertEquals(FruitTransaction.Operation.RETURN, transactions.get(3)
                .getOperation(), "The fourth operation should be RETURN");
        assertEquals("banana", transactions.get(3)
                .getFruit(), "The fourth fruit should be banana");
        assertEquals(10, transactions.get(3)
                .getQuantity(), "The fourth quantity should be 10");
    }

    @Test
    void convertToTransaction_shouldHandleEmptyInput() {
        List<String> input = Arrays.asList();

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(input);

        assertTrue(transactions.isEmpty(), "The transactions list should be empty");
    }

    @Test
    void convertToTransaction_shouldThrowExceptionForInvalidInput() {
        List<String> input = Arrays.asList(
                "invalid,input,line"
        );

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransaction(input);
        });

        String expectedMessage = "Unknown operation:";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage),
                "Exception message should contain 'Unknown operation:'");
    }
}
