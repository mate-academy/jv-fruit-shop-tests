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
        Integer actual = 10;
        assertEquals(actual, Storage.storage.get(new Fruit("apple")));
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}
