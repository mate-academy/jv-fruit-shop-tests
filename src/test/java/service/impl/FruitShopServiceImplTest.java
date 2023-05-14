package service.impl;

import static org.junit.Assert.assertEquals;

import db.FruitStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import service.FruitShopService;
import strategy.ActivitiesStrategy;
import strategy.ActivitiesStrategyImpl;
import strategy.activities.ActivitiesHandler;
import strategy.activities.BalanceHandler;
import strategy.activities.PurchaseHandler;
import strategy.activities.ReturnHandler;
import strategy.activities.SupplyHandler;

public class FruitShopServiceImplTest {
    private static Map<String, ActivitiesHandler> activitiesHandlerMap = new HashMap<>();
    private static ActivitiesStrategy strategy = new ActivitiesStrategyImpl(activitiesHandlerMap);
    private static FruitShopService fruitShopService = new FruitShopServiceImpl(strategy);
    private static Map<String, Integer> expected = new HashMap<>();
    private static List<FruitTransaction> fruitTransactions = new ArrayList<>();

    @BeforeClass
    public static void beforeClass() {
        activitiesHandlerMap.put("b", new BalanceHandler());
        activitiesHandlerMap.put("s", new SupplyHandler());
        activitiesHandlerMap.put("p", new PurchaseHandler());
        activitiesHandlerMap.put("r", new ReturnHandler());
    }

    @Test
    public void precessValidTransactionOk() {
        expected.put("banana", 20);
        fruitTransactions.add(new FruitTransaction("b", "banana", 20));
        fruitShopService.processData(fruitTransactions);
        assertEquals(expected, FruitStore.supplies);
    }

    @Test
    public void precessThreeValidTransactionsOk() {
        expected.put("banana", 20);
        expected.put("apple", 10);
        fruitTransactions.add(new FruitTransaction("b", "banana", 30));
        fruitTransactions.add(new FruitTransaction("b", "apple", 10));
        fruitTransactions.add(new FruitTransaction("p", "banana", 10));
        fruitShopService.processData(fruitTransactions);
        assertEquals(expected, FruitStore.supplies);
    }

    @Test (expected = RuntimeException.class)
    public void precessNullTransactionsOk() {
        fruitTransactions.add(null);
        fruitShopService.processData(fruitTransactions);
    }

    @After
    public void tearDown() {
        expected.clear();
        FruitStore.supplies.clear();
        fruitTransactions.clear();
    }
}
