package core.basesyntax.transactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void setQuantity_negativeQuantity_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        assertThrows(IllegalArgumentException.class, () ->
                fruitTransaction.setQuantity(-100));
    }

    @Test
    void setFruit_nullFruit_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        assertThrows(IllegalArgumentException.class, () ->
                fruitTransaction.setFruit(null));
    }

    @Test
    void setFruit_emptyFruit_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        assertThrows(IllegalArgumentException.class, () ->
                fruitTransaction.setFruit(""));
    }

    @Test
    void setFruit_fruitWithSpaces_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        assertThrows(IllegalArgumentException.class, () ->
                fruitTransaction.setFruit(" "));
    }

    @Test
    void setOperation_nullOperation_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        assertThrows(IllegalArgumentException.class, () ->
                fruitTransaction.setOperation(null));
    }

    @Test
    void setFruit_validFruit_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        assertEquals("banana", fruitTransaction.getFruit());
    }

    @Test
    void setQuantity_validQuantity_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(100);
        assertEquals(100, fruitTransaction.getQuantity());
    }

    @Test
    void setOperation_validOperation_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        assertEquals(FruitTransaction.Operation.BALANCE, fruitTransaction.getOperation());
    }
}
