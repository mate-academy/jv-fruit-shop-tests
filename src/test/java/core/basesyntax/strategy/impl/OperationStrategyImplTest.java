package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategy strategy;

    @Before
    public void setUp() {
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        strategy = new OperationStrategyImpl(operationHandlerMap);
        operationHandlerMap.put("b", new BalanceHandler());
        operationHandlerMap.put("s", new SupplyHandler());
        operationHandlerMap.put("r", new ReturnHandler());
        operationHandlerMap.put("p", new PurchaseHandler());
    }

    @Test
    public void getOperationStrategy_BalanceOperation_Ok() {
        Class<? extends OperationHandler> actual = strategy
                .getOperationStrategy(Operation.BALANCE.getOperationString()).getClass();
        Assert.assertEquals(BalanceHandler.class, actual);
    }

    @Test
    public void getOperationStrategy_SupplyOperation_Ok() {
        Class<? extends OperationHandler> actual = strategy
                .getOperationStrategy(Operation.SUPPLY.getOperationString()).getClass();
        Assert.assertEquals(SupplyHandler.class, actual);
    }

    @Test
    public void getOperationStrategy_ReturnOperation_Ok() {
        Class<? extends OperationHandler> actual = strategy
                .getOperationStrategy(Operation.RETURN.getOperationString()).getClass();
        Assert.assertEquals(ReturnHandler.class, actual);
    }

    @Test
    public void getOperationStrategy_PurchaseOperation_Ok() {
        Class<? extends OperationHandler> actual = strategy
                .getOperationStrategy(Operation.PURCHASE.getOperationString()).getClass();
        Assert.assertEquals(PurchaseHandler.class, actual);
    }
}
