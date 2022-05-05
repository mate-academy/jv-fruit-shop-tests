package core.basesyntax.service.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Operations;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static final String INVALID_OPERATION_CHARACTER = "D";
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
    public void balanceOperationHandler_Ok() {
        OperationHandler balanceOperation
                = operationStrategy.get(Operations.BALANCE.getOperation());
        Assert.assertTrue(operationHandlerMap.containsValue(balanceOperation));
    }

    @Test
    public void supplyOperationHandler_Ok() {
        OperationHandler supplyOperation
                = operationStrategy.get(Operations.SUPPLY.getOperation());
        Assert.assertTrue(operationHandlerMap.containsValue(supplyOperation));
    }

    @Test
    public void purchaseOperationHandler_Ok() {
        OperationHandler purchaseOperation
                = operationStrategy.get(Operations.PURCHASE.getOperation());
        Assert.assertTrue(operationHandlerMap.containsValue(purchaseOperation));
    }

    @Test
    public void returnOperationHandler_Ok() {
        OperationHandler returnOperation = operationStrategy.get(Operations.RETURN.getOperation());
        Assert.assertTrue(operationHandlerMap.containsValue(returnOperation));
    }

    @Test (expected = RuntimeException.class)
    public void operationStrategy_invalidOperation_NotOk() {
        operationStrategy.get(INVALID_OPERATION_CHARACTER)
                .handle("apple", 100);
    }

    @Test
    public void operationStrategy_operationIsEmpty_NotOk() {
        operationStrategy.get(null);
    }

    @After
    public void afterEachTest() {
        Storage.fruitStorage.clear();
    }
}
