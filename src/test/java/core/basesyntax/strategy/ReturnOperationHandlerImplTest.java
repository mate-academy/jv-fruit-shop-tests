package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operations.OperationHandler;
import core.basesyntax.strategy.operations.impl.ReturnOperationHandlerImpl;
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
    public void handler_returnOperation_ok() {
        Storage.storageMap.put("banana", 20);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 20);
        operationHandler.handler(fruitTransaction);
        Integer expected = 20 + fruitTransaction.getQuantity();
        Integer actual = Storage.storageMap.get(fruitTransaction.getFruit());
        assertEquals(actual, expected);
    }

    @AfterClass
    public static void afterClass() {
        Storage.storageMap.clear();
    }
}
