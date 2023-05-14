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
    private static final String NAME_FRUIT = "banana";
    private static final String OPERATION = "b";

    @Before
    public void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }

    @Test
    public void handle_purchaseIsMoreThenBalance_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(OPERATION, NAME_FRUIT, 81);
        try {
            purchaseOperationHandler.handle(fruitTransaction);
        } catch (RuntimeException e) {
            return;
        }
        fail("The purchase cannot be completed");
    }

    @Test
    public void handle_purchaseIsLessThenBalance_ok() {
        Storage.storage.put(NAME_FRUIT, 80);
        FruitTransaction fruitTransaction = new FruitTransaction(OPERATION, NAME_FRUIT, 60);
        purchaseOperationHandler.handle(fruitTransaction);
        int expected = 20;
        int actual = Storage.storage.get(NAME_FRUIT);
        assertEquals(expected, actual);
    }
}
