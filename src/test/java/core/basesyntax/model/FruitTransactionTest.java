package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void testConstructorAndGetters() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 15);

        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        assertEquals("banana", transaction.getFruit());
        assertEquals(15, transaction.getQuantity());
    }

    @Test
    void testSetters() {
        FruitTransaction transaction = new FruitTransaction();

        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("apple");
        transaction.setQuantity(8);

        assertEquals(FruitTransaction.Operation.PURCHASE, transaction.getOperation());
        assertEquals("apple", transaction.getFruit());
        assertEquals(8, transaction.getQuantity());
    }

    @Test
    void testOperationFromCodeValid() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.fromCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.fromCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.fromCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.fromCode("r"));
    }

    @Test
    void testOperationFromCodeInvalid() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> FruitTransaction.Operation.fromCode("x"));

        assertTrue(exception.getMessage().contains("Unknown operation"));
    }
}
