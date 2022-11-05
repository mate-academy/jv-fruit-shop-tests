package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import core.basesyntax.service.handler.BalanceOperationHandler;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handler.PurchaseOperationHandler;
import core.basesyntax.service.handler.ReturnOperationHandler;
import core.basesyntax.service.handler.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceImplTest {
    private static final Fruit DEFAULT_FRUIT = new Fruit("banana", 20);
    private static TransactionService transactionService;
    private static OperationStrategy operationStrategy;
    private List<FruitTransaction> transactions;

    @BeforeClass
    public static void initialization() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        transactionService = new TransactionServiceImpl(operationStrategy);
    }

    @Test
    public void balanceBananas_Ok() {
        transactions = List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                DEFAULT_FRUIT, 20));
        transactionService.executeOperation(transactions);
        List<Fruit> expected = List.of(DEFAULT_FRUIT);
        List<Fruit> actual = Storage.fruitList;
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void supplyBananas_Ok() {
        transactions = List.of(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                DEFAULT_FRUIT, 100));
        transactionService.executeOperation(transactions);
        List<Fruit> expected = List.of(new Fruit("banana", 120));
        List<Fruit> actual = Storage.fruitList;
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void purchaseBananas_Ok() {
        transactions = List.of(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                DEFAULT_FRUIT, 5));
        transactionService.executeOperation(transactions);
        List<Fruit> expected = List.of(new Fruit("banana", 15));
        List<Fruit> actual = Storage.fruitList;
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void returnBananas_Ok() {
        transactions = List.of(new FruitTransaction(FruitTransaction.Operation.RETURN,
                DEFAULT_FRUIT, 5));
        transactionService.executeOperation(transactions);
        List<Fruit> expected = List.of(new Fruit("banana", 25));
        List<Fruit> actual = Storage.fruitList;
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void balancePurchaseReturnBananas_Ok() {
        transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, DEFAULT_FRUIT, 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, DEFAULT_FRUIT, 15),
                new FruitTransaction(FruitTransaction.Operation.RETURN, DEFAULT_FRUIT, 2));
        transactionService.executeOperation(transactions);
        List<Fruit> expected = List.of(new Fruit("banana", 7));
        List<Fruit> actual = Storage.fruitList;
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void executeOperation_NullTransaction_NotOk() {
        transactionService.executeOperation(null);
    }

    @Test
    public void executeOperation_NoTransactions_StorageEmpty() {
        transactions = List.of();
        transactionService.executeOperation(transactions);
        List<Fruit> actual = Storage.fruitList;
        Assert.assertTrue(actual.isEmpty());
    }

    @After
    public void clearStorage() {
        Storage.fruitList.clear();
    }
}
