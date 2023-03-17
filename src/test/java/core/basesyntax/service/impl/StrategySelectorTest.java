package core.basesyntax.service.impl;

import core.basesyntax.db.DaoServiceHashMap;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.SaveStrategy;
import core.basesyntax.strategy.impl.SaveStrategyBalance;
import core.basesyntax.strategy.impl.SaveStrategyPurchase;
import core.basesyntax.strategy.impl.SaveStrategySupply;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StrategySelectorTest {
    private static DaoServiceHashMap<String, Integer> STORAGE;
    private static final Map<Operation, SaveStrategy> STRATEGIES_WITHOUT_RETURN =
            new HashMap<>();
    private static StrategySelector strategySelector;

    @BeforeAll
    public static void initialize() {
        STORAGE = new DaoServiceHashMap<>();
        STRATEGIES_WITHOUT_RETURN.put(Operation.SUPPLY, new SaveStrategySupply(STORAGE));
        STRATEGIES_WITHOUT_RETURN.put(Operation.PURCHASE, new SaveStrategyPurchase(STORAGE));
        STRATEGIES_WITHOUT_RETURN.put(Operation.BALANCE, new SaveStrategyBalance(STORAGE));
        strategySelector = new StrategySelector(STRATEGIES_WITHOUT_RETURN);
    }

    @Test
    public void selectStrategy_withValidOperations_ok() {
        Assertions.assertEquals(
                strategySelector.selectStrategy(Operation.SUPPLY).getClass(),
                SaveStrategySupply.class);
        Assertions.assertEquals(
                strategySelector.selectStrategy(Operation.BALANCE).getClass(),
                SaveStrategyBalance.class);
        Assertions.assertEquals(
                strategySelector.selectStrategy(Operation.PURCHASE).getClass(),
                SaveStrategyPurchase.class);
    }

    @Test
    public void selectStrategy_nonSupportedOperation_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> strategySelector.selectStrategy(Operation.RETURN));
    }

    @Test
    public void selectStrategy_withOperationNull_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> strategySelector.selectStrategy(null));
    }
}
