package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyHandlerTest {
    private static final FruitStorageDao storageDao = new FruitStorageDaoImpl();
    private static SupplyHandler supplyHandler;
    private static Map<String, Integer> storage;

    @BeforeClass
    public static void beforeClass() {
        storage = Storage.fruitStorage;
        supplyHandler = new SupplyHandler(storageDao);
    }

    @Before
    public void beforeEachTest() {
        storage.put("banana", 5);
    }

    @Test
    public void supplyApply_addValidData_ok() {
        supplyHandler.apply("banana", 10);
        Assert.assertNotNull("Provided Map is null;", storage);
        Assert.assertEquals("Size mismatch for maps;", 1, storage.size());
        Assert.assertTrue("Missing keys in storage;" + storage.keySet(),
                storage.containsKey("banana"));
        Assert.assertTrue("Missing values in storage;" + storage.values(),
                storage.containsValue(15));
    }

    @Test
    public void supplyApply_updateValidData_ok() {
        supplyHandler.apply("cherry", 6);
        Assert.assertNotNull("Provided Map is null;", storage);
        Assert.assertEquals("Size mismatch for maps;", 2, storage.size());
        Assert.assertTrue("Missing keys in storage;" + storage.keySet(),
                storage.containsKey("cherry"));
        Assert.assertTrue("Missing values in storage;" + storage.values(),
                storage.containsValue(6));
    }

    @Test
    public void testEquals() {
        SupplyHandler clazz = new SupplyHandler(storageDao);
        Assert.assertEquals(clazz, supplyHandler);
        Assert.assertEquals(clazz, clazz);
        Assert.assertEquals(clazz.hashCode(), supplyHandler.hashCode());
        Assert.assertNotEquals(clazz, null);
    }

    @After
    public void afterEachTest() {
        storage.clear();
    }
}
