package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ActionsDao;
import core.basesyntax.dao.ActionsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.fruit.FruitTransaction;
import core.basesyntax.service.ActionStrategy;
import core.basesyntax.service.BalanceCounter;
import core.basesyntax.service.actiontype.ActionStrategyBalance;
import core.basesyntax.service.actiontype.ActionStrategyPurchase;
import core.basesyntax.service.actiontype.ActionStrategyReturner;
import core.basesyntax.service.actiontype.ActionStrategySupplier;
import core.basesyntax.service.actiontype.ActionType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceCounterImplTest {
    private static ActionsDao actionsDao;
    private static ActionStrategy actionStrategy;
    private static final Map<String, ActionType> mapStrategy = new HashMap<>();

    @BeforeClass
    public static void beforeClass() {
        actionsDao = new ActionsDaoImpl(Storage.data);
        BalanceCounter makeBalance = new BalanceCounterImpl(actionsDao);
        mapStrategy.put("b", new ActionStrategyBalance(actionsDao));
        mapStrategy.put("p", new ActionStrategyPurchase(actionsDao));
        mapStrategy.put("s", new ActionStrategySupplier(actionsDao));
        mapStrategy.put("r", new ActionStrategyReturner(actionsDao));
        actionStrategy = new ActionStrategyImpl(mapStrategy);
    }

    @Before
    public void setUp() {
        actionsDao.add("apple", 15);
    }

    @Test
    public void calculateBalance_validStrategyBalance_ok() {
        List<FruitTransaction> fruits = new ArrayList<>();
        fruits.add(new FruitTransaction("b", "apple", 10));
        BalanceCounter makeBalance = new BalanceCounterImpl(actionsDao);
        makeBalance.calculateBalance(fruits, actionStrategy);
        assertEquals(actionsDao.getAmount("apple"), 10);
    }

    @Test
    public void calculateBalance_validStrategyPurchase_oOk() {
        List<FruitTransaction> fruits = new ArrayList<>();
        fruits.add(new FruitTransaction("p", "apple", 10));
        BalanceCounter makeBalance = new BalanceCounterImpl(actionsDao);
        makeBalance.calculateBalance(fruits, actionStrategy);
        assertEquals(actionsDao.getAmount("apple"), 5);
    }

    @Test
    public void calculateBalance_validStrategySupplier_ok() {
        List<FruitTransaction> fruits = new ArrayList<>();
        fruits.add(new FruitTransaction("s", "apple", 10));
        BalanceCounter makeBalance = new BalanceCounterImpl(actionsDao);
        makeBalance.calculateBalance(fruits, actionStrategy);
        assertEquals(actionsDao.getAmount("apple"), 25);
    }

    @Test
    public void calculateBalance_validStrategyReturner_ok() {
        List<FruitTransaction> fruits = new ArrayList<>();
        fruits.add(new FruitTransaction("r", "apple", 20));
        BalanceCounter makeBalance = new BalanceCounterImpl(actionsDao);
        makeBalance.calculateBalance(fruits, actionStrategy);
        assertEquals(actionsDao.getAmount("apple"), 35);
    }

    @After
    public void tearDown() {
        actionsDao.clear();
    }
}
