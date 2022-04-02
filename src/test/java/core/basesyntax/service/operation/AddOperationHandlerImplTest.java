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

public class AddOperationHandlerImplTest {
    private static final StorageDao fruitStorageDao = new StorageDaoImpl();
    private static OperationHandler addOperationHandler;
    private static Map<Fruit, Integer> storage;

    @BeforeClass
    public static void beforeClass() {
        addOperationHandler = new AddOperationHandlerImpl(fruitStorageDao);
        storage = Storage.storage;
    }

    @Test
    public void apply_validOutputSupply_Ok() {
        storage.put(new Fruit("banana"), 20);
        addOperationHandler.apply(new Fruit("banana"), 40);
        assertTrue(storage.containsKey(new Fruit("banana")));
        assertTrue(storage.containsValue(60));
        assertEquals(1, storage.size());
    }

    @Test
    public void apply_validOutputAdd_Ok() {
        storage.put(new Fruit("banana"), 30);
        addOperationHandler.apply(new Fruit("apple"), 40);
        assertTrue(storage.containsKey(new Fruit("banana")));
        assertTrue(storage.containsKey(new Fruit("apple")));
        assertEquals(30, (int) storage.get(new Fruit("banana")));
        assertEquals(40, (int) storage.get(new Fruit("apple")));
        assertEquals(2, storage.size());
    }

    @Test
    public void testEquals() {
        AddOperationHandlerImpl clazz = new AddOperationHandlerImpl(fruitStorageDao);
        assertEquals(clazz, addOperationHandler);
        assertEquals(clazz, clazz);
        assertEquals(clazz.hashCode(), addOperationHandler.hashCode());
        assertNotEquals(clazz, null);
    }

    @After
    public void afterEach() {
        storage.clear();
    }
}
