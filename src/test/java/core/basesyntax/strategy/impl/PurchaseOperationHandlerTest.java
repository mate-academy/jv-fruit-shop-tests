package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private OperationHandler operationHandler;

    @Before
    public void setUp() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void purchaseHandler_Ok() {
        FruitStorage.fruits.put("banana", 100);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 20);
        operationHandler.handle(transaction);
        int excepted = 80;
        int actual = FruitStorage.fruits.get("banana");
        assertEquals(excepted, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseHandler_NotOk() {
        FruitStorage.fruits.put("banana", 100);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 150);
        operationHandler.handle(transaction);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        FruitStorage.fruits.clear();
    }
}
