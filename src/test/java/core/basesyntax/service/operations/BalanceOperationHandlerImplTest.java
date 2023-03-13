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

public class BalanceOperationHandlerImplTest {
    private static StorageDao storageDao;
    private static OperationHandler balanceHandler;
    private static final Integer EXPECTED_QUANTITY = 20;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        balanceHandler = new BalanceOperationHandlerImpl(storageDao);
    }

    @Test
    public void handleBalanceNullFruitTransaction_NotOk() {
        try {
            balanceHandler.handle(null);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown for incorrect input operation");
    }

    @Test
    public void handleBalanceCorrectFruitTransaction_Ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "orange", 20);
        balanceHandler.handle(transaction);
        Integer actual = storageDao.get("orange");
        assertEquals(EXPECTED_QUANTITY, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getCalculationMap().clear();
    }
}
