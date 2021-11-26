package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BalanceHandlerTest {
    private static final FruitStorageDao storageDao = new FruitStorageDaoImpl();
    private static BalanceHandler balanceHandler;
    private static Map<String, Integer> storage;

    @BeforeClass
    public static void beforeClass() {
        storage = Storage.fruitStorage;
        balanceHandler = new BalanceHandler(storageDao);
    }

    @Test
    public void balanceApply_validDAta_ok() {
        balanceHandler.apply("banana", 10);
        Assert.assertNotNull("Provided Map is null;", storage);
        Assert.assertEquals("Size mismatch for maps;", 1, storage.size());
        Assert.assertTrue("Missing keys in storage;" + storage.keySet(),
                storage.containsKey("banana"));
        Assert.assertTrue("Missing values in storage;" + storage.values(),
                storage.containsValue(10));
    }

    @Test
    public void testEquals() {
        BalanceHandler clazz = new BalanceHandler(storageDao);
        assertEquals(clazz, balanceHandler);
        assertEquals(clazz, clazz);
        assertEquals(clazz.hashCode(), balanceHandler.hashCode());
        assertNotEquals(clazz, null);
    }

    @After
    public void afterEachTest() {
        storage.clear();
    }
}
