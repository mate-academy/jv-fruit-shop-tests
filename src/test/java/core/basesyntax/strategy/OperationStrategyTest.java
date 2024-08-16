package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.Operation;
import core.basesyntax.strategy.impl.BalanceHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseHandlerImpl;
import core.basesyntax.strategy.impl.ReturnHandlerImpl;
import core.basesyntax.strategy.impl.SupplyHandlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        Map<Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceHandlerImpl());
        operationHandlers.put(Operation.PURCHASE, new PurchaseHandlerImpl());
        operationHandlers.put(Operation.RETURN, new ReturnHandlerImpl());
        operationHandlers.put(Operation.SUPPLY, new SupplyHandlerImpl());
        operationStrategy = new OperationStrategy(operationHandlers);
    }

    @Test
    void getOperationHandler_getBalanceOperation_ok() {
        OperationHandler handler = operationStrategy.getHandler(Operation.BALANCE);
        assertTrue(handler instanceof BalanceHandlerImpl);
    }

    @Test
    void getOperationHandler_getPurchaseOperation_ok() {
        OperationHandler handler = operationStrategy.getHandler(Operation.PURCHASE);
        assertTrue(handler instanceof PurchaseHandlerImpl);
    }

    @Test
    void getOperationHandler_getReturnOperation_ok() {
        OperationHandler handler = operationStrategy.getHandler(Operation.RETURN);
        assertTrue(handler instanceof ReturnHandlerImpl);
    }

    @Test
    void getOperationHandler_getSupplyOperation_ok() {
        OperationHandler handler = operationStrategy.getHandler(Operation.SUPPLY);
        assertTrue(handler instanceof SupplyHandlerImpl);
    }
}
