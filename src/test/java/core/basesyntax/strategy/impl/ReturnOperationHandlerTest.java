package core.basesyntax.strategy.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
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
    public void handle_returnOperation_Ok() {
        int expectedQuantity = POSITIVE_QUANTITY;
        Transaction transaction =
                new Transaction(Transaction.Operation.RETURN,"Lemon", POSITIVE_QUANTITY);
        ReturnOperationHandlerImpl returnOperationHandler =
                new ReturnOperationHandlerImpl(storageDao);
        returnOperationHandler.handle(transaction);
        int actualQuantity = storageDao.getRemainingGoods("Lemon");
        Assert.assertEquals(expectedQuantity, actualQuantity);
        returnOperationHandler.handle(transaction);
        actualQuantity = storageDao.getRemainingGoods("Lemon");
        expectedQuantity = POSITIVE_QUANTITY * 2;
        Assert.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test (expected = RuntimeException.class)
    public void handle_returnOperation_NotOk() {
        Transaction transaction =
                new Transaction(Transaction.Operation.RETURN,"Lemon", ZERO_QUANTITY);
        ReturnOperationHandlerImpl returnOperationHandler =
                new ReturnOperationHandlerImpl(storageDao);
        returnOperationHandler.handle(transaction);
        transaction.setQuantity(NEGATIVE_QUANTITY);
        returnOperationHandler.handle(transaction);
    }
}
