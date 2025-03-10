package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private final OperationHandler balanceHandler = mock(OperationHandler.class);
    private final OperationHandler purchaseHandler = mock(OperationHandler.class);
    private final OperationHandler returnHandler = mock(OperationHandler.class);
    private final OperationHandler supplyHandler = mock(OperationHandler.class);

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        handlers.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);
        handlers.put(FruitTransaction.Operation.RETURN, returnHandler);
        handlers.put(FruitTransaction.Operation.SUPPLY, supplyHandler);
        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void getOperationHandler_validOperations_ok() {
        assertEquals(balanceHandler, operationStrategy
                .getOperationHandler(FruitTransaction.Operation.BALANCE));
        assertEquals(purchaseHandler, operationStrategy
                .getOperationHandler(FruitTransaction.Operation.PURCHASE));
        assertEquals(returnHandler, operationStrategy
                .getOperationHandler(FruitTransaction.Operation.RETURN));
        assertEquals(supplyHandler, operationStrategy
                .getOperationHandler(FruitTransaction.Operation.SUPPLY));
    }

    @Test
    void getOperationHandler_invalidOperation_notOk() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> operationStrategy.getOperationHandler(null));
        assertTrue(exception.getMessage().contains("Operation doesn't exist"));
    }
}
