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
        FruitStorage.fruits.put("orange", 100);
    }

    @Test
    public void purchaseHandler_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "orange", 45);
        operationHandler.handle(transaction);
        int excepted = 55;
        int actual = FruitStorage.fruits.get("orange");
        assertEquals(excepted, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseHandler_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "orange", 150);
        operationHandler.handle(transaction);
    }

    @AfterClass
    public static void afterClass() {
        FruitStorage.fruits.clear();
    }
}
