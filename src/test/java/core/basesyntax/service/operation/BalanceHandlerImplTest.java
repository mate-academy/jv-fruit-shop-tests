package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerImplTest {
    private static Handler balanceHandler;
    private int expected;
    private int actual;

    @BeforeClass
    public static void beforeClass() {
        balanceHandler = new BalanceHandlerImpl();
        Storage.storage.put(new Fruit("banana"), 100);
        Storage.storage.put(new Fruit("apple"), 42);
    }

    @Test
    public void changeAmount_balance_Ok() {
        expected = 100;
        actual = balanceHandler.changeAmount(new FruitRecord("b",
                new Fruit("banana"), 100));
        Assert.assertEquals(expected, actual);
        expected = 100;
        actual = balanceHandler.changeAmount(new FruitRecord("b",
                new Fruit("apple"), 100));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changeAmount_correctAmountInStorage_Ok() {
        expected = 100;
        actual = Storage.storage.get(new Fruit("banana"));
        Assert.assertEquals(expected, actual);
        expected = 42;
        actual = Storage.storage.get(new Fruit("apple"));
        Assert.assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
