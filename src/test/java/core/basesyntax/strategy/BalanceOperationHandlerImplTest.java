package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerImplTest {
    private static OperationHandler operationHandler;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        operationHandler = new BalanceOperationHandlerImpl(storageDao);
    }

    @Test
    public void operationWithQuantity_balanceOperation_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 50);
        operationHandler.handle(fruitTransaction);
        int expected = fruitTransaction.getQuantity();
        int actual = Storage.getStorageMap().get(fruitTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getStorageMap().clear();
    }
}
