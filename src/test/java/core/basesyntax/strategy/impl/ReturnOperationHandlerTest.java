package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private final ReturnOperationHandler returnOperationHandler
            = new ReturnOperationHandler();

    @Test
    void testApplyTransactionToStorage_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("Fruit");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(-1);
        assertThrows(RuntimeException.class,
                () -> returnOperationHandler.applyTransactionToStorage(fruitTransaction));
    }
}

