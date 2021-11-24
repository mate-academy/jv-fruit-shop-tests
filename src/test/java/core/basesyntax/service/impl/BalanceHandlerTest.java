package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.OperationHandler;
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
        int expected = 0;
        Assert.assertTrue(handler.operate(fruitName, expected));
        Assert.assertEquals(expected, storageDao.getByName(fruitName).getQuantity());
    }
}
