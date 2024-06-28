package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

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
        assertInstanceOf(BalanceOperationImpl.class, operationStrategy.get(Operation.BALANCE));
        assertInstanceOf(PurchaseOperationImpl.class, operationStrategy.get(Operation.PURCHASE));
        assertInstanceOf(ReturnOperationImpl.class, operationStrategy.get(Operation.RETURN));
        assertInstanceOf(SupplyOperationImpl.class, operationStrategy.get(Operation.SUPPLY));
    }

    @Test
    void get_incorrectGetData_ok() {
        assertFalse(operationStrategy.get(Operation.BALANCE) instanceof PurchaseOperationImpl);
    }
}
