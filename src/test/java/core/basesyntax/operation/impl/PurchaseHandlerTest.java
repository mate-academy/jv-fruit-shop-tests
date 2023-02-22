package core.basesyntax.operation.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.Handler;
import core.basesyntax.service.operation.PurchaseHandlerImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static Handler purchaseHandler;

    @Before
    public void setUp() {
        purchaseHandler = new PurchaseHandlerImpl();
        FruitStorage.fruitStorage.put("banana", 100);
    }

    @Test
    public void addToStoragePurchaseOperationHandler_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 10);
        purchaseHandler.handle(fruitTransaction);
        int expected = 90;
        int actual = FruitStorage.fruitStorage.get("banana");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void addToStoragePurchaseOperationHandler_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 110);
        purchaseHandler.handle(fruitTransaction);
    }

    @After
    public void tearDown() {
        FruitStorage.fruitStorage.clear();
    }
}
