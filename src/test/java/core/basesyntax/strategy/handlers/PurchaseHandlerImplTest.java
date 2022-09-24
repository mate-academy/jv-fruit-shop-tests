package core.basesyntax.strategy.handlers;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PurchaseHandlerImplTest extends PurchaseHandlerImpl {
    private OperationHandler purchaseOperationHandler;

    @Before
    public void setUp() {
        purchaseOperationHandler = new PurchaseHandlerImpl();
        Storage.storage.put("banana", 20);
        Storage.storage.put("apple", 70);
    }

    @Test
    public void PurchaseHandler_ValidTransaction_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction("apple", 30);
        purchaseOperationHandler.handle(fruitTransaction);
        int expected = 40;
        int actual = Storage.storage.get("apple");
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void PurchaseHandler_NotEnoughFruit_NotOK() {
        FruitTransaction fruitTransaction = new FruitTransaction("banana", 40);
        purchaseOperationHandler.handle(fruitTransaction);
    }
}