package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";

    @Test
    void constructor_validData_ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, BANANA, 20);
        assertEquals(Operation.BALANCE, transaction.getOperation());
        assertEquals("banana", transaction.getFruit());
        assertEquals(20, transaction.getQuantity());
    }

    @Test
    void constructor_invalidQuantity_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(Operation.SUPPLY, APPLE, -10));
    }

    @Test
    void setQuantity_validQuantity_ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, BANANA, 20);
        transaction.setQuantity(30);
        assertEquals(30, transaction.getQuantity());
    }
}
