package strategy;

import model.Fruit;
import model.FruitTransaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import storage.Storage;

public class BalanceOperationHandlerImplTest {
    private static OperationHandler balanceHandler;

    @BeforeClass
    public static void beforeClass() {
        balanceHandler = new BalanceOperationHandlerImpl();
    }

    @Before
    public void setUp() {
        Storage.storage.put(new Fruit("banana"), 100);
        Storage.storage.put(new Fruit("apple"), 42);
    }

    @Test
    public void changeBalance_isOk() {
        int expected = 100;
        int actual = balanceHandler.handle(new FruitTransaction("b",
                new Fruit("banana"), 100));
        Assert.assertEquals(expected, actual);
        expected = 100;
        actual = balanceHandler.handle(new FruitTransaction("b",
                new Fruit("apple"), 100));
        Assert.assertEquals(expected, actual);
    }
}
