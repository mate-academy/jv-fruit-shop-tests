package core.basesyntax.service.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.strategy.OperationHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseOperationHandler;

    @Before
    public void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void purchaseOperationHandler_isOk() {
        Storage.fruitStorage.put("apple", 100);
        purchaseOperationHandler.handle("apple", 20);
        int expected = 80;
        int actual = Storage.fruitStorage.get("apple");
        Assert.assertEquals(expected, actual);
    }
}
