package strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import model.Fruit;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import storage.Storage;

public class BalanceOperationHandlerTest {
    private static OperationHandler balanceOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    public void validBalance_Ok() {
        balanceOperationHandler.apply(new Fruit("banana"), 100);
        assertTrue(Storage.data.containsKey(new Fruit("banana")));
        assertTrue(Storage.data.containsValue(100));
        assertEquals(1, Storage.data.size());
    }

    @After
    public void afterEach() {
        Storage.data.clear();
    }
}
