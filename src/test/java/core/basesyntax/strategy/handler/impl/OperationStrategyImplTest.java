package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.handler.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private OperationStrategy operationStrategy;

    @BeforeEach
    public void setUp() {
        operationHandlerMap = new HashMap<>();
        OperationHandler handler = new BalanceHandler();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, handler);
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void getHandler_ValidOperation_Ok() {
        OperationHandler actualHandler = operationStrategy.getHandler(
                FruitTransaction.Operation.BALANCE);
        assertEquals(operationHandlerMap.get(FruitTransaction.Operation.BALANCE),
                actualHandler);
    }

    @Test
    public void getHandler_InvalidOperation_notOk() {
        IllegalArgumentException exception =
                    assertThrows(IllegalArgumentException.class, ()
                            -> operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY));
        assertEquals("No handler found "
                + "for operation: SUPPLY", exception.getMessage());
    }

    @Test
    public void getHandler_NullOperation_notOk() {
        NullPointerException exception =
                assertThrows(NullPointerException.class, ()
                        -> operationStrategy.getHandler(null));
        assertEquals("Operation cannot be null", exception.getMessage());
    }
}
