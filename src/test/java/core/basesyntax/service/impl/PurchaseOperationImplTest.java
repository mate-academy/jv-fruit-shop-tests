package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationImplTest {
    private static StorageDao storageDao;
    private static FruitTransaction transactionApple10;
    private static FruitTransaction transactionOrange20;
    private static FruitTransaction transactionBanana50;
    private static FruitTransaction transactionBanana150;

    @BeforeClass
    public static void beforeAll() {
        storageDao = new StorageDaoImpl();

        transactionApple10 = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                new Fruit("apple"),
                10);

        transactionOrange20 = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                new Fruit("orange"),
                20);

        transactionBanana50 = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                new Fruit("banana"),
                50);

        transactionBanana150 = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                new Fruit("banana"),
                150);
    }

    @Before
    public void beforeEachTest() {
        Storage.getStorage().put(new Fruit("apple"), 50);
        Storage.getStorage().put(new Fruit("banana"), 100);
    }

    @After
    public void afterEachTest() {
        Storage.getStorage().clear();
    }

    @Test(expected = RuntimeException.class)
    public void registerTransaction_nonPresentFruit_NotOk() {
        OperationHandler operationHandler = new PurchaseOperationImpl(storageDao);
        operationHandler.registerTransaction(transactionOrange20);
    }

    @Test
    public void registerTransaction_presentFruit_Ok() {
        OperationHandler operationHandler = new PurchaseOperationImpl(storageDao);
        int expectedQty = Storage.getStorage()
                .get(transactionApple10.getFruit()) - transactionApple10.getQuantity();
        operationHandler.registerTransaction(transactionApple10);
        int actualQty = Storage.getStorage()
                .get(transactionApple10.getFruit());
        assertEquals(expectedQty, actualQty);
        assertEquals(2, Storage.getStorage().size());

        expectedQty = Storage.getStorage()
                .get(transactionBanana50.getFruit()) - transactionBanana50.getQuantity();
        operationHandler.registerTransaction(transactionBanana50);
        actualQty = Storage.getStorage()
                .get(transactionBanana50.getFruit());
        assertEquals(Integer.valueOf(50), Storage.getStorage().get(transactionBanana50.getFruit()));
        assertEquals(expectedQty, actualQty);
        assertEquals(2, Storage.getStorage().size());
    }

    @Test(expected = RuntimeException.class)
    public void registerTransaction_notEnoughFruits_NotOk() {
        OperationHandler operationHandler = new PurchaseOperationImpl(storageDao);
        operationHandler.registerTransaction(transactionBanana150);
    }
}
