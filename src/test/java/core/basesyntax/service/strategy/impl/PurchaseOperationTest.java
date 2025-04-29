package core.basesyntax.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private final OperationHandler operationHandler = new PurchaseOperation();

    @AfterEach
    void tearDown() {
        StorageImpl.fruitStorage.clear();
    }

    @Test
    void purchaseOperationLessThanZero_NotOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,"orange",7);
        assertThrows(RuntimeException.class,
                () -> operationHandler.operation(fruitTransaction));
    }

    @Test
    void purchaseOperation_Ok() {
        StorageImpl.fruitStorage.put("orange",10);
        FruitTransaction fruitTransaction1 =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,"orange",7);
        assertDoesNotThrow(() -> operationHandler.operation(fruitTransaction1));

    }
}
