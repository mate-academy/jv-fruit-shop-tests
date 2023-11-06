package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.handlers.BalanceOperationHandler;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.handlers.PurchaseOperationHandler;
import core.basesyntax.handlers.ReturnOperationHandler;
import core.basesyntax.handlers.SupplyOperationHandler;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static final String OPERATION_BALANCE_CODE = "b";
    private static final String OPERATION_RETURN_CODE = "r";
    private static final String OPERATION_PURCHASE_CODE = "p";
    private static final String OPERATION_SUPPLY_CODE = "s";
    private static final String OPERATION_UNKNOWN_CODE = "123";
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(Operation.RETURN,
                new ReturnOperationHandler());
        operationHandlerMap.put(Operation.SUPPLY,
                new SupplyOperationHandler());
        operationStrategy = new OperationStrategy(operationHandlerMap);
    }

    @Test
    void findOperationHandler_balance_ok() {
        OperationHandler handler = operationStrategy
                .findOperationHandler(Operation.getOperationByString(OPERATION_BALANCE_CODE));
        assertEquals(BalanceOperationHandler.class, handler.getClass());
    }

    @Test
    void findOperationHandler_Purchase_ok() {
        OperationHandler handler = operationStrategy
                .findOperationHandler(Operation.getOperationByString(OPERATION_PURCHASE_CODE));
        assertEquals(PurchaseOperationHandler.class, handler.getClass());
    }

    @Test
    void findOperationHandler_Return_ok() {
        OperationHandler handler = operationStrategy
                .findOperationHandler(Operation.getOperationByString(OPERATION_RETURN_CODE));
        assertEquals(ReturnOperationHandler.class, handler.getClass());
    }

    @Test
    void findOperationHandler_Supply_ok() {
        OperationHandler handler = operationStrategy
                .findOperationHandler(Operation.getOperationByString(OPERATION_SUPPLY_CODE));
        assertEquals(SupplyOperationHandler.class, handler.getClass());
    }

    @Test
    void findOperationHandler_unknownOperation_notOk() {
        assertThrows(NoSuchElementException.class,
                () -> operationStrategy
                        .findOperationHandler(Operation
                                .getOperationByString(OPERATION_UNKNOWN_CODE)),
                "Should be NoSuchElementException for this Operation code: "
                        + OPERATION_UNKNOWN_CODE);
    }
}
