package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private FruitTransaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new FruitTransaction(Operation.BALANCE, "apple", 100);
    }

    @Test
    void constructor_createValidTransaction_ok() {
        assertEquals(Operation.BALANCE, transaction.getOperation());
        assertEquals("apple", transaction.getFruitName());
        assertEquals(100, transaction.getQuantity());
    }

    @Test
    void setFruitName_allowValidFruitName_ok() {
        transaction.setFruitName("banana");

        assertEquals("banana", transaction.getFruitName());
    }

    @Test
    void setQuantity_allowPositiveQuantity_ok() {
        transaction.setQuantity(50);

        assertEquals(50, transaction.getQuantity());
    }

    @Test
    void setQuantity_allowZeroQuantity_ok() {
        transaction.setQuantity(0);

        assertEquals(0, transaction.getQuantity());
    }

    @Test
    void setQuantity_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> transaction.setQuantity(-10), "Quantity can't be negative.");
    }

    @Test
    void setOperation_nullOperation_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> transaction.setOperation(null), "Operation can't be null.");
    }

    @Test
    void setFruitName_nullFruitName_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> transaction.setFruitName(null), "Fruit name can't be null.");
    }

    @Test
    void setFruitName_emptyFruitName_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> transaction.setFruitName(""), "Fruit name can't be empty.");
    }
}
