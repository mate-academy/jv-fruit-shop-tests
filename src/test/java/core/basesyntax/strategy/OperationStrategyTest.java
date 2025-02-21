package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Operation;
import core.basesyntax.service.operationhandler.BalanceHandler;
import core.basesyntax.service.operationhandler.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {

    private OperationStrategy operationStrategy;
    private Map<Operation, OperationHandler> operationHandlerMap;

    @BeforeEach
    void setUp() {
        operationHandlerMap = new HashMap<>();
        OperationHandler balanceHandler = new BalanceHandler();
        operationHandlerMap.put(Operation.BALANCE, balanceHandler);
        operationStrategy = new OperationStrategy(operationHandlerMap);
    }

    @Test
    void shouldReturnCorrectHandlerForOperation() {
        OperationHandler handler = operationStrategy.getHandler(Operation.BALANCE);
        assertEquals(operationHandlerMap.get(Operation.BALANCE), handler);
    }
}
