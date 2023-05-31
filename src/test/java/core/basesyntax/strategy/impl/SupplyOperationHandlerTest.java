package core.basesyntax.strategy.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
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
    public void handle_supplyOperation_Ok() {
        int expectedQuantity = POSITIVE_QUANTITY;
        Transaction transaction =
                new Transaction(Transaction.Operation.SUPPLY,"Lemon", POSITIVE_QUANTITY);
        SupplyOperationHandlerImpl supplyOperationHandler =
                new SupplyOperationHandlerImpl(storageDao);
        supplyOperationHandler.handle(transaction);
        int actualQuantity = storageDao.getRemainingGoods("Lemon");
        Assert.assertEquals(expectedQuantity, actualQuantity);
        supplyOperationHandler.handle(transaction);
        actualQuantity = storageDao.getRemainingGoods("Lemon");
        expectedQuantity = POSITIVE_QUANTITY * 2;
        Assert.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test (expected = RuntimeException.class)
    public void handle_supplyOperation_NotOk() {
        Transaction transaction =
                new Transaction(Transaction.Operation.SUPPLY,"Lemon", ZERO_QUANTITY);
        SupplyOperationHandlerImpl supplyOperationHandler =
                new SupplyOperationHandlerImpl(storageDao);
        supplyOperationHandler.handle(transaction);
        transaction.setQuantity(NEGATIVE_QUANTITY);
        supplyOperationHandler.handle(transaction);
    }
}
