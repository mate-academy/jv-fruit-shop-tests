package core.basesyntax.operation;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnFruit;
    private static StorageDao storageDao;

    @BeforeClass
    public static void setUpFirst() {
        storageDao = new StorageDaoImpl();
        returnFruit = new ReturnOperationHandler(storageDao);
    }

    @Test(expected = RuntimeException.class)
    public void apply_nullFruit_NotOk() {
        returnFruit.apply(null, 5);
    }

    @Test(expected = RuntimeException.class)
    public void apply_wrongQuantity_NotOk() {
        returnFruit.apply(new Fruit("orange"), -6);
    }

    @Test(expected = NullPointerException.class)
    public void apply_correctDataEmptyStorage_NotOk() {
        returnFruit.apply(new Fruit("orange"), 5);
    }

    @Test
    public void apply_correctData_Ok() {
        storageDao.add(new Fruit("orange"), 4);
        Assert.assertTrue(returnFruit.apply(new Fruit("orange"), 5));
    }

    @Test
    public void apply_validDataFromStorageAfterApply_Ok() {
        Fruit orange = new Fruit("orange");
        storageDao.add(orange, 0);
        returnFruit.apply(orange, 5);
        Assert.assertEquals(5, storageDao.get(orange).intValue());
    }

    @After
    public void cleanUp() {
        storageDao.clear();
    }
}
