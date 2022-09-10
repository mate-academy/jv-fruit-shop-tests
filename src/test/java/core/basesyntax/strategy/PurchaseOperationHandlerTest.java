package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private PurchaseOperationHandler purchaseOperationHandler;

    @Before
    public void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        Storage.storageFruits.put("apple",40);
        Storage.storageFruits.put("banana",9);
    }

    @Test
    public void purchaseOperationHandler_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction("apple",20);
        purchaseOperationHandler.handle(fruitTransaction);
        int expected = 20;
        int actual = Storage.storageFruits.get("apple");
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void purchaseOperationHandler_NotEnoughFruit_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction("banana",10);
        purchaseOperationHandler.handle(fruitTransaction);
        int expected = 15;
        int actual = Storage.storageFruits.get("banana");
        assertEquals(expected, actual);
    }
}
