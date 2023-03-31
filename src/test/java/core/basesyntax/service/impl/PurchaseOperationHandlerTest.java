package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static PurchaseOperationHandler purchaseOperationHandler;

    @Before
    public void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }

    @Test
    public void handle_purchaseIsLessThenBalance_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction("p", "banana", 81);
        try {
            purchaseOperationHandler.handle(fruitTransaction);
        } catch (RuntimeException e) {
            return;
        }
        fail("The purchase cannot be completed");
    }

    @Test
    public void handle_purchaseIsMoreThenBalance_ok() {
        Storage.storage.put("banana", 80);
        FruitTransaction fruitTransaction = new FruitTransaction("p", "banana", 60);
        purchaseOperationHandler.handle(fruitTransaction);
        int expected = 20;
        int actual = Storage.storage.get("banana");
        assertEquals(expected, actual);
    }
}
