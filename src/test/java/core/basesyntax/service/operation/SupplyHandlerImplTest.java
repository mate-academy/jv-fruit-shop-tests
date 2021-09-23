package core.basesyntax.service.operation;

import static org.junit.Assert. assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyHandlerImplTest {
    private static Handler purchaseHandler;
    private int expected;
    private int actual;

    @BeforeClass
    public static void beforeClass() {
        purchaseHandler = new SupplyHandlerImpl();
        Storage.storage.put(new Fruit("banana"), 200);
        Storage.storage.put(new Fruit("apple"), 110);
    }

    @Test
    public void changeAmount_supply_Ok() {
        actual = purchaseHandler.changeAmount(new FruitRecord(
                "r", new Fruit("banana"), 20));
        expected = 220;
        assertEquals(expected, actual);
        actual = purchaseHandler.changeAmount(new FruitRecord(
                "r", new Fruit("apple"), 40));
        expected = 150;
        assertEquals(expected, actual);
        actual = purchaseHandler.changeAmount(new FruitRecord(
                "r", new Fruit("banana"), 20));
        expected = 240;
        assertEquals(expected, actual);
        actual = purchaseHandler.changeAmount(new FruitRecord(
                "r", new Fruit("apple"), 4));
        expected = 154;
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
