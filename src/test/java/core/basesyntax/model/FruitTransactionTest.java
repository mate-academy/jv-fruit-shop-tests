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
    void constructor_ShouldCreateValidTransaction() {
        assertEquals(Operation.BALANCE, transaction.getOperation());
        assertEquals("apple", transaction.getFruitName());
        assertEquals(100, transaction.getQuantity());
    }

    @Test
    void setOperation_ShouldThrowExceptionForNullOperation() {
        assertThrows(IllegalArgumentException.class,
                () -> transaction.setOperation(null), "Operation can't be null.");
    }

    @Test
    void setFruitName_ShouldThrowExceptionForNullFruitName() {
        assertThrows(IllegalArgumentException.class,
                () -> transaction.setFruitName(null), "Fruit name can't be null.");
    }

    @Test
    void setFruitName_ShouldThrowExceptionForEmptyFruitName() {
        assertThrows(IllegalArgumentException.class,
                () -> transaction.setFruitName(""), "Fruit name can't be empty.");
    }

    @Test
    void setFruitName_ShouldAllowValidFruitName() {
        transaction.setFruitName("banana");

        assertEquals("banana", transaction.getFruitName());
    }

    @Test
    void setQuantity_ShouldThrowExceptionForNegativeQuantity() {
        assertThrows(IllegalArgumentException.class,
                () -> transaction.setQuantity(-10), "Quantity can't be negative.");
    }

    @Test
    void setQuantity_ShouldAllowPositiveQuantity() {
        transaction.setQuantity(50);

        assertEquals(50, transaction.getQuantity());
    }

    @Test
    void setQuantity_ShouldAllowZeroQuantity() {
        transaction.setQuantity(0);

        assertEquals(0, transaction.getQuantity());
    }
}
