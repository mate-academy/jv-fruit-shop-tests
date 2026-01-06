package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrategyImplTest {
    private OperationHandler handler;
    private Strategy strategy;
    private Map<Operation, OperationHandler> operationHandlerMap;

    @BeforeEach
    public void setUp() {
        operationHandlerMap = new HashMap<>();
        handler = (transaction, inventory) -> {};
        operationHandlerMap.put(Operation.SUPPLY, handler);
        strategy = new StrategyImpl(operationHandlerMap);
    }

    @Test
    public void getOperationHandlerReturnsHandlerWhenRegistered() {
        OperationHandler result = strategy.getOperationHandler(Operation.SUPPLY);
        assertNotNull(result);
        assertEquals(handler, result);
    }

    @Test
    public void getOperationHandlerThrowsExceptionWhenNotRegistered() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> strategy.getOperationHandler(Operation.BALANCE)
        );
        assertEquals(
                "No handler registered for operation: " + Operation.BALANCE,
                exception.getMessage()
        );
    }
}
