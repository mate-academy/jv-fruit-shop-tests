package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operations.OperationHandler;
import core.basesyntax.strategy.operations.impl.SupplyOperationHandlerImpl;
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
    public void handler_supplyOperation_ok() {
        Storage.storageMap.put("banana", 20);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 10);
        operationHandler.handler(fruitTransaction);
        Integer expected = 20 + fruitTransaction.getQuantity();
        Integer actual = Storage.storageMap.get("banana");
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.storageMap.clear();
    }
}
