package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.PurchaseHandler;
import core.basesyntax.service.SupplyHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DefaultOperationStrategyTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.SUPPLY, Mockito.mock(SupplyHandler.class));
        handlers.put(FruitTransaction.Operation.PURCHASE, Mockito.mock(PurchaseHandler.class));
        operationStrategy = new DefaultOperationStrategy(handlers);
    }

    @Test
    void getHandler_WhenValidOperationSupplied_ReturnsSupplyHandler() {
        OperationHandler actualHandler;
        actualHandler = operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY);
        assertTrue(actualHandler instanceof SupplyHandler);
    }

    @Test
    void getHandler_WhenInvalidOperationSupplied_ReturnsNull() {
        assertNull(operationStrategy.getHandler(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void getHandler_WhenEmptyHandlersMap_ReturnsNull() {
        Map<FruitTransaction.Operation, OperationHandler> emptyHandlers = new HashMap<>();
        operationStrategy = new DefaultOperationStrategy(emptyHandlers);

        assertNull(operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY));
        assertNull(operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE));
    }
}
