package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Operation;
import core.basesyntax.service.operationhandler.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OperationStrategyTest {

    private OperationStrategy operationStrategy;
    private Map<Operation, OperationHandler> operationHandlerMap;

    @BeforeEach
    void setUp() {
        operationHandlerMap = Mockito.mock(Map.class);

        OperationHandler mockHandler = Mockito.mock(OperationHandler.class);
        Mockito.when(operationHandlerMap.get(Operation.BALANCE)).thenReturn(mockHandler);

        operationStrategy = new OperationStrategy(operationHandlerMap);
    }

    @Test
    void getHandler_ShouldReturnCorrectHandlerForOperation() {
        OperationHandler handler = operationStrategy.getHandler(Operation.BALANCE);
        assertEquals(operationHandlerMap.get(Operation.BALANCE), handler);
    }
}
