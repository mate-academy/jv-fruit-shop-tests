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
    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Parser();
    }

    @Test
    void testParseValidInput() {
        List<String> inputReport = Arrays.asList(
                "type,fruit,quantity",
                "b,apple,100",
                "s,banana,50",
                "p,apple,20",
                "r,banana,10"
        );

        List<FruitTransaction> transactions = parser.parse(inputReport);

        assertEquals(4, transactions.size(), "The number of transactions should be 4");

        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0)
                .getOperation(), "The first operation should be BALANCE");
        assertEquals("apple", transactions.get(0)
                .getFruit(), "The first fruit should be apple");
        assertEquals(100, transactions.get(0).getQuantity(), "The first quantity should be 100");

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
    void testParseIgnoresHeader() {
        List<String> inputReport = Arrays.asList(
                "type,fruit,quantity",
                "b,apple,100"
        );

        List<FruitTransaction> transactions = parser.parse(inputReport);

        assertEquals(1, transactions
                .size(), "The number of transactions should be 1");
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0)
                .getOperation(), "The operation should be BALANCE");
        assertEquals("apple", transactions.get(0)
                .getFruit(), "The fruit should be apple");
        assertEquals(100, transactions.get(0)
                .getQuantity(), "The quantity should be 100");
    }

    @Test
    void testParseInvalidOperation() {
        List<String> inputReport = Arrays.asList(
                "type,fruit,quantity",
                "x,apple,100"
        );

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(inputReport);
        });

        String expectedMessage = "Unknown operation: x";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage),
                "Exception message should contain 'Unknown operation: x'");
    }

    @Test
    void testParseInvalidQuantity() {
        List<String> inputReport = Arrays.asList(
                "type,fruit,quantity",
                "b,apple,invalid_quantity"
        );

        Exception exception = assertThrows(RuntimeException.class, () -> {
            parser.parse(inputReport);
        });

        String expectedMessage = "Invalid quantity: invalid_quantity";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage),
                "Exception message should contain 'Invalid quantity: invalid_quantity'");
    }

    @Test
    void testParseNegativeQuantity() {
        List<String> inputReport = Arrays.asList(
                "type,fruit,quantity",
                "b,apple,-100"
        );

        Exception exception = assertThrows(RuntimeException.class, () -> {
            parser.parse(inputReport);
        });

        String expectedMessage = "Quantity cannot be negative: -100";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage),
                "Exception message should contain 'Quantity cannot be negative: -100'");
    }
}
