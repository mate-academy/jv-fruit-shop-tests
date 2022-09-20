package core.basesyntax.strategy;

import static org.junit.Assert.assertSame;

import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private Map<Operation, OperationHandler> operationHandleMap;

    @Before
    public void setUp() {
        operationHandleMap = new HashMap<>();
        operationHandleMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationHandleMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationHandleMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandleMap.put(Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandleMap);
    }

    @Test
    public void getBalanceOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.get(Operation.BALANCE);
        assertSame(actual.getClass(), BalanceOperationHandler.class);
    }

    @Test
    public void getPurchaseOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.get(Operation.PURCHASE);
        assertSame(actual.getClass(), PurchaseOperationHandler.class);
    }

    @Test
    public void getReturnOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.get(Operation.RETURN);
        assertSame(actual.getClass(), ReturnOperationHandler.class);
    }

    @Test
    public void getSupplyOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.get(Operation.SUPPLY);
        assertSame(actual.getClass(), SupplyOperationHandler.class);
    }
}
