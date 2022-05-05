package core.basesyntax.service.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balanceOperationHandler;

    @Before
    public void setUp() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    public void balanceOperationHandler_Ok() {
        Storage.fruitStorage.put("apple", 20);
        balanceOperationHandler.handle("apple", 20);
        int expected = 20;
        int actual = Storage.fruitStorage.get("apple");
        Assert.assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.fruitStorage.clear();
    }
}
