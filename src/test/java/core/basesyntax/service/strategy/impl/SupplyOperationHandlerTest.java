package core.basesyntax.service.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyOperationHandler;

    @Before
    public void setUp() {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @Test
    public void supplyOperationHandler_isOk() {
        Storage.fruitStorage.put("apple", 25);
        supplyOperationHandler.handle("apple", 25);
        int expected = 50;
        int actual = Storage.fruitStorage.get("apple");
        Assert.assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.fruitStorage.clear();
    }
}
