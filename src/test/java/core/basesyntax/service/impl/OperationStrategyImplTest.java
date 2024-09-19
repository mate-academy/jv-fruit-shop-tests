package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    @BeforeEach
    void setUp() {
        operationHandlers = new HashMap<>();
    }

    @Test
    public void getHandler_balanceOperation() {
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        OperationStrategyImpl strategy = new OperationStrategyImpl(operationHandlers);
        OperationHandler handler = strategy.getHandler(FruitTransaction.Operation.BALANCE);
        assertNotNull(handler);
    }

    @Test
    public void getHandler_supplyOperation() {
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategyImpl strategy = new OperationStrategyImpl(operationHandlers);
        OperationHandler handler = strategy.getHandler(FruitTransaction.Operation.SUPPLY);
        assertNotNull(handler);
    }

    @Test
    public void getHandler_purchaseOperation() {
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        OperationStrategyImpl strategy = new OperationStrategyImpl(operationHandlers);
        OperationHandler handler = strategy.getHandler(FruitTransaction.Operation.PURCHASE);
        assertNotNull(handler);
    }

    @Test
    public void getHandler_returnOperation() {
        OperationStrategyImpl strategy = new OperationStrategyImpl(operationHandlers);
        OperationHandler handler = strategy.getHandler(FruitTransaction.Operation.RETURN);
        assertNotNull(handler);
    }
}
