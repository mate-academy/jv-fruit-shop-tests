package core.basesyntax.strategy;

import static org.junit.Assert.assertTrue;

import core.basesyntax.model.Operation;
import core.basesyntax.strategy.handlers.BalanceOperationHandler;
import core.basesyntax.strategy.handlers.OperationHandler;
import core.basesyntax.strategy.handlers.PurchaseOperationHandler;
import core.basesyntax.strategy.handlers.ReturnOperationHandler;
import core.basesyntax.strategy.handlers.SupplyOperationHandler;
import java.util.Map;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy = new OperationStrategyImpl(Map.of(
            Operation.BALANCE, new BalanceOperationHandler(),
            Operation.PURCHASE, new PurchaseOperationHandler(),
            Operation.SUPPLY, new SupplyOperationHandler(),
            Operation.RETURN, new ReturnOperationHandler()
    ));

    @Test
    public void get_BalanceOperationHandler_ok() {
        OperationHandler operationHandler = operationStrategy.get(Operation.BALANCE);
        assertTrue(operationHandler instanceof BalanceOperationHandler);
    }

    @Test
    public void get_PurchaseOperationHandler_ok() {
        OperationHandler operationHandler = operationStrategy.get(Operation.PURCHASE);
        assertTrue(operationHandler instanceof PurchaseOperationHandler);
    }

    @Test
    public void get_SupplyOperationHandler_ok() {
        OperationHandler operationHandler = operationStrategy.get(Operation.SUPPLY);
        assertTrue(operationHandler instanceof SupplyOperationHandler);
    }

    @Test
    public void get_ReturnOperationHandler_ok() {
        OperationHandler operationHandler = operationStrategy.get(Operation.RETURN);
        assertTrue(operationHandler instanceof ReturnOperationHandler);
    }
}
