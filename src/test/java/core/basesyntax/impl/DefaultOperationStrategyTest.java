package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.PurchaseHandler;
import core.basesyntax.service.SupplyHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultOperationStrategyTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationStrategy = new DefaultOperationStrategy(handlers);
    }

    @Test
    void testGetHandlerWithValidOperation() {
        OperationHandler expectedHandler = new SupplyHandler();
        OperationHandler actualHandler;
        actualHandler = operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    void testGetHandlerWithInvalidOperation() {
        assertNull(operationStrategy.getHandler(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void testGetHandlerWithEmptyHandlersMap() {
        Map<FruitTransaction.Operation, OperationHandler> emptyHandlers = new HashMap<>();
        operationStrategy = new DefaultOperationStrategy(emptyHandlers);

        assertNull(operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY));
        assertNull(operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE));
    }
}
