package basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import basesyntax.dao.StorageDao;
import basesyntax.dao.StorageDaoImpl;
import basesyntax.db.Storage;
import basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerImplTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        StorageDao storageDao = new StorageDaoImpl();
        operationHandler = new SupplyOperationHandlerImpl(storageDao);
    }

    @Test
    public void supply_operation_Ok() {
        int bananaQuantity = 20;
        Storage.storageMap.put("banana", bananaQuantity);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 10);
        operationHandler.changeQuantity(fruitTransaction);
        Integer expected = bananaQuantity + fruitTransaction.getQuantity();
        Integer actual = Storage.storageMap.get("banana");
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.storageMap.clear();
    }
}
