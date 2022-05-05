package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseOperationHandler;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        purchaseOperationHandler = new PurchaseOperationHandler(storageDao);
    }

    @Test
    public void validOnePurchase_Ok() {
        Storage.storage.put(new Fruit("apple"), 50);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        new Fruit("apple"), 20);
        purchaseOperationHandler.operate(fruitTransaction);
        Integer actual = 30;
        assertEquals(actual, Storage.storage.get(new Fruit("apple")));
    }

    @Test (expected = RuntimeException.class)
    public void invalidPurchase_NotOk() {
        Storage.storage.put(new Fruit("banana"), 100);
        FruitTransaction bananaTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        new Fruit("banana"), 110);
        purchaseOperationHandler.operate(bananaTransaction);
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}
