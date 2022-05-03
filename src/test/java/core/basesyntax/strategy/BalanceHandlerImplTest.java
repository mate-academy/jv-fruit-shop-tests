package core.basesyntax.strategy;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerImplTest {
    private static StorageDao dao;
    private static BalanceHandler operationHandler;
    private static Map<Fruit, Integer> storage;

    @BeforeClass
    public static void beforeClass() {
        dao = new StorageDaoImpl();
        operationHandler = new BalanceHandler(dao);
        storage = Storage.storage;
    }

    @Test
    public void apply_validOutput_Ok() {
        operationHandler.apply(new Fruit("banana"), 100);
        Assert.assertTrue(Storage.storage.containsKey(new Fruit("banana")));
        Assert.assertTrue(Storage.storage.containsValue(100));
        Assert.assertEquals(1, Storage.storage.size());
    }
}
