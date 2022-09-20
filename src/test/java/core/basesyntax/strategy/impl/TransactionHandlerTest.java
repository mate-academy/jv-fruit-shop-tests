package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TransactionHandlerTest {
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
    public void balanceOperationHandler_Ok() {
        int expectedQuantity = POSITIVE_QUANTITY;
        Transaction transaction =
                new Transaction(Transaction.Operation.BALANCE,"Lemon", POSITIVE_QUANTITY);
        BalanceOperationHandlerImpl balanceOperationHandler =
                new BalanceOperationHandlerImpl(storageDao);
        balanceOperationHandler.handle(transaction);
        int actualQuantity = storageDao.getRemainingGoods("Lemon");
        assertEquals(expectedQuantity, actualQuantity);
        balanceOperationHandler.handle(transaction);
        actualQuantity = storageDao.getRemainingGoods("Lemon");
        expectedQuantity = POSITIVE_QUANTITY * 2;
        assertEquals(expectedQuantity, actualQuantity);

    }

    @Test (expected = RuntimeException.class)
    public void balanceOperatorHandler_NotOk() {
        Transaction transaction =
                new Transaction(Transaction.Operation.BALANCE,"Lemon", ZERO_QUANTITY);
        BalanceOperationHandlerImpl balanceOperationHandler =
                new BalanceOperationHandlerImpl(storageDao);
        balanceOperationHandler.handle(transaction);
        transaction.setQuantity(NEGATIVE_QUANTITY);
        balanceOperationHandler.handle(transaction);
    }

    @Test
    public void purchaseOperationHandler_Ok() {
        storageDao.update("Lemon", POSITIVE_QUANTITY * 2);
        int expectedQuantity = POSITIVE_QUANTITY;
        Transaction transaction =
                new Transaction(Transaction.Operation.PURCHASE,"Lemon", POSITIVE_QUANTITY);
        PurchaseOperationHandlerImpl purchaseOperationHandler =
                new PurchaseOperationHandlerImpl(storageDao);
        purchaseOperationHandler.handle(transaction);
        int actualQuantity = storageDao.getRemainingGoods("Lemon");
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test (expected = RuntimeException.class)
    public void purchaseOperationHandler_NotOk() {
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

    @Test
    public void returnOperationHandler_Ok() {
        int expectedQuantity = POSITIVE_QUANTITY;
        Transaction transaction =
                new Transaction(Transaction.Operation.RETURN,"Lemon", POSITIVE_QUANTITY);
        ReturnOperationHandlerImpl returnOperationHandler =
                new ReturnOperationHandlerImpl(storageDao);
        returnOperationHandler.handle(transaction);
        int actualQuantity = storageDao.getRemainingGoods("Lemon");
        assertEquals(expectedQuantity, actualQuantity);
        returnOperationHandler.handle(transaction);
        actualQuantity = storageDao.getRemainingGoods("Lemon");
        expectedQuantity = POSITIVE_QUANTITY * 2;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test (expected = RuntimeException.class)
    public void returnOperationHandler_NotOk() {
        Transaction transaction =
                new Transaction(Transaction.Operation.RETURN,"Lemon", ZERO_QUANTITY);
        ReturnOperationHandlerImpl returnOperationHandler =
                new ReturnOperationHandlerImpl(storageDao);
        returnOperationHandler.handle(transaction);
        transaction.setQuantity(NEGATIVE_QUANTITY);
        returnOperationHandler.handle(transaction);
    }

    @Test
    public void supplyOperationHandler_Ok() {
        int expectedQuantity = POSITIVE_QUANTITY;
        Transaction transaction =
                new Transaction(Transaction.Operation.SUPPLY,"Lemon", POSITIVE_QUANTITY);
        SupplyOperationHandlerImpl supplyOperationHandler =
                new SupplyOperationHandlerImpl(storageDao);
        supplyOperationHandler.handle(transaction);
        int actualQuantity = storageDao.getRemainingGoods("Lemon");
        assertEquals(expectedQuantity, actualQuantity);
        supplyOperationHandler.handle(transaction);
        actualQuantity = storageDao.getRemainingGoods("Lemon");
        expectedQuantity = POSITIVE_QUANTITY * 2;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test (expected = RuntimeException.class)
    public void supplyOperationHandler_NotOk() {
        Transaction transaction =
                new Transaction(Transaction.Operation.SUPPLY,"Lemon", ZERO_QUANTITY);
        SupplyOperationHandlerImpl supplyOperationHandler =
                new SupplyOperationHandlerImpl(storageDao);
        supplyOperationHandler.handle(transaction);
        transaction.setQuantity(NEGATIVE_QUANTITY);
        supplyOperationHandler.handle(transaction);
    }
}
