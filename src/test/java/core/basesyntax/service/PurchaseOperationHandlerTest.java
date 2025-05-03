package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.StorageDao;
import core.basesyntax.storage.StorageDaoImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static StorageDao storageDao;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void init() {
        storageDao = new StorageDaoImpl();
        operationHandler = new PurchaseOperationHandler(storageDao);
    }

    @After
    public void clearStorage() {
        FruitStorage.fruits.clear();
    }

    @Test
    public void handlePurchaseOperation_Ok() {
        FruitStorage.fruits.put(new Fruit("apple"), 100);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                new Fruit("apple"),
                40);
        operationHandler.handle(transaction);
        int expected = 60;
        int actual = FruitStorage.fruits.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handlePurchaseOperationWithNotExistingQuantity_NotOk() {
        FruitStorage.fruits.put(new Fruit("orange"), 100);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                new Fruit("orange"),
                200);
        operationHandler.handle(transaction);
    }

    @Test(expected = RuntimeException.class)
    public void handlePurchaseOperationWithNotExistingFruit_NotOk() {
        FruitStorage.fruits.put(new Fruit("orange"), 100);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                new Fruit("pineapple"),
                20);
        operationHandler.handle(transaction);
    }
}
