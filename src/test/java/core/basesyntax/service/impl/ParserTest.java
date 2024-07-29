package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String INVALID_OPERATION = "x";
    private static final String INVALID_QUANTITY = "invalid_quantity";
    private static final int QUANTITY_100 = 100;
    private static final int QUANTITY_50 = 50;
    private static final int QUANTITY_20 = 20;
    private static final int QUANTITY_10 = 10;
    private static final int NEGATIVE_QUANTITY = -100;
    private static final int EXPECTED_TRANSACTIONS_SIZE = 4;
    private static final int EXPECTED_SINGLE_TRANSACTION_SIZE = 1;
    private static final String UNKNOWN_OPERATION_MESSAGE = "Unknown operation: "
            + INVALID_OPERATION;
    private static final String INVALID_QUANTITY_MESSAGE = "Invalid quantity: "
            + INVALID_QUANTITY;
    private static final String NEGATIVE_QUANTITY_MESSAGE = "Quantity cannot be negative: "
            + NEGATIVE_QUANTITY;
    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Parser();
    }

    @Test
    void testParseValidInput() {
        List<String> inputReport = Arrays.asList(
                HEADER,
                "b," + APPLE + "," + QUANTITY_100,
                "s," + BANANA + "," + QUANTITY_50,
                "p," + APPLE + "," + QUANTITY_20,
                "r," + BANANA + "," + QUANTITY_10
        );

        List<FruitTransaction> transactions = parser.parse(inputReport);

        assertEquals(EXPECTED_TRANSACTIONS_SIZE, transactions.size(),
                "The number of transactions should be " + EXPECTED_TRANSACTIONS_SIZE);

        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation(),
                "The first operation should be BALANCE");
        assertEquals(APPLE, transactions.get(0).getFruit(),
                "The first fruit should be " + APPLE);
        assertEquals(QUANTITY_100, transactions.get(0).getQuantity(),
                "The first quantity should be " + QUANTITY_100);

        assertEquals(FruitTransaction.Operation.SUPPLY, transactions.get(1).getOperation(),
                "The second operation should be SUPPLY");
        assertEquals(BANANA, transactions.get(1).getFruit(),
                "The second fruit should be " + BANANA);
        assertEquals(QUANTITY_50, transactions.get(1).getQuantity(),
                "The second quantity should be " + QUANTITY_50);

        assertEquals(FruitTransaction.Operation.PURCHASE, transactions.get(2).getOperation(),
                "The third operation should be PURCHASE");
        assertEquals(APPLE, transactions.get(2).getFruit(),
                "The third fruit should be " + APPLE);
        assertEquals(QUANTITY_20, transactions.get(2).getQuantity(),
                "The third quantity should be " + QUANTITY_20);

        assertEquals(FruitTransaction.Operation.RETURN, transactions.get(3).getOperation(),
                "The fourth operation should be RETURN");
        assertEquals(BANANA, transactions.get(3).getFruit(),
                "The fourth fruit should be " + BANANA);
        assertEquals(QUANTITY_10, transactions.get(3).getQuantity(),
                "The fourth quantity should be " + QUANTITY_10);
    }

    @Test
    void testParseIgnoresHeader() {
        List<String> inputReport = Arrays.asList(
                HEADER,
                "b," + APPLE + "," + QUANTITY_100
        );

        List<FruitTransaction> transactions = parser.parse(inputReport);

        assertEquals(EXPECTED_SINGLE_TRANSACTION_SIZE, transactions.size(),
                "The number of transactions should be " + EXPECTED_SINGLE_TRANSACTION_SIZE);
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation(),
                "The operation should be BALANCE");
        assertEquals(APPLE, transactions.get(0).getFruit(),
                "The fruit should be " + APPLE);
        assertEquals(QUANTITY_100, transactions.get(0).getQuantity(),
                "The quantity should be " + QUANTITY_100);
    }

    @Test
    void testParseInvalidOperation() {
        List<String> inputReport = Arrays.asList(
                HEADER,
                INVALID_OPERATION + "," + APPLE + "," + QUANTITY_100
        );

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(inputReport);
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(UNKNOWN_OPERATION_MESSAGE),
                "Exception message should contain '" + UNKNOWN_OPERATION_MESSAGE + "'");
    }

    @Test
    void testParseInvalidQuantity() {
        List<String> inputReport = Arrays.asList(
                HEADER,
                "b," + APPLE + "," + INVALID_QUANTITY
        );

        Exception exception = assertThrows(RuntimeException.class, () -> {
            parser.parse(inputReport);
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(INVALID_QUANTITY_MESSAGE),
                "Exception message should contain '" + INVALID_QUANTITY_MESSAGE + "'");
    }

    @Test
    void testParseNegativeQuantity() {
        List<String> inputReport = Arrays.asList(
                HEADER,
                "b," + APPLE + "," + NEGATIVE_QUANTITY
        );

        Exception exception = assertThrows(RuntimeException.class, () -> {
            parser.parse(inputReport);
        });

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(NEGATIVE_QUANTITY_MESSAGE),
                "Exception message should contain '" + NEGATIVE_QUANTITY_MESSAGE + "'");
    }
}
