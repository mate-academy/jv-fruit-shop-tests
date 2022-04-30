package strategy;

import static org.junit.Assert.assertEquals;

import model.Fruit;
import model.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Test;
import storage.Storage;

public class PurchaseOperationHandlerImplTest {
    private static OperationHandler purchaseHandler;
    private int actual;
    private int expected;

    @BeforeClass
    public static void beforeClass() {
        purchaseHandler = new PurchaseOperationHandlerImpl();
        Storage.storage.put(new Fruit("banana"), 200);
        Storage.storage.put(new Fruit("apple"), 100);
    }

    @Test
    public void changeAmount_purchase_isOk() {
        actual = purchaseHandler.handle(new FruitTransaction(
                "p", new Fruit("banana"), 20));
        expected = 180;
        assertEquals(expected, actual);
        actual = purchaseHandler.handle(new FruitTransaction(
                "p", new Fruit("apple"), 20));
        expected = 80;
        assertEquals(expected, actual);
        actual = purchaseHandler.handle(new FruitTransaction(
                "p", new Fruit("banana"), 20));
        expected = 160;
        assertEquals(expected, actual);
        actual = purchaseHandler.handle(new FruitTransaction(
                "p", new Fruit("apple"), 20));
        expected = 60;
        assertEquals(expected, actual);
    }
}
