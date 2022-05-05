package strategy;

import static org.junit.Assert.assertEquals;

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
        Integer actual = 100;
        assertEquals(actual, Storage.data.get(new Fruit("banana")));
    }

    @After
    public void afterEach() {
        Storage.data.clear();
    }
}
