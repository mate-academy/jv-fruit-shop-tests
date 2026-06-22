package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    @BeforeEach
    void setUp() {
        operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationStrategy = new OperationStrategy(operationHandlers);
    }

    @Test
    void get_validOperation_ok() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertInstanceOf(BalanceOperationHandler.class, handler,
                "For BALANCE operation, BalanceOperationHandler should be returned");
    }

    @Test
    void get_invalidOperation_returnsNull() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        assertNull(handler, "Handler for an unknown operation should be null");
    }
}
