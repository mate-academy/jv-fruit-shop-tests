package basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import basesyntax.dao.StorageDao;
import basesyntax.dao.StorageDaoImpl;
import basesyntax.db.Storage;
import basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerImplTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        StorageDao storageDao = new StorageDaoImpl();
        operationHandler = new BalanceOperationHandlerImpl(storageDao);
    }

    @Test
    public void changeQuantity_balanceOperation_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20);
        operationHandler.changeQuantity(fruitTransaction);
        Integer expected = fruitTransaction.getQuantity();
        Integer actual = Storage.storageMap.get(fruitTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.storageMap.clear();
    }
}
