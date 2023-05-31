package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void purchaseOperationHandler_equalsApply_Ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 45);
        Transaction purchaseOperation = new Transaction("p", banana, 15);
        operationHandler.apply(purchaseOperation);
        assertEquals(Integer.valueOf(30), Storage.storage.get(banana));
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperationHandler_moreThanInStock_NotOk() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 10);
        Transaction purchaseOperation = new Transaction("p", banana, 20);
        operationHandler.apply(purchaseOperation);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
