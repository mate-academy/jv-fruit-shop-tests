package core.basesyntax.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private final OperationHandler operationHandler = new BalanceOperation();

    @AfterEach
    void tearDown() {
        StorageImpl.fruitStorage.clear();
    }

    @Test
    void balanceOperation_Ok() {
        StorageImpl.fruitStorage.put("orange", 10);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 20);
        assertDoesNotThrow(() -> operationHandler.operation(fruitTransaction));
        assertEquals(20, StorageImpl.fruitStorage.get("orange"));
    }

    @Test
    void balanceOperation_NegativeBalance_NotOK() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", -5);
        assertThrows(RuntimeException.class, () -> operationHandler.operation(fruitTransaction));
    }

    @Test
    void balanceOperation_ZeroBalance_OK() {
        StorageImpl.fruitStorage.put("orange", 10);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 0);
        assertDoesNotThrow(() -> operationHandler.operation(fruitTransaction));
        assertEquals(0, StorageImpl.fruitStorage.get("orange"));
    }
}
