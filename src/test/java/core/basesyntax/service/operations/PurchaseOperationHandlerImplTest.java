package core.basesyntax.service.operations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.template.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerImplTest {
    private static StorageDao storageDao;
    private static OperationHandler balanceHandler;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        balanceHandler = new PurchaseOperationHandlerImpl(storageDao);
    }

    @Before
    public void setUpBeforeEach() {
        Storage.getCalculationMap().put("apple", 40);
    }

    @Test
    public void handlePurchaseInputNullFruitTransaction_NotOk() {
        try {
            balanceHandler.handle(null);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown for incorrect input operation");
    }

    @Test
    public void handlePurchaseNotEnoughFruit_NotOk() {
        storageDao.put("apple", 40);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 45);
        try {
            balanceHandler.handle(transaction);
        } catch (RuntimeException e) {
            return;
        }
    }

    @Test
    public void handlePurchaseCorrectInputFruitTransaction_Ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 20);
        balanceHandler.handle(transaction);
        Integer actual = storageDao.get("apple");
        assertEquals(Integer.valueOf(20), actual);
    }

    @After
    public void tearDown() {
        Storage.getCalculationMap().clear();
    }
}
