package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private OperationStrategy operationStrategy;
    private OperationHandler balanceHandler;

    @BeforeEach
    void setUp() {
        balanceHandler = new BalanceOperation();
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);

        operationStrategy = new OperationStrategy(handlers);
    }

    @Test
    void get_validOperation_ok() {
        OperationHandler result = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertNotNull(result);
        assertEquals(balanceHandler, result);
    }

    @Test
    void get_unhandledOperation_notOk() {
        assertThrows(RuntimeException.class, () ->
                operationStrategy.get(FruitTransaction.Operation.PURCHASE));
    }
}


