package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        returnOperationHandler = new ReturnOperationHandler();
    }

    @Test
    public void process_initialQuantityIsNull_ok() {
        Integer expected = 38;
        returnOperationHandler.process(new FruitTransaction("r", new Fruit("apple"), 38));
        Integer actual = Storage.storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test
    public void process_initialQuantityIsNotNull_ok() {
        Storage.storage.put(new Fruit("apple"), 50);
        Storage.storage.put(new Fruit("banana"), 14);
        Integer expected = 164;
        returnOperationHandler.process(new FruitTransaction("r", new Fruit("apple"), 114));
        Integer actual = Storage.storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
        expected = 114;
        returnOperationHandler.process(new FruitTransaction("r", new Fruit("banana"), 100));
        actual = Storage.storage.get(new Fruit("banana"));
        assertEquals(expected, actual);
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}
