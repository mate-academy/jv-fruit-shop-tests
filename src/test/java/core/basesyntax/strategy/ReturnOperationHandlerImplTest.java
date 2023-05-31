package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerImplTest {
    private static OperationHandler operationHandler;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        operationHandler = new ReturnOperationHandlerImpl(storageDao);
    }

    @Test
    public void handle_returnOperation_Ok() {
        int quantity = 70;
        int returnQuantity = 30;
        String apple = "apple";
        Storage.getStorageMap().put(apple, quantity);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, apple, returnQuantity);
        operationHandler.handle(fruitTransaction);
        int expected = quantity + returnQuantity;
        int actual = Storage.getStorageMap().get(apple);
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getStorageMap().clear();
    }
}
