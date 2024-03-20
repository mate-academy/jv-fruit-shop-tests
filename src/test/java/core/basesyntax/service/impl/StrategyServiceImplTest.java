package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Operation;
import core.basesyntax.service.StrategyService;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StrategyServiceImplTest {
    private StrategyService strategyService;
    private Map<Operation, OperationHandler> operationHandlerMap;
    private BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        operationHandlerMap = new HashMap<>();
        strategyService = new StrategyServiceImpl(operationHandlerMap);
    }

    @Test
    void get_validData_ok() {
        OperationHandler expected = operationHandlerMap.put(Operation.BALANCE, balanceOperation);
        assertEquals(expected, strategyService.get(Operation.BALANCE));
    }

    @Test
    void get_nullValue_notOK() {
        RuntimeException expectedMessage = assertThrows(RuntimeException.class,
                () -> strategyService.get(null));
        assertEquals("Operation can't be null", expectedMessage.getMessage());
    }
}
