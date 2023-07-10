package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    @Test
    void testApplyTransactionToStorage_NotOk() {
        final PurchaseOperationHandler purchaseOperationHandler = new PurchaseOperationHandler();
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("Fruit");
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setQuantity(-1);
        assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.applyTransactionToStorage(fruitTransaction));
    }
}
