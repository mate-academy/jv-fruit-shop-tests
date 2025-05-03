package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private OperationHandler handler;

    @Before
    public void beforEachTest() {
        handler = new SupplyOperationHandler();
    }

    @Test
    public void supplyOperationHandler_correctData_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPLLY, "apple", 100);
        handler.apply(fruitTransaction);
        int amountAfter = Storage.getStorage().get("apple");
        Assert.assertEquals(100, amountAfter);
    }

    @After
    public void afterEachTest() {
        Storage.getStorage().clear();
    }
}
