package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.After;

public class TransactionHandlerTest {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int POSITIVE_QUANTITY = 1;
    private static final int ZERO_QUANTITY = 0;
    private static final int NEGATIVE_QUANTITY = -1;

    @After
    public void after() {
        Storage.storage.clear();
    }

    @Test
    public void balanceOperationHandlerImplTestOk() {
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

    @Test
    public void balanceOperatorHandlerImplTestNotOk() {
        Transaction transaction =
                new Transaction(Transaction.Operation.BALANCE,"Lemon", ZERO_QUANTITY);
        BalanceOperationHandlerImpl balanceOperationHandler =
                new BalanceOperationHandlerImpl(storageDao);
        try {
            balanceOperationHandler.handle(transaction);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
        transaction.setQuantity(NEGATIVE_QUANTITY);
            try {
                balanceOperationHandler.handle(transaction);
            } catch (Exception e) {
                Assert.assertSame(RuntimeException.class, e.getClass());
            }
    }

    @Test
    public void purchaseOperationHandlerImplTestOk() {
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

    @Test
    public void purchaseOperationHandlerImplTestNotOk() {
        storageDao.update("Lemon", ZERO_QUANTITY);
        Transaction transaction =
                new Transaction(Transaction.Operation.PURCHASE,"Lemon", POSITIVE_QUANTITY);
        PurchaseOperationHandlerImpl purchaseOperationHandler =
                new PurchaseOperationHandlerImpl(storageDao);
        try {
            purchaseOperationHandler.handle(transaction);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
        transaction.setQuantity(ZERO_QUANTITY);
        try {
            purchaseOperationHandler.handle(transaction);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
        transaction.setQuantity(NEGATIVE_QUANTITY);
        try {
            purchaseOperationHandler.handle(transaction);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void returnOperationHandlerImplTestOk() {
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

    @Test
    public void returnOperationHandlerImplTestNotOk() {
        Transaction transaction =
                new Transaction(Transaction.Operation.RETURN,"Lemon", ZERO_QUANTITY);
        ReturnOperationHandlerImpl returnOperationHandler =
                new ReturnOperationHandlerImpl(storageDao);
        try {
            returnOperationHandler.handle(transaction);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
        transaction.setQuantity(NEGATIVE_QUANTITY);
        try {
            returnOperationHandler.handle(transaction);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void supplyOperationHandlerImplTestOk() {
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

    @Test
    public void supplyOperationHandlerTestNotOk() {
        Transaction transaction =
                new Transaction(Transaction.Operation.SUPPLY,"Lemon", ZERO_QUANTITY);
        SupplyOperationHandlerImpl supplyOperationHandler =
                new SupplyOperationHandlerImpl(storageDao);
        try {
            supplyOperationHandler.handle(transaction);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
        transaction.setQuantity(NEGATIVE_QUANTITY);
        try {
            supplyOperationHandler.handle(transaction);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }
}
