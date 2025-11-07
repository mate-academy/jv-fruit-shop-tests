package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void testConstructorAndGetters() {
        FruitTransaction transaction =
                new FruitTransaction(Operation.BALANCE, "banana", 15);

        assertEquals(Operation.BALANCE, transaction.getOperation());
        assertEquals("banana", transaction.getFruit());
        assertEquals(15, transaction.getQuantity());
    }

    @Test
    void testSetters() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "banana", 0);

        transaction.setOperation(Operation.PURCHASE);
        transaction.setFruit("apple");
        transaction.setQuantity(8);

        assertEquals(Operation.PURCHASE, transaction.getOperation());
        assertEquals("apple", transaction.getFruit());
        assertEquals(8, transaction.getQuantity());
    }

    @Test
    void testOperationFromCodeValid() {
        assertEquals(Operation.BALANCE,
                Operation.fromCode("b"));
        assertEquals(Operation.SUPPLY,
                Operation.fromCode("s"));
        assertEquals(Operation.PURCHASE,
                Operation.fromCode("p"));
        assertEquals(Operation.RETURN,
                Operation.fromCode("r"));
    }

    @Test
    void testOperationFromCodeInvalid() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> Operation.fromCode("x"));

        assertTrue(exception.getMessage().contains("Unknown operation"));
    }
}
