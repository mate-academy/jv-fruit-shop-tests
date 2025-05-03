package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static OperationHandler handler;
    private static StorageDao<Fruit> storageDao;

    @BeforeClass
    public static void init() {
        storageDao = new StorageDaoImpl();
        handler = new BalanceHandler(storageDao);
    }

    @Test
    public void validData_Ok() {
        String fruitName = "banana";
        int fruitsAmount = 0;
        Assert.assertTrue(handler.operate(fruitName, fruitsAmount));
        Assert.assertEquals(fruitsAmount, storageDao.getByName(fruitName).getQuantity());
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
