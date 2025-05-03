package core.basesyntax.operation;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyHandler;
    private static StorageDao storageDao;

    @BeforeClass
    public static void setUpFirst() {
        storageDao = new StorageDaoImpl();
        supplyHandler = new SupplyOperationHandler(storageDao);
    }

    @Test(expected = RuntimeException.class)
    public void apply_nullFruit_NotOk() {
        supplyHandler.apply(null, 0);
    }

    @Test(expected = RuntimeException.class)
    public void apply_wrongQuantity_NotOk() {
        supplyHandler.apply(new Fruit("orange"), -1);
    }

    @Test
    public void apply_correctDataEmptyStorage_Ok() {
        supplyHandler.apply(new Fruit("orange"), 0);
    }

    @Test
    public void apply_correctData_Ok() {
        Storage.storage.put(new Fruit("orange"), 0);
        Assert.assertTrue(supplyHandler.apply(new Fruit("orange"), 0));
    }

    @Test
    public void apply_validDataFromStorageAfterApply_Ok() {
        Fruit orange = new Fruit("orange");
        Storage.storage.put(orange, 0);
        supplyHandler.apply(orange, 5);
        Assert.assertEquals(5, storageDao.get(orange).intValue());
    }

    @After
    public void cleanUp() {
        Storage.storage.clear();
    }
}
