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
    public static void beforeClass() {
        activityDaoDb = new ActivityDaoDbImpl();
        purchaseActivityHandler = new PurchaseActivityHandlerImpl(activityDaoDb);
        fruit = new Fruit("orange");
        fruitTransaction = new FruitTransaction(TypeActivity.PURCHASE, fruit,10);
        Storage.data.put(fruit, 15);
    }

    @Test
    public void test_purchase_ok() {
        purchaseActivityHandler.calculate(fruitTransaction);
        int actualCount = Storage.data.get(fruit);
        Assert.assertEquals("Must be 5", 5, actualCount);
    }

    @After
    public void clear() {
        Storage.data.clear();
    }
}
