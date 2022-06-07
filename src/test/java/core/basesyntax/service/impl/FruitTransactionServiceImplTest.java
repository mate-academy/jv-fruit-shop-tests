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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionServiceImplTest {
    private static FruitTransactionService fruitTransactionService;
    private static List<FruitTransaction> transactions;
    private static FruitTransactionStrategy strategy;
    private static Storage fruitsStorage;
    private static Map<FruitTransaction.Operation,OperationHandler> strategyMap;

    @BeforeClass
    public static void beforeClass() {
        transactions = new ArrayList<>();
        fruitsStorage = new Storage();
        strategyMap = new HashMap<>();
        strategy = new FruitTransactionStrategyImpl(strategyMap);
        fruitTransactionService = new FruitTransactionServiceImpl(strategy,fruitsStorage);
    }

    @Before
    public void setUp() {
        strategyMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        strategyMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        strategyMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        strategyMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
    }

    @Test
    public void processTransaction_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        transactions.add(fruitTransaction);
        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction1.setFruit("apple");
        fruitTransaction1.setQuantity(100);
        transactions.add(fruitTransaction1);
        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction2.setFruit("banana");
        fruitTransaction2.setQuantity(100);
        transactions.add(fruitTransaction2);
        FruitTransaction fruitTransaction3 = new FruitTransaction();
        fruitTransaction3.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction3.setFruit("banana");
        fruitTransaction3.setQuantity(13);
        transactions.add(fruitTransaction3);
        FruitTransaction fruitTransaction4 = new FruitTransaction();
        fruitTransaction4.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction4.setFruit("apple");
        fruitTransaction4.setQuantity(10);
        transactions.add(fruitTransaction4);
        FruitTransaction fruitTransaction5 = new FruitTransaction();
        fruitTransaction5.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction5.setFruit("apple");
        fruitTransaction5.setQuantity(20);
        transactions.add(fruitTransaction5);
        FruitTransaction fruitTransaction6 = new FruitTransaction();
        fruitTransaction6.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction6.setFruit("banana");
        fruitTransaction6.setQuantity(5);
        transactions.add(fruitTransaction6);
        FruitTransaction fruitTransaction7 = new FruitTransaction();
        fruitTransaction7.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction7.setFruit("banana");
        fruitTransaction7.setQuantity(50);
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
        fruitTransactionService.processTransaction(transactions);
    }

    @After
    public void tearDown() {
        transactions.clear();
        fruitsStorage.getFruitsStorage().clear();
    }
}
