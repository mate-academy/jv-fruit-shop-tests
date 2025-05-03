package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Operation;
import core.basesyntax.operations.BalanceOperationHandler;
import core.basesyntax.operations.PurchaseOperationHandler;
import core.basesyntax.operations.ReturnOperationHandler;
import core.basesyntax.operations.SupplyOperationHandler;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static Map<Operation, OperationHandler> operationHandlerMap;

    @BeforeClass
    public static void beforeClass() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnOperationHandler());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());

        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void getOperationBalance_Ok() {
        OperationHandler expected = operationHandlerMap.get(Operation.BALANCE);
        OperationHandler actual = operationStrategy.get(Operation.BALANCE);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getSupplyOperationHandler_Ok() {
        OperationHandler expected = operationHandlerMap.get(Operation.SUPPLY);
        Assert.assertEquals(expected.getClass(), SupplyOperationHandler.class);
    }

    @Test
    public void getPurchaseOperationHandler_Ok() {
        OperationHandler expected = operationHandlerMap.get(Operation.PURCHASE);
        Assert.assertEquals(expected.getClass(), PurchaseOperationHandler.class);
    }

    @Test
    public void getReturnOperationHandler_Ok() {
        OperationHandler expected = operationHandlerMap.get(Operation.RETURN);
        Assert.assertEquals(expected.getClass(), ReturnOperationHandler.class);
    }

    @Test
    public void get_NullOperationHandler_NotOk() {
        OperationHandler expected = null;
        OperationHandler actual = operationHandlerMap.get(null);
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationReturn_Ok() {
        OperationHandler expected = operationHandlerMap.get(Operation.RETURN);
        OperationHandler actual = operationStrategy.get(Operation.RETURN);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getOperationPurchase_Ok() {
        OperationHandler expected = operationHandlerMap.get(Operation.PURCHASE);
        OperationHandler actual = operationStrategy.get(Operation.PURCHASE);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getOperationSupply_NotOk() {
        OperationHandler expected = operationHandlerMap.get(Operation.SUPPLY);
        OperationHandler actual = operationStrategy.get(Operation.PURCHASE);
        Assert.assertNotEquals(expected, actual);
    }
}
