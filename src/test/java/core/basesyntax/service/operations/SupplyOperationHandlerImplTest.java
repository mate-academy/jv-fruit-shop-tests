package core.basesyntax.service.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.template.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerImplTest {
    private static StorageDao storageDao;
    private static OperationHandler balanceHandler;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        balanceHandler = new SupplyOperationHandlerImpl(storageDao);
    }

    @Test (expected = RuntimeException.class)
    public void handleSupplyNullFruitTransaction_NotOk() {
        balanceHandler.handle(null);
    }

    @Test
    public void handleSupplyInputFruitTransaction_Ok() {
        storageDao.put("apple", 20);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", 10);
        balanceHandler.handle(transaction);

        Integer actual = storageDao.get("apple");
        assertEquals(Integer.valueOf(30), actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getCalculationMap().clear();
    }
}
