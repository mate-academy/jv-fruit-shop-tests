package core.basesyntax.strategy;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerImplTest {
    private static StorageDao dao;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        dao = new StorageDaoImpl();
        operationHandler = new PurchaseHandlerImpl(dao);
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
        Storage.storage.put(new Fruit("apple"), 50);
        operationHandler.apply(new Fruit("apple"), 20);
        Assert.assertTrue(Storage.storage.containsKey(new Fruit("apple")));
        Assert.assertTrue(Storage.storage.containsValue(30));
        Assert.assertEquals(1, Storage.storage.size());
    }

    @Test
    public void apply_validOutputWithThree_Ok() {
        Storage.storage.put(new Fruit("banana"), 100);
        operationHandler.apply(new Fruit("banana"), 10);
        operationHandler.apply(new Fruit("banana"), 20);
        Assert.assertEquals(70, (int) Storage.storage.get(new Fruit("banana")));
        Assert.assertEquals(1, Storage.storage.size());
    }
}
