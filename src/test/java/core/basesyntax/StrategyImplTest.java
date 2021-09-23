package core.basesyntax;

import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.Strategy;
import core.basesyntax.service.impl.BalanceOperation;
import core.basesyntax.service.impl.PurchaseOperation;
import core.basesyntax.service.impl.ReturnOperation;
import core.basesyntax.service.impl.StrategyImpl;
import core.basesyntax.service.impl.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StrategyImplTest {
    private static Strategy strategy;

    @Before
    public void setUp() {
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE.getOperation(), new BalanceOperation());
        operationHandlerMap.put(Operation.PURCHASE.getOperation(), new PurchaseOperation());
        operationHandlerMap.put(Operation.RETURN.getOperation(), new ReturnOperation());
        operationHandlerMap.put(Operation.SUPPLY.getOperation(), new SupplyOperation());
        strategy = new StrategyImpl(operationHandlerMap);
    }

    @Test
    public void getActivity_ok() {
        Assert.assertEquals(strategy.getActivity(Operation.BALANCE.getOperation())
                .getClass(), BalanceOperation.class);
        Assert.assertEquals(strategy.getActivity(Operation.PURCHASE.getOperation())
                .getClass(), PurchaseOperation.class);
        Assert.assertEquals(strategy.getActivity(Operation.RETURN.getOperation())
                .getClass(), ReturnOperation.class);
        Assert.assertEquals(strategy.getActivity(Operation.SUPPLY.getOperation())
                .getClass(), SupplyOperation.class);
    }

    @Test
    public void getActivity_notOk() {
        Map<String, OperationHandler> anotherOperationHandlerMap = new HashMap<>();
        anotherOperationHandlerMap.put(Operation.BALANCE.getOperation(), new BalanceOperation());
        strategy = new StrategyImpl(anotherOperationHandlerMap);
        try {
            strategy.getActivity(Operation.RETURN.getOperation());
        } catch (RuntimeException e) {
            Assert.assertEquals("No such activityType", e.getMessage());
        }
    }
}
