package core.basesyntax.strategy;

import static org.junit.Assert.assertThrows;

import core.basesyntax.model.Operation;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impl.BalanceHandler;
import core.basesyntax.strategy.handler.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private Map<Operation, OperationHandler> operationHandlerMap;
    private OperationStrategy operationStrategy;

    @BeforeEach
    public void setUp() {
        operationHandlerMap = new HashMap<>();
        OperationHandler handler = new BalanceHandler();
        operationHandlerMap.put(Operation.BALANCE, handler);
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void getHandler_NullOperation_notOk() {
        NullPointerException exception =
                assertThrows(NullPointerException.class, ()
                        -> operationStrategy.getHandler(null));
        Assertions.assertEquals("Operation cannot be null", exception.getMessage());
    }

    @Test
    public void getHandler_InvalidOperation_notOk() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, ()
                        -> operationStrategy.getHandler(Operation.SUPPLY));
        Assertions.assertEquals("No handler found "
                + "for operation: SUPPLY", exception.getMessage());
    }

    @Test
    public void getHandler_ValidOperation_Ok() {
        OperationHandler actualHandler = operationStrategy.getHandler(
                Operation.BALANCE);
        Assertions.assertEquals(operationHandlerMap.get(Operation.BALANCE),
                actualHandler);
    }
}
