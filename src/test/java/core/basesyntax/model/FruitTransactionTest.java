package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    @Test
    public void testOperationGetOperation_invalidLetter_notOk() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FruitTransaction.Operation.getOperation("x"),
                "Expected exception for invalid operation letter"
        );

        assertTrue(exception.getMessage().contains("Operation not found by code: x"),
                "Exception message should mention missing operation for letter 'x'");
    }

    @Test
    public void testToString_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 50);

        String result = transaction.toString();

        String expected = "FruitTransaction{operation=PURCHASE, fruit='apple', quantity=50}";
        assertEquals(expected, result, "toString output should match expected format");
    }
}
