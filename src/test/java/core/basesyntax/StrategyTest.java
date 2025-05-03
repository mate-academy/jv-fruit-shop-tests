package core.basesyntax;

import core.basesyntax.fruitshop.model.OperationType;
import core.basesyntax.fruitshop.service.OperationStrategyImpl;
import core.basesyntax.fruitshop.service.operation.BalanceOperationHandler;
import core.basesyntax.fruitshop.service.operation.OperationHandler;
import core.basesyntax.fruitshop.service.operation.PurchaseOperationHandler;
import core.basesyntax.fruitshop.service.operation.ReturnOperationHandler;
import core.basesyntax.fruitshop.service.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StrategyTest {
    private static OperationStrategyImpl operationStrategy;

    @Before
    public void beforeAll() {
        Map<OperationType, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(OperationType.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(OperationType.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(OperationType.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void getOperationSupply_IsOk() {
        OperationHandler actual = operationStrategy.get(OperationType.SUPPLY);
        OperationHandler expected = new SupplyOperationHandler();
        Assert.assertEquals(actual.getClass(), expected.getClass());
    }

    @Test
    public void getOperationBalance_IsOk() {
        OperationHandler actual = operationStrategy.get(OperationType.BALANCE);
        OperationHandler expected = new BalanceOperationHandler();
        Assert.assertEquals(actual.getClass(), expected.getClass());
    }

    @Test
    public void getOperationPurchase_IsOk() {
        OperationHandler actual = operationStrategy.get(OperationType.PURCHASE);
        OperationHandler expected = new PurchaseOperationHandler();
        Assert.assertEquals(actual.getClass(), expected.getClass());
    }

    @Test
    public void getOperationReturn_IsOk() {
        OperationHandler actual = operationStrategy.get(OperationType.RETURN);
        OperationHandler expected = new ReturnOperationHandler();
        Assert.assertEquals(actual.getClass(), expected.getClass());
    }
}
