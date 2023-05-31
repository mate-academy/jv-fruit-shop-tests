package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.strategy.FruitOperationHandler;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitBalanceOperationHandlerTest {
    private static FruitOperationHandler fruitOperationHandler;

    @BeforeClass
    public static void setUp() {
        StorageDao storageDao = new StorageDaoImpl();
        fruitOperationHandler = new FruitBalanceOperationHandler(storageDao);
    }

    @Test
    public void operate_validData_ok() {
        fruitOperationHandler.operate("banana", 20);
        fruitOperationHandler.operate("apple", 30);
        Map<String, Integer> storage = Storage.getStorage();
        assertEquals(Integer.valueOf(20), storage.get("banana"));
        assertEquals(Integer.valueOf(30), storage.get("apple"));
    }

    @After
    public void tearDown() {
        Storage.getStorage().clear();
    }
}
