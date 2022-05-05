package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.StorageDao;
import core.basesyntax.storage.StorageDaoImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerTest {
    private static StorageDao storageDao;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void init() {
        storageDao = new StorageDaoImpl();

    }

    @After
    public void clearStorage() {
        FruitStorage.fruits.clear();
    }

    @Test
    public void handleBalanceOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                new Fruit("banana"),
                200);
        operationHandler = new BalanceOperationHandler(storageDao);
        operationHandler.handle(transaction);
        int expected = 200;
        int actual = FruitStorage.fruits.get(new Fruit("banana"));
        assertEquals(expected, actual);
    }

    @Test
    public void handlePurchaseOperation_Ok() {
        FruitStorage.fruits.put(new Fruit("apple"), 100);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                new Fruit("apple"),
                40);
        operationHandler = new PurchaseOperationHandler(storageDao);
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
        operationHandler = new PurchaseOperationHandler(storageDao);
        operationHandler.handle(transaction);
    }

    @Test(expected = RuntimeException.class)
    public void handlePurchaseOperationWithNotExistingFruit_NotOk() {
        FruitStorage.fruits.put(new Fruit("orange"), 100);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                new Fruit("pineapple"),
                20);
        operationHandler = new PurchaseOperationHandler(storageDao);
        operationHandler.handle(transaction);
    }

    @Test
    public void handleReturnOperation_Ok() {
        FruitStorage.fruits.put(new Fruit("kiwi"), 20);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                new Fruit("kiwi"),
                10);
        operationHandler = new ReturnOperationHandler(storageDao);
        operationHandler.handle(transaction);
        int expected = 30;
        int actual = FruitStorage.fruits.get(new Fruit("kiwi"));
        assertEquals(expected, actual);
    }

    @Test
    public void handleSupplyOperation_Ok() {
        FruitStorage.fruits.put(new Fruit("orange"), 42);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                new Fruit("orange"),
                60);
        operationHandler = new SupplyOperationHandler(storageDao);
        operationHandler.handle(transaction);
        int expected = 102;
        int actual = FruitStorage.fruits.get(new Fruit("orange"));
        assertEquals(expected, actual);
    }
}
