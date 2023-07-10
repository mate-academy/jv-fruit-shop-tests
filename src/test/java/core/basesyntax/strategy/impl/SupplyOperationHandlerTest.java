package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private final SupplyOperationHandler supplyOperationHandler
            = new SupplyOperationHandler();

    @Test
    void testApplyTransactionToStorage_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("Fruit");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(-1);
        assertThrows(RuntimeException.class,
                () -> supplyOperationHandler.applyTransactionToStorage(fruitTransaction));
    }
}

