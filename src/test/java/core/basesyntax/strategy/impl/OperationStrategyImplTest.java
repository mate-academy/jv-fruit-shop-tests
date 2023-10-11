package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private OperationHandler expectedResult;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        expectedResult = new BalanceHandler();
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void getOperationHandler_getsOperationHandler_Ok() {
        assertEquals(expectedResult,
                operationStrategy.getOperationHandler(FruitTransaction.Operation.BALANCE));
    }
}
