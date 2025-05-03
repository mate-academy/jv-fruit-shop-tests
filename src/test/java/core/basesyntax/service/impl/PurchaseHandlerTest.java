package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static OperationHandler handler;
    private static StorageDao<Fruit> storageDao;

    @BeforeClass
    public static void init() {
        storageDao = new StorageDaoImpl();
        handler = new PurchaseHandler(storageDao);
    }

    @Before
    public void setUpStorage() {
        storageDao.add(new Fruit("banana", 100));
        storageDao.add(new Fruit("apple", 5));
    }

    @Test
    public void validQuantity_Ok() {
        String fruitName = "banana";
        int fruitsAmount = 10;
        int expected = 90;
        Assert.assertTrue(handler.operate(fruitName, fruitsAmount));
        Assert.assertEquals(expected, storageDao.getByName(fruitName).getQuantity());
    }

    @Test
    public void equalQuantity_Ok() {
        String fruitName = "banana";
        int fruitsAmount = 100;
        int expected = 0;
        Assert.assertTrue(handler.operate(fruitName, fruitsAmount));
        Assert.assertEquals(expected, storageDao.getByName(fruitName).getQuantity());
    }

    @Test(expected = RuntimeException.class)
    public void purchaseMoreThanStorageContains_NotOk() {
        String fruitName = "apple";
        int fruitsAmount = 6;
        handler.operate(fruitName, fruitsAmount);
    }

    @Test(expected = RuntimeException.class)
    public void negativeAmount_NotOk() {
        String fruitName = "banana";
        int fruitsAmount = -1;
        handler.operate(fruitName, fruitsAmount);
    }

    @After
    public void resetStorage() {
        Storage.fruitStorage.clear();
    }
}
