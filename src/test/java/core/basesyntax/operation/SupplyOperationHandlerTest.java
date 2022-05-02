package core.basesyntax.operation;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler supply;
    private static StorageDao storageDao;

    @BeforeClass
    public static void setUpFirst() {
        storageDao = new StorageDaoImpl();
        supply = new SupplyOperationHandler(storageDao);
    }

    @Test(expected = RuntimeException.class)
    public void apply_nullFruit_NotOk() {
        supply.apply(null, 0);
    }

    @Test(expected = RuntimeException.class)
    public void apply_wrongQuantity_NotOk() {
        supply.apply(new Fruit("orange"), -1);
    }

    @Test(expected = NullPointerException.class)
    public void apply_correctDataEmptyStorage_NotOk() {
        supply.apply(new Fruit("orange"), 0);
    }

    @Test
    public void apply_correctData_Ok() {
        storageDao.add(new Fruit("orange"), 0);
        Assert.assertTrue(supply.apply(new Fruit("orange"), 0));
    }

    @Test
    public void apply_validDataFromStorageAfterApply_Ok() {
        Fruit orange = new Fruit("orange");
        storageDao.add(orange, 0);
        supply.apply(orange, 5);
        Assert.assertEquals(5, storageDao.get(orange).intValue());
    }

    @After
    public void clear() {
        storageDao.clear();
    }
}
