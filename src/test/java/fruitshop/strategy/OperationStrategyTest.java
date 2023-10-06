package fruitshop.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fruitshop.model.Operation;
import fruitshop.strategy.operation.OperationHandler;
import fruitshop.strategy.operation.impl.BalanceOperationHandler;
import fruitshop.strategy.operation.impl.PurchaseOperationHandler;
import fruitshop.strategy.operation.impl.ReturnOperationHandler;
import fruitshop.strategy.operation.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private OperationStrategy operationStrategy;
    private OperationHandler expected;

    @BeforeEach
    void setUp() {
        expected = new SupplyOperationHandler();
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategy(operationHandlerMap);
    }

    @Test
    void getOperationHandler_validCase_ok() {
        Operation operation = Operation.SUPPLY;
        OperationHandler actual = operationStrategy.getOperationHandler(operation);
        assertEquals(expected.getClass(), actual.getClass());
    }
}
