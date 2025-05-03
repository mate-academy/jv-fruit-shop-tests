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

public class PurchaseActivityHandlerImplTest {
    private static Fruit fruit;
    private static FruitTransaction fruitTransaction;
    private static ActivityDaoDb activityDaoDb;
    private static PurchaseActivityHandlerImpl purchaseActivityHandler;

    @BeforeClass
    public static void setUp() {
        activityDaoDb = new ActivityDaoDbImpl();
        purchaseActivityHandler = new PurchaseActivityHandlerImpl(activityDaoDb);
        fruit = new Fruit("orange");
    }

    @Test
    public void test_purchase_ok() {
        Storage.data.put(fruit, 15);
        fruitTransaction = new FruitTransaction(TypeActivity.PURCHASE, fruit,10);
        int expected = 5;
        purchaseActivityHandler.calculate(fruitTransaction);
        int actual = Storage.data.get(fruit);
        Assert.assertEquals("Must be equals", expected, actual);
    }

    @After
    public void clearUp() {
        Storage.data.clear();
    }
}
