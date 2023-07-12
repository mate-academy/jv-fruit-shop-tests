package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static FruitTransaction fruitTransaction;
    private static BalanceOperationHandler balanceOperationHandler;

    @BeforeAll
    static void beforeAll() {
        fruitTransaction = new FruitTransaction();
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @BeforeEach
    void setUp() {
        fruitTransaction.setFruit("Fruit");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
    }

    @Test
    void testApplyBalanceTransactionToStorage_Ok() {
        fruitTransaction.setQuantity(1);
        balanceOperationHandler.applyTransactionToStorage(fruitTransaction);
    }

    @Test
    void testApplyBalanceTransactionToStorage_NotOk() {
        fruitTransaction.setQuantity(-1);
        assertThrows(RuntimeException.class,
                () -> balanceOperationHandler.applyTransactionToStorage(fruitTransaction));
    }
}
