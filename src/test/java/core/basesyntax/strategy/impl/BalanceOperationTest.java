package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private BalanceOperation balanceOperation;

    @BeforeEach
    void initializeService() {
        balanceOperation = new BalanceOperation();
        Storage.cleanStorage();
    }

    @Test
    void doTransaction_acceptableAmount_ok() {
        balanceOperation.doTransaction("banana", 400);
        int expected = 400;
        int actual = Storage.getFruitQuantity("banana");
        assertEquals(expected, actual);
    }

    @Test
    void doTransaction_negativeAmount_ok() {
        balanceOperation.doTransaction("banana", -400);
        int expected = 0;
        int actual = Storage.getFruitQuantity("banana");
        assertEquals(expected, actual);
    }

}
