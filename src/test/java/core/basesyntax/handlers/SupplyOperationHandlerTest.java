package core.basesyntax.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
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

    @Test
    public void apply_correctDataEmptyStorage_Ok() {
        int expected = 0;
        Fruit orange = new Fruit("orange");
        supplyHandler.doOperation(orange, expected);
        int actual = Storage.storage.get(orange);
        assertEquals(expected, actual);
    }

    @Test
    public void apply_correctData_Ok() {
        Fruit orange = new Fruit("orange");
        Storage.storage.put(orange, 0);
        supplyHandler.doOperation(orange, 0);
        int expected = 0;
        int actual = Storage.storage.get(orange);
        assertEquals(expected, actual);
    }

    @Test
    public void apply_validDataFromStorageAfterApply_Ok() {
        Fruit orange = new Fruit("orange");
        Storage.storage.put(orange, 0);
        supplyHandler.doOperation(orange, 5);
        assertEquals(5, storageDao.get(orange).intValue());
    }

    @After
    public void cleanUp() {
        Storage.storage.clear();
    }
}
