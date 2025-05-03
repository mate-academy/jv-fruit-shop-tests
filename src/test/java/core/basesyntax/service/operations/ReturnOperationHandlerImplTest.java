package core.basesyntax.service.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.template.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerImplTest {
    private static StorageDao storageDao;
    private static OperationHandler balanceHandler;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        balanceHandler = new ReturnOperationHandlerImpl(storageDao);
    }

    @Test (expected = RuntimeException.class)
    public void handleReturnNullFruitTransaction_NotOk() {
        balanceHandler.handle(null);
    }

    @Test
    public void handleReturnCorrectFruitTransaction_Ok() {
        storageDao.put("grape", 20);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "grape", 3);
        balanceHandler.handle(transaction);
        Integer actual = storageDao.get("grape");
        assertEquals(Integer.valueOf(23), actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getCalculationMap().clear();
    }
}
