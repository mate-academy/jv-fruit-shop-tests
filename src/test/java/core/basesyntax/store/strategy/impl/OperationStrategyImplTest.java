package core.basesyntax.store.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.store.model.FruitTransaction;
import core.basesyntax.store.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    private OperationStrategyImpl operationStrategy;

    @BeforeEach
    void setUp() {
        OperationHandler balanceHandler = new BalanceOperation();
        OperationHandler supplyHandler = new SupplyOperation();
        OperationHandler purchaseHandler = new PurchaseOperation();
        OperationHandler returnHandler = new ReturnOperation();

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
        assertTrue(handler instanceof BalanceOperation);

        handler = operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY);
        assertTrue(handler instanceof SupplyOperation);

        handler = operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE);
        assertTrue(handler instanceof PurchaseOperation);

        handler = operationStrategy.getHandler(FruitTransaction.Operation.RETURN);
        assertTrue(handler instanceof ReturnOperation);
    }

    @Test
    void getHandler_invalidOperation_returnDefaultHandler() {
        OperationHandler handler = operationStrategy.getHandler(FruitTransaction.Operation.RETURN);
        assertNotNull(handler);
    }
}
