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
import org.junit.Test;

public class StrategySelectorTest {
    private static DaoService STORAGE;
    private static final Map<Operation, SaveStrategy> STRATEGIES_WITHOUT_RETURN =
            new HashMap<>();
    private static StrategySelector strategySelector;

    //instead of @BeforeAll
    static {
        STORAGE = new DaoServiceHashMap();
        STRATEGIES_WITHOUT_RETURN.put(Operation.SUPPLY, new SaveStrategySupply(STORAGE));
        STRATEGIES_WITHOUT_RETURN.put(Operation.PURCHASE, new SaveStrategyPurchase(STORAGE));
        STRATEGIES_WITHOUT_RETURN.put(Operation.BALANCE, new SaveStrategyBalance(STORAGE));
        strategySelector = new StrategySelector(STRATEGIES_WITHOUT_RETURN);
    }

    @Test
    public void selectStrategy_withValidOperations_ok() {
        Assert.assertEquals(
                strategySelector.selectStrategy(Operation.SUPPLY).getClass(),
                SaveStrategySupply.class);
        Assert.assertEquals(
                strategySelector.selectStrategy(Operation.BALANCE).getClass(),
                SaveStrategyBalance.class);
        Assert.assertEquals(
                strategySelector.selectStrategy(Operation.PURCHASE).getClass(),
                SaveStrategyPurchase.class);
    }

    @Test
    public void selectStrategy_nonSupportedOperation_notOk() {
        try {
            strategySelector.selectStrategy(Operation.RETURN);
            Assert.fail();
        } catch (RuntimeException e) {
            Assert.assertEquals(e.getClass(),
                    RuntimeException.class);
        }
    }

    @Test
    public void selectStrategy_withOperationNull_notOk() {
        try {
            strategySelector.selectStrategy(null);
            Assert.fail();
        } catch (RuntimeException e) {
            Assert.assertEquals(e.getClass(),
                    NullPointerException.class);
        }
    }
}
