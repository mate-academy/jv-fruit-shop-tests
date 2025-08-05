package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.operations.BalanceOperation;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseOperation;
import core.basesyntax.service.operations.ReturnOperation;
import core.basesyntax.service.operations.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void setUp() {
        Map<Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(Operation.SUPPLY, new SupplyOperation());
        operationHandlers.put(Operation.RETURN, new ReturnOperation());

        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    public void getAllOperationsHandlers_Ok() {
        assertInstanceOf(BalanceOperation.class, operationStrategy.get(Operation.BALANCE));
        assertInstanceOf(PurchaseOperation.class, operationStrategy.get(Operation.PURCHASE));
        assertInstanceOf(SupplyOperation.class, operationStrategy.get(Operation.SUPPLY));
        assertInstanceOf(ReturnOperation.class, operationStrategy.get(Operation.RETURN));
    }

    @Test
    public void get_NullOperationHandlers_NotOk() {
        assertThrows(IllegalArgumentException.class, () ->
                operationStrategy.get(null));
    }
}
