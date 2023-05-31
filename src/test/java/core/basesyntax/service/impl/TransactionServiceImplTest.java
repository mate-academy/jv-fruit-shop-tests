package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceImplTest {
    private static final Fruit FRUIT_BANANA = new Fruit("banana");
    private static final Fruit FRUIT_APPLE = new Fruit("apple");
    private static final int DEFAULT_QUANTITY = 111;
    private static final Map<Fruit, Integer> storage = Storage.storage;
    private static TransactionServiceImpl transactionService;
    private static List<FruitTransaction> testTransactionsList;
    private static Map<Fruit, Integer> expectedStorageMap;

    @BeforeClass
    public static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        OperationStrategy strategy = new OperationStrategyImpl(operationHandlerMap);
        transactionService = new TransactionServiceImpl(strategy, new StorageDaoImpl());
        testTransactionsList = new ArrayList<>();
        expectedStorageMap = new HashMap<>();
    }

    @Test
    public void applyTransactions_withEmptyTransactionsList_Ok() {
        transactionService.applyTransactions(Collections.emptyList());
        assertEquals(expectedStorageMap, storage);
    }

    @Test
    public void applyTransactions_applySingleTransaction_Ok() {
        expectedStorageMap.put(FRUIT_BANANA, DEFAULT_QUANTITY);
        testTransactionsList.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, FRUIT_BANANA, DEFAULT_QUANTITY));
        transactionService.applyTransactions(testTransactionsList);
        assertEquals(expectedStorageMap, storage);
    }

    @Test
    public void applyTransactions_applyMultipleTransaction_Ok() {
        expectedStorageMap.put(FRUIT_BANANA, 0);
        testTransactionsList.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, FRUIT_BANANA, DEFAULT_QUANTITY));
        testTransactionsList.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, FRUIT_BANANA, DEFAULT_QUANTITY));
        transactionService.applyTransactions(testTransactionsList);
        assertEquals(expectedStorageMap, storage);
    }

    @Test
    public void applyTransactions_applyMultipleFruitsTransaction_Ok() {
        expectedStorageMap.put(FRUIT_BANANA, 0);
        expectedStorageMap.put(FRUIT_APPLE, 0);
        testTransactionsList.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, FRUIT_BANANA, DEFAULT_QUANTITY));
        testTransactionsList.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, FRUIT_APPLE, DEFAULT_QUANTITY));
        testTransactionsList.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, FRUIT_BANANA, DEFAULT_QUANTITY));
        testTransactionsList.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, FRUIT_APPLE, DEFAULT_QUANTITY));
        transactionService.applyTransactions(testTransactionsList);
        assertEquals(expectedStorageMap, storage);
    }

    @Test
    public void applyTransactions_applyAllPossibleTransactions_Ok() {
        expectedStorageMap.put(FRUIT_BANANA, 152);
        expectedStorageMap.put(FRUIT_APPLE, 90);
        testTransactionsList.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, FRUIT_BANANA, 20));
        testTransactionsList.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, FRUIT_APPLE, 100));
        testTransactionsList.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, FRUIT_BANANA, 100));
        testTransactionsList.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, FRUIT_BANANA, 13));
        testTransactionsList.add(new FruitTransaction(
                FruitTransaction.Operation.RETURN, FRUIT_APPLE, 10));
        testTransactionsList.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, FRUIT_APPLE, 20));
        testTransactionsList.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, FRUIT_BANANA, 5));
        testTransactionsList.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, FRUIT_BANANA, 50));
        transactionService.applyTransactions(testTransactionsList);
        assertEquals(expectedStorageMap, storage);
    }

    @After
    public void afterEach() {
        testTransactionsList.clear();
        storage.clear();
        expectedStorageMap.clear();
    }
}
