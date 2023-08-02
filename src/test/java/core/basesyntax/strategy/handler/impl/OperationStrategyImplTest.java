package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.handler.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

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
        OperationHandler actualHandler =
                operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY);
        assertNull(actualHandler);
    }

    @Test
    public void getHandler_NullOperation_NotOk() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap
                = new HashMap<>();
        OperationHandler handler = new BalanceHandler();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, handler);
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        assertThrows(NullPointerException.class, ()
                -> operationStrategy.getHandler(null));
    }
}
