package core.basesyntax.service.impl;

import core.basesyntax.entity.Operation;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.quantity.handlers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OperationStrategyImplTest {

    private OperationHandler[] operationHandlers;
    private OperationStrategy operationStrategy;
    private final int BALANCE_HANDLER_INDEX = 0;
    private final int SUPPLY_HANDLER_INDEX = 1;
    private final int RETURN_HANDLER_INDEX = 2;
    private final int PURCHASE_HANDLER_INDEX = 3;

    @BeforeEach
    void setUp() {
        operationHandlers = new OperationHandler[]{
                new BalanceHandler(), new SupplyHandler(),
                new ReturnHandler(),new PurchaseHandler()};
        Map<Operation, OperationHandler> operationHandlerMap = Map.of(
                Operation.BALANCE, operationHandlers[BALANCE_HANDLER_INDEX],
                Operation.SUPPLY, operationHandlers[SUPPLY_HANDLER_INDEX],
                Operation.RETURN, operationHandlers[RETURN_HANDLER_INDEX],
                Operation.PURCHASE, operationHandlers[PURCHASE_HANDLER_INDEX]);
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void operate_ValidOperations_ReturnCorrectHandlers() {
        OperationHandler expectedBalanceHandler = operationHandlers[BALANCE_HANDLER_INDEX];
        OperationHandler expectedSupplyHandler = operationHandlers[SUPPLY_HANDLER_INDEX];
        OperationHandler expectedReturnHandler = operationHandlers[RETURN_HANDLER_INDEX];
        OperationHandler expectedPurchaseHandler = operationHandlers[PURCHASE_HANDLER_INDEX];

        OperationHandler actualBalanceHandler = operationStrategy.operate(Operation.BALANCE);
        OperationHandler actualSupplyHandler = operationStrategy.operate(Operation.SUPPLY);
        OperationHandler actualReturnHandler = operationStrategy.operate(Operation.RETURN);
        OperationHandler actualPurchaseHandler = operationStrategy.operate(Operation.PURCHASE);

        assertEquals(expectedBalanceHandler, actualBalanceHandler);
        assertEquals(expectedSupplyHandler, actualSupplyHandler);
        assertEquals(expectedReturnHandler, actualReturnHandler);
        assertEquals(expectedPurchaseHandler, actualPurchaseHandler);
    }

    @Test
    void operate_NullOperation_ReturnNullHandler() {
        assertThrows(IllegalArgumentException.class, () -> operationStrategy.operate(null));
    }

    @Test
    void operate_NonExistingOperation_ReturnNullHandler() {
        Operation nonExistingOperation = Operation.UNKNOWN;
        OperationHandler actualHandler = operationStrategy.operate(nonExistingOperation);
        assertNull(actualHandler);
    }
}
