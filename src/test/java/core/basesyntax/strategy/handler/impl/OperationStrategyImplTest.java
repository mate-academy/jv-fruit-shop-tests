package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.handler.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {

    @Test
    public void getHandler_ValidOperation_Ok() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap
                = new HashMap<>();
        OperationHandler handler = new BalanceHandler();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, handler);
        OperationStrategy operationStrategy =
                new OperationStrategyImpl(operationHandlerMap);
        OperationHandler actualHandler =
                operationStrategy.getHandler(FruitTransaction.Operation.BALANCE);
        assertEquals(handler, actualHandler);
    }

    @Test
    public void getHandler_InvalidOperation_NotOk() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap
                = new HashMap<>();
        OperationHandler handler = new BalanceHandler();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, handler);
        OperationStrategy operationStrategy =
                new OperationStrategyImpl(operationHandlerMap);
        IllegalArgumentException exception =
                    assertThrows(IllegalArgumentException.class, ()
                            -> operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY));
        assertEquals("No handler found "
                + "for operation: SUPPLY", exception.getMessage());
    }

    @Test
    public void getHandler_NullOperation_NotOk() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap
                = new HashMap<>();
        OperationHandler handler = new BalanceHandler();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, handler);
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        NullPointerException exception =
                assertThrows(NullPointerException.class, ()
                        -> operationStrategy.getHandler(null));
        assertEquals("Operation cannot be null", exception.getMessage());
    }
}
