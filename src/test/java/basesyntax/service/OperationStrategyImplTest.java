package basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import basesyntax.model.FruitTransaction;
import basesyntax.model.Operation;
import basesyntax.service.handler.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    @Test
    void getHandler_returnsCorrectHandler_Ok() {
        OperationHandler balanceHandler = new TestOperationHandler("b");
        OperationHandler purchaseHandler = new TestOperationHandler("p");
        OperationHandler returnHandler = new TestOperationHandler("r");
        OperationHandler supplyHandler = new TestOperationHandler("s");

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

    private static class TestOperationHandler implements OperationHandler {
        private final String handlerName;

        public TestOperationHandler(String handlerName) {
            this.handlerName = handlerName;
        }

        @Override
        public void handle(FruitTransaction transaction) {
            System.out.println("TestOperationHandler" + handlerName + "handling transaction");
        }
    }
}
