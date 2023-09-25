package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10);
    }

    @Test
    void testGetOperation() {
        assertEquals(FruitTransaction.Operation.SUPPLY, fruitTransaction.getOperation());
    }

    @Test
    void testSetOperation() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        assertEquals(FruitTransaction.Operation.PURCHASE,fruitTransaction.getOperation());
    }

    @Test
    void testGetFruit() {
        assertEquals("apple", fruitTransaction.getFruit());
    }

    @Test
    void testSetFruit() {
        fruitTransaction.setFruit("banana");
        assertEquals("banana", fruitTransaction.getFruit());
    }

    @Test
    void testGetQuantity() {
        assertEquals(10, fruitTransaction.getQuantity());
    }

    @Test
    void testSetQuantity() {
        fruitTransaction.setQuantity(5);
        assertEquals(5, fruitTransaction.getQuantity());
    }

    @Test
    void testEnumValues() {
        assertEquals(4, FruitTransaction.Operation.values().length);
    }

    @Test
    void testGetByCode() {
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.getByCode("s"));
    }

    @Test
    void testGetByUnlnownCode() {
        assertThrows(RuntimeException.class, () -> FruitTransaction.getByCode("x"));
    }
}
