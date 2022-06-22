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
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceCounterImplTest {
    private static ActionsDao actionsDao;
    private static BalanceCounter makeBalance;
    private static ActionStrategy actionStrategy;
    private static final Map<String, ActionType> mapStrategy = new HashMap<>();

    @BeforeClass
    public static void beforeClass() {
        makeBalance = new BalanceCounterImpl();
        actionsDao = new ActionsDaoImpl();
        mapStrategy.put("b", new ActionStrategyBalance());
        mapStrategy.put("p", new ActionStrategyProducer());
        mapStrategy.put("s", new ActionStrategySupplier());
        mapStrategy.put("r", new ActionStrategyReturner());
        actionStrategy = new ActionStrategyImpl(mapStrategy);
    }

    @Test
    public void correctChangeAmount_Ok() {
        actionsDao = new ActionsDaoImpl();
        actionsDao.add("apple", 15);
        actionsDao.add("orange", 20);
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit("s", "apple", 5));
        fruits.add(new Fruit("r", "apple", 2));
        fruits.add(new Fruit("p", "orange", 20));
        fruits.add(new Fruit("r", "orange", 40));
        BalanceCounter makeBalance = new BalanceCounterImpl();
        makeBalance.calculateBalance(fruits, actionStrategy);
        assertEquals(actionsDao.getAmount("apple"), 8);
        assertEquals(actionsDao.getAmount("orange"), 0);
    }

    @After
    public void tearDown() throws Exception {
        Storage.data.clear();
    }
}
