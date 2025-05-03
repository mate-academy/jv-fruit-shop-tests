package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.OperationType;
import core.basesyntax.service.StrategyOperationService;
import core.basesyntax.service.impl.StrategyOperationServiceImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import exception.OperationException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StrategyOperationServiceImplTest {
    private static StrategyOperationService strategyOperationService;
    private static Map<OperationType, OperationHandler> operationHandlersMap;

    @BeforeAll
    static void beforeAll() {
        operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(OperationType.BALANCE, new BalanceOperation());
        operationHandlersMap.put(OperationType.SUPPLY, new SupplyOperation());
        operationHandlersMap.put(OperationType.PURCHASE, new PurchaseOperation());
        operationHandlersMap.put(OperationType.RETURN, new ReturnOperation());
        strategyOperationService = new StrategyOperationServiceImpl(operationHandlersMap);
    }

    @Test
    void get_returnOperationHandlers_Ok() {
        assertTrue(strategyOperationService.get(OperationType.BALANCE)
                instanceof BalanceOperation);
        assertTrue(strategyOperationService.get(OperationType.SUPPLY)
                instanceof SupplyOperation);
        assertTrue(strategyOperationService.get(OperationType.PURCHASE)
                instanceof PurchaseOperation);
        assertTrue(strategyOperationService.get(OperationType.RETURN)
                instanceof ReturnOperation);
    }

    @Test
    void get_ifOperationTypeDoesNotExist_NotOk() {
        operationHandlersMap.remove(OperationType.BALANCE);
        OperationException exception = assertThrows(OperationException.class,
                () -> strategyOperationService.get(OperationType.BALANCE));
        String expected = "Operation type is not correct: " + OperationType.BALANCE;
        assertEquals(expected, exception.getMessage());
    }
}
