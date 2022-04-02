package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.OperationException;
import core.basesyntax.model.Fruit;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerImplTest {
    private static final StorageDao fruitStorageDao = new StorageDaoImpl();
    private static OperationHandler purchaseOperationHandler;
    private static Map<Fruit, Integer> storage;

    @BeforeClass
    public static void beforeClass() {
        purchaseOperationHandler = new PurchaseOperationHandlerImpl(fruitStorageDao);
        storage = Storage.storage;
    }

    @Test
    public void apply_validOutputWithOneItem_Ok() {
        storage.put(new Fruit("apple"), 50);
        purchaseOperationHandler.apply(new Fruit("apple"), 20);
        assertTrue(storage.containsKey(new Fruit("apple")));
        assertTrue(storage.containsValue(30));
        assertEquals(1, storage.size());
    }

    @Test
    public void apply_validOutputWithThreeItems_Ok() {
        storage.put(new Fruit("banana"), 100);
        storage.put(new Fruit("apple"), 40);
        purchaseOperationHandler.apply(new Fruit("banana"), 10);
        purchaseOperationHandler.apply(new Fruit("apple"), 30);
        purchaseOperationHandler.apply(new Fruit("banana"), 20);
        assertEquals(70, (int) storage.get(new Fruit("banana")));
        assertEquals(10, (int) storage.get(new Fruit("apple")));
        assertEquals(2, storage.size());
    }

    @Test (expected = OperationException.class)
    public void apply_negativeResultQuantity_NotOk() {
        storage.put(new Fruit("banana"), 100);
        purchaseOperationHandler.apply(new Fruit("banana"), 110);
    }

    @Test
    public void testEquals() {
        PurchaseOperationHandlerImpl clazz = new PurchaseOperationHandlerImpl(fruitStorageDao);
        assertEquals(clazz, purchaseOperationHandler);
        assertEquals(clazz, clazz);
        assertEquals(clazz.hashCode(), purchaseOperationHandler.hashCode());
        assertNotEquals(clazz, null);
    }

    @After
    public void afterEach() {
        storage.clear();
    }
}
