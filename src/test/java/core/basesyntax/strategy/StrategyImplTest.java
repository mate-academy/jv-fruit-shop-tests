package core.basesyntax.strategy;

import core.basesyntax.model.Operation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class StrategyImplTest {
    private Strategy strategy;
    Map<Operation, OperationHandler> operationHandlerMap;
    OperationHandler handler;

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
        assertSame(handler, result);
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
