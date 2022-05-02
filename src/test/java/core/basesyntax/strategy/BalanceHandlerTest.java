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

public class BalanceHandlerTest {
    private static StorageDao dao;
    private static OperationHandler operationHandler;
    private static Map<Fruit, Integer> storage;

    @BeforeClass
    public static void beforeClass() {
        dao = new StorageDaoImpl();
        operationHandler = new BalanceHandler(dao);
        storage = Storage.storage;
    }

    @Before
    public void before() {
        storage = Storage.storage;
    }

    @Test
    public void apply_validOutput_Ok() {
        operationHandler.apply(new Fruit("banana"), 100);
        Assert.assertTrue(storage.containsKey(new Fruit("banana")));
        Assert.assertTrue(storage.containsValue(100));
        Assert.assertEquals(1, storage.size());
    }
}
