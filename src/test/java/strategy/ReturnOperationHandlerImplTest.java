package strategy;

import static org.junit.Assert.assertEquals;

import model.Fruit;
import model.FruitTransaction;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import storage.Storage;

public class ReturnOperationHandlerImplTest {
    private static OperationHandler purchaseHandler;

    @BeforeClass
    public static void beforeClass() {
        purchaseHandler = new ReturnOperationHandlerImpl();
    }

    @Before
    public void setUp() {
        Storage.storage.put(new Fruit("banana"), 200);
        Storage.storage.put(new Fruit("apple"), 100);
    }

    @Test
    public void changeAmount_return_isOk() {
        int actual = purchaseHandler.handle(new FruitTransaction(
                "r", new Fruit("banana"), 20));
        int expected = 220;
        assertEquals(expected, actual);
        actual = purchaseHandler.handle(new FruitTransaction(
                "r", new Fruit("apple"), 40));
        expected = 140;
        assertEquals(expected, actual);
        actual = purchaseHandler.handle(new FruitTransaction(
                "r", new Fruit("banana"), 20));
        expected = 240;
        assertEquals(expected, actual);
        actual = purchaseHandler.handle(new FruitTransaction(
                "r", new Fruit("apple"), 4));
        expected = 144;
        assertEquals(expected, actual);
    }
}
