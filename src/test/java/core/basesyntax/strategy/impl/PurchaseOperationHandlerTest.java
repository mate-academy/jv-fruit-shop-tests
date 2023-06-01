package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final FruitTransaction.Operation PURCHASE_OPERATION = PURCHASE;
    private static final String BANANA_FRUIT = "banana";
    private final OperationHandler purchaseOperationHandler;

    private PurchaseOperationHandlerTest() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test
    void handle_invalidQuantity_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.handle(new FruitTransaction(
                PURCHASE_OPERATION, BANANA_FRUIT, -100)));
    }

    @Test
    void handle_notEnoughQuantity_notOk() {
        Storage.FRUIT_STORAGE.put(BANANA_FRUIT,50);
        Assertions.assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.handle(new FruitTransaction(
                PURCHASE_OPERATION, BANANA_FRUIT, 150)));
    }

    @Test
    void handle_validPurchaseOperation_ok() {
        Storage.FRUIT_STORAGE.put(BANANA_FRUIT,150);
        purchaseOperationHandler.handle(
                new FruitTransaction(PURCHASE_OPERATION, BANANA_FRUIT, 125));
        int expected = 25;
        int actual = Storage.FRUIT_STORAGE.get(BANANA_FRUIT);
        Assertions.assertEquals(expected, actual);
    }

    @AfterAll
    static void afterAll() {
        Storage.FRUIT_STORAGE.clear();
    }
}
