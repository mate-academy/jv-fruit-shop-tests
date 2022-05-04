package core.basesyntax.service.strategy.impl;

import core.basesyntax.model.Operations;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static Map<String, OperationHandler> operationHandlerMap;

    @Before
    public void setUp() {
        operationHandlerMap.put(Operations.BALANCE.getOperation(),
                new BalanceOperationHandler());
        operationHandlerMap.put(Operations.SUPPLY.getOperation(),
                new SupplyOperationHandler());
        operationHandlerMap.put(Operations.PURCHASE.getOperation(),
                new PurchaseOperationHandler());
        operationHandlerMap.put(Operations.RETURN.getOperation(),
                new ReturnOperationHandler());
    }

    @BeforeClass
    public static void beforeClass() {
        operationHandlerMap = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void balanceOperationHandler_isOk() {
        OperationHandler balanceOperation
                = operationStrategy.get(Operations.BALANCE.getOperation());
        Assert.assertTrue(operationHandlerMap.containsValue(balanceOperation));
    }

    @Test
    public void supplyOperationHandler_isOk() {
        OperationHandler supplyOperation
                = operationStrategy.get(Operations.SUPPLY.getOperation());
        Assert.assertTrue(operationHandlerMap.containsValue(supplyOperation));
    }

    @Test
    public void purchaseOperationHandler_isOk() {
        OperationHandler purchaseOperation
                = operationStrategy.get(Operations.PURCHASE.getOperation());
        Assert.assertTrue(operationHandlerMap.containsValue(purchaseOperation));
    }

    @Test
    public void returnOperationHandler_isOk() {
        OperationHandler returnOperation = operationStrategy.get(Operations.RETURN.getOperation());
        Assert.assertTrue(operationHandlerMap.containsValue(returnOperation));
    }
}
