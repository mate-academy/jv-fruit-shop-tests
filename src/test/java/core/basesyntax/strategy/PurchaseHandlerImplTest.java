package core.basesyntax.strategy;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerImplTest {
    private static StorageDao dao;
    private static OperationHandler operationHandler;
    private static Map<Fruit, Integer> storage;

    @BeforeClass
    public static void beforeClass() {
        dao = new StorageDaoImpl();
        operationHandler = new PurchaseHandlerImpl(dao);
        storage = Storage.storage;
    }

    @Before
    public void before() {
        Storage.storage.clear();
    }

    @Test(expected = RuntimeException.class)
    public void apply_lacksFruit_NotOk() {
        Storage.storage.put(new Fruit("apple"), 10);
        operationHandler.apply(new Fruit("apple"), 20);
    }

    @Test
    public void apply_validOutputWithOne_Ok() {
        storage.put(new Fruit("apple"), 50);
        operationHandler.apply(new Fruit("apple"), 20);
        Assert.assertTrue(storage.containsKey(new Fruit("apple")));
        Assert.assertTrue(storage.containsValue(30));
        Assert.assertEquals(1, storage.size());
    }

    @Test
    public void apply_validOutputWithThree_Ok() {
        storage.put(new Fruit("banana"), 100);
        operationHandler.apply(new Fruit("banana"), 10);
        operationHandler.apply(new Fruit("banana"), 20);
        Assert.assertEquals(70, (int) storage.get(new Fruit("banana")));
        Assert.assertEquals(1, storage.size());
    }
}
