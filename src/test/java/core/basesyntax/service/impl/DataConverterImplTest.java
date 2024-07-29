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
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int QUANTITY_100 = 100;
    private static final int QUANTITY_50 = 50;
    private static final int QUANTITY_20 = 20;
    private static final int QUANTITY_10 = 10;
    private static final int TRANSACTION_SIZE = 4;
    private static final String INVALID_INPUT_LINE = "invalid,input,line";
    private static final String UNKNOWN_OPERATION_MESSAGE = "Unknown operation:";
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_shouldConvertCorrectly() {
        List<String> input = Arrays.asList(
                "b," + APPLE + "," + QUANTITY_100,
                "s," + BANANA + "," + QUANTITY_50,
                "p," + APPLE + "," + QUANTITY_20,
                "r," + BANANA + "," + QUANTITY_10
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(input);

        assertEquals(TRANSACTION_SIZE, transactions.size(),
                "The number of transactions should be 4");

        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0)
                .getOperation(), "The first operation should be BALANCE");
        assertEquals(APPLE, transactions.get(0)
                .getFruit(), "The first fruit should be apple");
        assertEquals(QUANTITY_100, transactions.get(0)
                .getQuantity(), "The first quantity should be 100");

        assertEquals(FruitTransaction.Operation.SUPPLY, transactions.get(1)
                .getOperation(), "The second operation should be SUPPLY");
        assertEquals(BANANA, transactions.get(1)
                .getFruit(), "The second fruit should be banana");
        assertEquals(QUANTITY_50, transactions.get(1)
                .getQuantity(), "The second quantity should be 50");

        assertEquals(FruitTransaction.Operation.PURCHASE, transactions.get(2)
                .getOperation(), "The third operation should be PURCHASE");
        assertEquals(APPLE, transactions.get(2)
                .getFruit(), "The third fruit should be apple");
        assertEquals(QUANTITY_20, transactions.get(2)
                .getQuantity(), "The third quantity should be 20");

        assertEquals(FruitTransaction.Operation.RETURN, transactions.get(3)
                .getOperation(), "The fourth operation should be RETURN");
        assertEquals(BANANA, transactions.get(3)
                .getFruit(), "The fourth fruit should be banana");
        assertEquals(QUANTITY_10, transactions.get(3)
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
                INVALID_INPUT_LINE
        );

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransaction(input);
        });

        String expectedMessage = UNKNOWN_OPERATION_MESSAGE;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage),
                "Exception message should contain 'Unknown operation:'");
    }
}
