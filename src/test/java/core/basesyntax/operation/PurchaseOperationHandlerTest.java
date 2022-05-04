package core.basesyntax.operation;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseHandler;
    private static StorageDao storageDao;

    @BeforeClass
    public static void setUp() {
        storageDao = new StorageDaoImpl();
        purchaseHandler = new PurchaseOperationHandler(storageDao);
    }

    @Test
    public void apply_emptyData_Ok() {
        purchaseHandler.apply(new Fruit("apple"), 0);
    }

    @Test(expected = RuntimeException.class)
    public void apply_notEnoughQuantity_NotOK() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 0);
        purchaseHandler.apply(new Fruit("apple"), 1);
    }

    @Test
    public void apply_enoughQuantity_Ok() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 0);
        Assert.assertTrue(purchaseHandler.apply(apple, 0));
    }

    @After
    public void cleanUp() {
        Storage.storage.clear();
    }
}
