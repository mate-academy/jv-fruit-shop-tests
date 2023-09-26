package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.OperationType;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationsHandler;
import core.basesyntax.strategy.impl.BalanceOperationsHandler;
import core.basesyntax.strategy.impl.PurchaseOperationsHandler;
import core.basesyntax.strategy.impl.ReturnOperationsHandler;
import core.basesyntax.strategy.impl.SupplyOperationsHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private static FruitService fruitService;

    @BeforeAll
    static void beforeAll() {
        Map<OperationType, OperationsHandler> operationStrategies = new HashMap<>();
        operationStrategies.put(OperationType.BALANCE, new BalanceOperationsHandler());
        operationStrategies.put(OperationType.SUPPLY, new SupplyOperationsHandler());
        operationStrategies.put(OperationType.PURCHASE, new PurchaseOperationsHandler());
        operationStrategies.put(OperationType.RETURN, new ReturnOperationsHandler());
        fruitService = new FruitServiceImpl(operationStrategies);
    }

    @Test
    void getOperationStrategies_successSwitchToBalanceOperationHandler_Ok() {
        String operationCode = "b";
        OperationsHandler operation = fruitService.getOperationStrategies(operationCode);
        String expected = "core.basesyntax.strategy.impl.BalanceOperationsHandler";
        String actual = operation.getClass().getName();
        assertEquals(expected, actual);
    }

    @Test
    void getOperationStrategies_successSwitchToSupplyOperationHandler_Ok() {
        String operationCode = "s";
        OperationsHandler operation = fruitService.getOperationStrategies(operationCode);
        String expected = "core.basesyntax.strategy.impl.SupplyOperationsHandler";
        String actual = operation.getClass().getName();
        assertEquals(expected, actual);
    }

    @Test
    void getOperationStrategies_successSwitchToPurchaseOperationHandler_Ok() {
        String operationCode = "p";
        OperationsHandler operation = fruitService.getOperationStrategies(operationCode);
        String expected = "core.basesyntax.strategy.impl.PurchaseOperationsHandler";
        String actual = operation.getClass().getName();
        assertEquals(expected, actual);
    }

    @Test
    void getOperationStrategies_successSwitchToReturnOperationHandler_Ok() {
        String operationCode = "r";
        OperationsHandler operation = fruitService.getOperationStrategies(operationCode);
        String expected = "core.basesyntax.strategy.impl.ReturnOperationsHandler";
        String actual = operation.getClass().getName();
        assertEquals(expected, actual);
    }

    @Test
    void getOperationStrategies_throwIncorrectOperation_notOk() {
        String operationCode = "f";
        assertThrows(RuntimeException.class,
                () -> fruitService.getOperationStrategies(operationCode));
    }
}
