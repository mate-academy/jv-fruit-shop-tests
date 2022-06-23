package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ActionsDao;
import core.basesyntax.dao.ActionsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.fruit.Fruit;
import core.basesyntax.service.ActionStrategy;
import core.basesyntax.service.BalanceCounter;
import core.basesyntax.service.actiontype.ActionStrategyBalance;
import core.basesyntax.service.actiontype.ActionStrategyProducer;
import core.basesyntax.service.actiontype.ActionStrategyReturner;
import core.basesyntax.service.actiontype.ActionStrategySupplier;
import core.basesyntax.service.actiontype.ActionType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        mapStrategy.put("b", new ActionStrategyBalance());
        mapStrategy.put("p", new ActionStrategyProducer());
        mapStrategy.put("s", new ActionStrategySupplier());
        mapStrategy.put("r", new ActionStrategyReturner());
        actionStrategy = new ActionStrategyImpl(mapStrategy);
    }

    @Before
    public void setUp() throws Exception {
        actionsDao.clear();
        actionsDao.add("apple", 15);
    }

    @Test
    public void correctStrategyBalance_Ok() {
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit("b", "apple", 10));
        BalanceCounter makeBalance = new BalanceCounterImpl(actionsDao);
        makeBalance.calculateBalance(fruits, actionStrategy);
        assertEquals(actionsDao.getAmount("apple"), 15);
    }

    @Test
    public void correctStrategyProducer_Ok() {
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit("p", "apple", 10));
        BalanceCounter makeBalance = new BalanceCounterImpl(actionsDao);
        makeBalance.calculateBalance(fruits, actionStrategy);
        assertEquals(actionsDao.getAmount("apple"), 25);
    }

    @Test
    public void correctStrategySupplier_Ok() {
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit("s", "apple", 10));
        BalanceCounter makeBalance = new BalanceCounterImpl(actionsDao);
        makeBalance.calculateBalance(fruits, actionStrategy);
        assertEquals(actionsDao.getAmount("apple"), 5);
    }

    @Test
    public void correctStrategyReturner_Ok() {
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit("r", "apple", 20));
        BalanceCounter makeBalance = new BalanceCounterImpl(actionsDao);
        makeBalance.calculateBalance(fruits, actionStrategy);
        assertEquals(actionsDao.getAmount("apple"), 35);
    }
}
