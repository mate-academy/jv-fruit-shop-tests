package core.basesyntax.strategy.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    public static final int POSITIVE_QUANTITY = 1;
    public static final int ZERO_QUANTITY = 0;
    public static final int NEGATIVE_QUANTITY = -1;
    private StorageDao storageDao;

    @Before
    public void before() {
        storageDao = new StorageDaoImpl();
    }

    @After
    public void after() {
        Storage.storage.clear();
    }

    @Test
    public void handle_purchaseOperation_Ok() {
        storageDao.update("Lemon", POSITIVE_QUANTITY * 2);
        int expectedQuantity = POSITIVE_QUANTITY;
        Transaction transaction =
                new Transaction(Transaction.Operation.PURCHASE,"Lemon", POSITIVE_QUANTITY);
        PurchaseOperationHandlerImpl purchaseOperationHandler =
                new PurchaseOperationHandlerImpl(storageDao);
        purchaseOperationHandler.handle(transaction);
        int actualQuantity = storageDao.getRemainingGoods("Lemon");
        Assert.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test (expected = RuntimeException.class)
    public void handle_purchaseOperation_NotOk() {
        storageDao.update("Lemon", ZERO_QUANTITY);
        Transaction transaction =
                new Transaction(Transaction.Operation.PURCHASE,"Lemon", POSITIVE_QUANTITY);
        PurchaseOperationHandlerImpl purchaseOperationHandler =
                new PurchaseOperationHandlerImpl(storageDao);
        purchaseOperationHandler.handle(transaction);
        transaction.setQuantity(ZERO_QUANTITY);
        purchaseOperationHandler.handle(transaction);
        transaction.setQuantity(NEGATIVE_QUANTITY);
        purchaseOperationHandler.handle(transaction);
    }
}
