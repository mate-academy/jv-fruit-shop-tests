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

public class ReturnHandlerTest {
    private static OperationHandler handler;
    private static StorageDao<Fruit> storageDao;

    @BeforeClass
    public static void init() {
        storageDao = new StorageDaoImpl();
        handler = new ReturnHandler(storageDao);
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
        int expected = 110;
        Assert.assertTrue(handler.operate(fruitName, fruitsAmount));
        Assert.assertEquals(expected, storageDao.getByName(fruitName).getQuantity());
    }

    @Test
    public void zeroQuantity() {
        String fruitName = "banana";
        int fruitsAmount = 0;
        int expected = 100;
        Assert.assertTrue(handler.operate(fruitName, fruitsAmount));
        Assert.assertEquals(expected, storageDao.getByName(fruitName).getQuantity());
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
