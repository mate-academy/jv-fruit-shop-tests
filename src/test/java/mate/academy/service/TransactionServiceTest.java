package mate.academy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mate.academy.model.FruitTransaction;
import mate.academy.service.impl.TransactionServiceImpl;
import mate.academy.strategy.ActivityStrategy;
import mate.academy.strategy.ActivityStrategyImpl;
import mate.academy.strategy.activities.ActivityHandler;
import mate.academy.strategy.activities.AddActivityHandler;
import mate.academy.strategy.activities.SubtractActivityHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceTest {

    private static ActivityStrategy activityStrategy;

    private static TransactionService transactionService;
    private List<FruitTransaction> fruitTransactionList = new ArrayList<>();
    private Map<String, Integer> expectedResult = new HashMap<>();

    @BeforeClass
    public static void beforeClass() throws Exception {
        Map<FruitTransaction.Operation, ActivityHandler> activityHandlerMap = new HashMap<>();
        activityHandlerMap.put(FruitTransaction.Operation.BALANCE, new AddActivityHandler());
        activityHandlerMap.put(FruitTransaction.Operation.SUPPLY, new AddActivityHandler());
        activityHandlerMap.put(FruitTransaction.Operation.PURCHASE, new SubtractActivityHandler());
        activityHandlerMap.put(FruitTransaction.Operation.RETURN, new AddActivityHandler());
        activityStrategy = new ActivityStrategyImpl(activityHandlerMap);
        transactionService = new TransactionServiceImpl(activityStrategy);
    }

    @After
    public void tearDown() {
        fruitTransactionList.clear();
        expectedResult.clear();
    }

    @Test
    public void processedFruitTransaction_ok() {
        fruitTransactionList
                .add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        fruitTransactionList
                .add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        fruitTransactionList
                .add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        fruitTransactionList
                .add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        fruitTransactionList
                .add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));
        fruitTransactionList
                .add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20));
        fruitTransactionList
                .add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5));
        fruitTransactionList
                .add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
        Map<String, Integer> actualResult = transactionService.processedData(fruitTransactionList);
        expectedResult.put("banana", 152);
        expectedResult.put("apple",90);
        Assert.assertEquals(actualResult,expectedResult);
    }
}
