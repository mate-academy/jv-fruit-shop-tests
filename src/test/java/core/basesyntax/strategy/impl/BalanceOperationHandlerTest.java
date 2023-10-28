package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private BalanceOperationHandler balanceOperationHandler;

    @BeforeEach
    public void setUp() {
        Storage.fruits.clear();
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    public void handle_rightAction_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("banana");
        transaction.setQuantity(14);
        balanceOperationHandler.getTransaction(transaction);
        int quantity = Storage.fruits.get("banana");
        Assertions.assertEquals(quantity, 14);
    }

    @Test
    public void handle_negativeBalance_NotOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("apple");
        transaction.setQuantity(-10);
        Assertions.assertThrows(IllegalStateException.class,
                () -> balanceOperationHandler.getTransaction(transaction));
    }
}
