package core.basesyntax.strategy;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.Operation;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private final OperationStrategy strategy = new OperationStrategy();

    @Test
    public void getOperationHandler_withNullOperation_ThrowException() {
        assertThrows(RuntimeException.class, () -> strategy.getOperationHandler(null));
    }

    @Test
    public void getOperationHandler_Ok() {
        OperationStrategy operationStrategy = new OperationStrategy();

        OperationHandler balanceHandler
                = operationStrategy.getOperationHandler(Operation.BALANCE);
        assertTrue(balanceHandler instanceof BalanceOperationHandler);

        OperationHandler purchaseHandler
                = operationStrategy.getOperationHandler(Operation.PURCHASE);
        assertTrue(purchaseHandler instanceof PurchaseHandler);

        OperationHandler returnHandler
                = operationStrategy.getOperationHandler(Operation.RETURN);
        assertTrue(returnHandler instanceof ReturnHandler);

        OperationHandler supplyHandler
                = operationStrategy.getOperationHandler(Operation.SUPPLY);
        assertTrue(supplyHandler instanceof SupplyHandler);
    }
}
