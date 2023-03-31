package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static SupplyOperationHandler supplyOperationHandler;

    @Before
    public void setUp() {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }

    @Test
    public void purchaseOperation_purchaseIsMoreThenBalance_ok() {
        Storage.storage.put("banana", 80);
        FruitTransaction fruitTransaction = new FruitTransaction("s", "banana", 20);
        supplyOperationHandler.handle(fruitTransaction);
        int expected = 100;
        int actual = Storage.storage.get("banana");
        assertEquals(expected, actual);
    }
}
