package core.basesyntax.strategy;

import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static Map<String, OperationHandler> map = new HashMap<>();
    private OperationStrategy strategy = new OperationStrategy(map);

    @BeforeClass
    public static void beforeClass() {
        map.put("b", new BalanceOperationHandler());
        map.put("p", new PurchaseOperationHandler());
        map.put("s", new SupplyOperationHandler());
        map.put("r", new ReturnOperationHandler());
    }

    @Test
    public void getBehaviorBalance_Ok() {
        Assert.assertTrue(strategy.getByOperation("b") instanceof BalanceOperationHandler);
    }

    @Test
    public void getBehaviorPurchase_Ok() {
        Assert.assertTrue(strategy.getByOperation("p") instanceof PurchaseOperationHandler);
    }

    @Test
    public void getBehaviorSupply_Ok() {
        Assert.assertTrue(strategy.getByOperation("s") instanceof SupplyOperationHandler);
    }

    @Test
    public void getBehaviorReturn_Ok() {
        Assert.assertTrue(strategy.getByOperation("r") instanceof ReturnOperationHandler);
    }

    @Test
    public void getNoExistingBehavior_Not_Ok() {
        Assert.assertNull(strategy.getByOperation("Wrong operation"));
    }
}
