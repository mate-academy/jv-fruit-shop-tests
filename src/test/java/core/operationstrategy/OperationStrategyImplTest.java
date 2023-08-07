package core.operationstrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.service.OperationType;
import core.transactions.BalanceOperationHandler;
import core.transactions.OperationHandler;
import core.transactions.PurchaseOperationHandler;
import core.transactions.ReturnOperationHandler;
import core.transactions.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationHandler balanceHandler;
    private static OperationHandler purchaseHandler;
    private static OperationHandler returnHandler;
    private static OperationHandler supplyHandler;

    @BeforeAll
    static void setUp() {
        balanceHandler = new BalanceOperationHandler();
        purchaseHandler = new PurchaseOperationHandler();
        returnHandler = new ReturnOperationHandler();
        supplyHandler = new SupplyOperationHandler();
    }

    @Test
    public void testGetBalanceOperationHandler() {
        Map<OperationType, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.B, balanceHandler);
        operationHandlerMap.put(OperationType.S, supplyHandler);
        operationHandlerMap.put(OperationType.R, returnHandler);
        operationHandlerMap.put(OperationType.P, purchaseHandler);

        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlerMap);

        assertEquals(balanceHandler, operationStrategy.get(OperationType.B));
    }

    @Test
    public void testGetPurchaseOperationHandler() {
        Map<OperationType, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.B, balanceHandler);
        operationHandlerMap.put(OperationType.S, supplyHandler);
        operationHandlerMap.put(OperationType.R, returnHandler);
        operationHandlerMap.put(OperationType.P, purchaseHandler);

        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlerMap);

        assertEquals(purchaseHandler, operationStrategy.get(OperationType.P));
    }

    @Test
    public void testGetReturnOperationHandler() {
        Map<OperationType, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.B, balanceHandler);
        operationHandlerMap.put(OperationType.S, supplyHandler);
        operationHandlerMap.put(OperationType.R, returnHandler);
        operationHandlerMap.put(OperationType.P, purchaseHandler);

        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlerMap);

        assertEquals(returnHandler, operationStrategy.get(OperationType.R));
    }

    @Test
    public void testGetSupplyOperationHandler() {
        Map<OperationType, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.B, balanceHandler);
        operationHandlerMap.put(OperationType.S, supplyHandler);
        operationHandlerMap.put(OperationType.R, returnHandler);
        operationHandlerMap.put(OperationType.P, purchaseHandler);

        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlerMap);

        assertEquals(supplyHandler, operationStrategy.get(OperationType.S));
    }
}
