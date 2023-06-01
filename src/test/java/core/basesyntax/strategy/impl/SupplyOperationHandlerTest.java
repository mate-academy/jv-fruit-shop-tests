package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static final FruitTransaction.Operation SUPPLY_OPERATION = SUPPLY;
    private static final String BANANA_FRUIT = "banana";
    private final OperationHandler supplyOperationHandler;

    private SupplyOperationHandlerTest() {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @Test
    void handle_invalidQuantity_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> supplyOperationHandler.handle(new FruitTransaction(
                SUPPLY_OPERATION, BANANA_FRUIT, -100)));
    }

    @Test
    void handle_validBalanceOperation_ok() {
        Storage.FRUIT_STORAGE.put(BANANA_FRUIT, 100);
        supplyOperationHandler.handle(
                new FruitTransaction(SUPPLY_OPERATION, BANANA_FRUIT, 100));
        int expected = 200;
        int actual = Storage.FRUIT_STORAGE.get(BANANA_FRUIT);
        Assertions.assertEquals(expected, actual);
    }

    @AfterAll
    static void afterAll() {
        Storage.FRUIT_STORAGE.clear();
    }
}
