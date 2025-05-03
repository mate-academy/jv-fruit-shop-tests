package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void constructorTest() {
        FruitTransaction transaction = new FruitTransaction("b", "apple", "20");

        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getByCode());
        assertEquals("apple", transaction.getFruit());
        assertEquals(20, transaction.getQuantity());
    }

    @Test
    void getSetFruitTest() {
        FruitTransaction transaction = new FruitTransaction("b", "apple", "20");
        transaction.setFruit("banana");

        assertEquals("banana", transaction.getFruit());
    }

    @Test
    void getSetQuantityTest() {
        FruitTransaction transaction = new FruitTransaction("b", "apple", "20");
        transaction.setQuantity(30);

        assertEquals(30, transaction.getQuantity());
    }

    @Test
    void equalsTest() {
        FruitTransaction transaction1 = new FruitTransaction("b", "apple", "20");
        FruitTransaction transaction2 = new FruitTransaction("b", "apple", "20");
        FruitTransaction transaction3 = new FruitTransaction("b", "banana", "20");

        assertTrue(transaction1.equals(transaction2));
        assertFalse(transaction1.equals(transaction3));
    }

    @Test
    void getByCodeTest() {
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction
                .Operation.getByCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction
                .Operation.getByCode("s"));
        assertThrows(IllegalArgumentException.class, () -> FruitTransaction
                .Operation.getByCode("x"));
    }
}
