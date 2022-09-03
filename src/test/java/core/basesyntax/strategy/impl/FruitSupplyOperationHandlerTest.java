package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.strategy.FruitOperationHandler;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitSupplyOperationHandlerTest {
    private static FruitOperationHandler fruitOperationHandler;

    @BeforeClass
    public static void setUp() {
        StorageDao storageDao = new StorageDaoImpl();
        fruitOperationHandler = new FruitSupplyOperationHandler(storageDao);
    }

    @Test
    public void operate_validData_ok() {
        Map<String, Integer> storage = Storage.getStorage();
        storage.put("banana", 10);
        storage.put("apple", 25);
        fruitOperationHandler.operate("banana", 15);
        fruitOperationHandler.operate("apple", 5);
        assertEquals(Integer.valueOf(25), storage.get("banana"));
        assertEquals(Integer.valueOf(30), storage.get("apple"));
    }
}
