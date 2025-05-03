package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.basesyntax.model.Operation;
import core.basesyntax.basesyntax.strategy.OperationHandler;
import core.basesyntax.basesyntax.strategy.OperationStrategy;
import core.basesyntax.basesyntax.strategy.impl.BalanceHandlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationstrategyTest {
    private static OperationStrategy operationStrategy;
    private static Map<Operation, OperationHandler> operationHandlerMap;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceHandlerImpl());
    }

    @BeforeEach
    void setUp() {
        operationStrategy = new OperationStrategy(operationHandlerMap);
    }

    @Test
    void getHandler_BalanceOperation_ok() {
        Class<?> expected = BalanceHandlerImpl.class;
        OperationHandler handler = operationStrategy.getHandler(Operation.BALANCE);
        Class<?> actual = handler.getClass();
        assertEquals(expected, actual);
    }
}
