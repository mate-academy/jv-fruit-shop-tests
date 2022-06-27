package basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import basesyntax.dao.StorageDao;
import basesyntax.dao.StorageDaoImpl;
import basesyntax.db.Storage;
import basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerImplTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        StorageDao storageDao = new StorageDaoImpl();
        operationHandler = new PurchaseOperationHandlerImpl(storageDao);
    }

    @Test
    public void changeQuantity_purchaseOperation_ok() {
        int quantityBanana = 20;
        Storage.storageMap.put("banana", quantityBanana);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 10);
        operationHandler.changeQuantity(fruitTransaction);
        Integer expected = quantityBanana - fruitTransaction.getQuantity();
        Integer actual = Storage.storageMap.get("banana");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void changeQuantity_overPurchaseOperation_notOk() {
        Storage.storageMap.put("banana", 20);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 21);
        operationHandler.changeQuantity(fruitTransaction);
    }

    @AfterClass
    public static void afterClass() {
        Storage.storageMap.clear();
    }
}
