package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.BalanceOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OperationStrategyImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> handlers;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        handlers = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void get_existingOperation_ok() {
        OperationHandler handler = new BalanceOperation();
        handlers.put(FruitTransaction.Operation.BALANCE, handler);

        assertEquals(handler,
                operationStrategy.get(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void get_notExistingOperation_returnNull() {
        assertNull(operationStrategy.get(FruitTransaction.Operation.SUPPLY));
    }
}
