package core.basesyntax.strategy;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Map;

public class SupplyHandlerImplTest {
    private static OperationHandler operationHandler;
    private static Map<Fruit, Integer> storage;
    private static StorageDao dao;

    @BeforeClass
    public static void beforeClass() {
        dao = new StorageDaoImpl();
        operationHandler = new SupplyHandlerImpl(dao);
        storage = Storage.storage;
    }

    @Before
    public void before() {
        storage.clear();
    }

    @Test
    public void apply_validOutputAdd_Ok() {
        storage.put(new Fruit("banana"), 50);
        operationHandler.apply(new Fruit("apple"), 20);
        operationHandler.apply(new Fruit("melon"), 60);
        Assert.assertTrue(storage.containsKey(new Fruit("banana")));
        Assert.assertTrue(storage.containsKey(new Fruit("apple")));
        Assert.assertTrue(storage.containsKey(new Fruit("melon")));
        Assert.assertEquals(50, (int) storage.get(new Fruit("banana")));
        Assert.assertEquals(20, (int) storage.get(new Fruit("apple")));
        Assert.assertEquals(60, (int) storage.get(new Fruit("melon")));
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void apply_validOutputSupply_Ok() {
        storage.put(new Fruit("apple"), 50);
        storage.put(new Fruit("banana"), 30);
        operationHandler.apply(new Fruit("banana"), 40);
        Assert.assertTrue(storage.containsKey(new Fruit("apple")));
        Assert.assertTrue(storage.containsKey(new Fruit("banana")));
        Assert.assertTrue(storage.containsValue(50));
        Assert.assertTrue(storage.containsValue(70));
        Assert.assertEquals(2, storage.size());
    }
}
