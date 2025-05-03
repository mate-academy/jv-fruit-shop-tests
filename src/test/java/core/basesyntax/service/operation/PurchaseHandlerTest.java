package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.OperationException;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static final FruitStorageDao storageDao = new FruitStorageDaoImpl();
    private static PurchaseHandler purchaseHandler;
    private static Map<String, Integer> storage;

    @BeforeClass
    public static void beforeClass() {
        storage = Storage.fruitStorage;
        purchaseHandler = new PurchaseHandler(storageDao);
    }

    @Before
    public void beforeEachTest() {
        storage.put("banana", 5);
    }

    @Test
    public void purchaseApply_validData_ok() {
        purchaseHandler.apply("banana", 5);
        Assert.assertNotNull("Provided Map is null;", storage);
        Assert.assertEquals("Size mismatch for maps;", 1, storage.size());
        Assert.assertTrue("Missing keys in storage;" + storage.keySet(),
                storage.containsKey("banana"));
        Assert.assertTrue("Missing values in storage;" + storage.values(),
                storage.containsValue(0));
    }

    @Test(expected = OperationException.class)
    public void purchaseApply_invalidData_notOk() {
        purchaseHandler.apply("banana", 6);
    }

    @Test
    public void testEquals() {
        PurchaseHandler clazz = new PurchaseHandler(storageDao);
        Assert.assertEquals(clazz, purchaseHandler);
        Assert.assertEquals(clazz, clazz);
        Assert.assertEquals(clazz.hashCode(), purchaseHandler.hashCode());
        Assert.assertNotEquals(clazz, null);
    }

    @After
    public void afterEachTest() {
        storage.clear();
    }
}
