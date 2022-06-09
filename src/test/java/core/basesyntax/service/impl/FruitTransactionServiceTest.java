package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.FruitTransactionStrategy;
import core.basesyntax.strategy.FruitTransactionStrategyImpl;
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
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionServiceTest {
    private static FruitTransactionService fruitTransactionService;
    private static FruitTransactionStrategy strategy;
    private static Storage fruitsStorage;
    private static Map<FruitTransaction.Operation, OperationHandler> strategyMap;

    @BeforeClass
    public static void beforeClass() {
        fruitsStorage = new Storage();
        strategyMap = new HashMap<>();
        strategyMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        strategyMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        strategyMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        strategyMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        strategy = new FruitTransactionStrategyImpl(strategyMap);
        fruitTransactionService = new FruitTransactionServiceImpl(strategy, fruitsStorage);
    }

    @Test
    public void processTransaction_Ok() {
        List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction fruitTransaction = createFruitTransaction("banana", 20,
                FruitTransaction.Operation.BALANCE);
        transactions.add(fruitTransaction);
        FruitTransaction firstFruitTransaction = createFruitTransaction("apple", 100,
                FruitTransaction.Operation.BALANCE);
        transactions.add(firstFruitTransaction);
        FruitTransaction secondFruitTransaction = createFruitTransaction("banana", 100,
                FruitTransaction.Operation.SUPPLY);
        transactions.add(secondFruitTransaction);
        FruitTransaction thirdFruitTransaction = createFruitTransaction("banana", 13,
                FruitTransaction.Operation.PURCHASE);
        transactions.add(thirdFruitTransaction);
        FruitTransaction fourthFruitTransaction = createFruitTransaction("apple", 10,
                FruitTransaction.Operation.RETURN);
        transactions.add(fourthFruitTransaction);
        FruitTransaction fifthFruitTransaction = createFruitTransaction("apple", 20,
                FruitTransaction.Operation.PURCHASE);
        transactions.add(fifthFruitTransaction);
        FruitTransaction sixthFruitTransaction = createFruitTransaction("banana", 5,
                FruitTransaction.Operation.PURCHASE);
        transactions.add(sixthFruitTransaction);
        FruitTransaction seventhFruitTransaction = createFruitTransaction("banana", 50,
                FruitTransaction.Operation.SUPPLY);
        transactions.add(seventhFruitTransaction);
        fruitTransactionService.processTransaction(transactions);
        Map<String, Integer> expectedResult = new HashMap<>();
        expectedResult.put("banana", 152);
        expectedResult.put("apple", 90);
        Map<String, Integer> actualResult = fruitsStorage.getFruitsStorage();
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void processTransaction_emptyInput_notOk() {
        fruitTransactionService.processTransaction(Collections.emptyList());
    }

    @After
    public void tearDown() {
        fruitsStorage.getFruitsStorage().clear();
    }

    private FruitTransaction createFruitTransaction(String fruit, int quantity,
                                                    FruitTransaction.Operation operation) {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(quantity);
        fruitTransaction.setOperation(operation);
        return fruitTransaction;
    }
}
