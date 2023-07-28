package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Operation;
import core.basesyntax.strategy.handlers.BalanceOperationHandler;
import core.basesyntax.strategy.handlers.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private Map<Operation, OperationHandler> operationHandlersMap = new HashMap<>();
    private OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlersMap);

    @Test
    void get_getValue_ok() {
        BalanceOperationHandler balanceHandler = new BalanceOperationHandler();
        operationHandlersMap.put(Operation.BALANCE, balanceHandler);
        OperationHandler actual = operationStrategy.get(Operation.BALANCE);
        OperationHandler expected = balanceHandler;
        assertEquals(expected, actual);
    }

    @Test
    void get_mapDoesNotInitializedCompletely_notOk() {
        assertThrows(RuntimeException.class, () -> {
            operationStrategy.get(Operation.RETURN);
        });
    }

    @Test
    void get_nullInputParameter_notOk() {
        assertThrows(RuntimeException.class, () -> {
            operationStrategy.get(null);
        });
    }
}
