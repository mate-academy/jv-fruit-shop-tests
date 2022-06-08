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
    private static Map<FruitTransaction.Operation,OperationHandler> strategyMap;

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
        fruitTransactionService = new FruitTransactionServiceImpl(strategy,fruitsStorage);
    }

    @Test
    public void processTransaction_Ok() {
        List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction fruitTransaction = createFruitTransaction("banana", 20,
                FruitTransaction.Operation.BALANCE);
        transactions.add(fruitTransaction);
        FruitTransaction fruitTransaction1 = createFruitTransaction("apple", 100,
                FruitTransaction.Operation.BALANCE);
        transactions.add(fruitTransaction1);
        FruitTransaction fruitTransaction2 = createFruitTransaction("banana", 100,
                FruitTransaction.Operation.SUPPLY);
        transactions.add(fruitTransaction2);
        FruitTransaction fruitTransaction3 = createFruitTransaction("banana", 13,
                FruitTransaction.Operation.PURCHASE);
        transactions.add(fruitTransaction3);
        FruitTransaction fruitTransaction4 = createFruitTransaction("apple", 10,
                FruitTransaction.Operation.RETURN);
        transactions.add(fruitTransaction4);
        FruitTransaction fruitTransaction5 = createFruitTransaction("apple", 20,
                FruitTransaction.Operation.PURCHASE);
        transactions.add(fruitTransaction5);
        FruitTransaction fruitTransaction6 = createFruitTransaction("banana", 5,
                FruitTransaction.Operation.PURCHASE);
        transactions.add(fruitTransaction6);
        FruitTransaction fruitTransaction7 = createFruitTransaction("banana", 50,
                FruitTransaction.Operation.SUPPLY);
        transactions.add(fruitTransaction7);
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

    private FruitTransaction createFruitTransaction(String fruit, int quantity,
                                                    FruitTransaction.Operation operation) {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(quantity);
        fruitTransaction.setOperation(operation);
        return fruitTransaction;
    }

    @After
    public void tearDown() {
        fruitsStorage.getFruitsStorage().clear();
    }
}
