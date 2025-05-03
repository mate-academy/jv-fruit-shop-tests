package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.BalanceOperationHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperationHandler;
import core.basesyntax.strategy.operation.ReturnOperationHandler;
import core.basesyntax.strategy.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationOperationHandlerMap;
    private OperationStrategy operationStrategy;

    @Before
    public void setUp() {
        operationOperationHandlerMap = new HashMap<>();
        operationOperationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationOperationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationOperationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationOperationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationOperationHandlerMap);
    }

    @Test
    public void getBalanceOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.getOperation(
                FruitTransaction.Operation.BALANCE);
        assertEquals(BalanceOperationHandler.class, actual.getClass());
    }

    @Test
    public void getPurchaseOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.getOperation(
                FruitTransaction.Operation.PURCHASE);
        assertEquals(PurchaseOperationHandler.class, actual.getClass());
    }

    @Test
    public void getReturnOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.getOperation(
                FruitTransaction.Operation.RETURN);
        assertEquals(ReturnOperationHandler.class, actual.getClass());
    }

    @Test
    public void getSupplyOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.getOperation(
                FruitTransaction.Operation.SUPPLY);
        assertEquals(SupplyOperationHandler.class, actual.getClass());
    }
}
