package core.basesyntax.service.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.ActivityType;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.ActivityHandler;
import core.basesyntax.strategy.ActivityStrategy;
import core.basesyntax.strategy.impl.ActivityStrategyImpl;
import core.basesyntax.strategy.impl.BalanceActivityHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static FruitService fruitService;

    @BeforeClass
    public static void beforeClass() {
        Map<ActivityType, ActivityHandler> activityMap = new HashMap<>();
        activityMap.put(ActivityType.BALANCE, new BalanceActivityHandler());
        ActivityStrategy activityStrategy = new ActivityStrategyImpl(activityMap);
        fruitService = new FruitServiceImpl(activityStrategy);
    }

    @Test
    public void processTransaction_listIsNull_notOk() {
        try {
            fruitService.processTransaction(null);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail(
                "RuntimeException should be thrown if the list is null");
    }

    @Test
    public void processTransaction_successfulProcessing_ok() {
        Fruit banana = new Fruit("banana");
        Fruit apple = new Fruit("apple");
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(
                ActivityType.BALANCE, banana, 20));
        fruitTransactionList.add(new FruitTransaction(
                ActivityType.BALANCE, apple, 100));
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(banana, 20);
        expected.put(apple, 100);
        fruitService.processTransaction(fruitTransactionList);
        Map<Fruit, Integer> actual = FruitStorage.fruitStorage;
        Assert.assertEquals(expected, actual);
    }
}
