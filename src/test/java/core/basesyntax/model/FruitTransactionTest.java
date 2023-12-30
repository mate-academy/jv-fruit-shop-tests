package core.basesyntax.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(20);
        fruitTransaction.setOperation(Operation.SUPPLY);
    }

    @Test
    void operationSupplyIsNotExist_ok() {
        Assertions.assertNotEquals(fruitTransaction.getOperation(), Operation.BALANCE);
    }

    @Test
    void operationBalanceExist_ok() {
        fruitTransaction.setOperation(Operation.BALANCE);
        Assertions.assertEquals(fruitTransaction.getOperation(), Operation.BALANCE);
    }

    @Test
    void operationPurchaseExist_ok() {
        fruitTransaction.setOperation(Operation.PURCHASE);
        Assertions.assertEquals(fruitTransaction.getOperation(), Operation.PURCHASE);
    }

    @Test
    void operationReturnExist_ok() {
        fruitTransaction.setOperation(Operation.RETURN);
        Assertions.assertEquals(fruitTransaction.getOperation(), Operation.RETURN);
    }

    @Test
    void notSameQuantity_notOk() {
        fruitTransaction.setQuantity(50);
        Assertions.assertNotEquals(fruitTransaction.getQuantity(), 40);
    }

    @Test
    void sameQuantity_ok() {
        Assertions.assertEquals(fruitTransaction.getQuantity(), 20);
    }

    @Test
    void sameFruit_ok() {
        Assertions.assertEquals(fruitTransaction.getFruit(), "apple");
    }

    @Test
    void notSameFruit_notOk() {
        fruitTransaction.setFruit("banana");
        Assertions.assertNotEquals(fruitTransaction.getFruit(), "apple");
    }

    @Test
    void quantityIsNull_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            fruitTransaction.setQuantity(-100);
        });
    }
}
