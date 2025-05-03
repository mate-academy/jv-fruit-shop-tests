package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @Test
    public void process_initialStorageIsEmpty_ok() {
        Integer expected = 17;
        supplyOperationHandler.process(new FruitTransaction("s", new Fruit("apple"), 17));
        Integer actual = Storage.storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test
    public void process_initialStorageNotEmpty_ok() {
        Storage.storage.put(new Fruit("apple"), 50);
        Integer expected = 120;
        supplyOperationHandler.process(new FruitTransaction("s", new Fruit("apple"), 70));
        Integer actual = Storage.storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test
    public void process_initialStorageNotEmptyTwoItems_ok() {
        Storage.storage.put(new Fruit("apple"), 50);
        supplyOperationHandler.process(new FruitTransaction("s", new Fruit("apple"), 70));
        supplyOperationHandler.process(new FruitTransaction("s", new Fruit("banana"), 80));
        Integer expectedApple = 120;
        Integer expectedBanana = 80;
        Integer actualApple = Storage.storage.get(new Fruit("apple"));
        Integer actualBanana = Storage.storage.get(new Fruit("banana"));
        assertEquals(expectedApple, actualApple);
        assertEquals(expectedBanana, actualBanana);
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}
