package core.basesyntax.operation;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnHandler;
    private static StorageDao storageDao;

    @BeforeClass
    public static void setUp() {
        storageDao = new StorageDaoImpl();
        returnHandler = new ReturnOperationHandler(storageDao);
    }

    @Test(expected = RuntimeException.class)
    public void apply_nullFruit_NotOk() {
        returnHandler.apply(null, 5);
    }

    @Test(expected = RuntimeException.class)
    public void apply_wrongQuantity_NotOk() {
        returnHandler.apply(new Fruit("orange"), -6);
    }

    @Test(expected = RuntimeException.class)
    public void apply_correctDataEmptyStorage_NotOk() {
        returnHandler.apply(new Fruit("orange"), 1);
    }

    @Test
    public void apply_correctData_Ok() {
        Storage.storage.put(new Fruit("orange"), 4);
        Assert.assertTrue(returnHandler.apply(new Fruit("orange"), 5));
    }

    @Test
    public void apply_validDataFromStorageAfterApply_Ok() {
        Fruit orange = new Fruit("orange");
        Storage.storage.put(orange, 0);
        returnHandler.apply(orange, 5);
        Assert.assertEquals(5, storageDao.get(orange).intValue());
    }

    @After
    public void cleanUp() {
        Storage.storage.clear();
    }
}
