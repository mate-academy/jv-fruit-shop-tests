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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ReturnHandlerTest {
    private static final FruitStorageDao storageDao = new FruitStorageDaoImpl();
    private static ReturnHandler returnHandler;
    private static Map<String, Integer> storage;

    @BeforeClass
    public static void beforeClass() {
        storage = Storage.fruitStorage;
        returnHandler = new ReturnHandler(storageDao);
    }

    @Before
    public void beforeEachTest() {
        storage.put("banana", 5);
    }

    @Test(expected = OperationException.class)
    public void returnApply_invalidData_notOk() {
        returnHandler.apply("stinkSocks", 6);
    }

    @Test
    public void returnApply_validData_ok() {
        returnHandler.apply("banana", 6);
        Assert.assertNotNull("Provided Map is null;", storage);
        Assert.assertEquals("Size mismatch for maps;", 1, storage.size());
        Assert.assertTrue("Missing keys in storage;" + storage.keySet(),
                storage.containsKey("banana"));
        Assert.assertTrue("Missing values in storage;" + storage.values(),
                storage.containsValue(11));
    }

    @Test
    public void testEquals() {
        ReturnHandler clazz = new ReturnHandler(storageDao);
        assertEquals(clazz, returnHandler);
        assertEquals(clazz, clazz);
        assertEquals(clazz.hashCode(), returnHandler.hashCode());
        assertNotEquals(clazz, null);
    }

    @After
    public void afterEachTest() {
        storage.clear();
    }
}
