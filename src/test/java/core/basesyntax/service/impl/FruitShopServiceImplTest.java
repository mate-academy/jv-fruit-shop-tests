package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStore;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.ActivitiesStrategy;
import core.basesyntax.strategy.ActivitiesStrategyImpl;
import core.basesyntax.strategy.activities.ActivitiesHandler;
import core.basesyntax.strategy.activities.BalanceHandler;
import core.basesyntax.strategy.activities.PurchaseHandler;
import core.basesyntax.strategy.activities.ReturnHandler;
import core.basesyntax.strategy.activities.SupplyHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private static FruitShopService fruitShopService;
    private static ActivitiesStrategy strategy;
    private static Map<String, ActivitiesHandler> activitiesHandlerMap;
    private static Map<String, Integer> expected;
    private static List<FruitTransaction> fruitTransactions;

    @BeforeAll
    static void beforeAll() {
        activitiesHandlerMap = new HashMap<>();
        activitiesHandlerMap.put("b", new BalanceHandler());
        activitiesHandlerMap.put("s", new SupplyHandler());
        activitiesHandlerMap.put("p", new PurchaseHandler());
        activitiesHandlerMap.put("r", new ReturnHandler());
        strategy = new ActivitiesStrategyImpl(activitiesHandlerMap);
        fruitShopService = new FruitShopServiceImpl(strategy);
        fruitTransactions = new ArrayList<>();
        expected = new HashMap<>();
    }

    @Test
    void precessValidTransactionOk() {
        expected.put("banana", 20);
        fruitTransactions.add(new FruitTransaction("b", "banana", 20));
        fruitShopService.processData(fruitTransactions);
        assertEquals(expected, FruitStore.supplies);
    }

    @Test
    void precessThreeValidTransactionsOk() {
        expected.put("banana", 20);
        expected.put("apple", 10);
        fruitTransactions.add(new FruitTransaction("b", "banana", 30));
        fruitTransactions.add(new FruitTransaction("b", "apple", 10));
        fruitTransactions.add(new FruitTransaction("p", "banana", 10));
        fruitShopService.processData(fruitTransactions);
        assertEquals(expected, FruitStore.supplies);
    }

    @Test
    void precessNullTransactionsOk() {
        expected.put(null, null);
        fruitTransactions.add(null);
        assertThrows(RuntimeException.class, () -> fruitShopService.processData(fruitTransactions));
    }

    @AfterEach
    void tearDown() {
        expected.clear();
        FruitStore.supplies.clear();
        fruitTransactions.clear();
    }
}
