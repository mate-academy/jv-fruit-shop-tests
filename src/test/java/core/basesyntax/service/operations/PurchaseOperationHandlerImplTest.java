package core.basesyntax.service.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.template.FruitTransaction;
import org.junit.AfterClass;
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

    @Test (expected = RuntimeException.class)
    public void handlePurchaseInputNullFruitTransaction_NotOk() {
        balanceHandler.handle(null);
    }

    @Test (expected = RuntimeException.class)
    public void handlePurchaseNotEnoughFruit_NotOk() {
        storageDao.put("apple", 40);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 45);
        balanceHandler.handle(transaction);
    }

    @Test
    public void handlePurchaseCorrectInputFruitTransaction_Ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 20);
        balanceHandler.handle(transaction);
        Integer actual = storageDao.get("apple");
        assertEquals(Integer.valueOf(20), actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getCalculationMap().clear();
    }
}
