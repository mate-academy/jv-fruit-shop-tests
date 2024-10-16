package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
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
        OperationHandler returnHandler = new ReturnOperation();
        OperationHandler purchaseHandler = new PurchaseOperation();

        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, supplyHandler);
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, returnHandler);

        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void testGetHandlerForBalanceOperation() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertNotNull(handler, "Handler for BALANCE should not be null");
        assertTrue(handler instanceof BalanceOperation,
                "Should return instance of BalanceHandler for BALANCE");
    }

    @Test
    void testGetHandlerForSupplyOperation() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertNotNull(handler, "Handler for SUPPLY should not be null");
        assertTrue(handler instanceof SupplyOperation,
                "Should return instance of SupplyHandler for SUPPLY");
    }

    @Test
    void testGetHandlerForPurchaseOperation() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        assertNotNull(handler, "Handler for PURCHASE should not be null");
        assertTrue(handler instanceof PurchaseOperation,
                "Should return instance of PurchaseHandler for PURCHASE");
    }

    @Test
    void testGetHandlerForReturnOperation() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.RETURN);
        assertNotNull(handler, "Handler for RETURN should not be null");
        assertTrue(handler instanceof ReturnOperation,
                "Should return instance of ReturnHandler for RETURN");
    }

    @Test
    void testGetHandlerForUnknownOperation() {
        OperationHandler handler = operationStrategy.get(null);
        assertNull(handler, "Handler for null operation should be null");
    }
}
