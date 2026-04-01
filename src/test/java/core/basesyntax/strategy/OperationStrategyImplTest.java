package core.basesyntax.strategy;

import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;
    private Map<Operation, OperationHandler> operationHandlers;
    private BalanceHandler balanceHandler;
    private AddOperationHandler addOperationHandler;

    @BeforeEach
    void setUp() {
        balanceHandler = new BalanceHandler();
        addOperationHandler = new AddOperationHandler();
        operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.B, balanceHandler);
        operationHandlers.put(Operation.R, addOperationHandler);
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void get_validOutput_ok() {
        Assertions.assertEquals(balanceHandler, operationStrategy.get(Operation.B));
    }

    @Test
    void get_operationDoesntExist_returnNull() {
        Assertions.assertNull(operationStrategy.get(Operation.P));
    }

    @Test
    void get_operationIsNull_returnNull() {
        Assertions.assertNull(operationStrategy.get(null));
    }
}
