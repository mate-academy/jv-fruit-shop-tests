package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private OperationHandler handler;

    @Before
    public void beforEachTest() {
        handler = new BalanceOperationHandler();
    }

    @Test
    public void balanceOperationHandler_CorrectData_Ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100);
        handler.apply(fruitTransaction);
        int actual = Storage.getStorage().get("apple");
        int expected = 100;
        Assert.assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.getStorage().clear();
    }
}
