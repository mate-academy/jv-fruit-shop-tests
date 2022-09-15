package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerImplTest {
    private static StorageDao storageDao;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        storageDao = new StorageDaoImpl();
        operationHandler = new PurchaseOperationHandlerImpl(storageDao);
    }

    @Test
    public void purchaseOperationTest_Ok() {
        int quantity = 50;
        int purchaseQuantity = 20;
        String apple = "apple";
        Storage.getStorageMap().put(apple, quantity);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, apple, purchaseQuantity);
        operationHandler.handle(fruitTransaction);
        int expected = quantity - purchaseQuantity;
        int actual = Storage.getStorageMap().get(apple);
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.getStorageMap().clear();
    }
}
