package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private OperationHandler handler;

    @Before
    public void beforEachTest() {
        handler = new RetureOperationHandler();
    }

    @Test
    public void retureOperationHandler_correctData_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURE, "apple", 100);
        handler.apply(fruitTransaction);
        int amountAfter = Storage.getStorage().get("apple");
        Assert.assertEquals(100, amountAfter);
    }

    @After
    public void afterEachTest() {
        Storage.getStorage().clear();
    }
}
