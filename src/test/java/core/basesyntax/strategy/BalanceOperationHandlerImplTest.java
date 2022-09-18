package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operations.OperationHandler;
import core.basesyntax.strategy.operations.impl.BalanceOperationHandlerImpl;
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
    public void handler_balanceOperation_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20);
        operationHandler.handler(fruitTransaction);
        Integer expected = fruitTransaction.getQuantity();
        Integer actual = Storage.storageMap.get(fruitTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.storageMap.clear();
    }
}
