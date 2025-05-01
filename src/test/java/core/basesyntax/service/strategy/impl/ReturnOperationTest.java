package core.basesyntax.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private final OperationHandler operationHandler = new ReturnOperation();

    @AfterEach
    void tearDown() {
        StorageImpl.fruitStorage.clear();
    }

    @Test
    void returnOperation_OK() {
        StorageImpl.fruitStorage.put("apple", 10);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 5);
        assertDoesNotThrow(() -> operationHandler.operation(fruitTransaction));
        assertEquals(15, StorageImpl.fruitStorage.get("apple"));
    }

    @Test
    void returnOperation_NegativeQuantity_NotOK() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", -5);
        assertThrows(RuntimeException.class, () -> operationHandler.operation(fruitTransaction));
    }

}
