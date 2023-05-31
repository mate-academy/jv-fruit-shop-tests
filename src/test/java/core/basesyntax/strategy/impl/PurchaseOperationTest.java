package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationsStrategy;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private OperationsStrategy purchaseOperation;

    @BeforeEach
    void setUp() {
        purchaseOperation = new PurchaseOperation();
    }

    @AfterAll
    static void afterAll() {
        Storage.storage.clear();
    }

    @Test
    void handle_purchaseOperation_ok() {
        Storage.storage.put("banana", 105);
        FruitTransaction fruitTransaction = new FruitTransaction(
                PURCHASE, "banana", 85);
        purchaseOperation.handle(fruitTransaction);
        Integer expected = 20;
        Integer actual = Storage.storage.get(fruitTransaction.getFruit());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void handle_invalidQuantity_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                PURCHASE, "banana", 120);
        Assertions.assertThrows(RuntimeException.class, () ->
                purchaseOperation.handle(fruitTransaction));
    }
}
