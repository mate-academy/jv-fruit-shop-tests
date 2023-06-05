package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationsStrategy;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static final String BANANA = "banana";
    private static OperationsStrategy purchaseOperation;

    @BeforeAll
    static void beforeAll() {
        purchaseOperation = new PurchaseOperation();
    }

    @AfterAll
    static void afterAll() {
        Storage.storage.clear();
    }

    @Test
    void handle_purchaseOperation_ok() {
        Storage.storage.put(BANANA, 105);
        FruitTransaction fruitTransaction = new FruitTransaction(
                PURCHASE, BANANA, 85);
        purchaseOperation.handle(fruitTransaction);
        Integer expected = 20;
        Integer actual = Storage.storage.get(fruitTransaction.getFruit());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void handle_invalidQuantity_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                PURCHASE, BANANA, 120);
        Assertions.assertThrows(RuntimeException.class, () ->
                purchaseOperation.handle(fruitTransaction));
    }
}
