package basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import basesyntax.dao.StorageDao;
import basesyntax.dao.StorageDaoImpl;
import basesyntax.db.Storage;
import basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerImplTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        StorageDao storageDao = new StorageDaoImpl();
        operationHandler = new ReturnOperationHandlerImpl(storageDao);
    }

    @Test
    public void return_operation_Ok() {
        int bananaQuantity = 20;
        Storage.storageMap.put("banana", bananaQuantity);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 20);
        operationHandler.changeQuantity(fruitTransaction);
        Integer expected = bananaQuantity + fruitTransaction.getQuantity();
        Integer actual = Storage.storageMap.get(fruitTransaction.getFruit());
        assertEquals(actual, expected);
    }

    @AfterClass
    public static void afterClass() {
        Storage.storageMap.clear();
    }
}
