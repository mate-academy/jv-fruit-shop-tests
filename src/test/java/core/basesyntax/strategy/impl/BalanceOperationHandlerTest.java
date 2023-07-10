package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private final BalanceOperationHandler balanceOperationHandler
            = new BalanceOperationHandler();

    @Test
    void testApplyTransactionToStorage_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("Fruit");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(1);
        balanceOperationHandler.applyTransactionToStorage(fruitTransaction);
    }

    @Test
    void testApplyTransactionToStorage_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("Fruit");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(-1);
        assertThrows(RuntimeException.class,
                () -> balanceOperationHandler.applyTransactionToStorage(fruitTransaction));
    }
}

