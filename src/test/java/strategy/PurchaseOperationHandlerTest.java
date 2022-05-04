package strategy;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import model.Fruit;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import storage.Storage;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseOperationHandler;
    private static Map<Fruit, Integer> storage;

    @BeforeClass
    public static void beforeClass() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        storage = Storage.data;
    }

    @Test
    public void validOnePurchase_Ok() {
        storage.put(new Fruit("apple"), 50);
        purchaseOperationHandler.apply(new Fruit("apple"), 20);
        Integer actual = 30;
        assertEquals(actual, storage.get(new Fruit("apple")));
    }

    @Test
    public void validManyPurchase_Ok() {
        storage.put(new Fruit("banana"), 200);
        storage.put(new Fruit("apple"), 50);
        purchaseOperationHandler.apply(new Fruit("banana"), 15);
        purchaseOperationHandler.apply(new Fruit("apple"), 20);
        purchaseOperationHandler.apply(new Fruit("banana"), 25);
        assertEquals(160, (int) storage.get(new Fruit("banana")));
        assertEquals(30, (int) storage.get(new Fruit("apple")));
        assertEquals(2, storage.size());
    }

    @Test (expected = RuntimeException.class)
    public void invalidPurchase_NotOk() {
        storage.put(new Fruit("banana"), 100);
        purchaseOperationHandler.apply(new Fruit("banana"), 110);
    }

    @After
    public void afterEach() {
        storage.clear();
    }
}
