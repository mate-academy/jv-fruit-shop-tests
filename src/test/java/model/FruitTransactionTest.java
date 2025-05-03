package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private final FruitTransaction fruitTransaction
            = new FruitTransaction(Operation.BALANCE, "banana", 10);

    @Test
    void getQuantity_ok() {
        Assertions.assertEquals(10, fruitTransaction.getQuantity());
    }

    @Test
    void getFruit_ok() {
        Assertions.assertEquals("banana", fruitTransaction.getFruit());
    }

    @Test
    void getOperation_ok() {
        Assertions.assertEquals(Operation.BALANCE, fruitTransaction.getOperation());
    }
}
