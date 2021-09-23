package core.basesyntax.service.operation;

import static org.junit.Assert. assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnHandlerImplTest {
    private static Handler purchaseHandler;

    @BeforeClass
    public static void beforeClass() {
        purchaseHandler = new ReturnHandlerImpl();
        Storage.storage.put(new Fruit("banana"), 200);
        Storage.storage.put(new Fruit("apple"), 100);
    }

    @Test
    public void changeAmount_Return_Ok() {
        int actual = purchaseHandler.changeAmount(new FruitRecord(
                "r", new Fruit("banana"), 20));
        int expected = 220;
        assertEquals(expected, actual);

        actual = purchaseHandler.changeAmount(new FruitRecord(
                "r", new Fruit("apple"), 40));
        expected = 140;
        assertEquals(expected, actual);

        actual = purchaseHandler.changeAmount(new FruitRecord(
                "r", new Fruit("banana"), 20));
        expected = 240;
        assertEquals(expected, actual);

        actual = purchaseHandler.changeAmount(new FruitRecord(
                "r", new Fruit("apple"), 4));
        expected = 144;
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
