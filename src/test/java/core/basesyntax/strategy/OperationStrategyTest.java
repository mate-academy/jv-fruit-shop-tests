package core.basesyntax.strategy;

import static org.junit.Assert.assertSame;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static Map<String, OperationHandler> map;

    @BeforeClass
    public static void beforeClass() {
        map = new HashMap<>();
        map.put("b", new BalanceOperationHandler());
        map.put("s", new SupplyOperationHandler());
        map.put("p", new PurchaseOperationHandler());
        map.put("r", new ReturnOperationHandler());
        operationStrategy = new OperationStrategy(map);
    }

    @Test
    public void operationHandler_balanceHandlerCall_OK() {
        Assert.assertSame(operationStrategy.getByOperation("b").getClass(),
                BalanceOperationHandler.class);
    }

    @Test
    public void operationHandler_supplyHandlerCall_OK() {
        assertSame(operationStrategy.getByOperation("s").getClass(),
                SupplyOperationHandler.class);
    }

    @Test
    public void operationHandler_purchaseHandlerCall_OK() {
        assertSame(operationStrategy.getByOperation("p").getClass(),
                PurchaseOperationHandler.class);
    }

    @Test
    public void operationHandler_returnHandlerCall_OK() {
        assertSame(operationStrategy.getByOperation("r").getClass(),
                ReturnOperationHandler.class);
    }
}
