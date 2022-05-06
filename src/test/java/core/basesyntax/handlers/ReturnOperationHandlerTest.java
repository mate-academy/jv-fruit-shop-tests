package core.basesyntax.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
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
    public void return_Ok() {
        Storage.storage.put(new Fruit("apple"), 5);
        returnOperationHandler.process(new Fruit("apple"), 5);
        Integer expected = 10;
        Integer actual = Storage.storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test
    public void return_newFruit_Ok() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(new Fruit("mango"), 20);
        returnOperationHandler.process(apple, 15);
        int expected = 15;
        int actual = Storage.storage.get(apple);
        assertEquals(expected, actual);
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}
