package core.basesyntax.service.operations;

import static org.junit.Assert.assertEquals;

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

    @Test (expected = RuntimeException.class)
    public void handleBalanceNullFruitTransaction_NotOk() {
        balanceHandler.handle(null);
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
