package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.operations.Operation;
import org.junit.jupiter.api.Test;

class StrategyTest {
    private OperationStrategy operationStrategy;
    private OperationHandler expectedHandler;
    private OperationHandler actualHandler;

    @Test
    void getStrategy_Ok() {
        operationStrategy = new OperationStrategyImpl();
        expectedHandler = new BalanceHandler();
        actualHandler = operationStrategy.get(Operation.BALANCE);
        assertEquals(expectedHandler, actualHandler);
    }

    @Test
    void getNullHandler() {
        operationStrategy = new OperationStrategyImpl();
        expectedHandler = new BalanceHandler();
        actualHandler = operationStrategy.get(null);
        assertNull(actualHandler);
    }
}
