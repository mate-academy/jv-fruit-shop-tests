package core.basesyntax.model;

import core.basesyntax.strategy.impl.BalanceOperationImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FruitTransactionTest {
    private static final String BANANA = "banana";
    private  static final int TEST_QUANTITY = 1;

    @Test
    void getOperation_correctOperation_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.BALANCE, BANANA, TEST_QUANTITY);
        assertEquals(Operation.BALANCE, fruitTransaction.getOperation());
    }

    @Test
    void getFruitName_correctFruitName_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.PURCHASE, BANANA, TEST_QUANTITY);
        assertEquals(BANANA, fruitTransaction.getFruitName());
    }

    @Test
    void getQuantity() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.PURCHASE, BANANA, TEST_QUANTITY);
        assertEquals(TEST_QUANTITY, fruitTransaction.getQuantity());
    }
}