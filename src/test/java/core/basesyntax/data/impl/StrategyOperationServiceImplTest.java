package core.basesyntax.data.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.data.exeption.OperationException;
import core.basesyntax.data.model.OperationType;
import core.basesyntax.data.servise.StrategyOperationService;
import core.basesyntax.data.servise.ipl.StrategyOperationServiceImpl;
import core.basesyntax.data.strategy.BalanceOperationHandler;
import core.basesyntax.data.strategy.OperationHandler;
import core.basesyntax.data.strategy.PurchaseOperationHandler;
import core.basesyntax.data.strategy.ReturnOperationHandler;
import core.basesyntax.data.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StrategyOperationServiceImplTest {
    private static StrategyOperationService strategyOperationService;
    private static Map<OperationType, OperationHandler> operationHandlersMap;

    @BeforeAll
    static void beforeAll() {
        operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(OperationType.BALANCE, new BalanceOperationHandler());
        operationHandlersMap.put(OperationType.SUPPLY, new SupplyOperationHandler());
        operationHandlersMap.put(OperationType.PURCHASE, new PurchaseOperationHandler());
        operationHandlersMap.put(OperationType.RETURN, new ReturnOperationHandler());
        strategyOperationService = new StrategyOperationServiceImpl(operationHandlersMap);
    }

    @Test
    void get_returnOperationHandlers_Ok() {
        Assertions.assertTrue(strategyOperationService.get(OperationType.BALANCE)
                instanceof BalanceOperationHandler);
        Assertions.assertTrue(strategyOperationService.get(OperationType.SUPPLY)
                instanceof SupplyOperationHandler);
        Assertions.assertTrue(strategyOperationService.get(OperationType.PURCHASE)
                instanceof PurchaseOperationHandler);
        Assertions.assertTrue(strategyOperationService.get(OperationType.RETURN)
                instanceof ReturnOperationHandler);
    }

    @Test
    void get_ifOperationTypeDoesNotExist_NotOk() {
        operationHandlersMap.remove(OperationType.BALANCE);
        OperationException exception = assertThrows(OperationException.class,
                () -> strategyOperationService.get(OperationType.BALANCE));
        String expected = "Operation type is not correct: " + OperationType.BALANCE;
        Assertions.assertEquals(expected, exception.getMessage());
    }
}
