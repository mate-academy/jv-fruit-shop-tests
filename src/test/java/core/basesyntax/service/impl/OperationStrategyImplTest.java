package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy strategy;
    private static final FruitTransaction.Operation OPERATION = FruitTransaction.Operation.BALANCE;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OPERATION, new BalanceOperation());
        strategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void get_operationNull_notOk() {
        String expected = "No OperationHandler found for operation: null";
        assertEquals(expected, assertThrows(RuntimeException.class,
                () -> strategy.get(null)).getMessage());
    }

    @Test
    void get_receiveOperation_Ok() {
        OperationHandler expected = new BalanceOperation();
        OperationHandler actual = strategy.get(OPERATION);
        assertEquals(expected.getClass(),actual.getClass());
    }
}
