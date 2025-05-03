package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static FruitTransaction fruitTransaction;
    private static ReturnOperationHandler returnOperationHandler;

    @BeforeAll
    static void beforeAll() {
        fruitTransaction = new FruitTransaction();
        returnOperationHandler = new ReturnOperationHandler();
    }

    @BeforeEach
    void setUp() {
        Storage.fruitsStorage.put("Fruit", 20);
        fruitTransaction.setFruit("Fruit");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
    }

    @Test
    void testApplyReturnTransactionToStorage_Ok() {
        fruitTransaction.setQuantity(1);
        returnOperationHandler.applyTransactionToStorage(fruitTransaction);
    }

    @Test
    void testApplyReturnTransactionToStorage_NotOk() {
        fruitTransaction.setQuantity(-1);
        assertThrows(RuntimeException.class,
                () -> returnOperationHandler.applyTransactionToStorage(fruitTransaction));
    }
}
