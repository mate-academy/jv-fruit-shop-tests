package core.basesyntax.service.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnOperationHandler;

    @Before
    public void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
    }

    @Test
    public void returnOperationHandler_isOk() {
        Storage.fruitStorage.put("apple", 100);
        returnOperationHandler.handle("apple", 20);
        int expected = 120;
        int actual = Storage.fruitStorage.get("apple");
        Assert.assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.fruitStorage.clear();
    }
}
