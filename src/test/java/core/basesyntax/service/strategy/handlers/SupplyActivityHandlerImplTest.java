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

public class SupplyActivityHandlerImplTest {
    private static Fruit fruit;
    private static FruitTransaction fruitTransaction;
    private static ActivityDaoDb activityDaoDb;
    private static SupplyActivityHandlerImpl supplyActivityHandler;

    @BeforeClass
    public static void setUp() {
        activityDaoDb = new ActivityDaoDbImpl();
        supplyActivityHandler = new SupplyActivityHandlerImpl(activityDaoDb);
        fruit = new Fruit("orange");
    }

    @Test
    public void test_supply_ok() {
        fruitTransaction = new FruitTransaction(TypeActivity.PURCHASE, fruit,25);
        supplyActivityHandler.calculate(fruitTransaction);
        int expected = 25;
        int actual = Storage.data.get(fruit);
        Assert.assertEquals("Must be equals", expected, actual);
    }

    @After
    public void clearUp() {
        Storage.data.clear();
    }
}
