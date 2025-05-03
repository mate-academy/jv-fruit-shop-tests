package core.basesyntax.strategy;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyHandlerImplTest {
    private static OperationHandler operationHandler;
    private static StorageDao dao;

    @BeforeClass
    public static void beforeClass() {
        dao = new StorageDaoImpl();
        operationHandler = new SupplyHandlerImpl(dao);
    }

    @Before
    public void before() {
        Storage.storage.clear();
    }

    @Test
    public void apply_validOutputAdd_Ok() {
        Storage.storage.put(new Fruit("banana"), 50);
        operationHandler.apply(new Fruit("apple"), 20);
        operationHandler.apply(new Fruit("melon"), 60);
        Assert.assertTrue(Storage.storage.containsKey(new Fruit("banana")));
        Assert.assertTrue(Storage.storage.containsKey(new Fruit("apple")));
        Assert.assertTrue(Storage.storage.containsKey(new Fruit("melon")));
        Assert.assertEquals(50, (int) Storage.storage.get(new Fruit("banana")));
        Assert.assertEquals(20, (int) Storage.storage.get(new Fruit("apple")));
        Assert.assertEquals(60, (int) Storage.storage.get(new Fruit("melon")));
        Assert.assertEquals(3, Storage.storage.size());
    }

    @Test
    public void apply_validOutputSupply_Ok() {
        Storage.storage.put(new Fruit("apple"), 50);
        Storage.storage.put(new Fruit("banana"), 30);
        operationHandler.apply(new Fruit("banana"), 40);
        Assert.assertTrue(Storage.storage.containsKey(new Fruit("apple")));
        Assert.assertTrue(Storage.storage.containsKey(new Fruit("banana")));
        Assert.assertTrue(Storage.storage.containsValue(50));
        Assert.assertTrue(Storage.storage.containsValue(70));
        Assert.assertEquals(2, Storage.storage.size());
    }
}
