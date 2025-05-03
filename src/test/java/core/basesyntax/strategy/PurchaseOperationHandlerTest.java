package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private OperationHandler handler;

    @Before
    public void setUp() {
        handler = new PurchaseOperationHandler();
    }

    @Test
    public void purchaseOperation_Ok() {
        Storage.fruitsStorage.put("banana", 50);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 30);
        handler.operate(fruitTransaction);
        int expected = 20;
        int actual = Storage.fruitsStorage.get("banana");
        assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_NotOk() {
        Storage.fruitsStorage.put("banana", 10);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 30);
        handler.operate(fruitTransaction);
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();
    }

}
