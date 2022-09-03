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

public class FruitReturnOperationHandlerTest {
    private static FruitOperationHandler fruitOperationHandler;

    @BeforeClass
    public static void setUp() {
        StorageDao storageDao = new StorageDaoImpl();
        fruitOperationHandler = new FruitReturnOperationHandler(storageDao);
    }

    @Test
    public void operate_validData_ok() {
        Map<String, Integer> storage = Storage.getStorage();
        storage.put("banana", 50);
        storage.put("apple", 45);
        fruitOperationHandler.operate("banana", 35);
        fruitOperationHandler.operate("apple", 20);
        assertEquals(Integer.valueOf(85), storage.get("banana"));
        assertEquals(Integer.valueOf(65), storage.get("apple"));
    }

    @After
    public void tearDown() {
        Storage.getStorage().clear();
    }
}
