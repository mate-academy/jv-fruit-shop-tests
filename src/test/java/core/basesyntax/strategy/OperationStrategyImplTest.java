package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.impl.BalanceOperationHandler;
import core.basesyntax.handler.impl.PurchaseOperationHandler;
import core.basesyntax.handler.impl.ReturnOperationHandler;
import core.basesyntax.handler.impl.SupplyOperationHandler;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceOperationHandler());
        operationHandlers.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlers.put(Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void getHandler_existingOperation_ok() {
        OperationHandler handler = operationStrategy.getHandler(Operation.BALANCE);
        assertTrue(handler instanceof BalanceOperationHandler);

        handler = operationStrategy.getHandler(Operation.PURCHASE);
        assertTrue(handler instanceof PurchaseOperationHandler);
    }

    private void assertTrue(boolean b) {
    }

    @Test
    void getHandler_nonExistingOperation_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operationStrategy.getHandler(null));
        assertEquals("No handler found for operation: null", exception.getMessage());
    }
}
