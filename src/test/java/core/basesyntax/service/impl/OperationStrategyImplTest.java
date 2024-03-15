package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.entity.Operation;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.quantity.handlers.BalanceHandler;
import core.basesyntax.service.quantity.handlers.OperationHandler;
import core.basesyntax.service.quantity.handlers.PurchaseHandler;
import core.basesyntax.service.quantity.handlers.ReturnHandler;
import core.basesyntax.service.quantity.handlers.SupplyHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static final int BALANCE_HANDLER_INDEX = 0;
    private static final int SUPPLY_HANDLER_INDEX = 1;
    private static final int RETURN_HANDLER_INDEX = 2;
    private static final int PURCHASE_HANDLER_INDEX = 3;
    private OperationHandler[] operationHandlers;
    private OperationStrategy operationStrategy;

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
    void operate_returnCorrectHandlers_ok() {
        OperationHandler expectedBalanceHandler = operationHandlers[BALANCE_HANDLER_INDEX];
        OperationHandler actualBalanceHandler = operationStrategy.operate(Operation.BALANCE);
        assertEquals(expectedBalanceHandler, actualBalanceHandler);

        OperationHandler expectedSupplyHandler = operationHandlers[SUPPLY_HANDLER_INDEX];
        OperationHandler actualSupplyHandler = operationStrategy.operate(Operation.SUPPLY);
        assertEquals(expectedSupplyHandler, actualSupplyHandler);

        OperationHandler expectedReturnHandler = operationHandlers[RETURN_HANDLER_INDEX];
        OperationHandler actualReturnHandler = operationStrategy.operate(Operation.RETURN);
        assertEquals(expectedReturnHandler, actualReturnHandler);

        OperationHandler expectedPurchaseHandler = operationHandlers[PURCHASE_HANDLER_INDEX];
        OperationHandler actualPurchaseHandler = operationStrategy.operate(Operation.PURCHASE);
        assertEquals(expectedPurchaseHandler, actualPurchaseHandler);
    }

    @Test
    void operate_NullArgument_notOk() {
        assertThrows(IllegalArgumentException.class, () -> operationStrategy.operate(null));
    }

    @Test
    void operate_unknownOperation_notOk() {
        Operation nonExistingOperation = Operation.UNKNOWN;
        OperationHandler actualHandler = operationStrategy.operate(nonExistingOperation);
        assertNull(actualHandler);
    }
}
