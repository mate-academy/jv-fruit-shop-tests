package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private final Map<Operation, OperationHandler> operationHandlers = Map.of(
            Operation.BALANCE, new BalanceOperationImpl(),
            Operation.PURCHASE, new PurchaseOperationImpl(),
            Operation.RETURN, new ReturnOperationImpl(),
            Operation.SUPPLY, new SupplyOperationImpl()
    );
    private final OperationStrategyImpl operationStrategy =
            new OperationStrategyImpl(operationHandlers);

    @Test
    void get_correctGetData_ok() {
        assertTrue(operationStrategy.get(Operation.BALANCE) instanceof BalanceOperationImpl);
        assertTrue(operationStrategy.get(Operation.PURCHASE) instanceof PurchaseOperationImpl);
        assertTrue(operationStrategy.get(Operation.RETURN) instanceof ReturnOperationImpl);
        assertTrue(operationStrategy.get(Operation.SUPPLY) instanceof SupplyOperationImpl);
    }

    @Test
    void get_incorrectGetData_ok() {
        assertFalse(operationStrategy.get(Operation.BALANCE) instanceof PurchaseOperationImpl);
    }
}
