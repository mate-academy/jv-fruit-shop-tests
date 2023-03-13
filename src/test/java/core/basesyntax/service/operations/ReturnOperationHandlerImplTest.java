package core.basesyntax.service.operations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

    @Test
    public void handleReturnNullFruitTransaction_NotOk() {
        try {
            balanceHandler.handle(null);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown for incorrect input operation");
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
