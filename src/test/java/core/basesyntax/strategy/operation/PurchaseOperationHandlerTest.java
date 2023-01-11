package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private OperationHandler operationHandler;

    @Before
    public void setUp() {
        operationHandler = new PurchaseOperationHandler();
        Storage.fruitsStorage.put("banana", 20);
    }

    @Test
    public void addToStoragePurchaseOperationHandler_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 10);
        operationHandler.operation(fruitTransaction);
        int expected = 10;
        int actual = Storage.fruitsStorage.get("banana");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void addToStoragePurchaseOperationHandler_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "banana", 30);
        operationHandler.operation(fruitTransaction);
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();
    }
}
