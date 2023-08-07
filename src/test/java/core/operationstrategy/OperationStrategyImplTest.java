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
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    @Test
    public void testGetOperationHandler() {
        OperationHandler balanceHandler = new BalanceOperationHandler();
        OperationHandler purchaseHandler = new PurchaseOperationHandler();
        OperationHandler returnHandler = new ReturnOperationHandler();
        OperationHandler supplyHandler = new SupplyOperationHandler();

        Map<OperationType, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.B, balanceHandler);
        operationHandlerMap.put(OperationType.S, supplyHandler);
        operationHandlerMap.put(OperationType.R, returnHandler);
        operationHandlerMap.put(OperationType.P, purchaseHandler);

        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlerMap);

        assertEquals(balanceHandler, operationStrategy.get(OperationType.B));
        assertEquals(purchaseHandler, operationStrategy.get(OperationType.P));
        assertEquals(returnHandler, operationStrategy.get(OperationType.R));
        assertEquals(supplyHandler, operationStrategy.get(OperationType.S));
    }
}
