package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationsStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static final String FRUIT_NAME = "banana";
    private static final int BALANCE_FRUIT_QUANTITY = 105;
    private static final int FRUIT_QUANTITY = 85;
    private static final int INVALID_FRUIT_QUANTITY = 120;
    private static OperationsStrategy purchaseOperation;

    @BeforeAll
    static void beforeAll() {
        purchaseOperation = new PurchaseOperation();
    }

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
        Storage.storage.put(FRUIT_NAME, BALANCE_FRUIT_QUANTITY);
    }

    @Test
    void handle_purchaseOperation_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, FRUIT_NAME, FRUIT_QUANTITY);
        purchaseOperation.handle(fruitTransaction);
        Integer expected = BALANCE_FRUIT_QUANTITY - FRUIT_QUANTITY;
        Integer actual = Storage.storage.get(fruitTransaction.getFruit());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void handle_invalidQuantity_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, FRUIT_NAME, INVALID_FRUIT_QUANTITY);
        Assertions.assertThrows(RuntimeException.class, () ->
                purchaseOperation.handle(fruitTransaction));
    }
}
