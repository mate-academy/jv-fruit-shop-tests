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
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransitionServiceImplTest {
    private static List<FruitTransition> transitionData;
    private static TransitionService transitionService;
    private static Fruit banana;

    @BeforeClass
    public static void setUp() {
        banana = new Fruit("banana");
        transitionData = new ArrayList<>();
        transitionData.add(new FruitTransition("b", banana, 100));
        transitionData.add(new FruitTransition("s", banana, 100));
        Map<String, OperationHandler> dataOperation = new HashMap<>();
        dataOperation.put(BALANCE.getOperation(), new BalanceHandlerImpl());
        dataOperation.put(SUPPLY.getOperation(), new SupplyHandlerImpl());
        OperationHandlerStrategy strategy = new OperationHandlerStrategyImpl(dataOperation);
        transitionService = new TransitionServiceImpl(strategy);
    }

    @After
    public void afterEach() {
        FruitStorage.storage.clear();
    }

    @Test
    public void setTestTransaction_ok() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(banana, 200);
        transitionService.doTransition(transitionData);
        Map<Fruit, Integer> actual = FruitStorage.storage;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void setEmptyTransaction_emptyDB_ok() {
        transitionService.doTransition(Collections.emptyList());
        assertTrue(FruitStorage.storage.isEmpty());
    }
}
