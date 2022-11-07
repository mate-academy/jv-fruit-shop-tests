package core.basesyntax.service;

import static core.basesyntax.model.FruitOperation.BALANCE;
import static core.basesyntax.model.FruitOperation.SUPPLY;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransition;
import core.basesyntax.service.impl.TransitionServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationHandlerStrategy;
import core.basesyntax.strategy.impl.BalanceHandlerImpl;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.impl.SupplyHandlerImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransitionServiceTest {
    private static List<FruitTransition> transitionData;
    private static Map<String, OperationHandler> dataOperation;
    private static OperationHandlerStrategy strategy;
    private static TransitionService transitionService;
    private static Fruit banana;

    @BeforeClass
    public static void setUp() {
        banana = new Fruit("banana");
        transitionData = new ArrayList<>();
        transitionData.add(new FruitTransition("b", banana, 100));
        transitionData.add(new FruitTransition("s", banana, 100));
        dataOperation = new HashMap<>();
        dataOperation.put(BALANCE.getOperation(), new BalanceHandlerImpl());
        dataOperation.put(SUPPLY.getOperation(), new SupplyHandlerImpl());
        strategy = new OperationHandlerStrategyImpl(dataOperation);
        transitionService = new TransitionServiceImpl(strategy);
    }

    @Before
    public void beforeAll() {
        FruitStorage.storage.remove(banana);
    }

    @Test
    public void setTestTransaction_ok() {
        int expected = 200;
        transitionService.doTransition(transitionData);
        int actual = FruitStorage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void setEmptyTransaction_ok() {
        transitionService.doTransition(Collections.emptyList());
    }

    @Test
    public void setEmptyTransaction_emptyDb_ok() {
        transitionService.doTransition(Collections.emptyList());
        assertTrue(FruitStorage.storage.isEmpty());
    }
}
