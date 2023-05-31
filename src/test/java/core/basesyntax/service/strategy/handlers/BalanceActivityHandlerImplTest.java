package core.basesyntax.service.strategy.handlers;

import core.basesyntax.dao.ActivityDaoDb;
import core.basesyntax.dao.ActivityDaoDbImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.TypeActivity;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceActivityHandlerImplTest {
    private static Fruit fruit;
    private static FruitTransaction fruitTransaction;
    private static ActivityDaoDb activityDaoDb;
    private static BalanceActivityHandlerImpl balanceActivityHandler;

    @BeforeClass
    public static void setUp() {
        activityDaoDb = new ActivityDaoDbImpl();
        balanceActivityHandler = new BalanceActivityHandlerImpl(activityDaoDb);
        fruit = new Fruit("orange");
        fruitTransaction = new FruitTransaction(TypeActivity.BALANCE, fruit,0);
    }

    @Test
    public void test_set_zero_ok() {
        int expected = 0;
        balanceActivityHandler.calculate(fruitTransaction);
        int actual = Storage.data.get(fruit);
        Assert.assertEquals("Must be equals", expected, actual);
    }

    @Test
    public void test_set_non_zero_ok() {
        int expected = 10;
        fruitTransaction.setCount(expected);
        balanceActivityHandler.calculate(fruitTransaction);
        int actual = Storage.data.get(fruit);
        Assert.assertEquals("Must be equals", 10, actual);
    }

    @After
    public void clearUp() {
        Storage.data.clear();
    }
}
