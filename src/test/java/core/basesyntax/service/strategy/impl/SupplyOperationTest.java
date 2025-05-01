package core.basesyntax.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private final OperationHandler operationHandler = new SupplyOperation();

    @AfterEach
    void tearDown() {
        StorageImpl.fruitStorage.clear();
    }

    @Test
    void supplyOperation_OK() {
        StorageImpl.fruitStorage.put("orange", 10);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange", 5);
        assertDoesNotThrow(() -> operationHandler.operation(fruitTransaction));
        assertEquals(15, StorageImpl.fruitStorage.get("orange"));
    }

    @Test
    void supplyOperation_NegativeQuantity_NotOK() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange", -5);
        assertThrows(RuntimeException.class, () -> operationHandler.operation(fruitTransaction));
    }

}
