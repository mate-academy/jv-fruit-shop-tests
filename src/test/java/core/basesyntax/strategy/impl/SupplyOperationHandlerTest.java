package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    @Test
    void testApplyTransactionToStorage_NotOk() {
        final SupplyOperationHandler supplyOperationHandler = new SupplyOperationHandler();
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("Fruit");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(-1);
        assertThrows(RuntimeException.class,
                () -> supplyOperationHandler.applyTransactionToStorage(fruitTransaction));
    }
}

