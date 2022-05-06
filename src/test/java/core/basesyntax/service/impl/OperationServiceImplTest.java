package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationService;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.strategy.BalanceOperationHandler;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.PurchaseOperationHandler;
import core.basesyntax.service.strategy.ReturnOperationHandler;
import core.basesyntax.service.strategy.SupplyOperationHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationServiceImplTest {
    private static OperationService operationService;
    private static FruitTransaction testTransaction;
    private static final Fruit testFruit = new Fruit("apple");
    private static final int testQuantity = 10;

    @BeforeClass
    public static void setUp() {
        testTransaction = new FruitTransaction();
        testTransaction.setFruit(testFruit);
        testTransaction.setQuantity(testQuantity);
        StorageDao storageDao = new StorageDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(storageDao));
        handlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(storageDao));
        handlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(storageDao));
        handlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(storageDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlerMap);
        operationService = new OperationServiceImpl(operationStrategy);
    }

    @Test
    public void executeTransaction_emptyTransactions_Ok() {
        operationService.executeTransactions(Collections.EMPTY_LIST);
        assertTrue(Storage.storage.isEmpty());
    }

    @Test
    public void executeTransaction_balanceTransaction_Ok() {
        testTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        List<FruitTransaction> transactions = List.of(testTransaction);

        operationService.executeTransactions(transactions);
        int expected = testQuantity;
        int actual = Storage.storage.get(testFruit);
        assertEquals(expected, actual);

        operationService.executeTransactions(transactions);
        expected = testQuantity * 2;
        actual = Storage.storage.get(testFruit);
        assertEquals(expected, actual);
    }

    @Test
    public void executeTransaction_returnTransaction_Ok() {
        testTransaction.setOperation(FruitTransaction.Operation.RETURN);
        List<FruitTransaction> transactions = List.of(testTransaction);

        operationService.executeTransactions(transactions);
        int expected = testQuantity;
        int actual = Storage.storage.get(testFruit);
        assertEquals(expected, actual);

        operationService.executeTransactions(transactions);
        expected = testQuantity * 2;
        actual = Storage.storage.get(testFruit);
        assertEquals(expected, actual);
    }

    @Test
    public void executeTransaction_supplyTransaction_Ok() {
        testTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        List<FruitTransaction> transactions = List.of(testTransaction);

        operationService.executeTransactions(transactions);
        int expected = testQuantity;
        int actual = Storage.storage.get(testFruit);
        assertEquals(expected, actual);

        operationService.executeTransactions(transactions);
        expected = testQuantity * 2;
        actual = Storage.storage.get(testFruit);
        assertEquals(expected, actual);
    }

    @Test
    public void executeTransaction_purchaseTransaction_Ok() {
        testTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        List<FruitTransaction> transactions = List.of(testTransaction);
        operationService.executeTransactions(transactions);

        FruitTransaction purchaseTransaction = new FruitTransaction();
        purchaseTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseTransaction.setFruit(testFruit);
        purchaseTransaction.setQuantity(testQuantity - 5);
        transactions = List.of(purchaseTransaction);
        operationService.executeTransactions(transactions);

        int expected = 5;
        int actual = Storage.storage.get(testFruit);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void executeTransaction_purchaseOperationInsufficientStock_NotOk() {
        testTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        List<FruitTransaction> transactions = List.of(testTransaction);
        operationService.executeTransactions(transactions);

        FruitTransaction purchaseTransaction = new FruitTransaction();
        purchaseTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseTransaction.setFruit(testFruit);
        purchaseTransaction.setQuantity(testQuantity + 5);
        transactions = List.of(purchaseTransaction);
        operationService.executeTransactions(transactions);
    }

    @After
    public void cleanUp() {
        Storage.storage.clear();
    }
}
