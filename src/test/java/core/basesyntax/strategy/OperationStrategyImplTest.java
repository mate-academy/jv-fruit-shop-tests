package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.handler.BalanceHandler;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.PurchaseHandler;
import core.basesyntax.handler.ReturnHandler;
import core.basesyntax.handler.SupplyHandler;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    private OperationStrategyImpl operationStrategy;

    @BeforeEach
    void setUp() {
        OperationHandler balanceHandler = new BalanceHandler();
        OperationHandler supplyHandler = new SupplyHandler();
        OperationHandler purchaseHandler = new PurchaseHandler();
        OperationHandler returnHandler = new ReturnHandler();

        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        handlers.put(FruitTransaction.Operation.SUPPLY, supplyHandler);
        handlers.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);
        handlers.put(FruitTransaction.Operation.RETURN, returnHandler);

        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void getHandler_validOperation_returnCorrectHandler() {
        OperationHandler handler = operationStrategy.getHandler(FruitTransaction.Operation.BALANCE);
        assertTrue(handler instanceof BalanceHandler);

        handler = operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY);
        assertTrue(handler instanceof SupplyHandler);

        handler = operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE);
        assertTrue(handler instanceof PurchaseHandler);

        handler = operationStrategy.getHandler(FruitTransaction.Operation.RETURN);
        assertTrue(handler instanceof ReturnHandler);
    }

    @Test
    void getHandler_invalidOperation_returnNull() {
        OperationHandler handler = operationStrategy.getHandler(null);
        assertNull(handler);
    }
}
