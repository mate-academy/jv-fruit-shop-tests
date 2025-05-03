package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.BalanceHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationHandlerStrategyTest {
    private static OperationHandlerStrategy operationHandlerStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
    }

    @BeforeEach
    void setUp() {
        operationHandlerStrategy = new OperationHandlerStrategyImpl(operationHandlerMap);
    }

    @Test
    void getOperation_BalanceOperation_ok() {
        Class expected = BalanceHandler.class;
        Class actual = operationHandlerStrategy
                .getOperation(FruitTransaction.Operation.BALANCE).getClass();
        Assertions.assertEquals(expected, actual);
    }
}
