package core.basesyntax.services;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.services.handlers.BalanceFruitHandler;
import core.basesyntax.services.handlers.DecreaseFruitHandler;
import core.basesyntax.services.handlers.FruitHandler;
import core.basesyntax.services.handlers.IncreaseFruitHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class CountingActivitiesServiceImplTest {
    private static CountingActivitiesService countingActivitiesService;
    private static List<Operation> operations;
    private static FruitStrategy fruitStrategy;

    @Before
    public void setUp() {
        countingActivitiesService = new CountingActivitiesServiceImpl();
        operations = new ArrayList<>();
        operations.add(new Operation("b", new Fruit("banana"), 20));
        operations.add(new Operation("b", new Fruit("apple"), 100));
        operations.add(new Operation("s", new Fruit("banana"), 100));
        operations.add(new Operation("p", new Fruit("banana"), 13));
        operations.add(new Operation("r", new Fruit("apple"), 10));
        operations.add(new Operation("p", new Fruit("apple"), 20));
        operations.add(new Operation("p", new Fruit("banana"), 5));
        operations.add(new Operation("s", new Fruit("banana"), 50));
        Map<String, FruitHandler> fruitStrategyMap = new HashMap<>();
        fruitStrategyMap.put("r", new IncreaseFruitHandler());
        fruitStrategyMap.put("s", new IncreaseFruitHandler());
        fruitStrategyMap.put("p", new DecreaseFruitHandler());
        fruitStrategyMap.put("b", new BalanceFruitHandler());
        fruitStrategy = new FruitStrategyImpl(fruitStrategyMap);
    }

    @Test
    public void countingActivitiesWithCorrectData_Ok() {
        Map<Fruit, Integer> actual = countingActivitiesService
                .countingActivities(operations, fruitStrategy);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 152);
        expected.put(new Fruit("apple"), 90);
        assertEquals("Test failed: wrong data from storage",actual,expected);
    }
}
