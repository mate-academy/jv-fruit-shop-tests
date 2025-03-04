package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handlers.BalanceOperationHandlers;
import core.basesyntax.strategy.handlers.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static OperationHandler balanceHandler;

    @BeforeAll
    static void beforeAll() {
        balanceHandler = new BalanceOperationHandlers();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void get_validOperation_Ok() {
        assertEquals(balanceHandler, operationStrategy.get(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void get_unregisteredOperation_notOk() {
        assertNull(operationStrategy.get(FruitTransaction.Operation.PURCHASE));
    }
}
