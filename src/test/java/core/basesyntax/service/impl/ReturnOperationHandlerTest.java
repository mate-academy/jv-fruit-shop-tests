package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static ReturnOperationHandler returnOperationHandler;

    @Before
    public void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }

    @Test
    public void returnOperation_fruitQuantity_ok() {
        Storage.storage.put("banana", 80);
        FruitTransaction fruitTransaction = new FruitTransaction("r", "banana", 10);
        returnOperationHandler.handle(fruitTransaction);
        int expected = 90;
        int actual = Storage.storage.get("banana");
        assertEquals(expected, actual);
    }
}
