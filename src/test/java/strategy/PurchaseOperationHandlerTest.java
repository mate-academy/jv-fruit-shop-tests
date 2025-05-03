package strategy;

import static org.junit.Assert.assertEquals;

import model.Fruit;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import storage.Storage;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void validOnePurchase_Ok() {
        Storage.data.put(new Fruit("apple"), 50);
        purchaseOperationHandler.apply(new Fruit("apple"), 20);
        Integer actual = 30;
        assertEquals(actual, Storage.data.get(new Fruit("apple")));
    }

    @Test
    public void validManyPurchase_Ok() {
        Storage.data.put(new Fruit("banana"), 200);
        Storage.data.put(new Fruit("apple"), 50);
        purchaseOperationHandler.apply(new Fruit("banana"), 15);
        purchaseOperationHandler.apply(new Fruit("apple"), 20);
        purchaseOperationHandler.apply(new Fruit("banana"), 25);
        assertEquals(160, (int) Storage.data.get(new Fruit("banana")));
        assertEquals(30, (int) Storage.data.get(new Fruit("apple")));
        assertEquals(2, Storage.data.size());
    }

    @Test (expected = RuntimeException.class)
    public void invalidPurchase_NotOk() {
        Storage.data.put(new Fruit("banana"), 100);
        purchaseOperationHandler.apply(new Fruit("banana"), 110);
    }

    @After
    public void afterEach() {
        Storage.data.clear();
    }
}
