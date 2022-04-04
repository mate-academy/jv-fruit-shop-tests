package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerImplTest {
    private static StorageDao fruitStorageDao;
    private static OperationHandler balanceOperationHandler;
    private static Map<Fruit, Integer> storage;

    @BeforeClass
    public static void beforeClass() {
        fruitStorageDao = new StorageDaoImpl();
        balanceOperationHandler = new BalanceOperationHandlerImpl(fruitStorageDao);
        storage = Storage.storage;
    }

    @Test
    public void apply_validOutput_Ok() {
        balanceOperationHandler.apply(new Fruit("banana"), 100);
        assertTrue(storage.containsKey(new Fruit("banana")));
        assertTrue(storage.containsValue(100));
        assertEquals(1, storage.size());
    }

    @Test
    public void testEquals() {
        BalanceOperationHandlerImpl clazz = new BalanceOperationHandlerImpl(fruitStorageDao);
        assertEquals(clazz, balanceOperationHandler);
        assertEquals(clazz, clazz);
        assertEquals(clazz.hashCode(), balanceOperationHandler.hashCode());
        assertNotEquals(clazz, null);
    }

    @After
    public void afterEach() {
        storage.clear();
    }
}
