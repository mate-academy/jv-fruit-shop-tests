package core.service.strategy;

import core.model.OperationType;
import core.service.operation.BalanceOperationTypeHandler;
import core.service.operation.OperationTypeHandler;
import core.service.operation.PurchaseOperationTypeHandler;
import core.service.operation.ReturnOperationTypeHandler;
import core.service.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationTypeStrategyImplTest {
    private OperationTypeStrategy strategy;

    @Before
    public void setUp() {
        Map<OperationType, OperationTypeHandler> operationTypeHandlerMap = new HashMap<>();
        operationTypeHandlerMap.put(OperationType.BALANCE, new BalanceOperationTypeHandler());
        operationTypeHandlerMap.put(OperationType.PURCHASE, new PurchaseOperationTypeHandler());
        operationTypeHandlerMap.put(OperationType.SUPPLY, new SupplyOperationHandler());
        operationTypeHandlerMap.put(OperationType.RETURN, new ReturnOperationTypeHandler());
        strategy = new OperationTypeStrategyImpl(operationTypeHandlerMap);
    }

    @Test
    public void getHandle_Ok() {
        Assert.assertEquals(strategy.getHandle(OperationType.BALANCE).getClass(),
                BalanceOperationTypeHandler.class);
    }

    @Test(expected = RuntimeException.class)
    public void getHandle_NotOk() {
        Map<OperationType, OperationTypeHandler> handlerMap = new HashMap<>();
        handlerMap.put(OperationType.BALANCE, new BalanceOperationTypeHandler());
        strategy = new OperationTypeStrategyImpl(handlerMap);
        strategy.getHandle(OperationType.RETURN);
    }

    @Test(expected = RuntimeException.class)
    public void getHandleWithNull_NotOk() {
        Map<OperationType, OperationTypeHandler> handlerMap = new HashMap<>();
        handlerMap.put(OperationType.BALANCE, new BalanceOperationTypeHandler());
        strategy = new OperationTypeStrategyImpl(handlerMap);
        strategy.getHandle(null);
    }
}
