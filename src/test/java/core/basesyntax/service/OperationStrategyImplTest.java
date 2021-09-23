package core.basesyntax.service;

import core.basesyntax.operationtype.BalanceHandler;
import core.basesyntax.operationtype.OperationHandler;
import core.basesyntax.operationtype.PurchaseHandler;
import core.basesyntax.operationtype.ReturnHandler;
import core.basesyntax.operationtype.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;

    @Before
    public void setUp() throws Exception {
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("b", new BalanceHandler());
        operationHandlerMap.put("p", new PurchaseHandler());
        operationHandlerMap.put("r", new ReturnHandler());
        operationHandlerMap.put("s", new SupplyHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void name() {
        Assert.assertEquals(operationStrategy.getOperation("p").getClass(), PurchaseHandler.class);
        Assert.assertEquals(operationStrategy.getOperation("b").getClass(), BalanceHandler.class);
        Assert.assertEquals(operationStrategy.getOperation("r").getClass(), ReturnHandler.class);
        Assert.assertEquals(operationStrategy.getOperation("s").getClass(), SupplyHandler.class);
    }
}
