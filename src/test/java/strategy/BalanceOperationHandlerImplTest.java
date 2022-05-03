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
    private int expected;
    private int actual;

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
        expected = 100;
        actual = balanceHandler.handle(new FruitTransaction("b",
                new Fruit("banana"), 100));
        Assert.assertEquals(expected, actual);
        expected = 100;
        actual = balanceHandler.handle(new FruitTransaction("b",
                new Fruit("apple"), 100));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changeBalance_correctAmount_isOk() {
        expected = 100;
        actual = Storage.storage.get(new Fruit("banana"));
        Assert.assertEquals(expected, actual);
    }
}
