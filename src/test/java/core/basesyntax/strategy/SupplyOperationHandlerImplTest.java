package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerImplTest {
    private static OperationHandler operationHandler;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        operationHandler = new SupplyOperationHandlerImpl(storageDao);
    }

    @Test
    public void supplyOperationTest_Ok() {
        int quantity = 150;
        int supplyQuantity = 30;
        String apple = "apple";
        Storage.getStorageMap().put(apple, quantity);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, apple, supplyQuantity);
        operationHandler.handle(fruitTransaction);
        int expected = quantity + supplyQuantity;
        int actual = Storage.getStorageMap().get(apple);
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getStorageMap().clear();
    }
}
