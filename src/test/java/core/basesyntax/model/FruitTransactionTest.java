package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void fruitTransaction_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class, () ->
                new FruitTransaction(Operation.BALANCE, "apple", -20));
    }

    @Test
    void fruitTransaction_nullFruit_notOk() {
        assertThrows(IllegalArgumentException.class, () ->
                new FruitTransaction(Operation.BALANCE, null, 10));
    }

    @Test
    void fruitTransaction_blankFruit_notOk() {
        assertThrows(IllegalArgumentException.class, () ->
                new FruitTransaction(Operation.BALANCE, "", 10));
    }

    @Test
    void fruitTransaction_existingFruits_ok() {
        FruitTransaction fruitTransaction1 = new FruitTransaction(Operation.BALANCE,
                "apple", 20);
        assertEquals(fruitTransaction1.getName(), "apple");
        FruitTransaction fruitTransaction2 = new FruitTransaction(Operation.PURCHASE,
                "banana", 25);
        assertEquals(fruitTransaction2.getName(), "banana");
        FruitTransaction fruitTransaction3 = new FruitTransaction(Operation.RETURN,
                "cherry", 30);
        assertEquals(fruitTransaction3.getName(), "cherry");
        FruitTransaction fruitTransaction4 = new FruitTransaction(Operation.SUPPLY,
                "strawberry", 35);
        assertEquals(fruitTransaction4.getName(), "strawberry");
        assertEquals(fruitTransaction1.getQuantity(), 20);
        assertEquals(fruitTransaction2.getQuantity(), 25);
        assertEquals(fruitTransaction3.getQuantity(), 30);
        assertEquals(fruitTransaction4.getQuantity(), 35);
        assertEquals(fruitTransaction1.getOperation(), Operation.BALANCE);
        assertEquals(fruitTransaction2.getOperation(), Operation.PURCHASE);
        assertEquals(fruitTransaction3.getOperation(), Operation.RETURN);
        assertEquals(fruitTransaction4.getOperation(), Operation.SUPPLY);
    }
}
