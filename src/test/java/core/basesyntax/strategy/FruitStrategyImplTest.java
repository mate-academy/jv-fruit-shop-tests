package core.basesyntax.strategy;

import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.PurchaseOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitStrategyImplTest {
    private static FruitStrategy fruitStrategy;
    private static Map<String, OperationHandler> strategyMap;

    @BeforeClass
    public static void beforeClass() {
        strategyMap = new HashMap<>();
        strategyMap.put("b", new BalanceOperation());
        strategyMap.put("s", new SupplyOperation());
        strategyMap.put("p", new PurchaseOperation());
        strategyMap.put("r", new ReturnOperation());
        fruitStrategy = new FruitStrategyImpl(strategyMap);
    }

    @Test
    public void getByOperation_appropriateBalanceOperation_ok() {
        Assert.assertEquals("The operations must match.",
                strategyMap.get("b"), fruitStrategy.getByOperation("b"));
    }

    @Test
    public void getByOperation_appropriatePurchaseOperation_ok() {
        Assert.assertEquals("The operations must match.",
                strategyMap.get("p"), fruitStrategy.getByOperation("p"));
    }

    @Test
    public void getByOperation_appropriateReturnOperation_ok() {
        Assert.assertEquals("The operations must match.",
                strategyMap.get("r"), fruitStrategy.getByOperation("r"));
    }

    @Test
    public void getByOperation_appropriateSupplyOperation_ok() {
        Assert.assertEquals("The operations must match.",
                strategyMap.get("s"), fruitStrategy.getByOperation("s"));
    }

    @Test
    public void getByOperation_nullOperation_ok() {
        Assert.assertEquals(
                "Method should return null due to an unknown operation.",
                null, fruitStrategy.getByOperation("q"));
    }
}
