package strategy;

import static org.junit.Assert.assertEquals;

import model.Fruit;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import storage.Storage;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        returnOperationHandler = new ReturnOperationHandler();
    }

    @Test
    public void validReturn_Ok() {
        Storage.data.put(new Fruit("banana"), 10);
        returnOperationHandler.apply(new Fruit("banana"), 40);
        Integer actual = 50;
        assertEquals(actual, Storage.data.get(new Fruit("banana")));
    }

    @After
    public void afterEach() {
        Storage.data.clear();
    }
}
