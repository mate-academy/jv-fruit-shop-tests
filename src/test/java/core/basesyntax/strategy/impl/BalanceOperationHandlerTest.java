package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final String BANANA_FRUIT = "banana";
    private static OperationHandler balanceOperationHandler;

    @BeforeAll
    static void beforeAll() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    void handle_invalidQuantity_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> balanceOperationHandler.handle(new FruitTransaction(
                BALANCE, BANANA_FRUIT, -100)));
    }

    @Test
    void handle_validBalanceOperation_ok() {
        balanceOperationHandler.handle(
                new FruitTransaction(BALANCE, BANANA_FRUIT, 100));
        int expected = 100;
        int actual = Storage.FRUIT_STORAGE.get(BANANA_FRUIT);
        Assertions.assertEquals(expected, actual);
    }

    @AfterAll
    static void afterAll() {
        Storage.FRUIT_STORAGE.clear();
    }
}
