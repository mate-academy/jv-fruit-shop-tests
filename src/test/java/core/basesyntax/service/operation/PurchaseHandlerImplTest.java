package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerImplTest {
    private static Handler purchaseHandler;

    @BeforeClass
    public static void beforeClass() {
        purchaseHandler = new PurchaseHandlerImpl();
        Storage.storage.put(new Fruit("banana"), 200);
        Storage.storage.put(new Fruit("apple"), 100);
    }

    @Test
    public void changeAmount_Purchase_Ok() {
        int actual = purchaseHandler.changeAmount(new FruitRecord(
                "p", new Fruit("banana"), 20));
        int expected = 180;
        assertEquals(expected, actual);

        actual = purchaseHandler.changeAmount(new FruitRecord(
                "p", new Fruit("apple"), 20));
        expected = 80;
        assertEquals(expected, actual);

        actual = purchaseHandler.changeAmount(new FruitRecord(
                "p", new Fruit("banana"), 20));
        expected = 160;
        assertEquals(expected, actual);

        actual = purchaseHandler.changeAmount(new FruitRecord(
                "p", new Fruit("apple"), 20));
        expected = 60;
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
