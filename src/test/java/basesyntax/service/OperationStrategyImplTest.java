package basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import basesyntax.model.Operation;
import basesyntax.service.handler.BalanceOperation;
import basesyntax.service.handler.OperationHandler;
import basesyntax.service.handler.PurchaseOperation;
import basesyntax.service.handler.ReturnOperation;
import basesyntax.service.handler.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    @Test
    void getHandler_returnsCorrectHandler_Ok() {
        OperationHandler balanceHandler = new BalanceOperation();
        OperationHandler purchaseHandler = new PurchaseOperation();
        OperationHandler returnHandler = new ReturnOperation();
        OperationHandler supplyHandler = new SupplyOperation();

        Map<Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(Operation.BALANCE, balanceHandler);
        handlerMap.put(Operation.PURCHASE, purchaseHandler);
        handlerMap.put(Operation.RETURN, returnHandler);
        handlerMap.put(Operation.SUPPLY, supplyHandler);

        OperationStrategy strategy = new OperationStrategyImpl(handlerMap);

        OperationHandler actualBalance = strategy.getHandler(Operation.BALANCE);
        assertNotNull(actualBalance);
        assertSame(balanceHandler, actualBalance);

        OperationHandler actualPurchase = strategy.getHandler(Operation.PURCHASE);
        assertNotNull(actualPurchase);
        assertSame(purchaseHandler, actualPurchase);

        OperationHandler actualReturn = strategy.getHandler(Operation.RETURN);
        assertNotNull(actualReturn);
        assertSame(returnHandler, actualReturn);

        OperationHandler actualSupply = strategy.getHandler(Operation.SUPPLY);
        assertNotNull(actualSupply);
        assertSame(supplyHandler, actualSupply);
    }
}
