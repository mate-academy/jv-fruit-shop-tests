package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {

    @Before
    public void fillStorage() {
        Storage.fruits.put("pineapple",500);
        Storage.fruits.put("strawberry",50);
    }

    @After
    public void cleanAfter() {
        Storage.fruits.clear();
    }

    @Test
    public void purchaseOperationHandler_process_Ok() {
        FruitTransaction f1 = new FruitTransaction(Operation.PURCHASE, "pineapple", 100);
        FruitTransaction f2 = new FruitTransaction(Operation.PURCHASE, "strawberry", 20);
        PurchaseOperationHandler purchaseOperationHandler = new PurchaseOperationHandler();
        purchaseOperationHandler.process(f1);
        purchaseOperationHandler.process(f2);

        Assert.assertEquals(Storage.fruits.size(),2);
        Assert.assertEquals(Storage.fruits.get("pineapple"),(Integer) 400);
        Assert.assertEquals(Storage.fruits.get("strawberry"), (Integer) 30);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperationHandler_process_NotOk() {
        FruitTransaction f1 = new FruitTransaction(Operation.PURCHASE, "pineapple", 600);
        PurchaseOperationHandler purchaseOperationHandler = new PurchaseOperationHandler();
        purchaseOperationHandler.process(f1);
    }
}
