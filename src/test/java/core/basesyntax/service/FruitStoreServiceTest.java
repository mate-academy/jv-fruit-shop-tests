package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.impl.FruitStoreServiceImpl;
import core.basesyntax.startegy.ActivityHandler;
import core.basesyntax.startegy.ActivityStrategy;
import core.basesyntax.startegy.ActivityType;
import core.basesyntax.startegy.impl.ActivityStrategyImpl;
import core.basesyntax.startegy.impl.AddActivityHandler;
import core.basesyntax.startegy.impl.BalanceActivityHandler;
import core.basesyntax.startegy.impl.PurchaseActivityHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitStoreServiceTest {
    private static FruitDao fruitDao;
    private static final Map<ActivityType, ActivityHandler> activityMap = new HashMap<>();
    private static ActivityStrategy activityStrategy;
    private static List<TransactionDto> transactions;
    private static FruitStoreService fruitStore;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        activityStrategy = new ActivityStrategyImpl(activityMap);
        transactions = new ArrayList<>();
        fruitStore = new FruitStoreServiceImpl(fruitDao, activityStrategy);
        activityMap.put(ActivityType.BALANCE, new BalanceActivityHandler(fruitDao));
        activityMap.put(ActivityType.PURCHASE, new PurchaseActivityHandler(fruitDao));
        activityMap.put(ActivityType.RETURN, new AddActivityHandler(fruitDao));
        activityMap.put(ActivityType.SUPPLY, new AddActivityHandler(fruitDao));
    }
    
    @Before
    public void beforeEachTest() {
        transactions.add(new TransactionDto("b", "banana", 65));
        transactions.add(new TransactionDto("s", "banana", 5));
        transactions.add(new TransactionDto("r", "banana", 5));
        transactions.add(new TransactionDto("p", "banana", 10));
    }

    @Test
    public void changeBalance_allActivity_ok() {
        List<Fruit> expected = new ArrayList<>();
        expected.add(new Fruit("banana", 65));
        List<Fruit> actual = fruitStore.changeBalance(transactions);
        assertEquals(expected, actual);
    }

    @Test
    public void changeBalance_supply_ok() {
        transactions.add(new TransactionDto("s", "banana", 10));
        List<Fruit> expected = new ArrayList<>();
        expected.add(new Fruit("banana", 75));
        List<Fruit> actual = fruitStore.changeBalance(transactions);
        assertEquals(expected, actual);
    }

    @Test
    public void changeBalance_return_ok() {
        transactions.add(new TransactionDto("r", "banana", 5));
        List<Fruit> expected = new ArrayList<>();
        expected.add(new Fruit("banana", 70));
        List<Fruit> actual = fruitStore.changeBalance(transactions);
        assertEquals(expected, actual);
    }

    @Test
    public void changeBalance_purchase_ok() {
        transactions.add(new TransactionDto("p", "banana", 10));
        List<Fruit> expected = new ArrayList<>();
        expected.add(new Fruit("banana", 55));
        List<Fruit> actual = fruitStore.changeBalance(transactions);
        assertEquals(expected, actual);
    }

    @Test
    public void changeBalance_balance_ok() {
        transactions.add(new TransactionDto("b", "banana", 100));
        List<Fruit> expected = new ArrayList<>();
        expected.add(new Fruit("banana", 100));
        List<Fruit> actual = fruitStore.changeBalance(transactions);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void changeBalance_purchaseMoreThanBalance_notOk() {
        transactions.add(new TransactionDto("p", "banana", 75));
        fruitStore.changeBalance(transactions);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
        transactions.clear();
    }
}
