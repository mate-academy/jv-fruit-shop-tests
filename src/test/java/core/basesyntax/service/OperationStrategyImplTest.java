package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    private OperationStrategy operationStrategy;
    private Map<Operation, OperationHandler> operationHandlers;

    @BeforeEach
    void seUP() {
        operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void getHandler_validOperation_returnHandlerOk() {
        OperationHandler operationHandler = operationStrategy.getHandler(Operation.SUPPLY);
        assertEquals(SupplyOperationHandler.class, operationHandler.getClass());
    }

    @Test
    void getHandler_invalidOperation_returnNull() {
        OperationHandler operationHandler = operationStrategy.getHandler(Operation.PURCHASE);
        assertNull(operationHandler);
    }

    @Test
    public void getHandler_nullOperation_returnsNull() {
        OperationHandler handler = operationStrategy.getHandler(null);
        assertNull(handler);
    }
}
