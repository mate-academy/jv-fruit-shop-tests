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
    public static void beforeClass() {
        activityDaoDb = new ActivityDaoDbImpl();
        balanceActivityHandler = new BalanceActivityHandlerImpl(activityDaoDb);
        fruit = new Fruit("orange");
        fruitTransaction = new FruitTransaction(TypeActivity.BALANCE, fruit,0);
    }

    @Test
    public void test_set_zero_ok() {
        balanceActivityHandler.calculate(fruitTransaction);
        int actualCount = Storage.data.get(fruit);
        Assert.assertEquals("Must be 0", 0, actualCount);
    }

    @Test
    public void test_set_non_zero_ok() {
        fruitTransaction.setCount(10);
        balanceActivityHandler.calculate(fruitTransaction);
        int actualCount = Storage.data.get(fruit);
        Assert.assertEquals("Must be 10", 10, actualCount);
    }

    @After
    public void clear() {
        Storage.data.clear();
    }
}
