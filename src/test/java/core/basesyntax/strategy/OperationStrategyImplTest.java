package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private OperationHandler operationHandler;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @Before
    public void setMap() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void getOperation_ok() {
        operationHandler = operationStrategy
                .getOperation(FruitTransaction.Operation.BALANCE);
        assertEquals(BalanceOperationHandler.class, operationHandler.getClass());
        operationHandler = operationStrategy
                .getOperation(FruitTransaction.Operation.PURCHASE);
        assertEquals(PurchaseOperationHandler.class, operationHandler.getClass());
        operationHandler = operationStrategy
                .getOperation(FruitTransaction.Operation.RETURN);
        assertEquals(ReturnOperationHandler.class, operationHandler.getClass());
        operationHandler = operationStrategy
                .getOperation(FruitTransaction.Operation.SUPPLY);
        assertEquals(SupplyOperationHandler.class, operationHandler.getClass());
    }

    @Test
    public void getOperation_notOk() {
        operationHandler = operationStrategy.getOperation(null);
        assertNull(operationHandler);
    }
}
