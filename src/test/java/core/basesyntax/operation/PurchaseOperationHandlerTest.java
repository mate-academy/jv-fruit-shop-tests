package core.basesyntax.operation;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchase;
    private static StorageDao storageDao;

    @BeforeClass
    public static void setUp() {
        storageDao = new StorageDaoImpl();
        purchase = new PurchaseOperationHandler(storageDao);
    }

    @Test(expected = NullPointerException.class)
    public void apply_emptyData_NotOk() {
        purchase.apply(new Fruit("apple"), 0);
    }

    @Test(expected = RuntimeException.class)
    public void apply_notEnoughQuantity_NotOK() {
        Fruit apple = new Fruit("apple");
        storageDao.add(apple, 0);
        purchase.apply(new Fruit("apple"), 1);
    }

    @Test
    public void apply_enoughQuantity_Ok() {
        Fruit apple = new Fruit("apple");
        storageDao.add(apple, 0);
        Assert.assertTrue(purchase.apply(apple, 0));
    }
}
