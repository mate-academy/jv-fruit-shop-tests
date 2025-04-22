package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
    }

    @BeforeEach
    void setUp() {
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void get_BalanceOperation_ok() {
        Class expected = BalanceHandler.class;
        Class actual = operationStrategy.get(FruitTransaction.Operation.BALANCE).getClass();
        assertEquals(expected, actual);
    }
}