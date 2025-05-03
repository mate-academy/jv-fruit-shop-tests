package core.basesyntax.service.impl;

import core.basesyntax.db.DaoService;
import core.basesyntax.db.DaoServiceHashMap;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.SaveStrategy;
import core.basesyntax.strategy.impl.SaveStrategyBalance;
import core.basesyntax.strategy.impl.SaveStrategyPurchase;
import core.basesyntax.strategy.impl.SaveStrategySupply;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategySelectorTest {
    private static DaoService storage;
    private static final Map<Operation, SaveStrategy> STRATEGIES_WITHOUT_RETURN =
            new HashMap<>();
    private static StrategySelector strategySelector;

    @BeforeClass
    public static void initialize() {
        storage = new DaoServiceHashMap();
        STRATEGIES_WITHOUT_RETURN.put(Operation.SUPPLY, new SaveStrategySupply(storage));
        STRATEGIES_WITHOUT_RETURN.put(Operation.PURCHASE, new SaveStrategyPurchase(storage));
        STRATEGIES_WITHOUT_RETURN.put(Operation.BALANCE, new SaveStrategyBalance(storage));
        strategySelector = new StrategySelector(STRATEGIES_WITHOUT_RETURN);
    }

    @Test
    public void selectStrategy_withValidOperations_ok() {
        Assert.assertEquals(
                SaveStrategySupply.class,
                strategySelector.selectStrategy(Operation.SUPPLY).getClass());
        Assert.assertEquals(
                SaveStrategyBalance.class,
                strategySelector.selectStrategy(Operation.BALANCE).getClass());
        Assert.assertEquals(
                SaveStrategyPurchase.class,
                strategySelector.selectStrategy(Operation.PURCHASE).getClass());
    }

    @Test(expected = RuntimeException.class)
    public void selectStrategy_nonSupportedOperation_notOk() {
        strategySelector.selectStrategy(Operation.RETURN);
    }

    @Test(expected = NullPointerException.class)
    public void selectStrategy_withOperationNull_notOk() {
        strategySelector.selectStrategy(null);
    }
}
