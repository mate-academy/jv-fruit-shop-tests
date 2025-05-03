package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationHandlerStrategyTest {
    private static OperationHandlerStrategy operationHandlerStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
    private static OperationHandler expected;

    @BeforeAll
    static void beforeAll() {
        expected = new BalanceHandler();
        handlerMap.put(FruitTransaction.Operation.BALANCE, expected);
        operationHandlerStrategy = new OperationHandlerStrategyImpl(handlerMap);
    }

    @Test
    void get_validOperation_Ok() {
        assertEquals(expected, operationHandlerStrategy.get(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void get_nullOperation_NotOk() {
        assertNull(operationHandlerStrategy.get(null));
    }
}
